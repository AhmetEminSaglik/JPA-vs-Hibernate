package org.ahmeteminsaglik.customterminal.config.concrete;

import org.ahmeteminsaglik.customterminal.config.abstracts.CmdLineConfigFunctions;

public class CMDLineSingletonBuilder {
    private static final CmdLineConfigFunctions cmdLine = new CmdLineConfigFunctionsImpl();

    public static CmdLineConfigFunctions getCmdLine() {
        return cmdLine;
    }
}
