package org.aes.compare.customterminal.business.concretes;

import org.aes.compare.uiconsole.model.EnumCMDLineParserResult;

public class ResponseResult {
    private EnumCMDLineParserResult enumParserResult;
    private String response;

    public ResponseResult(EnumCMDLineParserResult enumParserResult, String response) {
        this.enumParserResult = enumParserResult;
        this.response = response;
    }

    public boolean isForIndexValue() {
        if (enumParserResult == EnumCMDLineParserResult.RUN_FOR_INDEX_VALUE) {
            return true;
        }
        return false;
    }
}
