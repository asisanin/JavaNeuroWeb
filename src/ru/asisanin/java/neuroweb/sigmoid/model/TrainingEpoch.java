package ru.asisanin.java.neuroweb.sigmoid.model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

import static ru.asisanin.java.neuroweb.sigmoid.model.GlobalProperties.*;

/**
 * Created by Asisanin on 21.03.2017.
 */
@XmlRootElement(name = "TrainingEpoch")
@XmlType(propOrder = {"trainingSets"})
public class TrainingEpoch {

    private int inputCount;

    private int outputCount;

    private int iterationCount;

    private ArrayList<TrainingSet> trainingSets;

    public TrainingEpoch() {
        setInputCount(DEFAULT_STUDY_INPUT_COUNT);
        setOutputCount(DEFAULT_STUDY_OUTPUT_COUNT);
        setIterationCount(DEFAULT_STUDY_ITERATION_COUNT);
        trainingSets = new ArrayList<>();
        for (int i = 0; i < iterationCount ; i++) {
            trainingSets.add(new TrainingSet());
        }
    }

    public TrainingEpoch(ArrayList<TrainingSet> trainingSets) {
        int inputCount = trainingSets.get(0).getInputCount();
        int outputCount = trainingSets.get(0).getOutputCount();
        for (TrainingSet trainingSet : trainingSets) {
            if ( (inputCount != trainingSet.getInputCount()) || (outputCount != trainingSet.getOutputCount()) ) {
                throw new Error("Different input/output count in study iterations.");
            }
        }
        setInputCount(inputCount);
        setOutputCount(outputCount);
        setIterationCount(trainingSets.size());
        setTrainingSets(trainingSets);
    }

    @XmlAttribute
    public int getInputCount() {
        return inputCount;
    }

    @XmlAttribute
    public int getOutputCount() {
        return outputCount;
    }

    @XmlAttribute
    public int getIterationCount() {
        return iterationCount;
    }

    @XmlElementWrapper(name = "trainingSets")
    @XmlElement(name = "trainingSet")
    public ArrayList<TrainingSet> getTrainingSets() {
        return trainingSets;
    }

    public TrainingSet getTrainingSet(int index) {
        return trainingSets.get(index);
    }

    private void setInputCount(int inputCount) {
        this.inputCount = inputCount;
    }

    private void setOutputCount(int outputCount) {
        this.outputCount = outputCount;
    }

    private void setIterationCount(int iterationCount) {
        this.iterationCount = iterationCount;
    }

    private void setTrainingSets(ArrayList<TrainingSet> trainingSets) {
        this.trainingSets = trainingSets;
    }
}
