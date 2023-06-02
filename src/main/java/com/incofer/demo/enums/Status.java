package com.incofer.demo.enums;

public enum Status
{
    MOVE ("moving"),
    STOP("stopped"),
    OUT_OF_SERVICE("out of service");
    private String displayText;

    Status( final String pDisplayText )
    {
        this.displayText = pDisplayText;
    }
    public final String getDisplayText()
    {
        return this.displayText;
    }
}
