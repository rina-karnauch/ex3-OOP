import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * a class to test class LongTermStorage
 *
 * @author rina.karnauch
 */
public class LongTermTest {

    /*
    magic number for indexes
     */
    private final static int SUCCESS_RETURN = 0;

    /*
    magic number for long term capacity
     */
    private final static int CAPACITY = 1000;

    /*
    magic number for return values
     */
    private final static int NO_ROOM_FAILURE = -1;

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
     * a function to test the longTermStorage's addItem function.
     */
    @Test
    public void testAddItem() {

        LongTermStorage lst = new LongTermStorage();

        // the lst is empty, we can add 1000 items still.
        Item item0 = ItemFactory.createSingleItem(BASEBALL_BAT);

        assertEquals(SUCCESS_RETURN, lst.addItem(item0, 150));

        // constrains don't count here, and we got enough space.
        Item item4 = ItemFactory.createSingleItem(FOOTBALL);
        assertEquals(SUCCESS_RETURN, lst.addItem(item4, 100));

        // trying to add too much
        Item item2 = ItemFactory.createSingleItem(HELMET_SIZE_3);
        assertEquals(NO_ROOM_FAILURE, lst.addItem(item2, 70));

        lst.resetInventory();

        // reset is giving us ability to add more.
        assertEquals(SUCCESS_RETURN, lst.addItem(item2, 150));
        assertEquals(SUCCESS_RETURN, lst.addItem(item4, 60));

        // one more is wrong
        assertEquals(NO_ROOM_FAILURE, lst.addItem(item0, 6));
    }

    /**
     * a test for the the longTermStorage's resetInventory function.
     */
    @Test
    public void testResetInventory() {

        LongTermStorage lst = new LongTermStorage();
        Item[] legalItems = ItemFactory.createAllLegalItems();

        // let's try and reset
        lst.resetInventory();

        // should be zero now, or doesn't contain.
        for (Item legalItem : legalItems) {
            String type = legalItem.getType();
            if (lst.getInventory().containsKey(legalItem.getType())) {
                assertEquals(0, lst.getItemCount(type));
            }
        }
    }

    /**
     * a test to check if a new long term storage is empty of all kinds of items.
     */
    @Test
    public void testGetItemCountEmptyLongTerm() {
        LongTermStorage lst = new LongTermStorage();
        Item[] legalItems = ItemFactory.createAllLegalItems();

        for (Item legalItem : legalItems) {
            String type = legalItem.getType();
            assertEquals(0, lst.getItemCount(type));
        }
    }

    /**
     * a test for the method getItemCount for inserting too much into lst after updating
     */
    @Test
    public void testGetItemCountNoSpaceInsertion() {
        LongTermStorage lst = new LongTermStorage();

        Item item0 = ItemFactory.createSingleItem(BASEBALL_BAT); // volume - 2
        lst.addItem(item0, 300); // 600 occupied

        Item item2 = ItemFactory.createSingleItem(HELMET_SIZE_1); // volume - 3
        lst.addItem(item2, 100); // 900 occupied


        Item item3 = ItemFactory.createSingleItem(HELMET_SIZE_3); // volume- 5
        lst.addItem(item3, 100); // -> will fail

        assertEquals(0, lst.getItemCount(item3.getType()));
    }

    /**
     * a test for the method getItemCount for trying to insert too much  from the beginning
     */
    @Test
    public void testGetItemCountInsertingTooMuch() {
        LongTermStorage lst = new LongTermStorage();
        Item[] items = ItemFactory.createAllLegalItems();

        for (Item item : items) {
            int volume = item.getVolume();
            int illegalInsertionAmount = CAPACITY / volume + 1;
            lst.addItem(item, illegalInsertionAmount);
            assertEquals(0, lst.getItemCount(item.getType()));
        }
    }

    /**
     * a test for the longTermStorage's getItemCount function, with manipulations.
     */
    @Test
    public void testGetItemCount() {

        LongTermStorage lst = new LongTermStorage();

        Item item0 = ItemFactory.createSingleItem(BASEBALL_BAT);
        lst.addItem(item0, 400);
        assertEquals(400, lst.getItemCount(item0.getType()));

        // check after updating
        lst.addItem(item0, 50);
        assertEquals(450, lst.getItemCount(item0.getType()));

        // trying to add too much.
        Item item3 = ItemFactory.createSingleItem(HELMET_SIZE_3);
        lst.addItem(item3, 900);
        assertEquals(450, lst.getItemCount(item0.getType()));
        // shouldn't be added at all
        assertEquals(0, lst.getItemCount(item3.getType()));

        // new items, no constrains of course.
        Item item4 = ItemFactory.createSingleItem(FOOTBALL);
        lst.addItem(item4, 20);
        assertEquals(20, lst.getItemCount(item4.getType()));

        // let's reset and check if empty.
        lst.resetInventory();
        Item[] legalItems = ItemFactory.createAllLegalItems();

        // empty lst is zero amount.
        for (Item legalItem : legalItems) {
            String type = legalItem.getType();
            assertEquals(0, lst.getItemCount(type));
        }
    }

    /**
     * a test to check the inventory is empty at init of lst
     */
    @Test
    public void testGetInventoryEmptyAtInitialization() {
        LongTermStorage lst = new LongTermStorage();
        Item[] legalItems = ItemFactory.createAllLegalItems();

        Map<String, Integer> inventory = lst.getInventory();
        // might have not been initialized because empty lst.
        if (inventory != null) {
            // check that its empty because nothing was added yet.
            for (Item legalItem : legalItems) {
                String type = legalItem.getType();
                int currentAmount = lst.getItemCount(type);
                if (inventory.containsKey(type)) {
                    assertEquals(currentAmount, (int) inventory.get(type));
                }
            }
        }
    }

    /**
     * a function to test the longTermStorage's getInventory function, with manipulations.
     */
    @Test
    public void testGetInventory() {
        LongTermStorage lst = new LongTermStorage();
        Item[] legalItems = ItemFactory.createAllLegalItems();

        Map<String, Integer> inventory;

        // going to be used:
        Item item1 = ItemFactory.createSingleItem(BASEBALL_BAT);

        Item item3 = ItemFactory.createSingleItem(HELMET_SIZE_3);
        String type3 = item3.getType();

        Item item5 = ItemFactory.createSingleItem(FOOTBALL);
        String type5 = item5.getType();

        // check if added correctly
        lst.addItem(item1, 200);
        inventory = lst.getInventory();
        boolean check = (inventory.containsKey(type3) || inventory.containsKey(type5));
        if (check) // check if still zero, for another implementation.
        {
            assertEquals(0, (int) inventory.get(type3));
            assertEquals(0, (int) inventory.get(type5));
        }

        // check another adding
        lst.addItem(item3, 50);
        inventory = lst.getInventory();
        int value = inventory.get(type3);
        assertEquals(50, value);

        // not enough space, not going to change, still zero values
        lst.addItem(item5, 700);
        inventory = lst.getInventory();
        if (inventory.containsKey(type5)) {
            assertEquals(0, (int) inventory.get(type5));
        } else {
            assertFalse(inventory.containsKey(type5));
        }

        // should be able to add afterwards, even though we tried to add 700 before
        lst.addItem(item5, 25);
        inventory = lst.getInventory();
        value = inventory.get(type5);
        assertEquals(25, value);

        lst.resetInventory();
        inventory = lst.getInventory();

        // check that empty again
        for (Item legalItem : legalItems) {
            String type = legalItem.getType();
            int currentAmount = lst.getItemCount(type);
            if (inventory.containsKey(type)) {
                assertEquals(currentAmount, (int) inventory.get(type));
            }
        }
    }

    /**
     * test for getCapacity function at initialization
     */
    @Test
    public void testGetCapacityAtInitialization() {
        LongTermStorage lst = new LongTermStorage();
        assertEquals(CAPACITY, lst.getCapacity());
    }

    /**
     * a function to test the longTermStorage's getCapacity function.
     */
    @Test
    public void testGetCapacity() {

        Item[] legalItems = ItemFactory.createAllLegalItems();
        int[] amounts = {200, 100, -20, 30, 4};
        LongTermStorage lst = new LongTermStorage();

        for (Item item : legalItems) {
            for (int amount : amounts) {
                lst.addItem(item, amount);
                assertEquals(CAPACITY, lst.getCapacity());
            }
        }

        // and even if we reset
        lst.resetInventory();
        assertEquals(CAPACITY, lst.getCapacity());
    }

    /**
     * a test for all empty lst and its available capacity
     */
    @Test
    public void testGetAvailableCapacityEmptyStorage() {
        LongTermStorage lst = new LongTermStorage();
        assertEquals(CAPACITY, lst.getAvailableCapacity());
    }


    /**
     * a test for adding too much wont bother available capacity
     */
    @Test
    public void testGetAvailableTooMuchInsertion() {
        LongTermStorage lst = new LongTermStorage();
        Item[] items = ItemFactory.createAllLegalItems();

        for (Item i : items) {
            lst.addItem(i, CAPACITY + 1);
            assertEquals(CAPACITY, lst.getAvailableCapacity());
        }
    }


    /**
     * a function to test the longTermStorage's getAvailableCapacity function.
     */
    @Test
    public void testGetAvailableCapacity() {
        LongTermStorage lst = new LongTermStorage();

        int newClearCapacity = lst.getAvailableCapacity();

        // adding items would reduce
        Item item1 = ItemFactory.createSingleItem(BASEBALL_BAT);
        lst.addItem(item1, 150); // reduced by 300, 700 left
        assertEquals(newClearCapacity - 300, lst.getAvailableCapacity());

        newClearCapacity = lst.getAvailableCapacity(); // 700

        // again reduction
        Item item5 = ItemFactory.createSingleItem(FOOTBALL);
        lst.addItem(item5, 35); // 35x4=140, 700-140=560
        assertEquals(newClearCapacity - 140, lst.getAvailableCapacity());

        // again reduction
        Item item3 = ItemFactory.createSingleItem(HELMET_SIZE_3);
        newClearCapacity = lst.getAvailableCapacity(); // 560
        lst.addItem(item3, 34); // 34x5=170, 560-170=390
        assertEquals(newClearCapacity - 170, lst.getAvailableCapacity());

        // not legal to add so much, still remains the same
        Item item4 = ItemFactory.createSingleItem(SPORES_ENGINE);
        newClearCapacity = lst.getAvailableCapacity();
        lst.addItem(item4, 150);
        assertEquals(newClearCapacity, lst.getAvailableCapacity());

        // and when we reset it back, its back to all clear.
        lst.resetInventory();
        assertEquals(CAPACITY, lst.getCapacity());
    }

    /**
     * test for checking getters are not returning null
     */
    @Test
    public void testGettersNotNUll() {
        LongTermStorage lts = new LongTermStorage();

        assertNotNull(lts.getInventory());
    }
}
