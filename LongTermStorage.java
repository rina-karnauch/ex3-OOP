import oop.ex3.spaceship.Item;

import java.util.HashMap;
import java.util.Map;

public class LongTermStorage extends Storage {

    /*
    success on return value
     */
    private final static int SUCCESS_RETURN = 0;

    /*
    failure on no room error return value
     */
    private final static int NO_ROOM_ERROR = -1;

    /*
   failure on general reason return value
    */
    private final static int GENERAL_ERROR = -1;

    /*
    textual error to print on error
     */
    private final static String POTENTIAL_ERROR = "Error: Your request cannot be completed at this time.";

    /*
    long term capacity value
     */
    private static final int LONG_TERM_CAPACITY = 1000;

    /*
    map of items inside
     */
    private Map<Item, Integer> items;

    /*
   field of the capacity of the locker.
    */
    private final int capacity;

    /*
    field of unoccupied capacity.
     */
    private int unoccupiedCapacity;

    /**
     * This constructor initializes a Long-Term Storage object.
     */
    public LongTermStorage() {
        this.capacity = LONG_TERM_CAPACITY;
        this.unoccupiedCapacity = LONG_TERM_CAPACITY;
        this.items = new HashMap<Item, Integer>();
    }

    /**
     * This method resets the long-term storage's inventory.
     */
    public void resetInventory() {
        this.unoccupiedCapacity = LONG_TERM_CAPACITY;
        this.items = new HashMap<Item, Integer>();
    }

    /**
     * This method adds n Items of the given type to the long- term storage unit.
     *
     * @param item the item type
     * @param n    the number of items
     * @return -1 for failure and 0 for success
     */
    public int addItem(Item item, int n) {
        if (item == null) {
            return potentialError();
        }
        if (n < 0) { // n is negative
            return potentialError();
        }
        int neededVolume = n * (item.getVolume());
        int available = this.getAvailableCapacity();
        if (available < neededVolume) { // no space
            return noRoomError(item, n);
        } else { // we can add
            int beforeAmount = this.getItemCount(item.getType());
            this.items.put(item, beforeAmount + n);
            this.unoccupiedCapacity -= (n * item.getVolume()); // reduced
            return SUCCESS_RETURN;
        }
    }

    /**
     * This method returns the number of Items of type type the long-term storage contains.
     *
     * @return int of amount of items
     */
    public int getItemCount(String type) {
        if (type == null) {
            return potentialError();
        }
        for (Item key : this.items.keySet()) {
            if (key.getType().equals(type)) {
                return this.items.get(key);
            }
        }
        return SUCCESS_RETURN;
    }

    /**
     * This method returns the number of Items of type type the long-term storage contains.
     *
     * @return a map of items' strings as keys, amounts as values.
     */
    public Map<String, Integer> getInventory() {
        Map<String, Integer> inventory = new HashMap<String, Integer>();
        if (this.items.isEmpty()) { // empty one
            return inventory;
        }
        for (Item key : this.items.keySet()) {
            String type = key.getType();
            int value = this.items.get(key);
            inventory.put(type, value);
        }
        return inventory;
    }

    /**
     * Returns the long-term storages total capacity.
     *
     * @return int of long-term's capacity.
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Returns the long-term storages available capacity.
     *
     * @return the amount of storage left in long-term storage of the ship.
     */
    public int getAvailableCapacity() {
        return this.unoccupiedCapacity;
    }

    /*
    This method prints a no room error for the LTS part.
     */
    private int noRoomError(Item item, int n) {
        System.out.println("Error: Your request cannot be completed at this time. " +
                "Problem: no room for " + n + " items of type " + item.getType());
        return NO_ROOM_ERROR;
    }

    /*
     * a method to declare a potential error
     */
    private int potentialError() {
        System.out.println(POTENTIAL_ERROR);
        return GENERAL_ERROR;
    }

}
