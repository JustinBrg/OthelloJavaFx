import szte.mi.Move;
import szte.mi.Player;
// try to catch the exception like : try {
//    array[index] = someValue;
//}
//catch(ArrayIndexOutOfBoundsException exception) {
//    handleTheExceptionSomehow(exception);
//} at lines : 176,86,272,8(runner)
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/** STILL TO DO: EXCEPTIONS FALLS KEIN INT EINGEGEBEN WIRD,METHODEN UPDATEN, FLIPCOINS METHODE UPDATEN. NOCH COUNTER
 * EINBAUEN DAMIT FLIPCOINTS NUR IN RICHTUNG PASSIERT WO MIND. 2MAL DIESER COIN VORKOMMT */


public class OthelloGame implements szte.mi.Player {
    private final Scanner inputScanner;
    private static final int BLACK = 1;
    private static final int WHITE = 2;
    private static final int EMPTY = 0;
    private final int sizer;
    private final int sizec;
    //private final in
    private int[][] field;
    private boolean blackturn = false;
    private boolean whiteturn = false;
    private int blackcounter;
    private int whitecounter;


    public OthelloGame(int sizer, int sizec) {
        this.sizer = sizer;
        this.sizec = sizec;

        this.field = new int[sizer][sizec];  //* Feld initialized
        newField();

        this.inputScanner = new Scanner(System.in);
    }


    private void newField() {               // produces a new field
        for (int i = 0; i < sizer; i++) {
            for (int j = 0; j < sizec; j++) {
                this.field[i][j] = EMPTY;
            }
        }
        this.field[3][3] = WHITE;     // starting postions
        this.field[4][4] = WHITE;
        this.field[4][3] = BLACK;
        this.field[3][4] = BLACK;
    }

    public void printField(int currentMove) {          // print the field
        System.out.println("Move " + currentMove);  //+" this is a zero indexed game"
        for (int i = 0; i < this.sizer; i++) {
            for (int j = 0; j < this.sizec; j++) {
                System.out.print(this.field[i][j] + " ");
            }
            System.out.println();
        }
        // System.out.println();
    }

    public int[][] getfield() {
        for (int i = 0; i < this.sizer; i++) {
            for (int j = 0; j < this.sizec; j++) {
            }
        }
        return this.field;
    }

    public void Fieldupdate() { // Methode um neues spielfeld zu zeigen, mit veränderungen nach Spielzug eines spielers.

        for (int i = 0; i < this.sizer; i++) {
            for (int j = 0; j < this.sizec; j++) {
                System.out.print(this.field[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private int askforPlay(String RC) {
        System.out.println("Select a " + RC + "position between 0 and 7"); //Methode zu einem zusammen, das für row und column nur einmal gefragt wird?
        return Integer.parseInt(this.inputScanner.nextLine());
    }

    public void PlayerMove(int turn) {// int x & int y
        boolean repeatturnblack = true;
        boolean repeatturnwhite = true;
        boolean turnendblack = false;
        boolean turnendwhite = false;
        System.out.println("Turn for Player " + BLACK);
        while (!turnendblack && !hasPlayerwon()) {
            if (turn == BLACK) {  //increase nummoves by 2 so number is even and black plays again
                showvalidmove(turn);
                int posX = askforPlay("row"); // int y
                int posY = askforPlay("column");

                //showvalidmove(turn, posX, posY);
                if (moveisvalid(turn, posX, posY)) {
                    turnendblack = true;
                    BlackPlay(posX, posY);
                    flipcoins(turn, posX, posY);
                    Fieldupdate();
                    System.out.println("Player 1 set on " + "row = " + posX + " column =  " + posY);
                    repeatturnblack = false;
                } else {
                    System.out.println("Spot not available, choose another one");
                    Fieldupdate();
                }
            }
        }
        if (!repeatturnblack) {  // sorgt grundlos für spot not available error in whiteplay??
            turn = WHITE;
        }
        System.out.println("Turn for Player " + WHITE);
        while (!turnendwhite && !hasPlayerwon()) {
            //if (turn == WHITE) {
            showvalidmove(turn);
            int posX = askforPlay("row");
            int posY = askforPlay("column");
            //showvalidmove(turn, posX, posY);
            if (moveisvalid(turn, posX, posY)) {
                turnendwhite = true;
                //showvalidmove(turn, posX, posY);
                WhitePlay(posX, posY);
                flipcoins(turn, posX, posY);
                Fieldupdate();
                System.out.println("Player 2 set on " + "row = " + posX + " column =  " + posY);

            } else {
                System.out.println("Spot not available, choose another one");
                Fieldupdate();
            }
        }
    }

    //}


    public void WhitePlay(int x, int y) {
        this.field[x][y] = WHITE;
    }


    public void BlackPlay(int x, int y) {
        this.field[x][y] = BLACK;
    }

    public void Direction(int x, int y) {

    }

    private boolean checkifborder(int z) {
        boolean border;
        border = z == 8;
        return border;
    }

    private void showvalidmove(int turn) { //anzeigen aller moves die machbar sind //gibt bisher nur einen Zug aus
        boolean found = false;
        boolean found2 = false;
        boolean found3 = false;
        boolean found4 = false;
        boolean found5 = false;
        boolean found6 = false;
        boolean found7 = false;
        boolean found8 = false;
        int col = WHITE;
        if (turn == WHITE) {
            col = BLACK;
        }
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++){


                if (this.field[x][y] == EMPTY) {
                    if (x + 1 < 8 && y + 1 < 8 && this.field[x + 1][y + 1] == col) { //pfeil diag unten rechts
                        int borderx = this.sizer - x;
                        int bordery = this.sizec - y;
                        int checkx = 1;
                        int checky = 1;
                        while (!found && x + checkx < 8 && y + checky < 8) {
                            if (this.field[x + checkx][y + checky] == turn) {
                                System.out.println("row at " + (x) + "column at "
                                        + (y) +
                                        "is a playable move");
                                found = true;
                            }
                            checkx++;
                            checky++;
                        }
                    }   //check ob diag unten rechts auch eigener stein kommt, dann playable

                    else if (x + 1 < 8 && this.field[x + 1][y] == col) {//geht pfeil runter
                        int checkx = 1;
                        int checky = 1;
                        while (!found2 && x + checkx < 8) {
                            if (this.field[x + checkx][y] == turn) {  //wenn in direct runter eigener stein liegt
                                System.out.println("row at " + (x) + "column at "
                                        + (y) +
                                        "is a playable move");
                                // wenn gefunden hört nicht auf, vielleicht boolean hinzufügen und dann ändern?
                                found2 = true;
                            }
                            checkx++;
                        }
                    } else if (y + 1 < 8 && this.field[x][y + 1] == col) {      //geht pfeil nach rechts
                        int checkx = 1;
                        int checky = 1;
                        while (!found3 && y + checky < 8) {
                            if (this.field[x][y + checky] == turn) {
                                System.out.println("row at " + (x) + "column at "
                                        + (y) +
                                        "is a playable move");
                                found3 = true;
                            }
                            checky++;
                        }
                    } else if (y - 1 > -1 && this.field[x][y - 1] == col) {       //geht pfeil nach links
                        int checkx = 1;
                        int checky = 1;
                        while (!found4 && y - checky > -1) {
                            if (this.field[x][y - checky] == turn) {
                                System.out.println("row at " + (x) + "column at "
                                        + (y) +
                                        "is a playable move");
                                found4 = true;
                            }
                            checky++;
                        }
                    } else if (x - 1 > -1 && y - 1 > -1 && this.field[x - 1][y - 1] == col) { //geht pfeil diag oben links
                        int checkx = 1;
                        int checky = 1;
                        while (!found5 && x - checkx > -1 && y - checky > -1) {
                            if (this.field[x - checkx][y - checky] == turn) {
                                System.out.println("row at " + (x) + "column at "
                                        + (y) +
                                        "is a playable move");
                                found5 = true;
                            }
                            checkx++;
                            checky++;
                        }
                    } else if (x - 1 > -1 && this.field[x - 1][y] == col) {     //geht pfeil nach oben
                        int checkx = 1;
                        int checky = 1;
                        while (!found6 && x - checkx > -1) {
                            if (this.field[x - checkx][y] == turn) {
                                System.out.println("row at " + (x) + "column at "
                                        + (y) +
                                        "is a playable move");
                                found6 = true;
                            }
                            checkx++;
                        }
                    } else if (x - 1 > -1 && y + 1 < 8 && this.field[x - 1][y + 1] == col) {    //pfeil diag oben rechts
                        int checkx = 1;
                        int checky = 1;
                        while (!found7 && x - checkx > -1 && y + checky < 8) {
                            if (this.field[x - checkx][y + checky] == turn) {
                                System.out.println("row at " + (x) + "column at "
                                        + (y) +
                                        "is a playable move");
                                found7 = true;
                            }
                            checkx++;
                            checky++;
                        }
                    } else if (x + 1 < 8 && y - 1 > -1 && this.field[x + 1][y - 1] == col) {    //pfeil diag unten links

                        int checkx = 1;
                        int checky = 1;
                        while (!found8 && x + checkx < 8 && y - checky > -1) {
                            if (this.field[x + checkx][y - checky] == turn) {
                                System.out.println("row at " + (x) + "column at "
                                        + (y) +
                                        "is a playable move");
                                found8 = true;
                            }
                            checkx++;
                            checky++;
                        }
                    }
                }
            }
        }
    }



        private boolean moveisvalid ( int turn, int x, int y){   //updaten nach showisvalid thema
            boolean valid = false;
            int col = WHITE;
            if (turn == WHITE) {
                col = BLACK;
            }

            if (this.field[x][y] == EMPTY) {
                if (x + 1 < 8 && y + 1 < 8 && this.field[x + 1][y + 1] == col) {
                    int checkx = 1;
                    int checky = 1;
                    while (!valid && x + checkx < 8 && y + checky < 8) {
                        if (this.field[x + checkx][y + checky] == turn) {
                            valid = true;
                        }
                        checkx++;
                        checky++;
                    }
                } else if (x + 1 < 8 && this.field[x + 1][y] == col) {
                    int checkx = 1;
                    int checky = 1;
                    while (!valid && x + checkx < 8) {
                        if (this.field[x + checkx][y] == turn) {
                            valid = true;
                        }
                        checkx++;
                    }
                } else if (y + 1 < 8 && this.field[x][y + 1] == col) {
                    int checkx = 1;
                    int checky = 1;
                    while (!valid && x + checkx < 8 && y + checky < 8) {
                        if (this.field[x][y + checky] == turn) {
                            valid = true;
                        }
                        checky++;
                    }
                } else if (y - 1 > -1 && this.field[x][y - 1] == col) {
                    int checkx = 1;
                    int checky = 1;
                    while (!valid && x < 8 && y - checky > -1) {
                        if (this.field[x][y - checky] == turn) {
                            valid = true;
                        }
                        checky++;
                    }
                } else if (x - 1 > -1 && y - 1 > -1 && this.field[x - 1][y - 1] == col) {
                    int checkx = 1;
                    int checky = 1;
                    while (!valid && x - checkx > -1 && y - checky > -1) {
                        if (this.field[x - checkx][y - checky] == turn) {
                            valid = true;
                        }
                        checkx++;
                        checky++;
                    }
                } else if (x - 1 > -1 && this.field[x - 1][y] == col) {
                    int checkx = 1;
                    int checky = 1;
                    while (!valid && x - checkx > -1) {
                        if (this.field[x - checkx][y] == turn) {
                            valid = true;
                        }
                        checkx++;
                    }
                } else if (x - 1 > -1 && y + 1 < 8 && this.field[x - 1][y + 1] == col) {
                    int checkx = 1;
                    int checky = 1;
                    while (!valid && x - checkx > -1 && y + checky < 8) {
                        if (this.field[x - checkx][y + checky] == turn) {
                            valid = true;
                        }
                        checkx++;
                        checky++;
                    }
                } else if (x + 1 < 8 && y - 1 > -1 && this.field[x + 1][y - 1] == col) {
                    int checkx = 1;
                    int checky = 1;
                    while (!valid && x + checkx < 8 && y - checky > -1) {
                        if (this.field[x + checkx][y - checky] == turn) {
                            valid = true;
                        }
                        checkx++;
                        checky++;
                    }
                }
            }
            return valid;
        }
        private void flipcoins ( int turn, int x, int y) {
            int i = 0;
            int samecolordown = 0;
            int samecolorup = 0;
            int samecolorleft = 0;
            int samecolorright = 0;
            int samecolordiagleftup = 0;
            int samecolordiagrightup = 0;
            int samecolordiagleftdown = 0;
            int samecolordiagrightdown = 0;
            boolean valid = false;
            boolean canflipdown = false;
            boolean canflipright = false;
            boolean canflipleft = false;
            boolean canflipup = false;
            boolean canflipdiagupleft = false;
            boolean canflipdiagupright = false;
            boolean canflipdiagdownright = false;
            boolean canflipdiagdownleft = false;
            boolean found = false;
            boolean found2 = false;
            boolean found3 = false;
            boolean found4 = false;
            boolean found5 = false;
            boolean found6 = false;
            boolean found7 = false;
            boolean found8 = false;
            int col = WHITE;
            if (turn == WHITE) {
                col = BLACK;
            }
            //PROBLEM IST DAS WENN WHITE NEBEN BLACK IST, ABER KEIN BLACK AUF HORIZONTAL, TROTZDEM GEFLIPPED WIRD
            // VIELLEICHT SCHAUEN OB MIND. 2 MAL IN VERTIKAL/HORIZONTAL EINE GLEICHE VORKOMMT, DANN ERST FLIPP
            //x = posX, position in reihenrichtung auf welcher chip gesetzt wurde
            //y = posY, position in spaltenrichtung auf welcher chip gesetzt wurde
            //Methode zum überprüfen ob in die Richtugn geflipped werden muss.
            while(i<8) {             //checkt ob mind 2 coins in einer richtung liegen damit in diese richtung geflippt wird
                if (x + i < 8 && this.field[x + i][y] == turn) {        //zählt alle farben von turn in richtung
                    canflipdown = true;
                } else if (y + i < 8 && this.field[x][y + i] == turn && this.field[x][y + i] != EMPTY) {
                    canflipright = true;
                } else if (y - i > -1 && this.field[x][y - i] == turn && this.field[x][y - i] != EMPTY) {
                    canflipleft = true;
                } else if (x - i > -1 && this.field[x - i][y] == turn && this.field[x - i][y] != EMPTY) {
                    canflipup = true;
                } else if (x - i > -1 && y - i > -1 && this.field[x - i][y - i] == turn && this.field[x - i][y - i] != EMPTY) {
                    canflipdiagupleft = true;
                } else if (x - i > -1 && y + i < 8 && this.field[x - i][y + i] == turn && this.field[x - i][y + i] != EMPTY) {
                    canflipdiagupright = true;
                } else if (x + i < 8 && y + i < 8 && this.field[x + i][y + i] == turn && this.field[x + i][y + i] != EMPTY) {
                    canflipdiagdownright = true;
                } else if (x + i < 8 && y - i > -1 && this.field[x + i][y - i] == turn && this.field[x + i][y - i] != EMPTY) {
                    canflipdiagdownleft = true;
                }
                i++;
        }
            if (canflipdown && x + 1 < 8 && this.field[x + 1][y] == col) {//geht pfeil runter //1.Zug col = white
                int checkx = 1;
                while (!found2 && x + checkx < 8) {
                    if(this.field[x + checkx][y] == turn){
                        found2 = true;
                    }
                    else if (this.field[x + checkx][y] == col){
                        this.field[x + checkx][y] = turn;  //wenn in direct runter eigener stein liegt
                    }

                        // wenn gefunden hört nicht auf, vielleicht boolean hinzufügen und dann ändern?


                    checkx++;
                }
            }
           else if (canflipright && y + 1 < 8 && this.field[x][y + 1] == col) {      //geht pfeil nach rechts
                int checky = 1;
                while (!found3 && y + checky < 8) {
                    if(this.field[x][y+checky] == turn) {

                        found3 = true;
                    }
                    else if (this.field[x][y + checky] == col){
                        this.field[x][y + checky] = turn;
                        }
                    checky++;
                }
            }
            else if (canflipleft && y - 1 > -1 && this.field[x][y - 1] == col) {       //geht pfeil nach links
                int checkx = 1;
                int checky = 1;
                while (!found4 && y - checky > -1) {
                    if (this.field[x][y - checky] == turn) {
                        found4 = true;
                    }
                    else if(this.field[x][y-checky] == col){
                        this.field[x][y-checky] = turn;
                    }
                    checky++;
                }
            }
            else if (canflipup && x - 1 > -1 && this.field[x - 1][y] == col) {     //geht pfeil nach oben
                int checkx = 1;
                int checky = 1;
                while (!found6 && x - checkx > -1) {
                    if (this.field[x - checkx][y] == turn) {

                        found6 = true;
                    }
                    else if(this.field[x-checkx][y] == col){
                        this.field[x-checkx][y] = turn;
                    }
                    checkx++;
                }
            }
            else if (canflipdiagupleft && x - 1 > -1 && y - 1 > -1 && this.field[x - 1][y - 1] == col) {//geht pfeil diag oben links
                int checkx = 1;
                int checky = 1;
                while (!found5 && x - checkx > -1 && y - checky > -1) {
                    if (this.field[x - checkx][y - checky] == turn) {
                        found5 = true;
                    }
                    else if(this.field[x - checkx][y - checky] == col){
                        this.field[x - checkx][y - checky] = turn;
                    }
                    checkx++;
                    checky++;
                }
            }
            else if (canflipdiagupright && x - 1 > -1 && y + 1 < 8 && this.field[x - 1][y + 1] == col) {    //pfeil diag oben rechts
                int checkx = 1;
                int checky = 1;
                while (!found6 && x - checkx > -1 && y + checky < 8) {

                    if (this.field[x - checkx][y + checky] == turn) {
                        found6 = true;
                    }
                    else if(this.field[x - checkx][y + checky] == col){
                        this.field[x - checkx][y + checky] = turn;
                    }
                    checkx++;
                    checky++;
                }
            }
            else if (canflipdiagdownright && x + 1 < 8 && y + 1 < 8 && this.field[x + 1][y + 1] == col) { //pfeil diag unten rechts
                int checkx = 1;
                int checky = 1;
                while (!found7 && x + checkx < 8 && y + checky < 8) {

                    if (this.field[x + checkx][y + checky] == turn) {
                        found7 = true;
                    }
                    else if(this.field[x + checkx][y + checky] == col){
                        this.field[x + checkx][y + checky] = turn;
                    }
                    checkx++;
                    checky++;
                }
            }
            else if (canflipdiagdownleft && x+ 1 < 8 && y - 1 > -1 && this.field[x + 1][y - 1] == col) {    //pfeil diag unten links

                int checkx = 1;
                int checky = 1;
                while (!found8 && x + checkx < 8 && y - checky > -1) {
                    if (this.field[x + checkx][y - checky] == turn) {
                        found8 = true;
                    }
                    else if(this.field[x + checkx][y - checky] == col){
                        this.field[x + checkx][y - checky] = turn;
                    }
                    checkx++;
                    checky++;
                }
            }

        }


        private boolean hasPlayerwon () {
            boolean winner = false;
            for (int i = 0; i < sizer; i++) {
                for (int j = 0; j < sizec; j++) {
                    if (this.field[i][j] == BLACK) {
                        blackcounter++;
                    }
                    if (this.field[i][j] == WHITE) {
                        whitecounter++;
                    }
                }
                if (blackcounter + whitecounter == 64) {
                    System.out.println("Game is over");
                    if (blackcounter > whitecounter) {
                        System.out.println("Black has won the game");
                    } else if (blackcounter == whitecounter) {
                        System.out.println("It is a Draw!");
                    } else {
                        System.out.println("White has won the game!");
                    }
                }

            }
            return winner;
        }
        public void countcoins () {
            for (int i = 0; i < sizer; i++) {
                for (int j = 0; j < sizec; j++) {
                    if (this.field[i][j] == BLACK) {
                        blackcounter++;
                    }
                    if (this.field[i][j] == WHITE) {
                        whitecounter++;
                    }
                }
            }
        }
        public void game () {
            int numMoves = 0;
            printField(numMoves);
            while (!hasPlayerwon()) {
                int turn = (numMoves % 2 == 0) ? BLACK : WHITE;//Black gerade, White ungerade // turn ist BLACK bei gerade, und WHITe bei ungerade
                try {
                    PlayerMove(turn);   //versucht den player move zu machen, falls nicht geht, -> wiederholen
                }                       //wenn exception bei player 2, wird zug wiederholt aber für player 1..vielleicht exception bei moveisvalid setzen=
                catch (ArrayIndexOutOfBoundsException exception) {
                    System.out.println("Index out of bounds, choose another one.");
                    PlayerMove(turn);
                }
                numMoves++; //numMoves um 2 erhöhen, da 2 Spielzüge pro PlayerMove passieren.
                numMoves++;

                printField(numMoves);
            }
        }
        @Override
        public void init ( int order, long t, Random rnd){
        }

        @Override
        public Move nextMove (Move prevMove,long tOpponent, long t){
            return null;
        }
    }


