import oop.ex3.spaceship.Item;

/**
 * a class of Spaceship
 *
 * @author rina.karnauch
 */
public class Spaceship {

    /*
    return for success in action
     */
    private final static int SUCCESS_RETURN = 0;

    /*
    magic number for returns
     */
    private final static int FAILURE_RETURN_CREW_ID_MISSING = -1;
    /*
   magic number for returns
    */
    private final static int FAILURE_RETURN_NEGATIVE_CAPACITY = -2;
    /*
   magic number for returns
    */
    private final static int FAILURE_RETURN_NO_LOCKERS_LEFT = -3;

    /*
    field of crew IDs of current spaceship.
     */
    private final int[] crewIDs;

    /*
    field of name of spaceship
     */
    private String name;

    /*
    field of number of lockers in the ship.
     */
    private final int numOfLockers;

    /*
    array of constrains of pairs of items in lockers
     */
    private Item[][] constrains;

    /*
    array of lockers
     */
    private Locker[] lockers;

    /*
    array of locker owners, locker in index i contains the owner of the locker.
     */
    private int[] lockerOwners;

    /*
    number of occupied lockers
     */
    private int occupiedLockers;

    /*
    object of long term storage on ship
     */
    private LongTermStorage longTermStorage;

    /**
     * constructor method for Spaceship class
     *
     * @param name         name fo the spaceship
     * @param crewIDs      array of crew IDS
     * @param numOfLockers number of lockers available in the ship
     * @param constrains   constrains for all lockers
     */
    // in the API
    public Spaceship(String name, int[] crewIDs, int numOfLockers, Item[][] constrains) {

        this.name = name;
        this.crewIDs = crewIDs;
        this.numOfLockers = numOfLockers;
        this.constrains = constrains;
        this.longTermStorage = new LongTermStorage();
        this.lockers = new Locker[numOfLockers];
        this.occupiedLockers = 0;
        this.lockerOwners = new int[numOfLockers];
    }

    /**
     * This method creates a Locker object, and adds it as part of the Spaceships storage.
     *
     * @param crewID   the id of the crew member
     * @param capacity the capacity of the locker
     * @return -1,-2, -3 for failure, 0 for success.
     */
    // in the API
    public int createLocker(int crewID, int capacity) {
        boolean exists = false;
        for (int id : this.crewIDs) {
            if (id == crewID) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            return FAILURE_RETURN_CREW_ID_MISSING;
        } else if (capacity < 0) {  // runtime savior
            return FAILURE_RETURN_NEGATIVE_CAPACITY;
        } else if (this.occupiedLockers == this.numOfLockers) // everything is occupied, runtime savior as well.
        {
            return FAILURE_RETURN_NO_LOCKERS_LEFT;
        } else // values are okay, able to create locker.
        {
            this.lockers[occupiedLockers] = new Locker(this.longTermStorage, capacity, constrains);
            this.lockerOwners[occupiedLockers] = crewID;
            this.occupiedLockers += 1;

            return SUCCESS_RETURN;
        }
    }

    /**
     * This method returns the long-term storage object associated with that Spaceship.
     *
     * @return the long term storage object of the current ship.
     */
    // in the API
    public LongTermStorage getLongTermStorage() {
        return this.longTermStorage;
    }

    /**
     * This methods returns an array with the crews ids.
     *
     * @return array of crew IDs
     */
    public int[] getCrewIDs() {
        return this.crewIDs;
    }

    /**
     * This methods returns an array of the Lockers, whose length is
     *
     * @return array of locker objects lengthened numOfLockers
     */
    // in the API
    public Locker[] getLockers() {
        return this.lockers;
    }

}
