package ru.asisanin.java.neuroweb.sigmoid;

import ru.asisanin.java.neuroweb.sigmoid.io.XMLIO;
import ru.asisanin.java.neuroweb.sigmoid.model.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Asisanin on 16.03.2017.
 */
public class Main {
    public static void main(String[] args) throws Throwable{
//        SigmoidNeuroWeb sigmoidNeuroWeb = new SigmoidNeuroWeb(5, new ArrayList<Integer>(Arrays.asList(4,3)));
//        SerializeIO.serializeNeuroWeb(sigmoidNeuroWeb);

//        SigmoidNeuroWeb sigmoidNeuroWeb = SerializeIO.deSerializeNeuroWeb("objects.dat");
//        System.out.print("Hello");

//        SigmoidNeuron sigmoidNeuron = new SigmoidNeuron(5);
//        XMLIO.saveObjectToXML(sigmoidNeuron,"TestNeuron.xml");
//
//        SigmoidLayer sigmoidLayer = new SigmoidLayer(5,3);
//        XMLIO.saveObjectToXML(sigmoidLayer,"TestLayer.xml");
//
//        SigmoidNeuroWeb sigmoidNeuroWeb = new SigmoidNeuroWeb(2, new ArrayList<Integer>(Arrays.asList(2,2,1)));
//        XMLIO.saveObjectToXML(sigmoidNeuroWeb,"TestNeuroWeb4.xml");

//        SigmoidNeuron sigmoidNeuron;
//        sigmoidNeuron = (SigmoidNeuron) XMLIO.openObjectFromXML(SigmoidNeuron.class, "TestNeuron.xml");
//        System.out.print("Hello");

//        TrainingEpoch trainingEpoch = new TrainingEpoch();
//        XMLIO.saveObjectToXML(trainingEpoch,"XOR_TrainingEpoch.xml");


        //training
        SigmoidNeuroWeb sigmoidNeuroWeb;
        sigmoidNeuroWeb = (SigmoidNeuroWeb) XMLIO.openObjectFromXML(SigmoidNeuroWeb.class, "data/TestNeuroWeb3.xml");
        TrainingEpoch trainingEpoch;
        trainingEpoch = (TrainingEpoch) XMLIO.openObjectFromXML(TrainingEpoch.class, "data/XOR_TrainingEpoch.xml");

        sigmoidNeuroWeb = sigmoidNeuroWeb.educate(trainingEpoch, 10000);
//        XMLIO.saveObjectToXML(sigmoidNeuroWeb,"OR_EducatedNeuroWeb_100000epoch.xml");
//        System.out.print("Hello");

//        SigmoidNeuroWeb sigmoidNeuroWeb;
//        sigmoidNeuroWeb = (SigmoidNeuroWeb) XMLIO.openObjectFromXML(SigmoidNeuroWeb.class, "XOREducatedNeuroWeb_1000epoch.xml");
////        sigmoidNeuroWeb = (SigmoidNeuroWeb) XMLIO.openObjectFromXML(SigmoidNeuroWeb.class, "TestNeuroWeb2.xml");
        System.out.println("0 XOR 0 = " + sigmoidNeuroWeb.getOutputs(new ArrayList<Double>(Arrays.asList(0.0,0.0))).get(0));
        System.out.println("0 XOR 1 = " + sigmoidNeuroWeb.getOutputs(new ArrayList<Double>(Arrays.asList(0.0,1.0))).get(0));
        System.out.println("1 XOR 0 = " + sigmoidNeuroWeb.getOutputs(new ArrayList<Double>(Arrays.asList(1.0,0.0))).get(0));
        System.out.println("1 XOR 1 = " + sigmoidNeuroWeb.getOutputs(new ArrayList<Double>(Arrays.asList(1.0,1.0))).get(0));

//        SigmoidNeuroWeb sigmoidNeuroWeb;
//        sigmoidNeuroWeb = (SigmoidNeuroWeb) XMLIO.openObjectFromXML(SigmoidNeuroWeb.class, "OREducatedNeuroWeb_1000epoch.xml");
////        sigmoidNeuroWeb = (SigmoidNeuroWeb) XMLIO.openObjectFromXML(SigmoidNeuroWeb.class, "TestNeuroWeb2.xml");
//        System.out.println("0 OR 0 = " + sigmoidNeuroWeb.getOutputs(new ArrayList<Double>(Arrays.asList(0.0,0.0))).get(0));
//        System.out.println("0 OR 1 = " + sigmoidNeuroWeb.getOutputs(new ArrayList<Double>(Arrays.asList(0.0,1.0))).get(0));
//        System.out.println("1 OR 0 = " + sigmoidNeuroWeb.getOutputs(new ArrayList<Double>(Arrays.asList(1.0,0.0))).get(0));
//        System.out.println("1 OR 1 = " + sigmoidNeuroWeb.getOutputs(new ArrayList<Double>(Arrays.asList(1.0,1.0))).get(0));

//        SigmoidNeuroWeb sigmoidNeuroWeb;
//        sigmoidNeuroWeb = (SigmoidNeuroWeb) XMLIO.openObjectFromXML(SigmoidNeuroWeb.class, "EducatedNeuroWeb_4_100epoch.xml");
////        sigmoidNeuroWeb = (SigmoidNeuroWeb) XMLIO.openObjectFromXML(SigmoidNeuroWeb.class, "TestNeuroWeb2.xml");
//        System.out.println("Out = " + sigmoidNeuroWeb.getOutputs(new ArrayList<Double>(Arrays.asList(0.0,0.0))).get(0));
    }
}
