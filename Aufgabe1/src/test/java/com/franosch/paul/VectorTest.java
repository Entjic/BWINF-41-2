package com.franosch.paul;

import com.franosch.paul.model.Edge;
import com.franosch.paul.model.Node;
import com.franosch.paul.model.Point;
import com.franosch.paul.model.Vector;
import com.franosch.paul.util.VectorCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VectorTest {

    @Test
    public void simpleVectorDegreeTest() {

        Vector a = new Vector(1.0, 1.0);
        Vector b = new Vector(-1.0, 1.0);

        Assertions.assertEquals(90.0, VectorCalculator.calcDegree(a, b));

    }

    @Test
    public void correctDirectionVectorTest() {

        Node from = new Node(0, new Point(1.0, 1.0));
        Node to = new Node(1, new Point(0.0, 0.0));

        Edge edge = new Edge(from, to, Math.sqrt(2.0));

        Assertions.assertEquals(new Vector(-1.0, -1.0), edge.vector(from));
        Assertions.assertEquals(new Vector(1.0, 1.0), edge.vector(to));

    }

}
