package battleship;

import java.util.Arrays;
import java.util.Scanner;

public class Battleship {
    public enum ships {
        AC(5, "Aircraft Carrier"),
        B(4, "Battleship"),
        S(3, "Submarine"),
        C(3, "Cruiser"),
        D(2, "Destroyer");

        private final int length;
        private final String name;

        ships(int length, String name) {
            this.length = length;
            this.name = name;
        }
        public int getLength() {
            return length;
        }
        public String getName() {
            return name;
        }
    }
    static char[] letters = {' ','A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
    boolean flag = false;
    String[][] game = new String[11][11];
    String[][] fog = new String[11][11];
    boolean isMoveValid = false;

    public void runGame(){
        getReady();
        displayBoard(game);
        arrangeShips();
        System.out.println("The game starts!");
        displayBoard(fog);
        System.out.println("Take a shot!");
        while (!isGameOver()){
//            makeMove();
        }
        if (isGameOver()) {
            System.out.println("You sank the last ship! You won. Congratulations!");
        }
    }

    public void getReady(){
        initializeBoard(game);
        initializeBoard(fog);
    }
    public void initializeBoard(String[][] game) {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (i == 0 && j == 0){
                    game[i][j] = " ";
                } else if (i == 0 && j > 0){
                    game[i][j] = Integer.toString(j);
                } else if (j == 0 && i > 0){
                    game[i][j] = letters[i] + "";
                } else {
                    game[i][j] = "~";
                }
            }
        }
    }
    public void displayBoard(String[][] game){
        for (int i = 0; i < 11; i++) {
            for (int j = 0 ; j < 11; j ++){
                System.out.print(game[i][j]);
                System.out.print(" ");
            }
            System.out.println("");
        }
    }
    public void arrangeShips(){
        for (ships x: ships.values()){
            flag = false;
            System.out.println("Enter the coordinates of the "+x.getName()+" ("+x.getLength()+" cells):");
            Scanner scanner = new Scanner(System.in);
            String coordinates = scanner.nextLine();
            checkCoordinates(coordinates, x);

            while (!flag){
                scanner = new Scanner(System.in);
                coordinates = scanner.nextLine();
                checkCoordinates(coordinates, x);
            }
            placeShips(coordinates);
            displayBoard(game);
        }
    }
    public boolean checkCoordinates(String coordinate, ships ship){
        String[] co = coordinate.split(" ");
        String start = co[0], end = co[1];
        String startCoord = start.substring(1), endCoord = end.substring(1);

        if (start.charAt(0) == end.charAt(0) || startCoord.equals(endCoord)){
            int x = Integer.parseInt(startCoord), y = Integer.parseInt(endCoord);
            int a = start.charAt(0), b = end.charAt(0);

            if(start.charAt(0) == end.charAt(0) && Math.abs(x - y) == ship.getLength()-1){
                if (proximityCheck(start, end)){
                    flag = true;
                }else{
                    System.out.println("Error! You placed it too close to another one. Try again:");
                }
            } else if (startCoord.equals(endCoord) && Math.abs(a - b) == ship.getLength()-1){
                if (proximityCheck(start, end)){
                    flag = true;
                }else{
                    System.out.println("Error! You placed it too close to another one. Try again:");
                }
            } else {
                System.out.println("Error! Wrong length of the "+ship.getName()+"! Try Again:");
            }
        } else {
            System.out.println("Error! Wrong ship location! Try again:");
        }
        return flag;
    }
    public boolean proximityCheck(String start, String end){
        boolean spaced = false;
        String startCoord = start.substring(1), endCoord = end.substring(1);

        int x = Integer.parseInt(startCoord),
                y = Integer.parseInt(endCoord);//the numbers
        int a = Arrays.binarySearch(letters, Character.toUpperCase(start.charAt(0))),
                b = Arrays.binarySearch(letters, Character.toUpperCase(end.charAt(0))); //the letters converted

        if (x == y){//vertical
            int min = Math.min(a,b);
            int max = Math.max(a,b);
            //top and bottom
            if (max != 10){
                if (game[min-1][x].equals("O") || game[max+1][x].equals("O")){
                    return spaced;
                }
            } else {
                if (game[min-1][x].equals("O")){
                    return spaced;
                }
            }

            for (int i = min; i <= max; i++){
                //check sides
                if (x != 10){
                    if (game[i][x-1].equals("O") || game[i][x+1].equals("O")){
                        spaced = false;
                        break;
                    } else {
                        spaced = true;
                    }
                } else {
                    if (game[i][x-1].equals("O")){
                        spaced = false;
                        break;
                    } else {
                        spaced = true;
                    }
                }
            }
        } else if (a == b){ //horizontal
            int min = Math.min(x,y);
            int max = Math.max(x,y);
            if (max != 10){
                if (game[a][min-1].equals("O") || game[a][min+1].equals("O")) {
                    return spaced;
                }
            } else{
                if (game[a][min-1].equals("O")) {
                    return spaced;
                }
            }
            for (int i = min; i <= max; i++){
                //check tops and bottoms
                if (a != 10){
                    if (game[a-1][i].equals("O") || game[a+1][i].equals("O")) {
                        spaced = false;
                        break;
                    } else {
                        spaced = true;
                    }
                } else {
                    if (game[a-1][i].equals("O")) {
                        spaced = false;
                        break;
                    } else {
                        spaced =  true;
                    }
                }
            }
        }
        return spaced;
    }
    public void placeShips(String coordinate){
        String[] co = coordinate.split(" ");
        String start = co[0], end = co[1]; //the coordinate
        String startCoord = start.substring(1), endCoord = end.substring(1);

        int x = Integer.parseInt(startCoord),
                y = Integer.parseInt(endCoord);//the numbers
        int a = Arrays.binarySearch(letters, Character.toUpperCase(start.charAt(0))),
                b = Arrays.binarySearch(letters, Character.toUpperCase(end.charAt(0))); //the letters converted

        //if same letter
        if (start.charAt(0) == end.charAt(0)) {
            int min = Math.min(x,y);
            int max = Math.max(x,y);
            for (int i = min; i <= max; i++){
                game[a][i] = "O";
            }
        }

        //if same number
        if (x == y){
            int min = Math.min(a,b);
            int max = Math.max(a,b);
            for (int i = min; i <= max; i++) {
                game[i][x] = "O";
            }
        }
    }
    public void makeMove(String[][] game, String[][] fog){

        Scanner scanner = new Scanner(System.in);

        String move = scanner.next();
        String startCoord = move.substring(1);
        int x = Integer.parseInt(startCoord);//the numbers

        if (moveValidation(x, move.charAt(0))) {
            int a = Arrays.binarySearch(letters, Character.toUpperCase(move.charAt(0))); //the letters converted
            if (game[a][x].equals("~")) {
                game[a][x] = "M";
                fog[a][x] = "M";
                System.out.println("You missed!");
            } else {
                game[a][x] = "X";
                fog[a][x] = "X";
                if(sunkShip(a,x)){
                    System.out.println("You sank a ship! Specify a new target:");
                } else{
                    System.out.println("You hit a ship!");
                }
            }
        } else {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
        }

    }

    public boolean moveValidation(int x, char y) {
        boolean v = false;
        for (char le: letters){
            if(le == y){
                v = true;
                break;
            }
        }
        if (x < 10 && v){
            isMoveValid =  true;
        }
        return isMoveValid;
    }

    public boolean sunkShip(int x, int y){
        boolean sunk;
        if (x == 10 && y != 10) {
            if (game[x-1][y] == "O" || game[x][y+1] == "O" || game[x][y-1] == "O"){
                sunk = false;
            } else {
                sunk = true;
            }
        } else if (x != 10 && y == 10){
            if (game[x-1][y] == "O" || game[x+1][y] == "O" || game[x][y-1] == "O"){
                sunk = false;
            } else {
                sunk = true;
            }
        } else if (x ==10 && y == 10) {
            if (game[x-1][y] == "O" || game[x][y-1] == "O"){
                sunk = false;
            } else {
                sunk = true;
            }
        } else {
            if (game[x+1][y] == "O" || game[x-1][y] == "O" || game[x][y+1] == "O" || game[x][y-1] == "O"){
                sunk = false;
            } else{
                sunk = true;
            }
        }
        return sunk;
    }

    public boolean isGameOver(){
        boolean over = false;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (game[i][j] == "O"){
                    over = false;
                    return over;
                } else {
                    over = true;
                }
            }
        }
        return over;
    }
}
