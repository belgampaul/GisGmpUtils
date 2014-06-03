package com.bftcom.apps.gisgmp.ws.gisgmp.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.roskazna.smevunifoservice.UnifoTransferMsg;
import ru.rosrazna.xsd.smevunifoservice.ExportData;

/**
 * @author ikka
 * @date: 30.05.2014.
 */
public enum RequestType {
  IMPORT_CHARGE,
  EXPORT_CHARGE,
  EXPORT_CHARGE_ANNULATION,

  IMPORT_PAYMENT,
  EXPORT_PAYMENT,
  EXPORT_PAYMENT_ANNULATION,

  EXPORT_QUITTANCE;

  //slf4j logger
  private static Logger log = LoggerFactory.getLogger(RequestType.class);

  public static RequestType determineRequestType(UnifoTransferMsg inputmsg) {
    //запросы на экспорт
    if (inputmsg.getMessageData().getAppData().getExportData() != null) {
      return determineExportRequestType(inputmsg);
    } else {
      return determineImportRequestType(inputmsg);
    }
  }

  private static RequestType determineImportRequestType(UnifoTransferMsg inputmsg) {
    if (inputmsg.getMessageData().getAppData().getImportData().getImportRequest().getCharge() != null) {
      return IMPORT_CHARGE;
    } else if (inputmsg.getMessageData().getAppData().getImportData().getImportRequest().getFinalPayment() != null) {
      return IMPORT_PAYMENT;
    }
    log.error("Unknown import type for the received message");
    return null;
  }

  private static RequestType determineExportRequestType(UnifoTransferMsg inputmsg) {
    ExportData exportData = inputmsg.getMessageData().getAppData().getExportData();
    if (exportData.getDataRequest() != null && "PAYMENT".equals(exportData.getDataRequest().getKind())) {
      return EXPORT_PAYMENT;
    } else if (exportData.getDataRequest() != null && "ALLQUITTANCE".equals(exportData.getDataRequest().getKind())) {
      return EXPORT_QUITTANCE;
    }
    log.error("Unknown export type for the received message");
    return null;
  }

}
