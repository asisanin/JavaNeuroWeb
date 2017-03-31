package ru.asisanin.java.neuroweb.sigmoid.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;

import static ru.asisanin.java.neuroweb.sigmoid.model.GlobalProperties.*;

/**
 * Created by Asisanin on 16.03.2017.
 */

@XmlRootElement(name = "Neuron")
@XmlType(propOrder = {"weights"})
public class SigmoidNeuron implements Serializable {


    public static final int DEFAULT_SYNAPSES_COUNT = 3;


    private int synapsesCount;

    private ArrayList<Double> weights;

    public SigmoidNeuron() {

        this(DEFAULT_SYNAPSES_COUNT - 1);

    }

    public SigmoidNeuron(int prevLayerSize)
    {
        setSynapsesCount(prevLayerSize + BIAS_NEURON_COUNT);
        weights = new ArrayList<Double>();
        for (int i = 0; i < prevLayerSize + BIAS_NEURON_COUNT; i++)
        {
            weights.add(Math.random() * WEIGHT_INTERVAL + WEIGHT_OFFSET);
        }
    }

    public SigmoidNeuron(ArrayList<Double> weights)
    {
        this.weights = new ArrayList<>(weights);
        setSynapsesCount(this.weights.size());
    }

    public double getOutput(ArrayList<Double> inputValues)
    {
        if (inputValues.size() == (synapsesCount - BIAS_NEURON_COUNT)) {
            double inputSum = 0;
            for (int i = 0; i < inputValues.size(); i++) {
                inputSum += inputValues.get(i) * weights.get(i);
            }
            if (USING_BIAS_NEURON == true) {
                inputSum += weights.get(synapsesCount - 1);
            }
            return activationFunction(inputSum);
        }
        else {
            return 0;
        }
    }

//    public void correctWeights(ArrayList<Double> inputValues)

    @XmlAttribute
    public int getSynapsesCount() {
        return synapsesCount;
    }

    private void setSynapsesCount(int synapsesCount) {
        this.synapsesCount = synapsesCount;
    }

    @XmlElementWrapper(name = "weights")
    @XmlElement(name = "weight")
    public ArrayList<Double> getWeights() {
        return weights;
    }

    public void setWeight(int index, double newValue) {
        weights.set(index, newValue);
    }

    private static double activationFunction(double inputValue)
    {
        return (1.0 / (1.0 + Math.exp(-inputValue)));
    }
}
