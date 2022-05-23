
import java.util.Scanner;

public class Game {

    private static boolean lost = false;
    private static boolean won = false;
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        Board board = getUserSetting();

        while(!lost && !won) {
            board.printBoard();
            turn(board);
        }

        if (lost) System.out.println("BOOM, you lose");
        else System.out.println("Great job, you win!");
    }

    private static void turn(Board board) {
        System.out.println();
        System.out.println("Enter a position (two numbers separated by a space), " +
                           "if you would like to flag or un-flag the space, enter an F " +
                           "separated by a space at the end of your input");
        String pos = scan.nextLine();
        int x = Integer.parseInt(pos.substring(0, pos.indexOf(" "))) - 1;
        int y;
        if (pos.indexOf(" ") == pos.lastIndexOf(" ")) y = Integer.parseInt(pos.substring(pos.indexOf(" ") + 1)) - 1;
        else y = Integer.parseInt(pos.substring(pos.indexOf(" ") + 1, pos.lastIndexOf(" "))) - 1;
        //Repeat getting pos until a valid position is inputted
        while (x >= board.gethSize() || x < 0 || y >= board.getvSize() || y < 0) {
            System.out.println("Not a valid position, please enter a valid position");
            pos = scan.nextLine();
            x = Integer.parseInt(pos.substring(0, pos.indexOf(" "))) - 1;
            if (pos.indexOf(" ") == pos.lastIndexOf(" ")) y = Integer.parseInt(pos.substring(pos.indexOf(" ") + 1)) - 1;
            else y = Integer.parseInt(pos.substring(pos.indexOf(" ") + 1, pos.lastIndexOf(" "))) - 1;
        }

        System.out.println();

        //If user has put an f at the end of their input, flag the space, if not, uncover it
        if (pos.substring(pos.length() - 1).toLowerCase().equals("f")) {
            board.getBoard()[y][x].flagSpace(board.getNumBombs());
        }
        else board.getBoard()[y][x].uncoverSpace();

        board.clearBoard();

        System.out.println(Space.getTotalFlagged() + " spaces flagged, " + (board.getNumBombs() - Space.getTotalFlagged()) + " flags left");
        System.out.println();

        if (Space.getBombsFlagged() == board.getNumBombs()) won = true;
    }

    public static Board getUserSetting() {

        int numBombs;
        int hSize;
        int vSize;
        final double AUTO_BOMB_PERCENTAGE = 0.15416666667;

        System.out.println("Do you want custom settings?");
        String custom  = scan.nextLine();
        System.out.println();
        if (custom.toLowerCase().equals("yes")) {

            System.out.println("Enter board vertical length");
            vSize = Integer.parseInt(scan.nextLine());
            System.out.println();

            System.out.println("Enter board horizontal length");
            hSize = Integer.parseInt(scan.nextLine());
            System.out.println();

            System.out.println("Enter number of bombs (enter auto if you want number of bombs to be calculated for you)");
            String str = scan.nextLine().toLowerCase();
            if(str.equals("auto")) numBombs = (int)((hSize * vSize) * AUTO_BOMB_PERCENTAGE);
            else numBombs = Integer.parseInt(str);
            System.out.println();
        }
        else {
            System.out.println("Easy, medium, or hard?");
            switch(scan.nextLine().toLowerCase()) {
                case "easy":
                    int size = (int)(Math.random() * 3 + 8);
                    hSize = size;
                    vSize = size;
                    numBombs = 10;
                    break;

                case "medium":
                    if ((int)(Math.random() * 2) == 1) {
                        hSize = 13;
                        vSize = 15;
                    }
                    else {
                        hSize = 16;
                        vSize = 16;
                    }
                    numBombs = 40;
                    break;

                case "hard":
                    hSize = 16;
                    vSize = 30;
                    numBombs = 99;
                    break;

                default:
                    hSize = 16;
                    vSize = 16;
                    numBombs = 40;
            }
        }
        System.out.println("There are " + numBombs + " bombs, good luck");
        System.out.println();

        return new Board(hSize, vSize, numBombs);
    }

    public static void lostGame() {
        lost = true;
    }
}
