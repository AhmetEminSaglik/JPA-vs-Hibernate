package org.ahmeteminsaglik.uiconsole;

import org.ahmeteminsaglik.uiconsole.business.GlobalProcess;

public class UIConsoleApp {

    public void start() {
        GlobalProcess globalProcess = new GlobalProcess();
        globalProcess.startProcess();
    }

}
