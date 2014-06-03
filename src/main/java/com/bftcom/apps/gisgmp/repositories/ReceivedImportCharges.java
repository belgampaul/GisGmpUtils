package com.bftcom.apps.gisgmp.repositories;

import ru.roskazna.smevunifoservice.UnifoTransferMsg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ikka
 * @date: 01.06.2014.
 */
public class ReceivedImportCharges {
  private final static Object lock = new Object();
  private static Map<Long, UnifoTransferMsg> cntToUnifoTransferMsgMap = new ConcurrentHashMap<>();
  private static AtomicInteger cnt = new AtomicInteger(0);


  public static void put(UnifoTransferMsg msg) {
    synchronized (lock) {
      cntToUnifoTransferMsgMap.put((long) cnt.incrementAndGet(), msg);
    }

  }

  public static List<UnifoTransferMsg> getReceivedImportCharges() {
    return new ArrayList<>(cntToUnifoTransferMsgMap.values());
  }
}
