package com.bftcom.apps.gisgmp.gui.panels;

import ru.roskazna.smevunifoservice.UnifoTransferMsg;

/**
 * @author ikka
 * @date: 01.06.2014.
 */
public class ImportChargeArrivedEvent {
  private UnifoTransferMsg unifoTransferMsg;
  public ImportChargeArrivedEvent(UnifoTransferMsg unifoTransferMsg) {
    this.unifoTransferMsg = unifoTransferMsg;
  }


  public UnifoTransferMsg getUnifoTransferMsg() {
    return unifoTransferMsg;
  }
}
