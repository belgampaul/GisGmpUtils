package com.bftcom.apps.gisgmp.ws.gisgmp.responses;

import com.bftcom.apps.gisgmp.ws.gisgmp.responses.Response;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import ru.gosuslugi.smev.rev111111.*;
import ru.roskazna.smevunifoservice.UnifoTransferMsg;
import ru.roskazna.xsd.doacknowledgmentresponse.DoAcknowledgmentResponseType;
import ru.roskazna.xsd.exportpaymentsresponse.ExportPaymentsResponse;
import ru.roskazna.xsd.exportquittanceresponse.ExportQuittanceResponse;
import ru.roskazna.xsd.organization.BankType;
import ru.roskazna.xsd.paymentinfo.PaymentIdentificationDataType;
import ru.roskazna.xsd.pgu_datarequest.DataRequest;
import ru.roskazna.xsd.postblock.PostBlock;
import ru.roskazna.xsd.ticket.Ticket;
import ru.rosrazna.xsd.smevunifoservice.ExportDataResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

/**
 * Date: 26.03.13
 *
 * @author p.shapoval
 */
public class QuittanceExportResponse extends Response {
  // фабрики объектов
  protected static final ObjectFactory messageObjectFactory = new ObjectFactory();
  protected static final ru.rosrazna.xsd.smevunifoservice.ObjectFactory serviceObjectFactory = new ru.rosrazna.xsd.smevunifoservice.ObjectFactory();
  protected static final ru.roskazna.xsd.pgu_datarequest.ObjectFactory dataRequestObjectFactory = new ru.roskazna.xsd.pgu_datarequest.ObjectFactory();
  protected static final ru.roskazna.xsd.postblock.ObjectFactory postBlockObjectFactory = new ru.roskazna.xsd.postblock.ObjectFactory();
  protected static final ru.roskazna.xsd.pgu_importrequest.ObjectFactory importRequestObjectFactory = new ru.roskazna.xsd.pgu_importrequest.ObjectFactory();
  protected static final ru.roskazna.xsd.charge.ObjectFactory chargeObjectFactory = new ru.roskazna.xsd.charge.ObjectFactory();
  protected static final ru.roskazna.xsd.budgetindex.ObjectFactory budgetIndexObjectFactory = new ru.roskazna.xsd.budgetindex.ObjectFactory();
  protected static final ru.roskazna.xsd.organization.ObjectFactory organizationObjectFactory = new ru.roskazna.xsd.organization.ObjectFactory();
  protected static final ru.roskazna.xsd.bill.ObjectFactory billObjectFactory = new ru.roskazna.xsd.bill.ObjectFactory();

  public static final String EXCHANGE_TYPE = "6";

  //fields
  private UnifoTransferMsg unifoTransferMsg;//полный запрос (все данные оказываются здесь)
  private MessageDataType messageData;      //данные сообщения
  private AppDataType appData;              //Блок сведений
  private PostBlock postBlock;              //Блок почтовой информации
  private DataRequest dataRequest;
  private MessageType message;
  protected Ticket ticket;

  private OrgExternalType sender;
  private OrgExternalType recipient;
  //todo@shapoval разобраться куда ставить originator
  private OrgExternalType originator;

  public QuittanceExportResponse() {
    try {
      formMessageStructure(); //всегда первым вызываем этот метод в конструкторе
      initData();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void initData() {
    postBlock.setTimeStamp(new XMLGregorianCalendarImpl(new GregorianCalendar()));
    //todo убрать хардкод и заменить на генератор
    postBlock.setID(String.valueOf(new Date().getTime()).substring(0, 7));
  }

  private void formMessageStructure() throws IOException {
    Properties prop = new Properties();
    FileInputStream fileInputStream = new FileInputStream(properties.getProperty("quittance.path"));
    prop.loadFromXML(fileInputStream);
    
    unifoTransferMsg = new UnifoTransferMsg();
    appendMessage(unifoTransferMsg);
    messageData = messageObjectFactory.createMessageDataType();

    appData = messageObjectFactory.createAppDataType();
    messageData.setAppData(appData);

    ExportQuittanceResponse.Quittances.Quittance quittance = new ExportQuittanceResponse.Quittances.Quittance();
    String supplierBillID = prop.getProperty("SupplierBillID");
    if (prop.getProperty("IsRevoked") != null) {
      quittance.setIsRevoked(true);
    }
    quittance.setSupplierBillID(supplierBillID);
    quittance.setApplicationID(prop.getProperty("ApplicationID"));
    quittance.setPayeeINN(prop.getProperty("PayeeINN"));
    quittance.setPayeeKPP(prop.getProperty("PayeeKPP"));
    quittance.setKBK(prop.getProperty("KBK"));
    quittance.setOKATO(prop.getProperty("OKATO"));
    quittance.setPayerIdentifier(prop.getProperty("PayerIdentifier"));
    quittance.setBillStatus(prop.getProperty("BillStatus"));
    PaymentIdentificationDataType paymentIdentificationDataType = new PaymentIdentificationDataType();
    BankType bankType = new BankType();
    paymentIdentificationDataType.setBank(bankType);
    bankType.setBIK(prop.getProperty("Bank.BIK"));
    
    paymentIdentificationDataType.setSystemIdentifier(prop.getProperty("SystemIdentifier"));
    quittance.setPaymentIdentificationData(paymentIdentificationDataType);

    ExportQuittanceResponse exportQuittanceResponse = new ExportQuittanceResponse();

    ExportQuittanceResponse.Quittances quittances = new ExportQuittanceResponse.Quittances();
    exportQuittanceResponse.setQuittances(quittances);
    quittances.getQuittance().add(quittance);    
   
    ExportDataResponse exportDataResponse = new ExportDataResponse();
    exportDataResponse.setResponseTemplate(exportQuittanceResponse);

    appData.setExportDataResponse(exportDataResponse);
    postBlock = postBlockObjectFactory.createPostBlock();
    fileInputStream.close();
  }

  private void appendMessage(UnifoTransferMsg unifoTransferMsg) {
    message = messageObjectFactory.createMessageType();
    // Отправитель (Sender)
    sender = messageObjectFactory.createOrgExternalType();
    message.setSender(sender);
    setSenderDetails();
    // Получатель (Recipient)
    recipient = messageObjectFactory.createOrgExternalType();
    message.setRecipient(recipient);
    setRecipientDetails();
    // Тип сообщения (TypeCode)
    message.setTypeCode(TypeCodeType.GFNC);
    // Статус (Status)
    message.setStatus(StatusType.REQUEST);
    // Дата запроса (Date)
    message.setDate(new XMLGregorianCalendarImpl(new GregorianCalendar()));
    // Категория взаимодействия (ExchangeType)
    message.setExchangeType(EXCHANGE_TYPE);
    // Добавляем Message в UnifoTransferMsg
    //request.getContent().add(messageObjectFactory.createMessage(message));  
  }

  private void setRecipientDetails(){
//        recipient.setCode(getRecipientCode());
//        recipient.setName(getRecipientName());
  }

  private void setSenderDetails(){
//        sender.setCode(getSenderCode());
//        sender.setName(getSenderName());
  }
  public void prepareRequest() {
    unifoTransferMsg.setMessageData(messageData);
  }

  public UnifoTransferMsg getResponse() {
    return unifoTransferMsg;
  }
}
