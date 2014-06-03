/*
 * Copyright 2000-2012 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.openapi.util;

@SuppressWarnings({"HardCodedStringLiteral", "UtilityClassWithoutPrivateConstructor", "UnusedDeclaration"})
public class SystemInfo extends SystemInfoRt {
  public static final String OS_NAME = SystemInfoRt.OS_NAME;
  public static final String OS_VERSION = SystemInfoRt.OS_VERSION;
  public static final String OS_ARCH = System.getProperty("os.arch");
  public static final String JAVA_VERSION = System.getProperty("java.version");
  public static final String JAVA_RUNTIME_VERSION = System.getProperty("java.runtime.version");
  public static final String ARCH_DATA_MODEL = System.getProperty("sun.arch.data.model");
  public static final String SUN_DESKTOP = System.getProperty("sun.desktop", "");

  public static final boolean isWindows = SystemInfoRt.isWindows;
  public static final boolean isMac = SystemInfoRt.isMac;
  public static final boolean isOS2 = SystemInfoRt.isOS2;
  public static final boolean isLinux = SystemInfoRt.isLinux;
  public static final boolean isFreeBSD = _OS_NAME.startsWith("freebsd");
  public static final boolean isSolaris = _OS_NAME.startsWith("sunos");
  public static final boolean isUnix = SystemInfoRt.isUnix;

//  // version numbers from http://msdn.microsoft.com/en-us/library/windows/desktop/ms724832.aspx
//  public static final boolean isWin2kOrNewer = isWindows && isOsVersionAtLeast("5.0");
//  public static final boolean isWinVistaOrNewer = isWindows && isOsVersionAtLeast("6.0");
//  public static final boolean isWin7OrNewer = isWindows && isOsVersionAtLeast("6.1");
  /** @deprecated unsupported (to remove in IDEA 13) */
  public static final boolean isWindows9x = _OS_NAME.startsWith("windows 9") || _OS_NAME.startsWith("windows me");
  /** @deprecated unsupported (to remove in IDEA 13) */
  public static final boolean isWindowsNT = _OS_NAME.startsWith("windows nt");
  /** @deprecated use {@linkplain #OS_VERSION} (to remove in IDEA 13) */
  public static final boolean isWindows2000 = _OS_NAME.startsWith("windows 2000");
  /** @deprecated use {@linkplain #OS_VERSION} (to remove in IDEA 13) */
  public static final boolean isWindows2003 = _OS_NAME.startsWith("windows 2003");
  /** @deprecated use {@linkplain #OS_VERSION} (to remove in IDEA 13) */
  public static final boolean isWindowsXP = _OS_NAME.startsWith("windows xp");
  /** @deprecated use {@linkplain #OS_VERSION} (to remove in IDEA 13) */
  public static final boolean isWindowsVista = _OS_NAME.startsWith("windows vista");
  /** @deprecated use {@linkplain #OS_VERSION} (to remove in IDEA 13) */
  public static final boolean isWindows7 = _OS_NAME.startsWith("windows 7");

  /** @deprecated inaccurate (to remove in IDEA 13) */
  public static final boolean isKDE = SUN_DESKTOP.toLowerCase().contains("kde");
  /** @deprecated inaccurate (to remove in IDEA 13) */
  public static final boolean isGnome = SUN_DESKTOP.toLowerCase().contains("gnome");

  public static final boolean isXWindow = isUnix && !isMac;

  public static final boolean isMacSystemMenu = isMac && "true".equals(System.getProperty("apple.laf.useScreenMenuBar"));

  public static final boolean isFileSystemCaseSensitive = SystemInfoRt.isFileSystemCaseSensitive;
//  public static final boolean areSymLinksSupported = isUnix || isWinVistaOrNewer;
//
  public static final boolean is32Bit = ARCH_DATA_MODEL == null || ARCH_DATA_MODEL.equals("32");
  public static final boolean is64Bit = !is32Bit;
  public static final boolean isAMD64 = "amd64".equals(OS_ARCH);
  public static final boolean isMacIntel64 = isMac && "x86_64".equals(OS_ARCH);





  /**
   * Whether IDEA is running under MacOS X version 10.4 or later.
   *
   * @since 5.0.2
   */
  public static final boolean isMacOSTiger = isTiger();

  /**
   * Whether IDEA is running under MacOS X on an Intel Machine
   *
   * @since 5.0.2
   */
  public static final boolean isIntelMac = isIntelMac();

  /**
   * Running under MacOS X version 10.5 or later;
   *
   * @since 7.0.2
   */
  public static final boolean isMacOSLeopard = isLeopard();

  /**
   * Running under MacOS X version 10.6 or later;
   *
   * @since 9.0
   */
  public static final boolean isMacOSSnowLeopard = isSnowLeopard();

  /**
   * Running under MacOS X version 10.7 or later;
   *
   * @since 11.0
   */
  public static final boolean isMacOSLion = isLion();

  /**
   * Running under MacOS X version 10.8 or later;
   *
   * @since 11.1
   */
  public static final boolean isMacOSMountainLion = isMountainLion();

  /** @deprecated use {@linkplain #isXWindow} (to remove in IDEA 13) */
  public static boolean X11PasteEnabledSystem = isXWindow;

  private static boolean isIntelMac() {
    return isMac && "i386".equals(OS_ARCH);
  }

  private static boolean isTiger() {
    return isMac &&
        !OS_VERSION.startsWith("10.0") &&
        !OS_VERSION.startsWith("10.1") &&
        !OS_VERSION.startsWith("10.2") &&
        !OS_VERSION.startsWith("10.3");
  }

  private static boolean isLeopard() {
    return isMac && isTiger() && !OS_VERSION.startsWith("10.4");
  }

  private static boolean isSnowLeopard() {
    return isMac && isLeopard() && !OS_VERSION.startsWith("10.5");
  }

  private static boolean isLion() {
    return isMac && isSnowLeopard() && !OS_VERSION.startsWith("10.6");
  }

  private static boolean isMountainLion() {
    return isMac && isLion() && !OS_VERSION.startsWith("10.7");
  }



  private static int normalize(int number) {
    return number > 9 ? 9 : number;
  }

  private static int toInt(String string) {
    try {
      return Integer.valueOf(string);
    }
    catch (NumberFormatException e) {
      return 0;
    }
  }
}
