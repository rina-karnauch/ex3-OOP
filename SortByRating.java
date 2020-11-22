import oop.ex3.searchengine.Hotel;

import java.util.Comparator;

/**
 * class for sorting Hotels by Rating
 */
public class SortByRating implements Comparator<Hotel> {

    /*
    magic number minus one for returning.
     */
    private final static int SECOND_IS_SMALLER = -1;
    /*
    magic number one for returning.
     */
    private final static int FIRST_IS_SMALLER = 1;

    /**
     * a compare function for hotels by ratings.
     *
     * @param first  the first hotel
     * @param second the second hotel
     * @return negative int for first smaller than second, 0 for equal, positive int for first greater than second
     */
    @Override
    public int compare(Hotel first, Hotel second) {

        int firstHotelRating = first.getStarRating();
        int secondHotelRating = second.getStarRating();
        if (firstHotelRating < secondHotelRating) {
            return FIRST_IS_SMALLER;
        } else if (firstHotelRating == secondHotelRating) {
            String firstHotelProperty = first.getPropertyName();
            String secondHotelProperty = second.getPropertyName();
            return firstHotelProperty.compareTo(secondHotelProperty);
        } else {
            return SECOND_IS_SMALLER;
        }
    }
}
