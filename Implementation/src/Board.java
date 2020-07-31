/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5071.d9da8f6cd modeling language!*/


import java.util.List;

// line 90 "model.ump"
// line 183 "model.ump"
public class Board {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Board Attributes
    private List<Position> tiles;

    //Board Associations
    private List<Scenario> scenarios;
    private Position[][] positions = new Position[24][25];

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Board() {
    }

    public void addPosition(int x, int y, Position position) {
        positions[x][y] = position;
    }

    public String toString() {
        StringBuilder boardPrint = new StringBuilder();
        for (int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions[0].length; j++) {
                boardPrint.append("|" + positions[i][j].toString());
            }
            boardPrint.append("|\n");
        }
        return boardPrint.toString();
    }
}