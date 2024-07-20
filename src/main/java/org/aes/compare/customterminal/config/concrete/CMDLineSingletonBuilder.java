package org.aes.compare.customterminal.config.concrete;

import org.aes.compare.customterminal.config.abstracts.CmdLineConfigFunctions;

public class CMDLineSingletonBuilder {
    private static final CmdLineConfigFunctions cmdLine = new CmdLineConfigFunctionsImpl();

    public static CmdLineConfigFunctions getCmdLine() {
        return cmdLine;
    }
}
