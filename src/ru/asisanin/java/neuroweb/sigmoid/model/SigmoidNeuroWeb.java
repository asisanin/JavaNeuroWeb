package ru.asisanin.java.neuroweb.sigmoid.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import static ru.asisanin.java.neuroweb.sigmoid.model.GlobalProperties.*;

/**
 * Created by Asisanin on 16.03.2017.
 */

@XmlRootElement(name = "NeuronWeb")
@XmlType(propOrder = {"sigmoidLayers"})
public class SigmoidNeuroWeb implements Serializable{

    public static final int DEFAULT_INPUT_COUNT = 5;
    public static final ArrayList<Integer> DEFAULT_LAYERS_SIZES = new ArrayList<>(Arrays.asList(4, 3));

    private int inputCount;

    private int outputCount;

    private int layersCount;

    private ArrayList<SigmoidLayer> sigmoidLayers;

    public SigmoidNeuroWeb() {
        this(DEFAULT_INPUT_COUNT, DEFAULT_LAYERS_SIZES);
    }

    public SigmoidNeuroWeb(int inputCount, ArrayList<Integer> layersSizes)
    {
        setInputCount(inputCount);
        sigmoidLayers = new ArrayList<SigmoidLayer>();
        sigmoidLayers.add(new SigmoidLayer(layersSizes.get(0), inputCount));
        for (int i = 1; i < layersSizes.size(); i++) {
            sigmoidLayers.add(new SigmoidLayer(layersSizes.get(i), layersSizes.get(i - 1)));
        }
        setOutputCount(layersSizes.get(layersSizes.size() - 1));
        setLayersCount(layersSizes.size());
    }

    public SigmoidNeuroWeb(ArrayList<SigmoidLayer> sigmoidLayers)
    {
        setLayersCount(sigmoidLayers.size());
        setInputCount((sigmoidLayers.get(0)).getSynapsesCount());
        setOutputCount((sigmoidLayers.get(layersCount - 1)).getLayerSize());
        this.sigmoidLayers = new ArrayList<>(sigmoidLayers);
    }

    public SigmoidNeuroWeb(SigmoidNeuroWeb sigmoidNeuroWeb) {
        this(sigmoidNeuroWeb.getSigmoidLayers());
    }

    public ArrayList<Double> getOutputs(ArrayList<Double> inputValues) throws Throwable
    {
        for (SigmoidLayer sigmoidLayer : sigmoidLayers) {
            inputValues = sigmoidLayer.getOutputs(inputValues);
        }
        return inputValues;
    }

    public ArrayList<ArrayList<Double>> getAllOutputs(ArrayList<Double> inputValues) throws Throwable
    {
        ArrayList<ArrayList<Double>> outputValues = new ArrayList<>();
        for (SigmoidLayer sigmoidLayer : sigmoidLayers) {
            inputValues = sigmoidLayer.getOutputs(inputValues);
            outputValues.add(inputValues);
        }
        return outputValues;
    }

    public SigmoidNeuroWeb educate(TrainingEpoch trainingEpoch, int epochCount) throws Throwable {
        SigmoidNeuroWeb educatedSigmoidNeuroWeb = new SigmoidNeuroWeb(this);
        for (int i = 1; i <= epochCount; i++) {
            educatedSigmoidNeuroWeb = educateEpoch(trainingEpoch, i, epochCount);
        }
        return educatedSigmoidNeuroWeb;
    }

    public SigmoidNeuroWeb educateEpoch(TrainingEpoch trainingEpoch, int epoch, int epochCount) throws Throwable {
        //TODO все сложные вложенные функции выделить в методы, например
        //getSigmoidLayers().get(k + 1).getSigmoidNeurons().get(l).getWeights().get(j)
        //подумать насчет декомпозиции
        //например, для вычисления ошибки нейрона необходимы ошибки следующего слоя и все синапсы, выходящие из данного нейрона
        ArrayList<ArrayList<Double>> allOutputs;
        ArrayList<ArrayList<Double>> allErrors;
        ArrayList<ArrayList<ArrayList<Double>>> allCorrections;
        ArrayList<ArrayList<ArrayList<Double>>> allPrevCorrections = new ArrayList<>();
        ArrayList<ArrayList<Double>> layerCorrections;
        ArrayList<Double> neuronCorrections;
        ArrayList<Double> layerErrors;
        TrainingSet currentTrainingSet;
        SigmoidNeuroWeb educatedSigmoidNeuroWeb = new SigmoidNeuroWeb(this);
        int i, j, k, n, l;
        double multiplier;
        double currentOut;
        double prevIterationWeigtCorrection;
        for ( n = 0; n < trainingEpoch.getIterationCount(); n++) { //идем по обучающим сетам
            currentTrainingSet = trainingEpoch.getTrainingSet(n);
            allOutputs = educatedSigmoidNeuroWeb.getAllOutputs(currentTrainingSet.getInputValues());
            allErrors = new ArrayList<>(allOutputs.size());
            for (k = 0; k < allOutputs.size(); k++) {
                allErrors.add(null);
            }
            //расчет ошибок
            for ( k = allOutputs.size() - 1; k >= 0; k--) { //идем по слоям
                layerErrors = new ArrayList<>();
                for (  j = 0; j < allOutputs.get(k).size(); j++) { //идем по нейронам
                    multiplier = 0;
                    currentOut = allOutputs.get(k).get(j);
                    if (k == allOutputs.size() - 1) { //вычисляем множитель для выходного слоя
                        multiplier = (currentTrainingSet.getSampleOutputValues().get(j)) - currentOut;
                    }
                    else { // для скрытых слоев
                        for (  l = 0; l < allOutputs.get(k + 1).size(); l++) {
                            multiplier += allErrors.get(k + 1).get(l) * educatedSigmoidNeuroWeb.getSigmoidLayers().get(k + 1).getSigmoidNeurons().get(l).getWeights().get(j);
                        }
                    }
                    layerErrors.add(currentOut * (1 - currentOut) * multiplier);
                }
                allErrors. set(k, layerErrors);
            }

            //расчет корректировок весов и их применение
            allCorrections = new ArrayList<>(educatedSigmoidNeuroWeb.getLayersCount());
            for ( k = 0 ; k < educatedSigmoidNeuroWeb.getLayersCount(); k++) { //идем по слоям
                layerCorrections = new ArrayList<>();
                for ( j = 0; j < educatedSigmoidNeuroWeb.getSigmoidLayers().get(k).getLayerSize(); j++) { //идем по нейронам
                    neuronCorrections = new ArrayList<>();
                    for ( i = 0; i < educatedSigmoidNeuroWeb.getSigmoidLayers().get(k).getSigmoidNeurons().get(j).getSynapsesCount(); i++) {
                        if (n == 0) {
                            prevIterationWeigtCorrection = 0;
                        }
                        else {
                            prevIterationWeigtCorrection = allPrevCorrections.get(k).get(j).get(i) * EDUCATION_INERTIAL_COEFF;
                        }
                        if (k == 0) {
                            if (USING_BIAS_NEURON == true && i == educatedSigmoidNeuroWeb.getSigmoidLayers().get(k).getSigmoidNeurons().get(j).getSynapsesCount() - 1) {
                                neuronCorrections.add(educationSpeed(epoch, epochCount) * allErrors.get(k).get(j) + prevIterationWeigtCorrection);
                            }
                            else {
                                neuronCorrections.add(educationSpeed(epoch, epochCount) * allErrors.get(k).get(j) * currentTrainingSet.getInputValues().get(i) + prevIterationWeigtCorrection);
                            }
                        }
                        else {
                            if (USING_BIAS_NEURON == true && i == educatedSigmoidNeuroWeb.getSigmoidLayers().get(k).getSigmoidNeurons().get(j).getSynapsesCount() - 1) {
                                neuronCorrections.add(educationSpeed(epoch, epochCount) * allErrors.get(k).get(j) + prevIterationWeigtCorrection);
                            }
                            else {
                                neuronCorrections.add(educationSpeed(epoch, epochCount) * allErrors.get(k).get(j) * allOutputs.get(k - 1).get(i) + prevIterationWeigtCorrection);
                            }
                        }
                        educatedSigmoidNeuroWeb.getSigmoidLayers().get(k).getSigmoidNeurons().get(j).setWeight(i, educatedSigmoidNeuroWeb.getSigmoidLayers().get(k).getSigmoidNeurons().get(j).getWeights().get(i) + neuronCorrections.get(i));
                    }
                    layerCorrections.add(neuronCorrections);
                }
                allCorrections.add(layerCorrections);
            }
            allPrevCorrections = allCorrections;
        }
        return this;
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
    public int getLayersCount() {
        return layersCount;
    }

    private void setInputCount(int inputCount) {
        this.inputCount = inputCount;
    }

    private void setOutputCount(int outputCount) {
        this.outputCount = outputCount;
    }

    private void setLayersCount(int layersCount) {
        this.layersCount = layersCount;
    }

    @XmlElementWrapper(name = "Layers")
    @XmlElement(name = "Layer")
    public ArrayList<SigmoidLayer> getSigmoidLayers() {
        return sigmoidLayers;
    }
}
