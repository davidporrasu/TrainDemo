package com.incofer.demo.enums;

import java.util.Arrays;
import java.util.Optional;

public enum ActivityType
{
    ASSIGNED_WORK("WRK"),
    ASSIST("AST"),
    CHANGE_CREW("CRW"),
    CHANGE_ENGINES("ENG"),
    FUEL_ENGINES("FUL"),
    IMPLIED_REVERSAL("IRV"),
    PASSENGER_STOP("PAS"),
    QUICK_STOP("QKS"),
    REQUIRED_INSPECT("INS"),
    TERMINATE("TRM"),
    TURN_POINT("TP"),
    SCHEDULED_MEET("SCM"),
    USER_MEET("USM"),
    USER_PASS("USP"),
    WAYPOINT("WAY");

    private final String abbreviation;

    private ActivityType(final String abbreviation)
    {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation()
    {
        return this.abbreviation;
    }

    public static Optional<ActivityType> getActivityTypeByAbbreviation(final String abbreviation)
    {
        return Arrays.stream(values()).filter((activityType) -> {
            return activityType.abbreviation.equals(abbreviation);
        }).findFirst();
    }
}