package com.franosch.paul.solver;

import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Node;

import java.util.List;

public interface Solver {

    List<Node> solve(Graph graph, Node start);

    SolvingStrategy getSolvingStrategy();


}
