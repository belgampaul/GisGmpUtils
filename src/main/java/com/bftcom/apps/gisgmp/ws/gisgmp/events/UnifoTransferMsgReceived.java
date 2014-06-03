package com.bftcom.apps.gisgmp.ws.gisgmp.events;

import com.bftcom.apps.gisgmp.ws.gisgmp.enums.RequestType;

/**
 * @author ikka
 * @date: 30.05.2014.
 */
public class UnifoTransferMsgReceived {
  private final boolean isValid;
  private final RequestType requestType;

  public UnifoTransferMsgReceived(boolean isValid, RequestType requestType) {
    this.isValid = isValid;
    this.requestType = requestType;
  }

  public boolean isValid() {
    return isValid;
  }

  public RequestType getRequestType() {
    return requestType;
  }
}
