package ru.asisanin.java.neuroweb.sigmoid.io;

import ru.asisanin.java.neuroweb.sigmoid.model.SigmoidNeuroWeb;

import java.io.*;


/**
 * Created by Asisanin on 17.03.2017.
 */
public class SerializeIO {

    public static void serializeNeuroWeb(SigmoidNeuroWeb sigmoidNeuroWeb, String path) throws Throwable{
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(path));
        outputStream.writeObject((Object) sigmoidNeuroWeb);
        outputStream.flush();
        outputStream.close();
    }

    public static SigmoidNeuroWeb deSerializeNeuroWeb(String path) throws Throwable {
        FileInputStream fileInputStream = new FileInputStream(path);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        return (SigmoidNeuroWeb) objectInputStream.readObject();
    }
}
