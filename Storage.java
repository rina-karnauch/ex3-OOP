import oop.ex3.spaceship.Item;

import java.util.Map;

/**
 * abstract class to define a storage unit
 *
 * @author rina.karnauch
 */
public abstract class Storage {

    /**
     * This method adds n Items of the given type to the storage
     *
     * @param item the item
     * @param n    amount of items of this kind
     * @return -1 or 1 for failure, 0 for success.
     */
    // in API for locker and lts
    public abstract int addItem(Item item, int n);

    /**
     * This method return the number of Items of type the locker contains
     *
     * @param type the type's description to check amount
     * @return int of amount of type, or 0 for none existent.
     */
    // in API
    public int getItemCount(String type) {
        Map<String, Integer> items = this.getInventory();
        for (String key : items.keySet()) {
            if (key.equals(type)) {
                return items.get(key);
            }
        }
        return 0;
    }

    /**
     * This methods returns a map of all the item types contained in the locker,
     * and their respective quantities.
     *
     * @return a map of string as keys and ints as values.
     */
    // in API for locker and lts
    public abstract Map<String, Integer> getInventory();

    /**
     * This method returns the locker's total capacity.
     *
     * @return int for capacity.
     */
    // in API
    public abstract int getCapacity();

    /**
     * This method returns the locker's available capacity.
     *
     * @return int of how many storage units are unoccupied
     */
    // in API for locker and lts
    public abstract int getAvailableCapacity();

}
