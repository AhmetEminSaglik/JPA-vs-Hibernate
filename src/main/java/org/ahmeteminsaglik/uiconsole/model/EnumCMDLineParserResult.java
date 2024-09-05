package org.ahmeteminsaglik.uiconsole.model;

public enum EnumCMDLineParserResult {
    RUN_FOR_CMDLINE(1000),
    RUN_FOR_INDEX_VALUE(1001),
    RUN_FOR_INVALID_COMMAND(1002),
    CMD_PROCESS_COMPLETED(1200),
    CMD_CANCEL_PROCESS(1400);

    int id;

    EnumCMDLineParserResult(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    /*RUN_FOR_CMDLINE(0),
    RUN_FOR_INDEX_VALUE(1),
    RUN_FOR_INVALID_COMMAND(2),
    PROCESS_CANCELLED(400);

    int id;

    EnumCMDLineParserResult(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
*/
}
