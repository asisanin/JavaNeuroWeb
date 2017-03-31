package ru.asisanin.java.neuroweb.sigmoid.model;

/**
 * Created by asisanin on 18.03.17.
 */
public class GlobalProperties {

    public static final double WEIGHT_OFFSET = -2;
    public static final double WEIGHT_INTERVAL = 4;

    public static final boolean USING_BIAS_NEURON = true;
    public static final int BIAS_NEURON_COUNT = (USING_BIAS_NEURON == false) ? 0 : 1;
    public static final int DEFAULT_STUDY_INPUT_COUNT = 2;
    public static final int DEFAULT_STUDY_OUTPUT_COUNT = 1;
    public static final int DEFAULT_STUDY_ITERATION_COUNT = 4;
    public static final double EDUCATION_INERTIAL_COEFF = 0.5;

    public static double educationSpeed(int epoch, int epochCount) {

        return (0.8 / (1 + (Math.log10(epoch) / Math.log10(epochCount))));
//        return (0.8 / epoch);
//        return 0.8;
    }
}
