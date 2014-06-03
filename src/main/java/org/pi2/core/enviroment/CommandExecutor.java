package org.pi2.core.enviroment;

import com.google.common.base.Function;

import java.io.IOException;

/**
 * User: ikka
 * Date: 23.04.2014
 * Time: 12:51
 */
public interface CommandExecutor  {
  Object execute(String command, Function<String, Boolean> callback) throws IOException;
}
