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
    private Position[][] positions;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Board() {
    }


    public String toString() {
        return super.toString() + "[" + "]";
    }
}