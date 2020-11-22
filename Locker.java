import oop.ex3.spaceship.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Locker extends Storage {

    /*
    magic number for indexes and loops and returns
     */
    private final static int MOVEMENT_TO_LONG_TERM_WARNING = 1;

    /*
    magic number for returns
     */
    private final static int NO_ROOM_ERROR = -1;

    /*
    magic number for returns
     */
    private final static int CONTRADICTION_ERROR = -2;

    /*
    error message
     */
    private final static String POTENTIAL_ERROR = "Error: Your request cannot be completed at this time.";

    /*
   double for percentage calculations
    */
    private final static int LIMIT_OF_CAPACITY_PER_ITEM_DIVIDE_BY = 5;

    /*
     * double for percentage calculations
     */
    private final static double LIMIT_OF_PERCENTAGE_FOR_LOCKER = 0.5;

    /*
    field of capacity of locker
     */
    private final int capacity;

    /*
    field of the long term storage.
     */
    private LongTermStorage lts;
    /*
    field of the pairs of the constrains of the items.
     */
    private Item[][] constrains;
    /*
    counter of current amount of items inside
     */
    private int currentCapacity;
    /*
    Map of items in the locker and their amounts
     */
    private Map<String, Integer> items;

    /*
    field of unoccupied capacity in locker
     */
    private int unoccupiedCapacity;

    /**
     * This constructor initializes a Locker object that is associated with the given long-term storage
     * with the given capacity and Item constraints.
     *
     * @param lts        the common long term storage unit for the locker.
     * @param capacity   the capacity the locker can hold
     * @param constrains the pairs of constrains for the locker itself
     */
    // IN API
    public Locker(LongTermStorage lts, int capacity, Item[][] constrains) {
        this.capacity = capacity;
        this.unoccupiedCapacity = capacity;
        this.items = new HashMap<String, Integer>();
        this.lts = lts;
        this.constrains = constrains;
        this.currentCapacity = 0;
    }

    /**
     * This method adds n Items of the given type to the locker.
     *
     * @param item the item
     * @param n    amount of items of this kind
     * @return -1 or 1 for failure, 0 for success.
     */
    // IN API
    public int addItem(Item item, int n) {
        // let's check if it's a null item.
        if (item == null) {
            return potentialError();
        }
        // let's check if it's not restricted.
        boolean checkRestrictions = checkConstrains(item);
        if (!checkRestrictions)// we cannot add the item, restricted
        {
            return contradictionError(item); // -2
        } else if (n < 0) { // -1
            return noRoomRegularError(item, n);
        }
        // let's check if there is even enough space to add it at all.
        int neededVolume = n * item.getVolume(); // free needed volume.
        if (this.unoccupiedCapacity < neededVolume) {
            return noRoomRegularError(item, n); // -1
        }

        // we have enough space to accommodate, but need to check restrains.
        // let's check if the percentage is above 50% so we shall move some to the lts.
        double percentageAfterAdding = this.percentageOfCapacityOfItem(item, n);
        // if percentage > 0.5, modification will be needed afterwards- need to check if possible.

        int newAmount = this.getItemCount(item.getType()) + n; // new amount of items (NOT VOLUME) after inserting.

        if (percentageAfterAdding <= LIMIT_OF_PERCENTAGE_FOR_LOCKER) { // added peacefully
            // we can add it peacefully, haven't passed the limit.
            this.items.put(item.getType(), newAmount); // the new amount of the item's in the locker, not volume.
            this.unoccupiedCapacity -= (n * item.getVolume());
            this.currentCapacity += (n * item.getVolume());
            return 0;
        }
        // if immovable == 0 then we can move- can't otherwise.
        // updating new values, will be changed inside reallocate.
        int immovable = reallocateToLongTermStorage(item, n);
        if (immovable == 0) {
            // the new value of the item's amount in the locker is updated inside reallocation.
            return movingWarning(); // 1
        } else {
            return immovable; // msg is inside reallocation functions.
        }
    }

    /**
     * This method removes n Items of the type from the locker.
     *
     * @param item the item
     * @param n    amount of items to remove
     * @return -1 for failure, 0 for success.
     */
    // IN API
    public int removeItem(Item item, int n) {
        if (item == null) {
            return potentialError();
        }
        if (n < 0) {
            return removingNegativeAmountError(item);
        }
        int itemAmount = this.getItemCount(item.getType());
        if (itemAmount < n) {
            return removingLessThanExistsError(item, n);
        } else {
            this.items.put(item.getType(), itemAmount - n); // reduction of amount in the locker.
            this.currentCapacity -= n * item.getVolume();
            this.unoccupiedCapacity += n * item.getVolume();
            return 0;
        }
    }

    /**
     * This methods returns a map of all the item types contained in the locker,
     * and their respective quantities.
     *
     * @return a map of string as keys and ints as values.
     */
    // in API
    public Map<String, Integer> getInventory() {
        if (this.items == null || this.items.isEmpty()) {
            return new HashMap<String, Integer>();
        }
        return this.items;
    }

    /**
     * This method returns the locker's total capacity.
     *
     * @return int for capacity.
     */
    // IN API
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * This method returns the locker's available capacity.
     *
     * @return int of how many storage units are unoccupied
     */
    // IN API
    public int getAvailableCapacity() {
        return this.unoccupiedCapacity;
    }

    /*
    method to print a contradiction error in addItem.
     */
    // NOT IN API
    private int contradictionError(Item item) {
        System.out.println("Error: Your request cannot be completed at this time. Problem: the locker cannot " +
                "contain items of type " + item.getType() + " as it contains a contradicting item");
        return CONTRADICTION_ERROR;
    }

    /*
    method to print a no room in regular capacity error in addItem.
     */
    // NOT IN API
    private int noRoomRegularError(Item item, int n) {
        System.out.println("Error: Your request cannot be completed at this time. Problem: no room for "
                + n + " items of type " + item.getType());
        return NO_ROOM_ERROR;
    }

    /*
    method to print a warning for moving to LTS.
    */
    // NOT IN API
    private int movingWarning() {
        System.out.println("Warning: Action successful, but has caused items to be move to storage");
        return MOVEMENT_TO_LONG_TERM_WARNING;
    }

    /*
     * a method to declare a potential error
     * @return MINUS ONE
     */
    // NOT IN API
    private int potentialError() {
        System.out.println(POTENTIAL_ERROR);
        return NO_ROOM_ERROR;
    }

    /*
     * This method reallocates elements into long term storage, and returns 0 if everything is moved
     * or a positive integer if there is an unmovable amount.
     *
     * @param item      the item to be added.
     * @param newAmount the new amount of type item in our locker.
     */
    // NOT IN API
    private int reallocateToLongTermStorage(Item item, int n) {
        int newAmount = n + this.getItemCount(item.getType());
        int itemVolume = item.getVolume();

        // needed amount of units to be taken by item, upper limit.
        int neededVolumeTakenByItem = this.capacity / LIMIT_OF_CAPACITY_PER_ITEM_DIVIDE_BY; // we'd like to get
        // there
        int neededAmountOfItemsAfterModifying = neededVolumeTakenByItem / itemVolume;
        int reductionInAmount = newAmount - neededAmountOfItemsAfterModifying;

        int checkAddingToLTS = this.lts.addItem(item, reductionInAmount);

        if (checkAddingToLTS == 0) // added successfully
        {
            // let's add n items inside
            this.unoccupiedCapacity = this.unoccupiedCapacity - (n * itemVolume);
            this.currentCapacity = this.currentCapacity + (n * itemVolume);

            this.items.put(item.getType(), neededAmountOfItemsAfterModifying); // locker storage change
            // we added amount of free units.
            this.unoccupiedCapacity = this.unoccupiedCapacity + (reductionInAmount * itemVolume);
            this.currentCapacity = this.currentCapacity - (reductionInAmount * itemVolume);
        }
        return checkAddingToLTS;
    }

    /*
     * This method checks if item is in the constrained array, and returns true if it is not(we can add it)
     * and false otherwise.
     *
     * @param item the item to check
     * @return true for not in constrained- can be added, false otherwise.
     */
    // NOT IN API
    private boolean checkConstrains(Item item) {
        String itemToAdd = item.getType();
        ArrayList<String> constricted = new ArrayList<String>();

        // we collect everything that is constricted with item.
        if (this.constrains != null) {
            for (Item[] constrain : this.constrains) {
                String first = constrain[0].getType();
                String second = constrain[1].getType();
                if (first.equals(itemToAdd)) {
                    constricted.add(second);
                } else if (second.equals(itemToAdd)) {
                    constricted.add(first);
                }
            }
        }
        // if we have a pair of constricted items, we can't add it to the locker, so we return false
        for (String itemNotAllowed : constricted) {
            if (this.getItemCount(itemNotAllowed) > 0) {
                return false;
            }
        }
        return true;
    }

    /*
    function of error when removing negative amount
     */
    private int removingNegativeAmountError(Item item) {
        System.out.println("Error: Your request cannot be completed at this time. Problem:" +
                " cannot remove a negative number of items of type " + item.getType());
        return NO_ROOM_ERROR;
    }

    /*
    function to print out a removing error
     */
    private int removingLessThanExistsError(Item item, int n) {
        System.out.println("Error: Your request cannot be completed at this time. Problem: the locker" +
                " does not contain " + n + " items of type " + item.getType());
        return NO_ROOM_ERROR;
    }

    /*
    This method gives us the percentage of item in our occupied locker capacity, after adding n elements.
     */
    // NOT IN API
    private double percentageOfCapacityOfItem(Item item, int n) {
        int itemVolume = item.getVolume();
        int beforeAddingAmount = this.getItemCount(item.getType());
        int afterAddingVolume = (beforeAddingAmount + n) * itemVolume;
        return ((double) afterAddingVolume / (double) capacity);
    }
}
