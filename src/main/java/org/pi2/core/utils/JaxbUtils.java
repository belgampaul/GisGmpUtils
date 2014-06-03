package org.pi2.core.utils;

import org.w3._2004._08.xop.include.Include;
import ru.roskazna.smevunifoservice.UnifoTransferMsg;

import javax.naming.spi.ObjectFactory;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author ikka
 * @date: 01.06.2014.
 */
public class JaxbUtils {
  public static <T> T inputStreamToObject(String xml, Class<T> clazz, Class[] classes) throws JAXBException {
    InputStream stream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
    JAXBContext jaxbContext = JAXBContext.newInstance(classes);
//2. Use JAXBContext instance to create the Unmarshaller.
    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//3. Use the Unmarshaller to unmarshal the XML document to get an instance of JAXBElement.
    T unmarshal = (T) unmarshaller.unmarshal(stream);
//    JAXBElement<T> unmarshalledObject = (JAXBElement<T>) unmarshaller.unmarshal(stream);
//4. Get the instance of the required JAXB Root Class from the JAXBElement.
//    T expenseObj = unmarshalledObject.getValue();

    return unmarshal;
  }
}
