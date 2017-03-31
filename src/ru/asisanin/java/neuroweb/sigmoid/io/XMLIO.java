package ru.asisanin.java.neuroweb.sigmoid.io;

import ru.asisanin.java.neuroweb.sigmoid.model.SigmoidNeuroWeb;
import ru.asisanin.java.neuroweb.sigmoid.model.SigmoidNeuron;

import javax.xml.bind.*;
import java.io.File;

/**
 * Created by Asisanin on 17.03.2017.
 */
public class XMLIO {

    public static void saveObjectToXML(Object object, String path) {
        try {
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            // устанавливаем флаг для читабельного вывода XML в JAXB
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // маршаллинг объекта в файл
            marshaller.marshal(object, new File(path));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static Object openObjectFromXML(Class inClass, String path) {
        try {
            // создаем объект JAXBContext - точку входа для JAXB
            JAXBContext jaxbContext = JAXBContext.newInstance(inClass);
            Unmarshaller un = jaxbContext.createUnmarshaller();

            return  un.unmarshal(new File(path));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
