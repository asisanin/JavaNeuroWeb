package ru.asisanin.java.neuroweb.sigmoid.model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

import static ru.asisanin.java.neuroweb.sigmoid.model.GlobalProperties.*;

/**
 * Created by Asisanin on 21.03.2017.
 */
@XmlRootElement(name = "TrainingSet")
@XmlType(propOrder = {"inputValues", "sampleOutputValues"})
public class TrainingSet {

    private int inputCount;

    private int outputCount;

    private ArrayList<Double> inputValues;

    private ArrayList<Double> sampleOutputValues;

    public TrainingSet() {
        inputCount = DEFAULT_STUDY_INPUT_COUNT;
        outputCount = DEFAULT_STUDY_OUTPUT_COUNT;
        inputValues = new ArrayList<>();
        for (int i = 0; i < inputCount ; i++) {
            inputValues.add(Math.random());
        }
        sampleOutputValues = new ArrayList<>();
        for (int i = 0; i < outputCount ; i++) {
            sampleOutputValues.add(Math.random());
        }
    }

    public TrainingSet(ArrayList<Double> inputValues, ArrayList<Double> sampleOutputValues) {
        setInputValues(inputValues);
        setSampleOutputValues(sampleOutputValues);
        setInputCount(inputValues.size());
        setOutputCount(sampleOutputValues.size());
    }

    @XmlAttribute
    private void setInputCount(int inputCount) {
        this.inputCount = inputCount;
    }

    @XmlAttribute
    private void setOutputCount(int outputCount) {
        this.outputCount = outputCount;
    }

    @XmlElementWrapper(name = "inputValues")
    @XmlElement(name = "inputValue")
    private void setInputValues(ArrayList<Double> inputValues) {
        this.inputValues = inputValues;
    }

    @XmlElementWrapper(name = "sampleOutputValues")
    @XmlElement(name = "sampleOutputValue")
    private void setSampleOutputValues(ArrayList<Double> sampleOutputValues) {
        this.sampleOutputValues = sampleOutputValues;
    }

    public int getInputCount() {

        return inputCount;
    }

    public int getOutputCount() {
        return outputCount;
    }

    public ArrayList<Double> getInputValues() {
        return inputValues;
    }

    public ArrayList<Double> getSampleOutputValues() {
        return sampleOutputValues;
    }
}
