import szte.mi.Move;

import java.util.Random;

public class KIPlayer implements szte.mi.Player  {
    static int Player = 0;
    static int Enemy = 0;
    Move move;
    Random rnd;
    static OthelloGame othellogame;



    @Override
    public void init(int order, long t, Random rnd) {

    }

    @Override
    public Move nextMove(Move prevMove, long tOpponent, long t) {
        return null;
    }
}
