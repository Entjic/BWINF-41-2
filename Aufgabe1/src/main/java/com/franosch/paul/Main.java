package com.franosch.paul;


import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Node;
import com.franosch.paul.solver.nearest_neighbour.NearestNeighbourHeuristic;
import com.franosch.paul.solver.nearest_neighbour.next_edge.AngleCriteriaNextEdgeProvider;
import com.franosch.paul.solver.nearest_neighbour.next_edge.WeightedNextEdgeProvider;
import com.franosch.paul.solver.post_optimization.ParameterConfiguration;
import com.franosch.paul.solver.post_optimization.ParameterTestSuit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("Input mode");
            String input = br.readLine();

            try {
                System.out.println("Hello World from Aufgabe1");
                ParameterTestSuit parameterTestSuit = new ParameterTestSuit();
                GraphGenerator graphGenerator = new GraphGenerator(6, false);
                final Graph graph = graphGenerator.generateGraph();
                final Node startNode = graphGenerator.findStartNode(graph);
                NearestNeighbourHeuristic nearestNeighbourHeuristic = new NearestNeighbourHeuristic(
                        new WeightedNextEdgeProvider(
                        new AngleCriteriaNextEdgeProvider()));
                final List<Node> solve = nearestNeighbourHeuristic.solve(graph, startNode);
                final ParameterConfiguration goodParameter =
                        parameterTestSuit.findGoodParameter(graph, solve);

                System.out.println("BEST PARAMETER " + goodParameter);

            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }

}
