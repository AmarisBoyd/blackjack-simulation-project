package com.amaris.blackjack_simulation_project;

public enum BlackjackAction {
    HIT("HIT"),
    STAND("STA"),
    DOUBLE_OR_HIT("DOH"),
    DOUBLE_OR_STAND("DOS"),
    SPLIT("SPL"),
    SPLIT_IF_DAS_IS_OFFERED_STAND("SDS"),
    SPLIT_IF_DAS_IS_OFFERED_HIT("SDH"),
    SURRENDER("SUR");

    private final String abbreviation;

    // Optional: Add a constructor to store a string label for easy printing
    BlackjackAction(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}