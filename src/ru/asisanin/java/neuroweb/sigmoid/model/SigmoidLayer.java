package ru.asisanin.java.neuroweb.sigmoid.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import static ru.asisanin.java.neuroweb.sigmoid.model.GlobalProperties.*;

/**
 * Created by Asisanin on 16.03.2017.
 */
@XmlRootElement(name = "NeuronLayer")
@XmlType(propOrder = {"sigmoidNeurons"})
public class SigmoidLayer implements Serializable {

    public static int DEFAULT_LAYER_SIZE = 5;
    public static int DEFAULT_PREV_LAYER_SIZE = 3;

    private int layerSize; //размер слоя не учитывает нейрон смещения

    private int synapsesCount; //сюда входит синапс от нейрона смещения

    private ArrayList<SigmoidNeuron> sigmoidNeurons;

    public SigmoidLayer() {
            this(DEFAULT_LAYER_SIZE, DEFAULT_PREV_LAYER_SIZE);
    }

    public SigmoidLayer(int layerSize, int prevLayerSize)
    {
        setLayerSize(layerSize);
        setSynapsesCount(prevLayerSize + BIAS_NEURON_COUNT);
        sigmoidNeurons = new ArrayList<SigmoidNeuron>();
        for (int i = 0; i < layerSize; i++)
        {
            sigmoidNeurons.add(new SigmoidNeuron(prevLayerSize));
        }
    }

    public SigmoidLayer(ArrayList<SigmoidNeuron> sigmoidNeurons)
    {
        int synapsesCount = sigmoidNeurons.get(0).getSynapsesCount();
        for (SigmoidNeuron sigmoidNeuron : sigmoidNeurons) {
            if (synapsesCount != sigmoidNeuron.getSynapsesCount()) {
                throw new Error("Different synapses count in neurons of one layer.");
            }
        }
        setLayerSize(sigmoidNeurons.size());
        setSynapsesCount(synapsesCount);
        this.sigmoidNeurons = new ArrayList<>(sigmoidNeurons);
    }

    public double getOutput(int neuronNumber, ArrayList<Double> inputValues)
    {
        return (sigmoidNeurons.get(neuronNumber)).getOutput(inputValues);
    }

    public ArrayList<Double> getOutputs(ArrayList<Double> inputValues) throws Throwable
    {
        if (synapsesCount != inputValues.size() + BIAS_NEURON_COUNT) {
            throw new Error("Layer size and number of input values are differs.");
        }
        ArrayList<Double> outputValues = new ArrayList<Double>();
        for (int i = 0; i < layerSize; i++) {
            outputValues.add(getOutput(i,inputValues));
        }
        return outputValues;
    }

    @XmlAttribute
    public int getLayerSize() {
        return layerSize;
    }

    @XmlAttribute
    public int getSynapsesCount() {
        return synapsesCount;
    }

    private void setLayerSize(int layerSize) {
        this.layerSize = layerSize;
    }

    private void setSynapsesCount(int synapsesCount) {
        this.synapsesCount = synapsesCount;
    }

    @XmlElementWrapper(name = "Neurons")
    @XmlElement(name = "Neuron")
    public ArrayList<SigmoidNeuron> getSigmoidNeurons() {
        return sigmoidNeurons;
    }

}
