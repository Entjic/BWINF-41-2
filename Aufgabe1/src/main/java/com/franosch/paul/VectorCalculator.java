package com.franosch.paul;

import com.franosch.paul.model.Vector;

public class VectorCalculator {

    public static Double calcDegree(Vector a, Vector b) {
        double dotProduct = a.x() * b.x() + a.y() * b.y();
        double lengthA = length(a);
        double lengthB = length(b);
        return Math.toDegrees(Math.acos(dotProduct / (lengthA * lengthB)));
    }

    public static Double length(Vector vector) {
        return Math.sqrt(vector.x() * vector.x() + vector.y() * vector.y());
    }
}
