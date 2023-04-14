package com.franosch.paul;

import com.franosch.paul.eval.AngleCriteriaAsserter;
import com.franosch.paul.eval.PathPrinter;
import com.franosch.paul.eval.SolutionEvaluator;
import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Node;
import com.franosch.paul.solver.*;
import com.franosch.paul.solver.nearest_neighbour.AllStartingNodesNearestNeighbourHeuristic;
import com.franosch.paul.solver.nearest_neighbour.NearestNeighbourHeuristic;
import com.franosch.paul.solver.nearest_neighbour.TraversableOrientedWeightedNeighbourHeuristic;
import com.franosch.paul.solver.nearest_neighbour.next_edge.AngleCriteriaNextEdgeProvider;
import com.franosch.paul.solver.nearest_neighbour.next_edge.TraversableWeightedNextEdgeProvider;
import com.franosch.paul.solver.nearest_neighbour.next_edge.WeightedNextEdgeProvider;
import com.franosch.paul.solver.post_optimization.*;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@RequiredArgsConstructor
public class TravelingSalesmanSolver {

    private final static SolvingStrategy DEFAULT_SOLVING_STRATEGY = SolvingStrategy.NEAREST_NEIGHBOUR_HEURISTIC;
    private final static PostOptimizationStrategy DEFAULT_POST_OPTIMIZATION_STRATEGY = PostOptimizationStrategy.TWO_OPT_HEURISTIC;

    public double solve(int number, boolean useTestResources) {
        return this.solve(number, useTestResources, DEFAULT_SOLVING_STRATEGY);
    }

    public double solve(int number) {
        return this.solve(number, DEFAULT_SOLVING_STRATEGY);
    }

    public double solve(int number, SolvingStrategy solvingStrategy) {
        return this.solve(number, false, solvingStrategy);
    }

    public double solve(int number, boolean testResources, SolvingStrategy solvingStrategy) {
        return this.solve(number, testResources, solvingStrategy, DEFAULT_POST_OPTIMIZATION_STRATEGY);
    }

    public double solveAndOptimizeViaTwoOptPostOptimization(Graph graph, SolvingStrategy solvingStrategy,
                                                            ParameterConfiguration parameterConfiguration) {
        List<Node> path = this.solveAndReturnPath(graph, solvingStrategy);
        PathPrinter pathPrinter = new PathPrinter();
        SolutionEvaluator solutionEvaluator = new SolutionEvaluator();

        System.out.println("naiv path");
        pathPrinter.print(path);
        pathPrinter.printPoints(path);

        Double evalNaive = solutionEvaluator.evaluate(graph, path);



        List<Node> best = Flux.range(1, 50)
                .parallel()
                .runOn(Schedulers.parallel())
                .flatMap(i -> {
                    System.out.print(i + " ");
                    PostOptimization postOptimizer = new TwoOptPostOptimization(solutionEvaluator, parameterConfiguration);
                    return Mono.just(postOptimizer.optimize(graph, path, 60000))
                            .subscribeOn(Schedulers.parallel());
                })
                .sequential()
                .collectList()
                .map(paths -> paths.stream().reduce((a, b) -> Double.compare(solutionEvaluator.evaluate(graph, a),
                        solutionEvaluator.evaluate(graph, b))
                        < 0 ? a : b).orElseThrow())
                .block();

        Double evalOptimized = solutionEvaluator.evaluate(graph, best);


        AngleCriteriaAsserter angleCriteriaAsserter = new AngleCriteriaAsserter();

        if (!angleCriteriaAsserter.fulfillsAngleCriteria(graph, best)) {
            throw new IllegalArgumentException("Angle Criteria integrity is violated");
        }

        return printResult(path, pathPrinter, evalNaive, best, evalOptimized);

    }

    private double printResult(final List<Node> path,
                               final PathPrinter pathPrinter,
                               final Double evalNaive,
                               final List<Node> optimized,
                               final Double evalOptimized) {
        System.out.println("eval naive " + evalNaive);
        System.out.println("eval optimized " + evalOptimized);

        System.out.println("improvement achieved by post optimization algorithm " + (evalNaive - evalOptimized));


        if (evalNaive <= evalOptimized) {
            pathPrinter.print(path);
            pathPrinter.printPoints(path);
            return evalNaive;
        } else {
            pathPrinter.print(optimized);
            pathPrinter.printPoints(optimized);
            return evalOptimized;
        }
    }

    public Graph generateGraphFromFile(int number, boolean testResources) {
        final GraphGenerator graphGenerator = new GraphGenerator(number, testResources);

        return graphGenerator.generateGraph();
    }

    public List<Node> solveAndReturnPath(Graph graph, SolvingStrategy solvingStrategy) {
        long start = System.currentTimeMillis();
        Node first = graph.findStartNode();

        Solver solver = this.createSolver(solvingStrategy);

        List<Node> solve = solver.solve(graph, first);
        long end = System.currentTimeMillis();
        long diff = end - start;
        System.out.println("time to find init solution " + (diff / 1000) + " seconds");
        return solve;
    }

    public double solve(int number, boolean useTestResources, SolvingStrategy solvingStrategy, PostOptimizationStrategy postOptimizationStrategy) {
        Graph graph = this.generateGraphFromFile(number, useTestResources);

        List<Node> solved = this.solveAndReturnPath(graph, solvingStrategy);

        final SolutionEvaluator solutionEvaluator = new SolutionEvaluator();

        Double evalNaive = solutionEvaluator.evaluate(graph, solved);

        System.out.println("found init solution");
        PathPrinter pathPrinter = new PathPrinter();
        pathPrinter.printPoints(solved);
        // this.printInitResult(solved, evalNaive, solvingStrategy);

        PostOptimization postOptimization = this.createPostOptimizer(postOptimizationStrategy);

        List<Node> optimize = postOptimization.optimize(graph, solved, 30000);

        AngleCriteriaAsserter angleCriteriaAsserter = new AngleCriteriaAsserter();

        if (!angleCriteriaAsserter.fulfillsAngleCriteria(graph, optimize)) {
            throw new IllegalArgumentException("Angle Criteria integrity is violated");
        }

        Double evalOptimized = solutionEvaluator.evaluate(graph, optimize);

        // this.printPostOptimizationResult(optimize, evalOptimized, postOptimizationStrategy);

        return printResult(solved, pathPrinter, evalNaive, optimize, evalOptimized);

    }


    private void printInitResult(List<Node> solved, Double length, SolvingStrategy solvingStrategy) {

        System.out.println("Solving strategy " + solvingStrategy.name() + " resulted in solution with length " + length);
        System.out.println("Full solution: ");
        System.out.println(solved);

    }


    private void printPostOptimizationResult(List<Node> solved, Double length, PostOptimizationStrategy postOptimizationStrategy) {

        System.out.println("Post-optimization strategy " + postOptimizationStrategy.name() + " resulted in solution with length " + length);
        System.out.println("Full solution: ");
        System.out.println(solved);

    }

    public Solver createSolver(SolvingStrategy solvingStrategy) {
        switch (solvingStrategy) {
            case NEAREST_NEIGHBOUR_HEURISTIC -> {
                return new NearestNeighbourHeuristic(
                        new WeightedNextEdgeProvider(
                                new AngleCriteriaNextEdgeProvider()
                        )
                );
            }
            case ALL_STARTING_NODES_NEAREST_NEIGHBOUR_HEURISTIC -> {
                return new AllStartingNodesNearestNeighbourHeuristic(new NearestNeighbourHeuristic(
                        new WeightedNextEdgeProvider(
                                new AngleCriteriaNextEdgeProvider())),
                        new SolutionEvaluator());
            }
            case TRAVERSABLE_ORIENTED_WEIGHTED_NEAREST_NEIGHBOUR_HEURISTIC -> {
                return new TraversableOrientedWeightedNeighbourHeuristic(
                        new TraversableWeightedNextEdgeProvider(
                                new AngleCriteriaNextEdgeProvider()));
            }
            default -> throw new IllegalArgumentException();
        }
    }

    private PostOptimization createPostOptimizer(PostOptimizationStrategy postOptimizationStrategy) {
        switch (postOptimizationStrategy) {
            case TWO_OPT_HEURISTIC -> {
                return new TwoOptPostOptimization(new SolutionEvaluator());
            }
            default -> throw new IllegalArgumentException();
        }
    }

}
