package com.amaris.blackjack_simulation_project;

public class BasicStrategy implements Strategy {

    private static final BlackjackAction HIT = BlackjackAction.HIT;
    private static final BlackjackAction STA = BlackjackAction.STAND;
    private static final BlackjackAction DOH = BlackjackAction.DOUBLE_OR_HIT;
    private static final BlackjackAction DOS = BlackjackAction.DOUBLE_OR_STAND;
    private static final BlackjackAction SPL = BlackjackAction.SPLIT;
    private static final BlackjackAction SDH = BlackjackAction.SPLIT_IF_DAS_IS_OFFERED_HIT;
    private static final BlackjackAction SDS = BlackjackAction.SPLIT_IF_DAS_IS_OFFERED_STAND;
    private static final BlackjackAction SUR = BlackjackAction.SURRENDER;

    private static final BlackjackAction[][] HARD_TOTALS = {
            // Dealer's upcard:
            //      2     3    4    5    6    7    8   9    10    A
            /*8*/  {HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT},
            /*9*/  {HIT, DOH, DOH, DOH, DOH, HIT, HIT, HIT, HIT, HIT},
            /*10*/ {DOH, DOH, DOH, DOH, DOH, DOH, DOH, DOH, HIT, HIT},
            /*11*/ {DOH, DOH, DOH, DOH, DOH, DOH, DOH, DOH, DOH, DOH},
            /*12*/ {HIT, HIT, STA, STA, STA, HIT, HIT, HIT, HIT, HIT},
            /*13*/ {STA, STA, STA, STA, STA, HIT, HIT, HIT, HIT, HIT},
            /*14*/ {STA, STA, STA, STA, STA, HIT, HIT, HIT, HIT, HIT},
            /*15*/ {STA, STA, STA, STA, STA, HIT, HIT, HIT, HIT, HIT},
            /*16*/ {STA, STA, STA, STA, STA, HIT, HIT, HIT, HIT, HIT},
            /*17*/ {STA, STA, STA, STA, STA, STA, STA, STA, STA, STA},
            /*18*/ {STA, STA, STA, STA, STA, STA, STA, STA, STA, STA},
            /*19*/ {STA, STA, STA, STA, STA, STA, STA, STA, STA, STA},
            /*20*/ {STA, STA, STA, STA, STA, STA, STA, STA, STA, STA}

    };
    private static final BlackjackAction[][] SOFT_TOTALS = {
            // Dealer's upcard:
            //      2     3    4    5    6    7    8   9    10    A
            /*13*/ {HIT, HIT, HIT, DOH, DOH, HIT, HIT, HIT, HIT, HIT},
            /*14*/ {HIT, HIT, HIT, DOH, DOH, HIT, HIT, HIT, HIT, HIT},
            /*15*/ {HIT, HIT, DOH, DOH, DOH, HIT, HIT, HIT, HIT, HIT},
            /*16*/ {HIT, HIT, DOH, DOH, DOH, HIT, HIT, HIT, HIT, HIT},
            /*17*/ {STA, DOH, DOH, DOH, DOH, STA, STA, HIT, HIT, HIT},
            /*18*/ {DOS, DOS, DOS, STA, STA, STA, STA, STA, HIT, HIT},
            /*19*/ {STA, STA, STA, STA, STA, STA, STA, STA, STA, STA},
            /*20*/ {STA, STA, STA, STA, STA, STA, STA, STA, STA, STA}
    };
    private static final BlackjackAction[][] PAIR_TOTALS = {
            // Dealer's upcard:
            //      2     3    4    5    6    7    8   9    10    A
            /*2*/ {SDH, SDH, SPL, SPL, SPL, SPL, HIT, HIT, HIT, HIT},
            /*3*/ {SDH, SDH, SPL, SPL, SPL, SPL, HIT, HIT, HIT, HIT},
            /*4*/ {HIT, HIT, HIT, SDH, SDH, HIT, HIT, HIT, HIT, HIT},
            /*5*/ {DOH, DOH, DOH, DOH, DOH, DOH, DOH, DOH, HIT, HIT},
            /*6*/ {SDH, SPL, SPL, SPL, SPL, HIT, HIT, HIT, HIT, HIT},
            /*7*/ {SPL, SPL, SPL, SPL, SPL, SPL, HIT, HIT, HIT, HIT},
            /*8*/ {SPL, SPL, SPL, SPL, SPL, SPL, SPL, SPL, SPL, SPL},
            /*9*/ {SPL, SPL, SPL, SPL, SPL, STA, SPL, SPL, STA, STA},
            /*10*/{STA, STA, STA, STA, STA, STA, STA, STA, STA, STA},
            /*A*/ {SPL, SPL, SPL, SPL, SPL, SPL, SPL, SPL, SPL, SPL}};
    private static final BlackjackAction[][] SUR_TOTALS_HARD = {
            // Dealer's upcard:
            //      2     3    4    5    6    7    8   9    10    A
            /*15*/ {HIT, HIT, DOH, DOH, DOH, HIT, HIT, HIT, SUR, SUR},
            /*16*/ {HIT, HIT, DOH, DOH, DOH, HIT, HIT, SUR, SUR, SUR},
            /*17*/ {STA, DOH, DOH, DOH, DOH, STA, STA, HIT, HIT, SUR},
            /*8s*/ {SPL, SPL, SPL, SPL, SPL, SPL, SPL, SPL, SPL, SUR},
    };


    // Method to check soft hand strategy
    public BlackjackAction checkSoftStrategy(Card dealerCard, Hand hand) {

        return SOFT_TOTALS[hand.getScore() - 13][dealerCard.getValue() - 2];
    }

    @Override
    // Method to check hard hand strategy
    public BlackjackAction checkHardStrategy(Card dealerCard, Hand hand) {
        // Table for hard hand strategy Starts at 8 because the table only covers 8-20
        int score = hand.getScore();
        if (score <= 8) {
            return HIT; //always hit under should be the only place this occurs
        }
        //if the player has a hard 21
        if (score >= 21) {
            //stay
            return STA;
        }


        return HARD_TOTALS[score - 8][dealerCard.getValue() - 2];
    }

    @Override
    public BlackjackAction checkPairStrategy(Card dealerCard, Hand hand) {
        return null;
    }

    @Override
    public BlackjackAction checkSurrenderStrategy(Card dealerCard, Hand hand) {
        return null;
    }


}
