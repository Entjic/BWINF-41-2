package com.franosch.paul;

import com.franosch.paul.model.Graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GraphGeneratorTest {

    @Test
    public void test(){
        GraphGenerator graphGenerator = new GraphGenerator(0, true);

        Graph graph = graphGenerator.generateGraph();

        Assertions.assertEquals(8, graph.getNodes().size());
    }

}
