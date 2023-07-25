package battleship;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        // Write your code here

        Battleship p1 = new Battleship();
        Battleship p2 = new Battleship();

        p1.getReady();
        p2.getReady();

        System.out.println("Player 1, place your ships on the game field");
        p1.displayBoard(p1.game);
        p1.arrangeShips();
        promptEnterKey();
        System.out.println("Player 2, place your ships on the game field");
        p2.displayBoard(p2.game);
        p2.arrangeShips();


        while(!p1.isGameOver() && !p2.isGameOver()) {
            promptEnterKey();

            displayTwoBoards(p1, p2);
            System.out.println("Player 1, it's your Turn!");
            p1.makeMove(p2.game, p2.fog);
            promptEnterKey();

            displayTwoBoards(p2, p1);
            System.out.println("Player 2, it's your Turn!");
            p2.makeMove(p1.game, p1.fog);
        }
        if (p1.isGameOver() || p2.isGameOver()) {
            System.out.println("You sank the last ship! You won. Congratulations!");
        }
    }

    public static void promptEnterKey() {
        System.out.println("Press Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void displayTwoBoards(Battleship p1, Battleship p2) {
        p2.displayBoard(p2.fog);
        System.out.println("---------------------");
        p1.displayBoard(p1.game);
    }

}
