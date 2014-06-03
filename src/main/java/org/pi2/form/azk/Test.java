package org.pi2.form.azk;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * User: ikka
 * Date: 27.09.13
 * Time: 7:28
 */
public class Test {
  public static void main(String[] args) {
    for (File file : File.listRoots()) {
      System.out.println(file.getAbsolutePath());
    }

    try {
      Desktop.getDesktop().open(new File("D:\\dl\\Mr Nobody (2009)\\Mr.Nobody.2009.Extended.1080p.BluRay.x264.YIFY.mkv"));
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
