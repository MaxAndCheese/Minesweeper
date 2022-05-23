
public class Space {

    private boolean uncovered = false;
    private boolean flagged = false;
    private boolean bomb;
    private int bombsInArea;
    private static int bombsFlagged = 0;
    private static int totalFlagged = 0;

    public Space(int d, boolean b) {
        bombsInArea = d;
        bomb = b;
    }

    public void uncoverSpace() {
        uncovered = true;
        if(flagged) {
            if(isBomb()) bombsFlagged--;
            totalFlagged--;
            flagged = false;
        }
        if(bomb) Game.lostGame();
    }

    public void flagSpace(int numBombs) {
        //Flags space if not flagged, un-flags space if flagged (can't be uncovered)
        if(!uncovered && !flagged && totalFlagged < numBombs) {
            flagged = true;
            if (isBomb()) bombsFlagged++;
            if (!isUncovered()) totalFlagged++;
        }
        else if (flagged) {
            flagged = false;
            if (isBomb()) bombsFlagged--;
            totalFlagged--;
        }
        else if(totalFlagged == numBombs) {
            System.out.println("You have run out of flags, please un-flag a space");
            System.out.println();
        }
    }

    public boolean isUncovered() {
        return uncovered;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public int getBombsInArea() {
        return bombsInArea;
    }

    public boolean isBomb() {
        return bomb;
    }

    public static int getBombsFlagged() {
        return bombsFlagged;
    }

    public static int getTotalFlagged() {
        return totalFlagged;
    }
}
