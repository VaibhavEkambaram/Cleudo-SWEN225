/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5071.d9da8f6cd modeling language!*/


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// line 11 "model.ump"
// line 117 "model.ump"
// line 141 "model.ump"
// line 199 "model.ump"
public class WeaponCard implements Card {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //WeaponCard Associations
    private final List<Scenario> scenarios;
    //WeaponCard Attributes
    private String weapon;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public WeaponCard(String aWeapon) {
        weapon = aWeapon;
        scenarios = new ArrayList<Scenario>();
    }

    //------------------------
    // INTERFACE
    //------------------------

    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfScenarios() {
        return 0;
    }

    public boolean setWeapon(String aWeapon) {
        boolean wasSet = false;
        weapon = aWeapon;
        wasSet = true;
        return wasSet;
    }

    public String getWeapon() {
        return weapon;
    }

    /* Code from template association_GetMany */
    public Scenario getScenario(int index) {
        Scenario aScenario = scenarios.get(index);
        return aScenario;
    }

    public List<Scenario> getScenarios() {
        List<Scenario> newScenarios = Collections.unmodifiableList(scenarios);
        return newScenarios;
    }

    public int numberOfScenarios() {
        int number = scenarios.size();
        return number;
    }

    public boolean hasScenarios() {
        boolean has = scenarios.size() > 0;
        return has;
    }

    public int indexOfScenario(Scenario aScenario) {
        int index = scenarios.indexOf(aScenario);
        return index;
    }

    /* Code from template association_AddManyToManyMethod */
    public boolean addScenario(Scenario aScenario) {
        boolean wasAdded = false;
        if (scenarios.contains(aScenario)) {
            return false;
        }
        scenarios.add(aScenario);
        if (aScenario.indexOfWeaponCard(this) != -1) {
            wasAdded = true;
        } else {
            wasAdded = aScenario.addWeaponCard(this);
            if (!wasAdded) {
                scenarios.remove(aScenario);
            }
        }
        return wasAdded;
    }

    /* Code from template association_RemoveMany */
    public boolean removeScenario(Scenario aScenario) {
        boolean wasRemoved = false;
        if (!scenarios.contains(aScenario)) {
            return wasRemoved;
        }

        int oldIndex = scenarios.indexOf(aScenario);
        scenarios.remove(oldIndex);
        if (aScenario.indexOfWeaponCard(this) == -1) {
            wasRemoved = true;
        } else {
            wasRemoved = aScenario.removeWeaponCard(this);
            if (!wasRemoved) {
                scenarios.add(oldIndex, aScenario);
            }
        }
        return wasRemoved;
    }

    /* Code from template association_AddIndexControlFunctions */
    public boolean addScenarioAt(Scenario aScenario, int index) {
        boolean wasAdded = false;
        if (addScenario(aScenario)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfScenarios()) {
                index = numberOfScenarios() - 1;
            }
            scenarios.remove(aScenario);
            scenarios.add(index, aScenario);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveScenarioAt(Scenario aScenario, int index) {
        boolean wasAdded = false;
        if (scenarios.contains(aScenario)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfScenarios()) {
                index = numberOfScenarios() - 1;
            }
            scenarios.remove(aScenario);
            scenarios.add(index, aScenario);
            wasAdded = true;
        } else {
            wasAdded = addScenarioAt(aScenario, index);
        }
        return wasAdded;
    }

    public void delete() {
        ArrayList<Scenario> copyOfScenarios = new ArrayList<Scenario>(scenarios);
        scenarios.clear();
        for (Scenario aScenario : copyOfScenarios) {
            aScenario.removeWeaponCard(this);
        }
    }


    /**
     * Unable to update umple code due to error at null
     * Unable to update umple code due to error at null
     * Unable to update umple code due to error at null
     * Unable to update umple code due to error at null
     */
    @Override
    public String toString() {
        return "";
    }

}