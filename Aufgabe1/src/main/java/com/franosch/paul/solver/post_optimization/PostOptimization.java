package com.franosch.paul.solver.post_optimization;

import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Node;

import java.util.List;

/**
 * Used to optimize an already existing tour
 */
public interface PostOptimization {

    List<Node> optimize(Graph graph, List<Node> nodes);

}
