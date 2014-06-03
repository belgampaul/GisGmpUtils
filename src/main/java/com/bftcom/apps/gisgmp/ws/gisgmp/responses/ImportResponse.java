package com.bftcom.apps.gisgmp.ws.gisgmp.responses;


import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import ru.gosuslugi.smev.rev111111.*;
import ru.roskazna.smevunifoservice.UnifoTransferMsg;
import ru.roskazna.xsd.pgu_datarequest.DataRequest;
import ru.roskazna.xsd.postblock.PostBlock;
import ru.roskazna.xsd.ticket.Ticket;
import ru.rosrazna.xsd.smevunifoservice.ImportDataResponse;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Базовый класс для запросов СМЭВ УНИФО
 * <p/>
 * Date: 11.01.13
 *
 * @author p.shapoval
 */
public class ImportResponse {
    //private static final Logger log = Logger.getLogger(com.bssys.azkserver.income.gisgmpexchange.requests.Response.class);

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



    public ImportResponse() {
        formMessageStructure(); //всегда первым вызываем этот метод в конструкторе
        initData();
    }

    /**
     * Формирует структуру сообщения
     */
    private void formMessageStructure() {
        unifoTransferMsg = new UnifoTransferMsg();
        appendMessage(unifoTransferMsg);
        messageData = messageObjectFactory.createMessageDataType();

        appData = messageObjectFactory.createAppDataType();
        messageData.setAppData(appData);
        ImportDataResponse importDataResponse = new ImportDataResponse();
        ticket = new Ticket();
        importDataResponse.setTicket(ticket);
        appData.setImportDataResponse(importDataResponse);

        postBlock = postBlockObjectFactory.createPostBlock();
    }

    /**
     * инициализирует данные запроса, которые должны быть инициализированы при создании сообщения
     */
    private void initData() {
        postBlock.setTimeStamp(new XMLGregorianCalendarImpl(new GregorianCalendar()));
        //todo убрать хардкод и заменить на генератор
        postBlock.setID(String.valueOf(new Date().getTime()).substring(0, 7));
    }

    public OrgExternalType getSender() {
        return sender;
    }

    public OrgExternalType getRecipient() {
        return recipient;
    }

    protected MessageDataType getMessageData() {
        return messageData;
    }

    public UnifoTransferMsg getResponse() {
        return unifoTransferMsg;
    }

    public void prepareRequest() {
        unifoTransferMsg.setMessageData(messageData);
    }

    /**
     *идентификатор запроса
     */
    public void setPostBlockId(String value) {
        //todo@shapoval Генерить произвольный уникальный номер запроса, равный 7 символам (должны использоваться только цифры при генерации номера)
        postBlock.setID(value);
    }

    /**
     * Формирует элемент Message и добавляет его в UnifoTransferMsg
     *
     * @param request UnifoTransferMsg
     */
    protected void appendMessage(UnifoTransferMsg request) {
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

//    abstract public String getSenderCode();
//    abstract public String getSenderName();
//
//    abstract public String getRecipientCode();
//    abstract public String getRecipientName();

    private void setRecipientDetails(){
//        recipient.setCode(getRecipientCode());
//        recipient.setName(getRecipientName());
    }

    private void setSenderDetails(){
//        sender.setCode(getSenderCode());
//        sender.setName(getSenderName());
    }

    protected PostBlock getPostBlock() {
        return postBlock;
    }

    protected DataRequest getDataRequest() {
        if (dataRequest == null) {
            dataRequest = dataRequestObjectFactory.createDataRequest();
            dataRequest.setSupplierBillIDs(dataRequestObjectFactory.createDataRequestSupplierBillIDs());
        }
        return dataRequest;
    }

    public void setSenderIdentifier(String id){
        //postBlock.setSenderIdentifier(getSenderIdentifier());
    }

    //protected abstract String getSenderIdentifier();

    /**
     * отладочный код
     */
//    public void debugRequest() {
//        for (JAXBElement<?> el : unifoTransferMsg.getContent()) {
//            Document doc = new DocumentImpl();
//            try {
//                JAXBContext.newInstance(el.getDeclaredType()).createMarshaller().marshal(el, doc);
//                if (log.isDebugEnabled()) {
//                    log.debug("-----BEGIN GIS GMP REQUEST-----");
//                    log.debug(XML.getFormattedXMLText(doc.getDocumentElement()));
//                    log.debug("----END GIS GMP REQUEST ----");
//                }
//            } catch (JAXBException e) {
//                log.debug(null, e);
//            }
//        }
//    }
}
