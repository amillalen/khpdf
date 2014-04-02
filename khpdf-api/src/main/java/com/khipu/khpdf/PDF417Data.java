package com.khipu.khpdf;

import java.io.Serializable;

public class PDF417Data implements Serializable {
    private int codeRows;
    private int codeCols;
    private int errorLevel;
    private int codewordsLen;
    private float quietZoneWidth;
    private float aspectRadio;
    private byte[] data;
    private float absoluteXPosition;
    private float absoluteYPosition;

    public PDF417Data(int codeRows,
                      int codeCols,
                      int errorLevel,
                      int codewordsLen,
                      float quietZoneWidth,
                      float aspectRadio,
                      byte[] data,
                      float absoluteXPosition,
                      float absoluteYPosition) {
        this.codeRows = codeRows;
        this.codeCols = codeCols;
        this.errorLevel = errorLevel;
        this.codewordsLen = codewordsLen;
        this.quietZoneWidth = quietZoneWidth;
        this.aspectRadio = aspectRadio;
        this.data = data;
        this.absoluteXPosition = absoluteXPosition;
        this.absoluteYPosition = absoluteYPosition;
    }

    public float getAbsoluteXPosition() {
        return absoluteXPosition;
    }

    public float getAbsoluteYPosition() {
        return absoluteYPosition;
    }

    public float getAspectRadio() {
        return aspectRadio;
    }

    public byte[] getData() {
        return data;
    }

    public int getCodeRows() {
        return codeRows;
    }

    public int getCodeCols() {
        return codeCols;
    }

    public int getErrorLevel() {
        return errorLevel;
    }

    public int getCodewordsLen() {
        return codewordsLen;
    }

    public float getQuietZoneWidth() {
        return quietZoneWidth;
    }
}
