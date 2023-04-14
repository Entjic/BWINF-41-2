package com.franosch.paul;


import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Node;
import com.franosch.paul.solver.Solver;
import com.franosch.paul.solver.SolvingStrategy;
import com.franosch.paul.solver.nearest_neighbour.TraversableOrientedWeightedNeighbourHeuristic;
import com.franosch.paul.solver.nearest_neighbour.next_edge.AngleCriteriaNextEdgeProvider;
import com.franosch.paul.solver.nearest_neighbour.next_edge.TraversableWeightedNextEdgeProvider;
import com.franosch.paul.solver.post_optimization.ParameterConfiguration;
import com.franosch.paul.solver.post_optimization.ParameterTestSuit;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        System.out.println("Bitte wähle den gewünschten Modus:");
        System.out.println("(1) Beispiel aus Datei lösen");
        System.out.println("(2) Gute Parameter finden für ein Beispiel aus einer Datei");

        int mode = readInt(br);

        while (true) {
            switch (mode) {
                case 1 -> {
                    System.out.println("Du befindest dich im Datei-Löser-Modus.");
                    System.out.println("Bitte gib die Nummer der Datei an!");
                    int nr = readInt(br);
                    TravelingSalesmanSolver travelingSalesmanSolver = new TravelingSalesmanSolver();
                    travelingSalesmanSolver.solve(nr);
                }
                case 2 -> {
                    try {
                        System.out.println("Du befindest dich im Parameterermittler-Modus.");
                        System.out.println("Bitte gib die Nummer der Datei an!");
                        int nr = readInt(br);
                        System.out.println("Bitte wähle die Heuristik die für den initialen Pfad verwendet werden soll:");
                        System.out.println("(1) Nearest-Neighbour Heuristik");
                        System.out.println("(2) Nearest-Neighbour Heuristik unter Berücksichtigung der möglichen Touren über einen Knoten");
                        System.out.println("(3) Nearest-Neighbour Heuristik ausgehend von jedem Knoten als Startknoten");
                        int heuristic = readInt(br);

                        TravelingSalesmanSolver travelingSalesmanSolver = new TravelingSalesmanSolver();

                        ParameterTestSuit parameterTestSuit = new ParameterTestSuit();
                        GraphGenerator graphGenerator = new GraphGenerator(nr, false);
                        final Graph graph = graphGenerator.generateGraph();
                        final Node startNode = graphGenerator.findStartNode(graph);
                        Solver solver;
                        switch (heuristic) {
                            case 1 -> solver = travelingSalesmanSolver.createSolver(SolvingStrategy.NEAREST_NEIGHBOUR_HEURISTIC);
                            case 2 -> solver = travelingSalesmanSolver.createSolver(SolvingStrategy.TRAVERSABLE_ORIENTED_WEIGHTED_NEAREST_NEIGHBOUR_HEURISTIC);
                            case 3 -> solver = travelingSalesmanSolver.createSolver(SolvingStrategy.ALL_STARTING_NODES_NEAREST_NEIGHBOUR_HEURISTIC);
                            default -> throw new IllegalArgumentException("Unexpected value " + heuristic);
                        }

                        final List<Node> solve = solver.solve(graph, startNode);
                        final ParameterConfiguration goodParameter =
                                parameterTestSuit.findGoodParameter(graph, solve);

                        System.out.println("BEST PARAMETER " + goodParameter);

                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                }

                default -> throw new IllegalStateException("Unexpected value: " + mode);
            }
        }
    }


    @SneakyThrows
    private static int readInt(BufferedReader br) {
        String modeInput = br.readLine();
        if (!isInt(modeInput)) {
            System.out.println("Expected integer value: " + modeInput + " is not an integer!");
            System.out.println("Try again");
            return readInt(br);
        }
        return Integer.parseInt(modeInput);
    }

    private static boolean isInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
