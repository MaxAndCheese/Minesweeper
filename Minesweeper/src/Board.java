
public class Board {

    private Space[][] board;
    private int hSize;
    private int vSize;
    private int numBombs;

    public Board(int h, int v, int n) {
        hSize = h;
        vSize = v;
        numBombs = n;
        board = new Space[v][h];

        //place bombs randomly
        int bombsPlaced = 0;
        int row = (int) (Math.random() * vSize);
        int column = (int) (Math.random() * hSize);
        while (bombsPlaced < numBombs) {
            while (board[row][column] != null && board[row][column].isBomb()) {
                row = (int) (Math.random() * vSize);
                column = (int) (Math.random() * hSize);
            }

            //If bomb, bombsInArea is -1
            board[row][column] = new Space(-1, true);
            bombsPlaced++;
            //System.out.println("Bomb placed at " + (column + 1) + ", " + (row + 1));
        }

        //Initialize all other spaces
        for (int i = 0; i < vSize; i++) {
            for (int j = 0; j < hSize; j++) {
                if (board[i][j] == null) board[i][j] = new Space(getPosBombsInArea(i, j), false);
            }
        }
    }

    public int gethSize() {
        return hSize;
    }

    public int getvSize() {
        return vSize;
    }

    public Space[][] getBoard() {
        return board;
    }

    public int getNumBombs() {
        return numBombs;
    }

    public int getPosBombsInArea(int row, int column) {

        int bombsInArea = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                if ((i != row || j != column) && i >= 0 && i < vSize && j >= 0 && j < hSize) {
                    if (board[i][j] != null && board[i][j].isBomb()) bombsInArea++;
                }
            }
        }
        return bombsInArea;
    }

    public void clearBoard() {
        for (int k = 0; k < hSize + vSize; k++) {
            for (int i = 0; i < vSize; i++) {
                for (int j = 0; j < hSize; j++) {
                    clearSpace(i, j);
                }
            }
        }
    }

    public void clearSpace(int row, int column) {
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                Space space = board[row][column];
                if (!space.isBomb() && (i != row || j != column) && i >= 0 && i < vSize && j >= 0 && j < hSize) {
                    if (board[i][j].isUncovered() && board[i][j].getBombsInArea() == 0) space.uncoverSpace();
                }
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i <= hSize; i++) {
            System.out.print(i + "\t");
        }
        System.out.println();
        for (int i = 0; i < vSize; i++) {
            for (int j = 0; j < hSize; j++) {
                if (j == 0) System.out.print(i + 1 + "\t");
                Space space = board[i][j];
                if (!space.isUncovered() || space.isBomb() || space.isFlagged()) {
                    if (space.isFlagged()) System.out.print("{F}\t");
                    //Uncomment the following line to activate cheat mode
                    //else if (space.isBomb()) System.out.print("[X]\t");
                    else System.out.print("[ ]\t");
                }
                else {
                    if (space.getBombsInArea() == 0) System.out.print("   \t");
                    else System.out.print("[" + space.getBombsInArea() + "]\t");
                }
            }
            System.out.println();
        }
    }
}
