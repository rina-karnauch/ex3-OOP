import oop.ex3.searchengine.Hotel;

import java.util.Comparator;

/**
 * class to sort array of hotels by city name.
 */
public class SortByCity implements Comparator<Hotel> {

    /**
     * a compare function for two hotels, by their city name.
     *
     * @param first  first hotel
     * @param second second hotel
     * @return negative for first smaller than second, zero for equals, positive for first greater than second
     */
    @Override
    public int compare(Hotel first, Hotel second) {
        String firstCity = first.getCity();
        String secondCity = second.getCity();
        return firstCity.compareTo(secondCity);
    }
}
