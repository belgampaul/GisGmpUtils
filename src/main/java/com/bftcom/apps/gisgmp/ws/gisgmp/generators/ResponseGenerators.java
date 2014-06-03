package com.bftcom.apps.gisgmp.ws.gisgmp.generators;

import com.bftcom.apps.gisgmp.config.Config;
import com.bftcom.apps.gisgmp.config.Constants;
import com.bftcom.apps.gisgmp.ws.gisgmp.responses.ErrorImportResponse;
import com.bftcom.apps.gisgmp.ws.gisgmp.responses.ImportResponse;
import ru.gosuslugi.smev.rev111111.MessageType;
import ru.gosuslugi.smev.rev111111.StatusType;
import ru.roskazna.smevunifoservice.UnifoTransferMsg;

/**
 * @author ikka
 * @date: 01.06.2014.
 */
public class ResponseGenerators {
  public static UnifoTransferMsg getChargeResponse(UnifoTransferMsg inputmsg) {

    UnifoTransferMsg unifoTransferMsg = new UnifoTransferMsg();
    ImportResponse importResponse;

    boolean appkey_c_imp_ch_positive = new Boolean(Config.read(Constants.APPKEY_C_IMP_CH_POSITIVE));
    if (appkey_c_imp_ch_positive) {
      importResponse = new ImportResponse();
      unifoTransferMsg.setMessage(getMessage());
      importResponse.prepareRequest();
      return importResponse.getResponse();
    } else {
      importResponse = new ErrorImportResponse();
      unifoTransferMsg.setMessage(getMessage());
      importResponse.prepareRequest();
      return importResponse.getResponse();
    }

    /*
    String billFor = inputmsg.getMessageData().getAppData().getImportData().getImportRequest().getCharge().getBillFor();
    String changeStatus = inputmsg.getMessageData().getAppData().getImportData().getImportRequest().getCharge().getChangeStatus();



    if (changeStatus.equals("1")) {
      if (billFor != null && billFor.contains("error")) {
        importResponse = new ErrorImportResponse();
      } else {
        importResponse = new ImportResponse();
      }
    } else if (changeStatus.equals("3")) {
      if (billFor != null && billFor.equalsIgnoreCase("cancel error")) {
        importResponse = new ErrorImportResponse();
      } else {
        importResponse = new ImportResponse();
      }
    } else if (changeStatus.equals("2")) {
      if (billFor != null && billFor.equalsIgnoreCase("modify error")) {
        importResponse = new ErrorImportResponse();
      } else {
        importResponse = new ImportResponse();
      }
    } else {
      importResponse = new ImportResponse();
    }

    //importResponse = new ErrorImportResponse();
    unifoTransferMsg.setMessage(getMessage());
    importResponse.prepareRequest();
//unifoTransferMsg;
    re1
    return importResponse.getResponse();
    */
  }


  private static MessageType getMessage() {
    MessageType messageType = new MessageType();
    messageType.setStatus(StatusType.RESULT);
    return messageType;
  }


}
