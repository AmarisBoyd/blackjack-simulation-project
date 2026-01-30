package com.amaris.blackjack_simulation_project;

public enum BlackjackAction {
    HIT("Hit"),
    STA("Stand"),
    DOH("Double or hit"),
    DOS("Double or stand"),
    SPL("Split"),
    SDS("Split if double after split  is offered otherwise stand"),
    SDH("Split if double after split is offered otherwise hit"),
    SUR("Surrender");

    private final String expansion;


    BlackjackAction(String abbreviation) {
        this.expansion = abbreviation;
    }

    public String getExpansion() {
        return expansion;
    }
}