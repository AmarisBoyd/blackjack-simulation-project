package com.amaris.blackjack_simulation_project.model;

public enum BlackjackAction {
    HIT("Hit"),
    STA("Stand"),
    DOH("Double or hit"),
    DOS("Double or stand"),
    SPL("Split"),
    SDS("Split if double after split  is offered otherwise stand"),
    SDH("Split if double after split is offered otherwise hit"),
    SUR("Surrender"),
    DEA("Dead Hand");// Only should be used for cleanup of a hand that causes an error

    private final String expansion;


    BlackjackAction(String expansion) {
        this.expansion = expansion;
    }

    public String getExpansion() {
        return expansion;
    }
}