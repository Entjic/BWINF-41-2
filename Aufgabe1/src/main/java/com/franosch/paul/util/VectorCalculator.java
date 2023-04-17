package com.franosch.paul.util;

import com.franosch.paul.model.Vector;

public class VectorCalculator {

    public static boolean matchesAngleCriteria(Vector first, Vector second) {
        double degree = VectorCalculator.calcDegree(first, second);
        return degree >= 90;
    }

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
