package org.aes.compare.customterminal.business.concretes;

import org.aes.compare.uiconsole.model.EnumCMDLineParserResult;

public class ResponseResult {
    private final EnumCMDLineParserResult enumParserResult;
    private final String response;

    public ResponseResult(EnumCMDLineParserResult enumParserResult, String response) {
        this.enumParserResult = enumParserResult;
        this.response = response;
    }

    public boolean isForIndexValue() {
        return enumParserResult == EnumCMDLineParserResult.RUN_FOR_INDEX_VALUE;
    }
}
