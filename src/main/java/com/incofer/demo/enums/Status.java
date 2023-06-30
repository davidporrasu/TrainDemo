package com.incofer.demo.enums;

public enum Status
{
    MOVE ("moving"),  // active
    ORIGIN( "origin"),
    STOP("stopped"),    // arrive
    OUT_OF_SERVICE("out of service");

    // PURGE
    // RETIRED

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
