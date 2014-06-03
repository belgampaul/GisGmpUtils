package core;

import java.io.IOException;

/**
 * User: ikka
 * Date: 10/29/13
 * Time: 1:24 AM
 */
public class SystemConstants {
  public static final String rootPath;
  static {
    String rootPath1;
    try {
      rootPath1 = new java.io.File(".").getCanonicalPath();
      System.out.println(rootPath1);
    } catch (IOException e) {
      rootPath1 = null;
      e.printStackTrace();
    }
    rootPath = rootPath1;
  }

  public static void main(String[] args) {
    new SystemConstants();
  }
}
