package org.aes.compare.uiconsole.model;

public enum EnumCMDLineParserResult {
    RUN_FOR_CMDLINE(0),
    RUN_FOR_INDEX_VALUE(1);

    int id;

    private EnumCMDLineParserResult(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
