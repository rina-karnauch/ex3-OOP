import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * a class to test class Locker
 *
 * @author rina.karnauch
 */
public class LockerTest {

    /*
    magic number for indexes
     */
    private final static int SUCCESS_RETURN = 0;
    /*
    magic number for indexes
     */
    private final static int MOVE_TO_LONG_TERM_WARNING = 1;

    /*
    magic number for return value
     */
    private final static int NO_ROOM_ERROR = -1;
    /*
    magic number for return value
     */
    private final static int CONTRADICTION_ERROR = -2;

    /*
    string of a known object
     */
    private final static String BASEBALL_BAT = "baseball bat";

    /*
    string of a known object
     */
    private final static String SPORES_ENGINE = "spores engine";

    /*
    string of a known object
     */
    private final static String FOOTBALL = "football";

    /*
    string of a known object
     */
    private final static String HELMET_SIZE_1 = "helmet, size 1";

    /*
    string of a known object
     */
    private final static String HELMET_SIZE_3 = "helmet, size 3";


    /**
     * a test for addItem to locker, when amount of items is too big.
     */
    @Test
    public void testAddItemNotEnoughSpace() {
        int nForTest;
        Item[][] constrains = ItemFactory.getConstraintPairs();
        LongTermStorage lts = new LongTermStorage();

        int capacity = 100;
        Item item = ItemFactory.createSingleItem(BASEBALL_BAT);
        Locker lockerTest = new Locker(lts, capacity, constrains);

        // no room for n items, because can hold less.
        int value = capacity / item.getVolume();
        nForTest = value + 2;

        assertEquals(NO_ROOM_ERROR, lockerTest.addItem(item, nForTest));
    }

    /**
     * a test for addItem of locker, when amount is legal.
     */
    @Test
    public void testAddItemLegalActions() {

        Item[][] constrains = ItemFactory.getConstraintPairs();
        LongTermStorage lts = new LongTermStorage();

        int capacity = 100;
        Item item = ItemFactory.createSingleItem(BASEBALL_BAT);
        Locker lockerTest = new Locker(lts, capacity, constrains);

        // nothing in our locker yet.
        // we add 20 of some kind with volume 2, so we are able to do so
        int toAdd = 20; // we will have 40 at capacity.
        assertEquals(SUCCESS_RETURN, lockerTest.addItem(item, toAdd));
        assertEquals(60, lockerTest.getAvailableCapacity());
    }

    /**
     * a test for addItem of locker, when trying to add when not enough space is available.
     */
    @Test
    public void testAddItemNotEnoughSpaceAfterAdding() {
        int nForTest;
        Item[][] constrains = ItemFactory.getConstraintPairs();
        LongTermStorage lts = new LongTermStorage();

        int capacity = 100;
        Item item = ItemFactory.createSingleItem("baseball bat");
        Locker lockerTest = new Locker(lts, capacity, constrains);

        // nothing in our locker yet.
        // we add 20 of some kind with volume 2, so we are able to do so
        int toAdd = 20; // we will have 40 at capacity.
        assertEquals(SUCCESS_RETURN, lockerTest.addItem(item, toAdd));
        assertEquals(60, lockerTest.getAvailableCapacity());

        // now we decided to add 80 more, but we can't, out of space
        nForTest = 80;
        assertEquals(NO_ROOM_ERROR, lockerTest.addItem(item, nForTest));
        assertEquals(60, lockerTest.getAvailableCapacity());
    }

    /**
     * a test for addItem of locker, checking if moving to long term was a success.
     */
    @Test
    public void testAddItemAndCheckLongStorageMovement() {
        Item[][] constrains = ItemFactory.getConstraintPairs();
        LongTermStorage lts = new LongTermStorage();

        int capacity = 100;
        Item item = ItemFactory.createSingleItem(BASEBALL_BAT);
        Locker lockerTest = new Locker(lts, capacity, constrains);

        // nothing in our locker yet.
        // we add 20 of some kind with volume 2, so we are able to do so
        int toAdd = 20; // we will have 40 at capacity.
        assertEquals(SUCCESS_RETURN, lockerTest.addItem(item, toAdd));
        int itemVolume = item.getVolume();
        int takenVolume = itemVolume * toAdd;
        assertEquals(capacity - takenVolume, lockerTest.getAvailableCapacity());

        // now let's try and add more(more than 50%), we can, but we should move it to the long storage and return 1.
        int nForTest = 20;
        assertEquals(MOVE_TO_LONG_TERM_WARNING, lockerTest.addItem(item, nForTest)); // we will have 40 at capacity. 30 moved.
        // check if long term storage is okay
        assertEquals(940, lts.getAvailableCapacity());
        assertEquals(80, lockerTest.getAvailableCapacity());

    }

    /**
     * a test for addItem to locker, when amount of items in lts will be overflowed.
     */
    @Test
    public void testAddItemLongTermStorageFullError() {

        Item[][] constrains = ItemFactory.getConstraintPairs();
        LongTermStorage lts = new LongTermStorage();

        int capacity = 100;
        Item item0 = ItemFactory.createSingleItem(BASEBALL_BAT);
        Locker lockerTest = new Locker(lts, capacity, constrains);

        // nothing in our locker yet.
        // we add 20 of some kind with volume 2, so we are able to do so
        int toAdd = 20; // we will have 40 at capacity.
        assertEquals(SUCCESS_RETURN, lockerTest.addItem(item0, toAdd));
        assertEquals(60, lockerTest.getAvailableCapacity());

        // now let's try and add more(more than 50%), we can, but we should move it to the long storage and return 1.
        int nForTest = 20;
        assertEquals(MOVE_TO_LONG_TERM_WARNING, lockerTest.addItem(item0, nForTest)); // we will have 40 at capacity. 30 moved.
        // check if long term storage is okay
        assertEquals(940, lts.getAvailableCapacity());
        assertEquals(80, lockerTest.getAvailableCapacity());

        // now we have 10 of item in our locker, we will have 60 in our lts.
        //
        lockerTest.removeItem(item0, 5);
        Item item4 = ItemFactory.createSingleItem(SPORES_ENGINE); // volume is 10.
        nForTest = 5; // let's add till we have a lot of them in our LTS.
        for (int i = 0; i < 19; i++) {
            lockerTest.addItem(item4, nForTest);
        }
        // now we can't add anything, no space in LTS
        // let's try and add to it.
        nForTest = 8;
        assertEquals(NO_ROOM_ERROR, lockerTest.addItem(item4, nForTest));
    }

    /**
     * a test to check adding item with constrains.
     */
    @Test
    public void testAddItemCheckConstrains() {
        Item[][] constrains = ItemFactory.getConstraintPairs();
        LongTermStorage lts = new LongTermStorage();

        int capacity = 300;

        Locker lockerTest = new Locker(lts, capacity, constrains);
        Item item = ItemFactory.createSingleItem(BASEBALL_BAT);
        assertEquals(SUCCESS_RETURN, lockerTest.addItem(item, 1));

        // nothing will be moved to the long term, enough space.
        assertEquals(1000, lts.getAvailableCapacity());
        assertEquals(298, lockerTest.getAvailableCapacity());

        // let's add the constricted item
        Item constricted = ItemFactory.createSingleItem(FOOTBALL);
        // should fail because constricted.
        assertEquals(CONTRADICTION_ERROR, lockerTest.addItem(constricted, 1));

        // check for a constrain before negative
        assertEquals(CONTRADICTION_ERROR, lockerTest.addItem(constricted, -5));
    }

    /**
     * a test for adding negative value to add item to locker.
     */
    @Test
    public void testAddItemNegativeValue() {

        Item[] legalItems = ItemFactory.createAllLegalItems();
        Item[][] constrains = ItemFactory.getConstraintPairs();
        LongTermStorage lts = new LongTermStorage();
        int capacity = 100;
        Locker lockerTest = new Locker(lts, capacity, constrains);

        int nForTest = -5;
        // all items get negative values to add, so we can't add them.
        for (Item item : legalItems) {
            assertEquals(NO_ROOM_ERROR, lockerTest.addItem(item, nForTest));
            nForTest -= 7;
        }

        Item item = ItemFactory.createSingleItem(SPORES_ENGINE);
        // check for negative amount of items to add
        // checking two types of negatives.
        int negative = -5;
        assertEquals(NO_ROOM_ERROR, lockerTest.addItem(item, -1));
        assertEquals(NO_ROOM_ERROR, lockerTest.addItem(item, negative));
    }

    /**
     * a test for addItem to locker with manipulations
     */
    @Test
    public void testAddItem() {

        Item[][] constrains = ItemFactory.getConstraintPairs();
        LongTermStorage lts = new LongTermStorage();

        // no room for n items, because can hold less.
        int capacity = 300;
        Item item1 = ItemFactory.createSingleItem(BASEBALL_BAT);
        Locker lockerTest = new Locker(lts, capacity, constrains);

        // nothing in our locker yet.
        // we add 50 of some kind with volume 2, so we are able to do so
        int toAdd = 50; // we will have 100 at capacity.
        assertEquals(SUCCESS_RETURN, lockerTest.addItem(item1, toAdd));
        assertEquals(200, lockerTest.getAvailableCapacity());

        // now we decided to add 150 more, but we can't, out of space
        int nForTest = 150;
        assertEquals(NO_ROOM_ERROR, lockerTest.addItem(item1, nForTest));
        assertEquals(200, lockerTest.getAvailableCapacity());

        // now let's try and add more(more than 50%), we can, but we should move it to the long storage and return 1.
        nForTest = 30;
        assertEquals(MOVE_TO_LONG_TERM_WARNING, lockerTest.addItem(item1, nForTest)); // we will have 160 at capacity, so we will need to
        // we need to be left with 60 volume, so we will move 100.
        // check if long term storage is okay
        assertEquals(900, lts.getAvailableCapacity());
        assertEquals(240, lockerTest.getAvailableCapacity());

        // now we have 30 of item in our locker, we will have 50 in our lts.
        //
        lockerTest.removeItem(item1, 5);
        // we removed 5 items, so now we have 50 volume, 25 items.
        Item item4 = ItemFactory.createSingleItem(SPORES_ENGINE); // volume is 10.
        nForTest = 5; // let's add till we have a lot of them in our LTS.
        for (int i = 0; i < 19; i++) {
            lockerTest.addItem(item4, nForTest);
        }

        // now we can't add anything, no space in LTS
        // let's try and add to it.
        nForTest = 8;
        assertEquals(NO_ROOM_ERROR, lockerTest.addItem(item4, nForTest));
        lts.resetInventory();

        // ---------------------- new locker ---------------------- //

        // let's try and check constrains
        lockerTest = new Locker(lts, capacity, constrains);
        Item item = ItemFactory.createSingleItem(FOOTBALL);// volume is 4.
        assertEquals(SUCCESS_RETURN, lockerTest.addItem(item, 1));
        // nothing will be deleted from long term.
        assertEquals(1000, lts.getAvailableCapacity());
        assertEquals(296, lockerTest.getAvailableCapacity());
        // let's add the constricted item
        Item constricted = ItemFactory.createSingleItem(BASEBALL_BAT);
        // should fail because constricted.
        assertEquals(CONTRADICTION_ERROR, lockerTest.addItem(constricted, 1));

        // check for a constrain before negative
        assertEquals(CONTRADICTION_ERROR, lockerTest.addItem(constricted, -34));

        // check for negative amount of items to add
        // checking two types of negatives.
        Item item2 = ItemFactory.createSingleItem(HELMET_SIZE_1);
        Item item3 = ItemFactory.createSingleItem(HELMET_SIZE_3);
        int negative = -2;
        assertEquals(NO_ROOM_ERROR, lockerTest.addItem(item2, -1));
        assertEquals(NO_ROOM_ERROR, lockerTest.addItem(item3, negative));
    }

    /**
     * a test to remove n items from an empty locker
     */
    @Test
    public void testRemoveItemEmptyLocker() {
        int capacity = 100;
        Item[][] constrains = ItemFactory.getConstraintPairs();
        LongTermStorage lts = new LongTermStorage();

        // removing from empty locker amount of more than 0 should fail.
        Locker lockerTest = new Locker(lts, capacity, constrains);

        Item item1 = ItemFactory.createSingleItem(BASEBALL_BAT);
        int amount = 5;
        assertEquals(NO_ROOM_ERROR, lockerTest.removeItem(item1, amount));
    }

    /**
     * a test to remove 0 items from an empty locker
     */
    @Test
    public void testRemoveItemZeroAmountEmptyLocker() {
        int capacity = 100;
        Item[][] constrains = ItemFactory.getConstraintPairs();
        LongTermStorage lts = new LongTermStorage();

        Locker lockerTest = new Locker(lts, capacity, constrains);

        Item item1 = ItemFactory.createSingleItem(BASEBALL_BAT);
        // removing from empty, zero amount should success.
        assertEquals(SUCCESS_RETURN, lockerTest.removeItem(item1, 0));
    }

    /**
     * a test to remove n items when there are less than n inside.
     */
    @Test
    public void testRemoveItemMoreThanExists() {
        int capacity = 100;
        Item[][] constrains = ItemFactory.getConstraintPairs();
        LongTermStorage lts = new LongTermStorage();

        Locker lockerTest = new Locker(lts, capacity, constrains);
        Item item1 = ItemFactory.createSingleItem(BASEBALL_BAT);
        int n = 20;

        // let's add some Items
        lockerTest.addItem(item1, n);
        // if we delete more than 20, should fail.
        assertEquals(NO_ROOM_ERROR, lockerTest.removeItem(item1, 30));
    }

    /**
     * a test to remove negative amount of items
     */
    @Test
    public void testRemoveItemNegativeAmount() {
        int capacity = 100;
        Item[][] constrains = ItemFactory.getConstraintPairs();
        LongTermStorage lts = new LongTermStorage();

        Locker lockerTest = new Locker(lts, capacity, constrains);
        Item item1 = ItemFactory.createSingleItem(BASEBALL_BAT);

        int n = -15;
        assertEquals(NO_ROOM_ERROR, lockerTest.removeItem(item1, n));
    }

    /**
     * a test to remove n items when there are more than n in the locker, but not n of item of that kind
     */
    @Test
    public void testRemoveItemNotEnoughItemFromType() {
        int capacity = 100;
        Item[][] constrains = ItemFactory.getConstraintPairs();
        LongTermStorage lts = new LongTermStorage();

        Locker lockerTest = new Locker(lts, capacity, constrains);
        Item item1 = ItemFactory.createSingleItem(BASEBALL_BAT);
        Item item2 = ItemFactory.createSingleItem(HELMET_SIZE_1);

        lockerTest.addItem(item1, 20);
        lockerTest.addItem(item2, 10);

        assertEquals(NO_ROOM_ERROR, lockerTest.removeItem(item1, 25));
    }

    /**
     * a test to remove items, legally
     */
    @Test
    public void testRemoveItemLegal() {
        int capacity = 100;
        Item[][] constrains = ItemFactory.getConstraintPairs();
        LongTermStorage lts = new LongTermStorage();

        Locker lockerTest = new Locker(lts, capacity, constrains);
        Item item1 = ItemFactory.createSingleItem(BASEBALL_BAT);

        lockerTest.addItem(item1, 20);
        assertEquals(SUCCESS_RETURN, lockerTest.removeItem(item1, 10));
        assertEquals(SUCCESS_RETURN, lockerTest.removeItem(item1, 10));
    }

    /**
     * a test to check that the count for each item is ZERO while initialization
     */
    @Test
    public void testGetItemCountZeroAtInit() {
        Item[] items = ItemFactory.createAllLegalItems();

        Item[][] constrains = ItemFactory.getConstraintPairs();
        LongTermStorage lts = new LongTermStorage();
        int capacity = 200;
        Locker lockerTest = new Locker(lts, capacity, constrains);

        for (Item i : items) {
            String type = i.getType();
            assertEquals(0, lockerTest.getItemCount(type));
        }
    }

    /**
     * a test to check that we get the right item count for visible items.
     */
    @Test
    public void testGetItemCountExistingItem() {
        Item item1 = ItemFactory.createSingleItem(BASEBALL_BAT); // volume is 2

        Item[][] constrains = ItemFactory.getConstraintPairs();
        LongTermStorage lts = new LongTermStorage();
        int capacity = 200;
        Locker lockerTest = new Locker(lts, capacity, constrains);

        lockerTest.addItem(item1, 20);
        String type1 = item1.getType();
        assertEquals(20, lockerTest.getItemCount(type1));
    }

    /**
     * a test to check that we get no count of a none existing item.
     */
    @Test
    public void testGetItemCountNoneExistingItem() {
        Item item1 = ItemFactory.createSingleItem(BASEBALL_BAT); // volume is 2
        Item item2 = ItemFactory.createSingleItem(HELMET_SIZE_3); // volume is 5

        Item[][] constrains = ItemFactory.getConstraintPairs();
        LongTermStorage lts = new LongTermStorage();
        int capacity = 200;
        Locker lockerTest = new Locker(lts, capacity, constrains);

        lockerTest.addItem(item1, 20);

        String type2 = item2.getType();
        assertEquals(0, lockerTest.getItemCount(type2));
    }

    /**
     * a test to check if constricted item count is always zero when one of them is inside.
     */
    @Test
    public void testGetItemCountConstrainCountIsAlwaysZero() {
        Item item1 = ItemFactory.createSingleItem(BASEBALL_BAT); // volume is 2
        Item item5 = ItemFactory.createSingleItem(FOOTBALL); // constricted with item1

        Item[][] constrains = ItemFactory.getConstraintPairs();
        LongTermStorage lts = new LongTermStorage();
        int capacity = 200;
        Locker lockerTest = new Locker(lts, capacity, constrains);

        lockerTest.addItem(item1, 20);

        String type5 = item5.getType();
        assertEquals(0, lockerTest.getItemCount(type5));

        lockerTest.addItem(item5, 20);
        assertEquals(0, lockerTest.getItemCount(type5));
    }


    /**
     * a test for legal actions and checking their counts after removing and adding and etc'
     */
    @Test
    public void testGetItemManipulations() {
        Item item1 = ItemFactory.createSingleItem(BASEBALL_BAT); // volume is 2
        Item item2 = ItemFactory.createSingleItem(HELMET_SIZE_1); // volume is 3

        Item[][] constrains = ItemFactory.getConstraintPairs();
        LongTermStorage lts = new LongTermStorage();

        int capacity = 200;
        Locker lockerTest = new Locker(lts, capacity, constrains);

        lockerTest.addItem(item1, 5); // 10 volumed for all cost
        lockerTest.addItem(item2, 7); // 21 volumed for all cost

        // at all available capacity should be 169
        assertEquals(169, lockerTest.getAvailableCapacity());

        // amount to be
        assertEquals(5, lockerTest.getItemCount(item1.getType()));
        // wrong amount
        assertFalse(lockerTest.getItemCount(item1.getType()) != 5);
        // amount to be
        assertEquals(7, lockerTest.getItemCount(item2.getType()));
        // wrong amount
        assertFalse(lockerTest.getItemCount(item2.getType()) != 7);

        // let's move to the lst and check if okay
        lockerTest.addItem(item1, 50); // let's add 100 volume to item1, now we will need to reduce the amount
        // we will be left with
        assertEquals(20, lockerTest.getItemCount(item1.getType()));
        assertFalse(lockerTest.getItemCount(item1.getType()) != 20);

        lockerTest.removeItem(item1, 20);
        // after removing we shall have 0.
        assertEquals(0, lockerTest.getItemCount(item1.getType()));

        lockerTest.removeItem(item2, 7);
        // same test for that too
        assertEquals(0, lockerTest.getItemCount(item2.getType()));
    }

    /**
     * test for checking getters are not returning null
     */
    @Test
    public void testGettersNotNUll() {
        Item[][] constrains = ItemFactory.getConstraintPairs();
        LongTermStorage lts = new LongTermStorage();

        int capacity = 100;
        Locker lockerTest = new Locker(lts, capacity, constrains);

        assertNotNull(lockerTest.getInventory());
    }

    /**
     * test for an empty locker inventory
     */
    @Test
    public void testGetInventoryEmptyLocker() {
        Item[][] constrains = ItemFactory.getConstraintPairs();
        LongTermStorage lts = new LongTermStorage();

        int capacity = 100;
        Locker lockerTest = new Locker(lts, capacity, constrains);

        Map<String, Integer> inventory = lockerTest.getInventory();

        // nothing at the map, so empty.
        assertTrue(inventory.isEmpty());
    }

    /**
     * a test for constricted item count in getInventory
     */
    @Test
    public void testGetInventoryConstrains() {

        Item item1 = ItemFactory.createSingleItem(BASEBALL_BAT);
        Item item5 = ItemFactory.createSingleItem(FOOTBALL);

        int length = ItemFactory.createAllLegalItems().length;

        Item[][] constrains = ItemFactory.getConstraintPairs();
        LongTermStorage lts = new LongTermStorage();

        int capacity = 100;
        Locker lockerTest = new Locker(lts, capacity, constrains);

        lockerTest.addItem(item1, 5);
        lockerTest.addItem(item5, 1); // constricted

        Map<String, Integer> inventory = lockerTest.getInventory();

        assertTrue((!inventory.containsKey(item5.getType())) || (inventory.get(item5.getType()) == 0));
        // two because put restriction as 0 always. possible implementation.
        assertTrue(inventory.size() == 1 || inventory.size() == length || inventory.size() == 2);
    }

    /**
     * a function to test locker's getInventory function, after manipulations
     */
    @Test
    public void testGetInventoryManipulations() {

        Item item1 = ItemFactory.createSingleItem(BASEBALL_BAT);
        Item item2 = ItemFactory.createSingleItem(HELMET_SIZE_1);
        Item item3 = ItemFactory.createSingleItem(HELMET_SIZE_3);
        Item item4 = ItemFactory.createSingleItem(SPORES_ENGINE);
        Item item5 = ItemFactory.createSingleItem(FOOTBALL);

        int length = ItemFactory.createAllLegalItems().length;

        Item[][] constrains = ItemFactory.getConstraintPairs();
        LongTermStorage lts = new LongTermStorage();

        int capacity = 100;
        Locker lockerTest = new Locker(lts, capacity, constrains);

        lockerTest.addItem(item1, 5);
        lockerTest.addItem(item2, 7);
        lockerTest.addItem(item3, 10);
        lockerTest.addItem(item4, 1);

        Map<String, Integer> inventory = lockerTest.getInventory();
        int inventorySize = inventory.size();

        // size should be 4 because we entered 4 elements, or all legal items.
        assertTrue(inventorySize == 4 || inventorySize == length);

        lockerTest.removeItem(item3, 9); // one item left.
        lockerTest.removeItem(item2, 7); // nothing of it left.

        // update map.
        inventory = lockerTest.getInventory();
        inventorySize = inventory.size();
        assertTrue(inventorySize == 4 || inventorySize == 3 || inventorySize == length);

        // let's check existences.
        String type2 = item2.getType();
        assertTrue(!inventory.containsKey(type2) || inventory.get(type2) == 0);

        // we removed- should be 0.
        // or zero or doesn't contain.
        assertTrue((!inventory.containsKey(type2)) || inventory.get(type2) == 0);

        // can't be inside, restricted by item #0.
        String type5 = item5.getType();
        assertFalse(inventory.containsKey(type5));

        // let's see if it's mapped correctly
        String type4 = item4.getType();
        assertEquals(MOVE_TO_LONG_TERM_WARNING, (int) inventory.get(type4));

        // another same check
        String type1 = item1.getType();
        assertEquals(5, (int) inventory.get(type1));

        // we have 5 of volume 5, therefore adding 6 more will get us to 65% of the capacity.
        // we will have to reduce it, and move it to another place- LTS.
        // we will have later 11 items of kind #2, volumed 5-55. we will have to reduce it to 20%- which is to 20 volume
        // which would be 20/5= 4 items left.
        lockerTest.addItem(item4, 6);
        // we added 30 more volume to 25 of baseball bat, we need to reduce it.
        inventory = lockerTest.getInventory();
        assertEquals(2, (int) inventory.get(type4));
    }

    /**
     * a function to test locker's getCapacity function.
     */
    @Test
    public void testGetCapacity() {
        Item[][] constrains = ItemFactory.getConstraintPairs();
        LongTermStorage lts = new LongTermStorage();

        int capacity = 100;
        Locker lockerTest = new Locker(lts, capacity, constrains);

        assertEquals(100, lockerTest.getCapacity());

        lockerTest = new Locker(lts, 0, constrains);
        assertEquals(SUCCESS_RETURN, lockerTest.getCapacity());
    }

    /**
     * test to check that everything is available when initialization of a locker
     */
    @Test
    public void testGetAvailableCapacityInit() {
        Item[][] constrains = ItemFactory.getConstraintPairs();
        LongTermStorage lts = new LongTermStorage();

        int capacity = 400;
        Locker lockerTest = new Locker(lts, capacity, constrains);
        assertEquals(capacity, lockerTest.getAvailableCapacity());
    }

    /**
     * test to check that inserting unwanted values doesn't change the available capacity.
     */
    @Test
    public void testGetAvailableCapacityIllegalAdding() {
        Item[][] constrains = ItemFactory.getConstraintPairs();
        LongTermStorage lts = new LongTermStorage();

        Item item = ItemFactory.createSingleItem(FOOTBALL); // volume is 4

        int capacity = 400;
        Locker lockerTest = new Locker(lts, capacity, constrains);

        int illegalNumber = 120;
        lockerTest.addItem(item, illegalNumber);
        assertEquals(capacity, lockerTest.getAvailableCapacity());

        illegalNumber = -5;
        lockerTest.addItem(item, illegalNumber);
        assertEquals(capacity, lockerTest.getAvailableCapacity());
    }

    /**
     * test of removing unknown item, won't change available capacity.
     */
    @Test
    public void testGetAvailableCapacityIllegalRemoving() {
        Item[][] constrains = ItemFactory.getConstraintPairs();
        LongTermStorage lts = new LongTermStorage();

        Item item = ItemFactory.createSingleItem(FOOTBALL); // volume is 4

        int capacity = 400;
        Locker lockerTest = new Locker(lts, capacity, constrains);

        // illegal amount for removing, nothing inside.
        int illegalNumber = -120;
        lockerTest.removeItem(item, illegalNumber);
        assertEquals(capacity, lockerTest.getAvailableCapacity());

        lockerTest.addItem(item, 20); // will take 80 volume.
        // we will have 320 left.
        assertEquals(320, lockerTest.getAvailableCapacity());

        illegalNumber = -120;
        lockerTest.removeItem(item, illegalNumber);
        assertEquals(320, lockerTest.getAvailableCapacity());

        // it shall not remove, not enough.
        illegalNumber = 30;
        lockerTest.removeItem(item, illegalNumber);
        assertEquals(320, lockerTest.getAvailableCapacity());
    }

    /**
     * a test to check that constrain adding won't differ available capacity
     */
    @Test
    public void testGetAvailableCapacityConstrainAdding() {
        Item[][] constrains = ItemFactory.getConstraintPairs();
        LongTermStorage lts = new LongTermStorage();

        Item item = ItemFactory.createSingleItem(FOOTBALL); // volume is 4
        Item constricted = ItemFactory.createSingleItem(BASEBALL_BAT); // volume is 2

        int capacity = 300;
        Locker lockerTest = new Locker(lts, capacity, constrains);

        lockerTest.addItem(item, 20); // will take 80, we left with 220
        assertEquals(220, lockerTest.getAvailableCapacity());

        lockerTest.addItem(constricted, 10);
        assertEquals(220, lockerTest.getAvailableCapacity());
        lockerTest.addItem(constricted, 1);
        assertEquals(220, lockerTest.getAvailableCapacity());
    }

    /**
     * a function to test locker's getAvailableCapacity function, general manipulations
     */
    @Test
    public void testGetAvailableCapacityManipulations() {

        Item item1 = ItemFactory.createSingleItem(BASEBALL_BAT);
        Item item3 = ItemFactory.createSingleItem(HELMET_SIZE_3);
        Item item5 = ItemFactory.createSingleItem(FOOTBALL);

        Item[][] constrains = ItemFactory.getConstraintPairs();
        LongTermStorage lts = new LongTermStorage();

        int capacity = 400;
        Locker lockerTest = new Locker(lts, capacity, constrains);

        lockerTest.addItem(item1, 20);
        // we added 20-> which took 40, so now its 390
        assertEquals(360, lockerTest.getAvailableCapacity());

        // we added constricted item, so still 360.
        lockerTest.addItem(item5, 10);
        assertEquals(360, lockerTest.getAvailableCapacity());

        // we added 10 items of 5-> 360-50=310
        lockerTest.addItem(item3, 10);
        assertEquals(310, lockerTest.getAvailableCapacity());

        lockerTest.addItem(item3, 40);
        //  we added 40 items of volume 5, so 200 volume, we already had 50 volume, so now we have 250 volume
        // now we need to move it to the LTS, reduce it to 20% out of 400 which is 80 volume.
        // 80 volume of volume 5 is 16 items that would be left-> we reduced in 34, so we will be left with
        // we have without 250, 360 occupied, now we reduce 16x5-> we need to be left with 280.
        assertEquals(280, lockerTest.getAvailableCapacity());

        // we remove item, so place left
        lockerTest.removeItem(item1, 10);
        assertEquals(300, lockerTest.getAvailableCapacity());

        // check if they complete each other,
        int checkMinus = lockerTest.getCapacity() - lockerTest.getAvailableCapacity();
        assertEquals(100, checkMinus);

    }
}
