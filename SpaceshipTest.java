import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * a class to test class Spaceship
 *
 * @author rina.karnauch
 */
public class SpaceshipTest {

    /*
    return on success in action
     */
    private final static int SUCCESS_RETURN = 0;

    /*
    magic number for indexes
     */
    private final static int FAILURE_RETURN_CREW_ID_MISSING = -1;
    /*
    magic number for indexes
     */
    private final static int FAILURE_RETURN_NEGATIVE_CAPACITY = -2;
    /*
    magic number for indexes
     */
    private final static int FAILURE_RETURN_NO_LOCKERS_LEFT = -3;
    /*
    magic number for indexes
     */
    private final static int LONG_TERM_CAPACITY = 1000;

    /*
    field for Test class
     */
    private Spaceship testShip;

    /**
     * init an object for the testShip class
     */
    @Before
    public void init() {
        String name = "TestShip";
        Item[][] constrains = ItemFactory.getConstraintPairs();
        int[] crewIDs = {319132353, 387476465, 298376475, 109873984, 342987564, 123987654,
                234234234, 556678932, 345426776, 78798345};
        int numOfLockers = 5;

        testShip = new Spaceship(name, crewIDs, numOfLockers, constrains);
    }

    /**
     * a test to check the Spaceship's getLongTermStorage function.
     */
    @Test
    public void testGetLongTermStorage() {
        init();
        // check if the type we are getting is correct, and is not null.
        assertNotNull(testShip.getLongTermStorage());
        // we haven't done anything it should be empty, all capacity available.
        assertEquals(LONG_TERM_CAPACITY, testShip.getLongTermStorage().getAvailableCapacity());
    }

    /**
     * a test to check the Spaceship's createLocker function with negative capacity
     */
    @Test
    public void testCreateLockerNegativeCapacity() {
        init();
        // check for negative capacity which is not meeting up with the locker class needs.
        int negativeCapacity = -5;
        int existentID = 387476465;
        assertEquals(FAILURE_RETURN_NEGATIVE_CAPACITY, testShip.createLocker(existentID, negativeCapacity));

        // check afterwards both problems, none existent and negative.
        // -1 is before -2
        int nonexistentID = 111111111;
        assertEquals(FAILURE_RETURN_CREW_ID_MISSING, testShip.createLocker(nonexistentID, negativeCapacity));
    }

    /**
     * a test to check zero lockers on spaceship
     */
    @Test
    public void testZeroLockers() {
        String name = "TestShip";
        Item[][] constrains = ItemFactory.getConstraintPairs();
        int[] crewIDs = {319132353, 387476465, 298376475, 109873984, 342987564, 123987654,
                234234234, 556678932, 345426776, 78798345};
        int numOfLockers = 0;

        this.testShip = new Spaceship(name, crewIDs, numOfLockers, constrains);
        Locker[] lockers = testShip.getLockers();
        Item[] items = ItemFactory.createAllLegalItems();
        for (Item i : items) {
            for (Locker l : lockers) {
                assertEquals(FAILURE_RETURN_CREW_ID_MISSING, l.addItem(i, 7));
            }
        }
    }

    /**
     * a test to check the Spaceship's createLocker function with none existent ID
     */
    @Test
    public void testCreateLockerNoneExistentID() {
        init();

        // check creating locker for none existent ID, and good capacity.
        int capacity = 6;
        int noneExistentID = 111111111;
        assertEquals(FAILURE_RETURN_CREW_ID_MISSING, testShip.createLocker(noneExistentID, capacity));

        // check creating locker for none existent ID, and not good capacity.
        capacity = -3;
        assertEquals(FAILURE_RETURN_CREW_ID_MISSING, testShip.createLocker(noneExistentID, capacity));
    }

    /**
     * a test to check the Spaceship's createLocker function, that creating is successful under legal terms.
     */
    @Test
    public void testCreateLockerLegalValues() {
        // init spaceship and lockers and etc'.
        init();
        Locker[] lockers = testShip.getLockers();
        int numOfLockers = lockers.length;
        int[] crewIDs = testShip.getCrewIDs();

        // randomizing locker positive capacity, with existent IDS.
        for (int i = 0; i < numOfLockers; i++) {
            Random rand = new Random();
            int randomLockerCapacity = rand.nextInt(10);
            randomLockerCapacity += 100; // making it from 100-110.
            // should be created with success, good values.
            assertEquals(SUCCESS_RETURN, testShip.createLocker(crewIDs[i], randomLockerCapacity));
        }
    }

    /**
     * a test to check weather constrains of spaceship are equal to the lockers.
     */
    @Test
    public void testCreateLockerSimilarConstrains() {
        init();

        Item[][] constrains = ItemFactory.getConstraintPairs();
        Locker[] lockers = testShip.getLockers();
        int[] crewIDs = testShip.getCrewIDs();
        int numOfLockers = lockers.length;

        for (int i = 0; i < numOfLockers; i++) {
            int randomLockerCapacity = 100;
            // should be created with success, good values.
            this.testShip.createLocker(crewIDs[i], randomLockerCapacity);
        }

        for (Locker l : lockers) {
            if (l != null) {
                for (Item[] constrain : constrains) {
                    l.addItem(constrain[0], 1);
                    assertEquals(FAILURE_RETURN_NEGATIVE_CAPACITY, l.addItem(constrain[1], 1));
                }
            }
        }
    }

    /**
     * a test to check if createLocker created a fixed sized locker as needed, and insertion is according to the statement:
     * The initialized Locker instances should appear at the beginning of the array
     * // (i.e. if the array is not full, the last elements should have the default value).
     */
    @Test
    public void testCreateLockerFixedSize() {
        init();
        Locker[] lockers = testShip.getLockers();

        // it's length is 5 even though its empty.
        assertEquals(5, lockers.length);
        int[] crewIDs = testShip.getCrewIDs();

        for (int i = 0; i < 3; i++) {
            Random rand = new Random();
            int randomLockerCapacity = rand.nextInt(10);
            randomLockerCapacity += 100; // making it from 100-110.
            // should be created with success, good values.
            this.testShip.createLocker(crewIDs[i + 1], randomLockerCapacity);
            assertNotNull(lockers[i]);
        }

        // unoccupied items are null -
        assertNull(lockers[3]);
        assertNull(lockers[4]);
    }

    /**
     * a function to test createLocker function after everything is occupied.
     */
    @Test
    public void testCreateLockerAfterAllOccupied() {
        init();
        Locker[] lockers = testShip.getLockers();
        int numOfLockers = lockers.length;
        int[] crewIDs = testShip.getCrewIDs();
        for (int i = 0; i < numOfLockers; i++) {
            Random rand = new Random();
            int randomLockerCapacity = rand.nextInt(10);
            randomLockerCapacity += 100; // making it from 100-110.
            // should be created with success, good values.
            this.testShip.createLocker(crewIDs[i], randomLockerCapacity);
        }

        // no lockers left, should fail, we are at full amount of lockers created at the ship.
        for (int i = numOfLockers; i < crewIDs.length; i++) {
            assertEquals(FAILURE_RETURN_NO_LOCKERS_LEFT, testShip.createLocker(crewIDs[i], 5));
        }
    }

    /**
     * a function to test the Spaceship's getLockers function.
     */
    @Test
    public void testGetLockersNotNull() {
        init();
        Locker[] lockers = testShip.getLockers();

        // check that is not null, because was created.
        assertNotNull(lockers);
    }

    /**
     * a function to test the Spaceship's getCrewIDS function, and that it is not null.
     */
    @Test
    public void testGetCrewIDsNotNUll() {
        // we created the ship.
        init();
        // we are not null.
        assertNotNull(testShip.getCrewIDs());
    }

    /**
     * a function to test the amount of crew members that it is as we initialized.
     */
    @Test
    public void testGetCrewIDsLength() {
        init();
        // let's check the length, if it's as we intended.
        int[] crewIDS = testShip.getCrewIDs();
        assertEquals(10, crewIDS.length);
    }

    /**
     * a function to test the getters and that they don't return null
     */
    @Test
    public void testNullGetters() {
        init();
        assertNotNull(this.testShip.getCrewIDs());
        assertNotNull(this.testShip.getLockers());
        assertNotNull(this.testShip.getLongTermStorage());
    }

}
