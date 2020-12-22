import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main extends Application implements EventHandler<ActionEvent> {
    private OthelloGame othello;
    private BorderPane layout = new BorderPane();
    private static final int BLACK = 1;
    private static final int WHITE = 2;
    private static final int EMPTY = 0;

    public GridPane startgame() {   //methode die immer wieder das feld updaten soll
        int currentmove = 0;
        GridPane gameboard = new GridPane();
        gameboard.setGridLinesVisible(true);
        gameboard.setPrefSize(800, 800);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int x = i;
                int y = j;
                Button playstone = new Button();    //spielstein
                playstone.setPrefSize(100, 100);
                playstone.setShape(new Circle(5));
                gameboard.setHgap(3);
                gameboard.setVgap(3);
                playstone.setVisible(false);    //spielfeld leer machen
                playstone.setBorder(new Border(new BorderStroke(Color.BEIGE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                        new BorderWidths(2))));
                gameboard.setAlignment(Pos.CENTER);
                //gameboard.add(playstone, i, j);
                gameboard.setStyle("-fx-background-image: url('https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQxsasGQIwQNwjek3F1nSwlfx60g6XpOggnxw5dyQrtCL_0x8IW')");
                Label label = new Label("0");
                AtomicInteger numMoves = new AtomicInteger(); //atomic int damit ++ in setonaction funktioniert
                if (othello.getfield()[i][j] == BLACK) {
                    playstone.setVisible(true);
                    playstone.setStyle("-fx-background-color: #000000; ");
                }
                else if(othello.getfield()[i][j] == WHITE){
                    playstone.setVisible(true);
                    playstone.setStyle("-fx-background-color: #ffffff; ");
                }
                else if(othello.getfield()[i][j] == EMPTY){
                    int turn = (numMoves.get() % 2 == 0) ? BLACK : WHITE;
                    playstone.setVisible(true);
                    //playstone.setShape(new Circle(3));
                    playstone.setStyle("-fx-background-color: #787878; ");
                    //playstone.setPrefSize(50,50);
                    playstone.setOnAction(actionEvent -> {
                        if(turn == BLACK){                  //falls nummoves gerade ist, turn = gerade -> black am zug
                            othello.BlackPlay(x, y);
                        }
                        else {
                            othello.WhitePlay(x,y);
                        }
                        numMoves.getAndIncrement();
                        layout.setCenter(startgame());
                        //othello.printField();
                    });
                }
                gameboard.add(playstone,i, j);  //erstelle spielsteine auf Brett
            }
        }
        return gameboard;
    }



    @Override
    public void start(Stage primaryStage) throws Exception {

        //Gridpane als Spielfeld

        Random rnd = new Random();

        //KIPlayer ki = new KIPlayer();
       // ki.init(0,8000,rnd);
        othello = new OthelloGame(8,8); // erstellt spielbrett
        layout.setCenter(startgame());
            primaryStage.setScene(new Scene(layout));
            primaryStage.setTitle("Othello");
            primaryStage.show();
        }


        public static void main (String[]args){
            launch(args);
        }

    @Override
    public void handle(ActionEvent actionEvent) {

    }
}
