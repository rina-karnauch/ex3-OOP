import oop.ex3.searchengine.Hotel;

import java.util.Comparator;

/**
 * class for sorting Hotels by Proximity
 */
public class SortByProximity implements Comparator<Hotel> {

    /*
    return value for first item is smaller
     */
    private final static int FIRST_IS_SMALLER = -1;

    /*
    return value for second item is smaller
     */
    private final static int SECOND_IS_SMALLER = 1;

    /*
    latitude field.
     */
    private final double latitude;

    /*
    longitude field.
     */
    private final double longitude;

    /**
     * constructor for a sort Proximity object for sorting by proximity
     *
     * @param latitude  latitude given coordinate
     * @param longitude longitude given coordinate
     */
    public SortByProximity(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * a compare function for hotels by ratings.
     *
     * @param first  the first hotel
     * @param second the second hotel
     * @return negative int for first smaller than second, 0 for equal, positive int for first greater than second
     */
    @Override
    public int compare(Hotel first, Hotel second) {

        double distFromFirst = getDistanceFromCoordinate(first);
        double distFromSecond = getDistanceFromCoordinate(second);

        if (distFromFirst < distFromSecond) {
            return FIRST_IS_SMALLER;
        } else if (distFromFirst == distFromSecond) {
            int firstPOI = first.getNumPOI();
            int secondPOI = second.getNumPOI();
            return comparePOINum(firstPOI, secondPOI);
        } else {
            return SECOND_IS_SMALLER;
        }
    }

    /*
    comparing POI numbers
     */
    private int comparePOINum(int first, int second) {
        return second - first;
    }

    /*
    function to get distance from the coordinate of the object.
     */
    private double getDistanceFromCoordinate(Hotel hotel) {
        double hotelLatitude = hotel.getLatitude();
        double hotelLongitude = hotel.getLongitude();
        return Math.pow(hotelLatitude - latitude, 2) + Math.pow(hotelLongitude - longitude, 2);
    }
}
