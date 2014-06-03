package com.bftcom.apps.gisgmp.ws.gisgmp;

import be.belgampaul.core.eventbus.EventBusService;
import com.bftcom.apps.gisgmp.config.Config;
import com.bftcom.apps.gisgmp.config.Constants;
import com.bftcom.apps.gisgmp.gui.panels.ImportChargeArrivedEvent;
import com.bftcom.apps.gisgmp.repositories.ReceivedImportCharges;
import com.bftcom.apps.gisgmp.ws.gisgmp.enums.RequestType;
import com.bftcom.apps.gisgmp.ws.gisgmp.events.UnifoTransferMsgReceived;
import com.bftcom.apps.gisgmp.ws.gisgmp.generators.ResponseGenerators;
import com.bftcom.apps.gisgmp.ws.gisgmp.responses.ErrorImportResponse;
import com.bftcom.apps.gisgmp.ws.gisgmp.responses.ImportResponse;
import com.bftcom.apps.gisgmp.ws.gisgmp.validation.Validators;
import org.apache.log4j.Logger;
import org.w3._2004._08.xop.include.Include;
import org.xml.sax.SAXException;
import ru.gosuslugi.smev.rev111111.MessageType;
import ru.gosuslugi.smev.rev111111.StatusType;
import ru.roskazna.smevunifoservice.SmevUnifoService;
import ru.roskazna.smevunifoservice.UnifoTransferMsg;

import javax.jws.HandlerChain;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import java.io.File;
import java.net.URL;

import static com.bftcom.apps.gisgmp.ws.gisgmp.enums.RequestType.IMPORT_CHARGE;
import static com.bftcom.apps.gisgmp.ws.gisgmp.enums.RequestType.determineRequestType;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 */
@WebService(portName = "SmevUnifoServiceSOAP", serviceName = "SmevUnifoService", targetNamespace = "http://roskazna.ru/SmevUnifoService/", endpointInterface = "ru.roskazna.smevunifoservice.SmevUnifoService")
///@SchemaValidation   D:\git\finroot_v2.31\azk2\Server\src\com\bssys\azkserver\smevunifo\azkRisouHandlers.xml
@HandlerChain(file = "/handlers.xml")
public class GisGmpMockWebService extends Service implements SmevUnifoService {


  private final static URL SMEVUNIFOSERVICE_WSDL_LOCATION;
  private final static Logger log = Logger.getLogger(GisGmpMockWebService.class.getName());

  static {
    URL url = null;
    url = GisGmpMockWebService.class.getResource("/schema/SmevUnifoService.wsdl");
    SMEVUNIFOSERVICE_WSDL_LOCATION = url;
  }

  public GisGmpMockWebService(URL wsdlLocation, QName serviceName) {
    super(wsdlLocation, serviceName);
  }

  public GisGmpMockWebService() {
    super(SMEVUNIFOSERVICE_WSDL_LOCATION, new QName("http://roskazna.ru/SmevUnifoService/", "SmevUnifoService"));
  }

  /**
   * @return returns SmevUnifoService
   */
  @WebEndpoint(name = "SmevUnifoServiceSOAP")
  public SmevUnifoService getSmevUnifoServiceSOAP() {
    return super.getPort(new QName("http://roskazna.ru/SmevUnifoService/", "SmevUnifoServiceSOAP"), SmevUnifoService.class);
  }


  @Override
  public UnifoTransferMsg unifoTransferMsg(@WebParam(name = "UnifoTransferMsg", targetNamespace = "http://roskazna.ru/SmevUnifoService/", partName = "inputmsg") UnifoTransferMsg inputmsg) {
    //ContextFactory contextFactory = new ContextFactory();
    log.info("Received new request");
    boolean isValid = true;
    try {
      log.debug("Validating soap packet against xsd schema.");
      Validators.validate(inputmsg);
      log.debug("soap packet is valid against its xsd schema.");
    } catch (JAXBException | SAXException e) {
      e.printStackTrace();
      log.fatal(String.format("received soap packet is invalid"));
      isValid = false;
    }

    RequestType requestType = RequestType.determineRequestType(inputmsg);
    log.debug(String.format("received a new %s request", requestType));
    EventBusService.getInstance().postEvent(new UnifoTransferMsgReceived(isValid, requestType));

    switch (requestType) {
      case IMPORT_CHARGE:
        ReceivedImportCharges.put(inputmsg);
        EventBusService.getInstance().postEvent(new ImportChargeArrivedEvent(inputmsg));
        return ResponseGenerators.getChargeResponse(inputmsg);
      default:
        //do nothing;
    }

    return null;
  }


}