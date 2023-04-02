package com.franosch.paul.solver.post_optimization;

import java.io.Serializable;

public record ParameterConfiguration(double startingTemperature, double temperaturModifier,
                                     int improvingIterationsUntilCooling,
                                     int iterationsUntilCooling) implements Serializable {

}
