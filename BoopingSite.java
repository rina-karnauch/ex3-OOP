import oop.ex3.searchengine.Hotel;
import oop.ex3.searchengine.HotelDataset;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BoopingSite {

    /*
    name of dataset
     */
    private String nameOfDataSet;

    /*
    hotels data base
     */
    private Hotel[] hotels;

    /*
    Map of cities in hotels.
     */
    private Map<String, Integer> cityMap;

    /*
    minimal value for latitude
     */
    private static final double MINIMAL_LATITUDE = -90.0;

    /*
    maximal value for latitude
     */
    private static final double MAXIMAL_LATITUDE = 90.0;

    /*
    minimal value for longitude
     */
    private static final double MINIMAL_LONGITUDE = -180.0;

    /*
    maximal value for longitude
     */
    private static final double MAXIMAL_LONGITUDE = 180.0;

    /**
     * This constructor receives as parameter a string, which is the name of the dataset
     *
     * @param name string of name of dataset
     */
    // in API
    public BoopingSite(String name) {
        this.nameOfDataSet = name;
        this.hotels = HotelDataset.getHotels(name);
        this.cityMap = new HashMap<String, Integer>();
        if (this.hotels.length > 0) {
            sortHotelsIntoMap();
        }
    }

    /**
     * This method returns an array of hotels located in the given city,
     * sorted from the highest star-rating to the lowest
     *
     * @param city string of the city name for the hotels to find and sort by star-rating.
     * @return array of sorted hotels.
     */
    // in API
    public Hotel[] getHotelsInCityByRating(String city) { // no city with no hotels
        if (!lowerCaseCheck(city) || hotels.length == 0 || (!cityExist(city))) {
            return (new Hotel[0]);
        }

        int amount = this.cityMap.get(city);
        Hotel[] cityHotels = new Hotel[amount];
        int j = 0;

        // copying the hotels from the same city
        for (Hotel hotel : hotels) {
            if (hotel.getCity().equals(city)) {
                cityHotels[j] = hotel;
                j = j + 1;
            }
        }

        // sorting them by rating
        Arrays.sort(cityHotels, new SortByRating());
        return cityHotels;
    }

    /**
     * This method returns an array of hotels,
     * sorted according to their Euclidean distance from the given geographic location, in ascending order.
     *
     * @param latitude  geographic measured latitude
     * @param longitude geographic measured longitude
     * @return array of sorted hotels.
     */
    // in API
    public Hotel[] getHotelsByProximity(double latitude, double longitude) {
        if (!checkCoordinatesValidations(latitude, longitude) || this.hotels.length == 0) {
            return new Hotel[0];
        }

        // sort them by proximity to a point
        SortByProximity sortProximityObject = new SortByProximity(latitude, longitude);
        Hotel[] sortedHotels = this.hotels.clone();
        Arrays.sort(sortedHotels, sortProximityObject);

        return sortedHotels;
    }

    /**
     * This method returns an array of hotels in the given city, sorted according to their Euclidean distance
     * from the given geographic location, in ascending order
     *
     * @param city      string of city to look for hotels in
     * @param latitude  geographic measured latitude
     * @param longitude geographic measured longitude
     * @return array of sorted hotels.
     */
    // in API
    public Hotel[] getHotelsInCityByProximity(String city, double latitude, double longitude) {
        if (!lowerCaseCheck(city) || hotels.length == 0 ||
                (!cityExist(city)) || !checkCoordinatesValidations(latitude, longitude)) {
            return (new Hotel[0]);
        }

        // amount of cities, to know the array's length
        int amount = this.cityMap.get(city);
        Hotel[] cityHotels = new Hotel[amount];
        int j = 0;

        // copying the hotels from the same city
        for (Hotel hotel : hotels) {
            if (hotel.getCity().equals(city)) {
                cityHotels[j] = hotel;
                j = j + 1;
            }
        }

        // sort current city hotels by proximity.
        SortByProximity sortProximityObject = new SortByProximity(latitude, longitude);
        Arrays.sort(cityHotels, sortProximityObject);

        return cityHotels;
    }


    /*
    a method to check if given coordinates are legal
    return true if legal, false otherwise
     */
    // not in API
    private boolean checkCoordinatesValidations(double latitude, double longitude) {
        return !(latitude < MINIMAL_LATITUDE) && !(latitude > MAXIMAL_LATITUDE)
                && !(longitude < MINIMAL_LONGITUDE) && !(longitude > MAXIMAL_LONGITUDE);
    }


    /*
     * a method to sort hotels data base by city and turn it into a map.
     */
    // not in API
    private void sortHotelsIntoMap() {
        Arrays.sort(hotels, new SortByCity());
        for (Hotel h : this.hotels) {
            String cityName = h.getCity();
            if (this.cityMap.containsKey(cityName)) {
                int value = this.cityMap.get(cityName);
                this.cityMap.put(cityName, value + 1);
            } else {
                this.cityMap.put(cityName, 1);
            }
        }
    }

    /*
     * a method to check if city by name exists in our records.
     */
    // not in API
    private boolean cityExist(String city) {
        if (city == null) {
            return false;
        }
        return this.cityMap.containsKey(city);
    }

    /*
     * a method to check if str is only lower case.
     */
    // not in API
    private boolean lowerCaseCheck(String str) {
        if (str == null) {
            return false;
        }
        char[] charArray = str.toCharArray();
        for (char c : charArray) {
            if (!Character.isLowerCase(c)) {
                return false;
            }
        }
        return true;
    }
}
