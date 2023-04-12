package com.franosch.paul;

import com.franosch.paul.eval.GraphResolvabilityAsserter;
import com.franosch.paul.model.Graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GraphSolvableTest {

    @Test
    public void testIsSecondSolvable(){
        final GraphGenerator graphGenerator = new GraphGenerator(2, true);
        final GraphResolvabilityAsserter graphResolvabilityAsserter = new GraphResolvabilityAsserter();

        Graph graph = graphGenerator.generateGraph();

        Assertions.assertTrue(graphResolvabilityAsserter.isSolvable(graph));

    }

    @Test
    public void testIsFifthSolvable(){
        final GraphGenerator graphGenerator = new GraphGenerator(5, true);
        final GraphResolvabilityAsserter graphResolvabilityAsserter = new GraphResolvabilityAsserter();

        Graph graph = graphGenerator.generateGraph();

        Assertions.assertTrue(graphResolvabilityAsserter.isSolvable(graph));

    }

}
