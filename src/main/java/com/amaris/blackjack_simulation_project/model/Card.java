package com.amaris.blackjack_simulation_project.model;

/**
 * Represents a single playing card for use in the simulation
 * <p>
 * A card is defined by its suit and rank and has a numeric value associated with its rank. The suit and rank are also
 * combined into a {@code suitAndRank} string for display purposes
 **/
@SuppressWarnings("LombokSetterMayBeUsed")
public class Card {
    /**The suit of the card
     *
     */
    String suit = "";
    /**The rank of the card
     *
     */
    String rank = "";
    /**String to hold suit and rank of card
     * */
    String suitAndRank;
    /**Integer to hold the value associated with the rank

     */
    private int value;

    /**
     Constructs an empty {@code Card} with strings initialized empty and value set at 0

     */
    public Card() {
        this.suit = "";
        this.rank = "";
        this.value = 0;
        this.suitAndRank = "";
    }


    /**Constructor to initialize suitAndRank and value
     @param suitAndRank The combined suit and rank of the card
     @param value  The numerical value of the card
     */
    public Card(String suitAndRank, int value) {
        //TODO these constructors need to be input validated
        this.suitAndRank = suitAndRank;
        this.value = value;
    }

    /**Constructor for tests when cards are created for just their value
     *
     * @param value The numerical value of the card
     */
    public Card(int value) {
        //Joker is often seen as wildcard, and it looks better when printing than having a null rank
        this.rank = "Joker";
        this.value = value;
    }

    /**
     * Returns a short abbreviation representing this card, combining its value
     * (or face-card letter) with the first letter of its suit-and-rank string.
     * For example, a 10-value Jack of Hearts might return {@code "JH"}, while
     * a 5 of Clubs would return {@code "5C"}.
     * @return  the abbreviation of the card, or an empty string if the suit,rank,or value has not been set
     */
    public String getAbbrev() {
        if (this.suit.isEmpty() || this.rank.isEmpty() || this.value == 0)
            return "";
        String abbrev = "";
        if (this.value != 10) {
            abbrev += this.value;
        } else switch (this.rank.toLowerCase()) {
            case "jack":
                abbrev += "J";
                break;
            case "queen":
                abbrev += "Q";
                break;
            case "king":
                abbrev += "K";
                break;
            case "ace":
                abbrev += "A";
                break;
            case "ten":
                abbrev += "10";
                break;
            default:
                abbrev += "?";
                break;

        }
        abbrev += (this.suitAndRank.charAt(0));
        return abbrev.toUpperCase();
    }

    /**Constructs a card with a given suit, rank, value
     @param suit The suit of a card (Eg Heart, Spade,Diamond, Club)
     @param rank The rank of a card (Eg a number 2-10, Ace, King...)
     @param value the numerical value of the rank of a card
      * */
    public Card(String suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
        this.suitAndRank = suit + " " + rank;
    }

    /**
     * Constructor to copy an existing cards value to a new card
     * @param card Object representing an already existing card
     */
    public Card(Card card) {
        this.suit = card.getSuit();
        this.rank = card.getRank();
        this.value = card.getValue();
        this.suitAndRank = card.getSuitAndRank();
    }

    /**Getter method for Rank and suit
     *
     * @return Value
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Setter to set a new value to a card
     * @param value the value to set a card to
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Getter for the combined suit and rank of the card
     * @return The combined suit and rank of the card
     */
    public String getSuitAndRank() {
        return this.suitAndRank;
    }

    /**
     * Getter for the rank of the card
     * @return The rank of the card
     */
    public String getRank() {
        return rank;
    }

    /**
     * Setter for the rank of a card
     * @param rank The rank to set the card to
     */
    public void setRank(String rank) {
        this.rank = rank;
    }

    /**
     * Returns the current suit of the card
     * @return the current suit of the card
     */
    public String getSuit() {
        return suit;
    }

    /**
     * Sets the card to a new suit
     * @param suit the new suit to set the card to
     */
    public void setSuit(String suit) {
        this.suit = suit;
    }

    /**toString method for easy printing
     *
     * @return A string representing the card
     */
    @Override
    public String toString() {
        this.suitAndRank = this.suit + " " + this.rank;
        return this.suitAndRank + ": " + this.value;

    }


}