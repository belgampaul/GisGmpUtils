package com.bftcom.apps.gisgmp.ws.gisgmp.validation;

import com.bftcom.apps.gisgmp.ws.gisgmp.GisGmpMockWebService;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import org.w3._2004._08.xop.include.Include;
import org.xml.sax.SAXException;
import ru.roskazna.smevunifoservice.UnifoTransferMsg;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.util.GregorianCalendar;

/**
 * @author ikka
 * @date: 01.06.2014.
 */
public class Validators {
  public static void validate(UnifoTransferMsg unifoTransferMsg) throws JAXBException, SAXException {
    SchemaFactory schemaFactory = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
    JAXBContext jaxbContext = null;

    jaxbContext = JAXBContext.newInstance(Include.class, UnifoTransferMsg.class);
    Schema schema = schemaFactory.newSchema(GisGmpMockWebService.class.getResource("/schema/xsd/request/UnifoTransferMsg.xsd"));
    Marshaller marshaller = jaxbContext.createMarshaller();
    marshaller.setSchema(schema);
    marshaller.marshal(unifoTransferMsg, System.out);
  }
}
