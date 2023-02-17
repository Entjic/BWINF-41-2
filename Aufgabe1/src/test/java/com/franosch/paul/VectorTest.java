package com.franosch.paul;

import com.franosch.paul.model.Vector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VectorTest {

    @Test
    public void simpleVectorDegreeTest() {

        Vector a = new Vector(1.0, 1.0);
        Vector b = new Vector(-1.0, 1.0);

        Assertions.assertEquals(90.0, VectorCalculator.calcDegree(a, b));

    }

}
