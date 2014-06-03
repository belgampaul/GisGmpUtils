package org.pi2.core.enviroment.windows;

import org.pi2.core.enviroment.CommandExecutor;
import com.google.common.base.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * User: ikka
 * Date: 23.04.2014
 * Time: 12:53
 */
public class WindowsCommandExecutor implements CommandExecutor {
  //logger
  private static final Logger log = LoggerFactory.getLogger(WindowsCommandExecutor.class);

  @Override
  public  Object execute(String command, final Function<String, Boolean> callback) throws IOException {
    ProcessBuilder builder = new ProcessBuilder(
        "cmd.exe", "/c", command);
    builder.redirectErrorStream(true);
    Process p = builder.start();
    final InputStream stream = p.getInputStream();
    new Thread(new Runnable() {
      public void run() {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
          String line;
          while ((line = reader.readLine()) != null) {
            callback.apply(line);
          }
        } catch (IOException e) {
          log.debug("", e);
        }
        System.out.println("finished");
      }
    }).start();

    return true;
  }
}
