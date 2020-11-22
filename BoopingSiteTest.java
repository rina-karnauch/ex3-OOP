import oop.ex3.searchengine.Hotel;
import oop.ex3.searchengine.HotelDataset;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * a class to test class BoopingSite
 *
 * @author rina.karnauch
 */
public class BoopingSiteTest {

    /*
    big file name path given to us
     */
    private static final String BIG_DATA_SET_FILE = "hotels_dataset.txt";

    /*
    file name path given to us
     */
    private static final String DATA_SET_FILE_1 = "hotels_tst1.txt";

    /*
    empty file name given to us
     */
    private static final String DATA_SET_FILE_2 = "hotels_tst2.txt";

    // --------- tests for getHotelsInCityByRating --------------

    /**
     * a test for a none existent city.
     */
    @Test
    public void testGetHotelsInCityByRatingNoSuchCity() {

        Hotel[] emptyWantedOutput = new Hotel[0];
        String noSuchCity = "rinakarnauch";

        // ------- all dataset file ------- //

        BoopingSite boopingTest = new BoopingSite(BIG_DATA_SET_FILE);
        Hotel[] output = boopingTest.getHotelsInCityByRating(noSuchCity);
        assertArrayEquals(emptyWantedOutput, output);

        // ------- first test file ------- //

        boopingTest = new BoopingSite(DATA_SET_FILE_1);
        output = boopingTest.getHotelsInCityByRating(noSuchCity);
        assertArrayEquals(emptyWantedOutput, output);

        // ------- second test file ------- //

        boopingTest = new BoopingSite(DATA_SET_FILE_2);
        output = boopingTest.getHotelsInCityByRating(noSuchCity);
        assertArrayEquals(emptyWantedOutput, output);
    }

    /**
     * a test for getHotelsInCity with illegal empty string input.
     */
    @Test
    public void testGetHotelsInCityByRatingEmptyCity() {

        Hotel[] emptyWantedOutput = new Hotel[0];
        String noSuchCity = "";

        // ------- first test file ------- //

        BoopingSite boopingTest = new BoopingSite(DATA_SET_FILE_1);
        Hotel[] output = boopingTest.getHotelsInCityByRating(noSuchCity);
        assertArrayEquals(emptyWantedOutput, output);

        // ------- second test file ------- //

        boopingTest = new BoopingSite(DATA_SET_FILE_2);
        output = boopingTest.getHotelsInCityByRating(noSuchCity);
        assertArrayEquals(emptyWantedOutput, output);

        // ------- all dataset file ------- //

        boopingTest = new BoopingSite(BIG_DATA_SET_FILE);
        output = boopingTest.getHotelsInCityByRating(noSuchCity);
        assertArrayEquals(emptyWantedOutput, output);
    }

    /**
     * a test for getHotelsInCity with illegal numeric string input.
     */
    @Test
    public void testGetHotelsInCityByRatingNumericCity() {
        Hotel[] emptyWantedOutput = new Hotel[0];
        String noSuchCity = "12345678";

        // ------- first test file ------- //

        BoopingSite boopingTest = new BoopingSite(DATA_SET_FILE_1);
        Hotel[] output = boopingTest.getHotelsInCityByRating(noSuchCity);
        assertArrayEquals(emptyWantedOutput, output);

        // ------- second test file ------- //

        boopingTest = new BoopingSite(DATA_SET_FILE_2);
        output = boopingTest.getHotelsInCityByRating(noSuchCity);
        assertArrayEquals(emptyWantedOutput, output);

        // ------- all dataset file ------- //

        boopingTest = new BoopingSite(BIG_DATA_SET_FILE);
        output = boopingTest.getHotelsInCityByRating(noSuchCity);
        assertArrayEquals(emptyWantedOutput, output);
    }


    /**
     * a test for the method getHotelsInCityByRating with legal input file hotels_dataset.txt
     */
    @Test
    public void testGetHotelsInCityByRatingBigData() {

        BoopingSite boopingTest = new BoopingSite(BIG_DATA_SET_FILE);
        Hotel[] hotels = HotelDataset.getHotels(BIG_DATA_SET_FILE);

        Map<String, Integer> cityMapWithHotelCounts = new HashMap<String, Integer>();

        // getting all cities.
        for (Hotel hotel : hotels) {
            String hotelCity = hotel.getCity();
            if (!cityMapWithHotelCounts.containsKey(hotelCity)) {
                cityMapWithHotelCounts.put(hotelCity, 1);
            } else {
                int lastValue = cityMapWithHotelCounts.get(hotelCity);
                cityMapWithHotelCounts.put(hotelCity, lastValue + 1);
            }
        }

        for (String city : cityMapWithHotelCounts.keySet()) {
            // sorted array by some city.
            Hotel[] boopingSortOutput = boopingTest.getHotelsInCityByRating(city);

            // check for validity of descending order sort
            for (int i = 0; i < boopingSortOutput.length - 1; i++) {
                int j = i + 1;
                // comparing objects should give us positive or zero at all times.

                int starValueI = boopingSortOutput[i].getStarRating();
                int starValueJ = boopingSortOutput[j].getStarRating();

                if (starValueI != starValueJ) {
                    assertTrue(starValueI > starValueJ); // first star is bigger.
                }
                // if rating is equal, we need to check its alphabetically ordered
                // should be in alphabetic order, from a to z.
                else {
                    String nameFirstHotel = boopingSortOutput[i].getCity();
                    String nameSecondHotel = boopingSortOutput[i].getCity();
                    assertTrue(nameFirstHotel.compareTo(nameSecondHotel) <= 0); // alphabetic order
                }
            }
        }
    }

    /**
     * a test for the method getHotelsInCityByRating with legal input file hotels_tst1.txt
     */
    @Test
    public void testGetHotelsInCityByRatingFile1() {

        BoopingSite boopingTest = new BoopingSite(DATA_SET_FILE_1);
        Hotel[] hotels = HotelDataset.getHotels(DATA_SET_FILE_1);

        Map<String, Integer> cityMapWithHotelCounts = new HashMap<String, Integer>();

        // getting all cities.
        for (Hotel hotel : hotels) {
            String hotelCity = hotel.getCity();
            if (!cityMapWithHotelCounts.containsKey(hotelCity)) {
                cityMapWithHotelCounts.put(hotelCity, 1);
            } else {
                int lastValue = cityMapWithHotelCounts.get(hotelCity);
                cityMapWithHotelCounts.put(hotelCity, lastValue + 1);
            }
        }

        for (String city : cityMapWithHotelCounts.keySet()) {
            // sorted array by some city.
            Hotel[] boopingSortOutput = boopingTest.getHotelsInCityByRating(city);

            // check for validity of descending order sort
            for (int i = 0; i < boopingSortOutput.length - 1; i++) {
                int j = i + 1;
                // comparing objects should give us positive or zero at all times.

                int starValueI = boopingSortOutput[i].getStarRating();
                int starValueJ = boopingSortOutput[j].getStarRating();

                if (starValueI != starValueJ) {
                    assertTrue(starValueI > starValueJ); // first star is bigger.
                }
                // if rating is equal, we need to check its alphabetically ordered
                // should be in alphabetic order, from a to z.
                else {
                    String nameFirstHotel = boopingSortOutput[i].getCity();
                    String nameSecondHotel = boopingSortOutput[i].getCity();
                    assertTrue(nameFirstHotel.compareTo(nameSecondHotel) <= 0); // alphabetic order
                }
            }
        }
    }


    /**
     * a test for the method getHotelsInCityByRating and legal input file hotels_tst2.txt
     */
    @Test
    public void testGetHotelsInCityByRatingEmptyFile() {

        BoopingSite boopingTest = new BoopingSite(DATA_SET_FILE_2);

        Hotel[] expected = new Hotel[0];
        Hotel[] boopingSortOutput = boopingTest.getHotelsInCityByRating("");
        assertArrayEquals(expected, boopingSortOutput);

        boopingSortOutput = boopingTest.getHotelsInCityByRating("rinakarnauch");
        assertArrayEquals(expected, boopingSortOutput);

        boopingSortOutput = boopingTest.getHotelsInCityByRating("jamoo");
        assertArrayEquals(expected, boopingSortOutput);
    }

    //  --------- tests for getHotelsByProximity --------------

    /**
     * a test for the method getHotelsByProximity with legal input file hotels_tst1.txt
     */
    @Test
    public void testGetHotelsByProximityFile1() {

        BoopingSite boopingTest = new BoopingSite(DATA_SET_FILE_1);
        Hotel[] hotels = HotelDataset.getHotels(DATA_SET_FILE_1);

        double legalLatitude1 = -34.23;
        double legalLongitude1 = 47.23;

        Hotel[] boopingSortOutput = boopingTest.getHotelsByProximity(legalLatitude1, legalLongitude1);
        int j;
        // we go through each item in the hotels, to check if sorted by proximity indeed.
        for (int i = 0; i < hotels.length - 1; i++) {
            j = i + 1;
            // comparing objects should give us positive or zero at all times.
            // if rating is equal, we need to check its alphabetically ordered
            double distanceFromIndexI = getDistanceForTests(boopingSortOutput[i], legalLatitude1, legalLongitude1);
            double distanceFromIndexJ = getDistanceForTests(boopingSortOutput[j], legalLatitude1, legalLongitude1);
            if (distanceFromIndexI == distanceFromIndexJ) {
                int firstPOI = boopingSortOutput[i].getNumPOI();
                int secondPOI = boopingSortOutput[i].getNumPOI();
                assertTrue(firstPOI >= secondPOI); // POI is decreasing
            } else {
                assertTrue(distanceFromIndexI < distanceFromIndexJ); // i is smaller then j in distance.
            }
        }

        // another latitude and longitude

        double legalLatitude2 = 84.23;
        double legalLongitude2 = -137.23;


        boopingSortOutput = boopingTest.getHotelsByProximity(legalLatitude2, legalLongitude2);
        // we go through each item in the hotels, to check if sorted by proximity indeed.
        for (int i = 0; i < hotels.length - 1; i++) {
            j = i + 1;

            // comparing objects should give us positive or zero at all times.
            // if rating is equal, we need to check its alphabetically ordered
            double distanceFromIndexI = getDistanceForTests(boopingSortOutput[i], legalLatitude2, legalLongitude2);
            double distanceFromIndexJ = getDistanceForTests(boopingSortOutput[j], legalLatitude2, legalLongitude2);
            if (distanceFromIndexI == distanceFromIndexJ) {
                int firstPOI = boopingSortOutput[i].getNumPOI();
                int secondPOI = boopingSortOutput[i].getNumPOI();
                assertTrue(firstPOI >= secondPOI); // POI is decreasing
            } else {

                assertTrue(distanceFromIndexI < distanceFromIndexJ); // increasing from distance
            }
        }
    }

    /**
     * a test for the method getHotelsByProximity with legal input file hotels_dataset.txt
     */
    @Test
    public void testGetHotelsByProximityFileDataSet() {

        BoopingSite boopingTest = new BoopingSite(BIG_DATA_SET_FILE);
        Hotel[] hotels = HotelDataset.getHotels(BIG_DATA_SET_FILE);

        double legalLatitude1 = 74.23;
        double legalLongitude1 = -7.43;

        Hotel[] boopingSortOutput = boopingTest.getHotelsByProximity(legalLatitude1, legalLongitude1);
        int j;
        // we go through each item in the hotels, to check if sorted by proximity indeed.
        for (int i = 0; i < hotels.length - 1; i++) {
            j = i + 1;

            // comparing objects should give us positive or zero at all times.
            // if rating is equal, we need to check its alphabetically ordered
            double distanceFromIndexI = getDistanceForTests(boopingSortOutput[i], legalLatitude1, legalLongitude1);
            double distanceFromIndexJ = getDistanceForTests(boopingSortOutput[j], legalLatitude1, legalLongitude1);
            if (distanceFromIndexI == distanceFromIndexJ) {
                int firstPOI = boopingSortOutput[i].getNumPOI();
                int secondPOI = boopingSortOutput[i].getNumPOI();
                assertTrue(firstPOI >= secondPOI); // POI is decreasing
            } else {
                assertTrue(distanceFromIndexI < distanceFromIndexJ); // increasing from distance
            }

        }

        // now need to check the sort

        double legalLatitude2 = -25.43;
        double legalLongitude2 = 137.23;

        boopingSortOutput = boopingTest.getHotelsByProximity(legalLatitude2, legalLongitude2);
        // we go through each item in the hotels, to check if sorted by proximity indeed.
        for (int i = 0; i < hotels.length - 1; i++) {
            j = i + 1;
            // comparing objects should give us positive or zero at all times.

            // if rating is equal, we need to check its alphabetically ordered
            double distanceFromIndexI = getDistanceForTests(boopingSortOutput[i], legalLatitude2, legalLongitude2);
            double distanceFromIndexJ = getDistanceForTests(boopingSortOutput[j], legalLatitude2, legalLongitude2);
            if (distanceFromIndexI == distanceFromIndexJ) {
                int firstPOI = boopingSortOutput[i].getNumPOI();
                int secondPOI = boopingSortOutput[i].getNumPOI();
                assertTrue(firstPOI >= secondPOI); // POI is decreasing
            } else {
                assertTrue(distanceFromIndexI < distanceFromIndexJ); // increasing from distance
            }

        }
    }

    /**
     * a test for the method getHotelsByProximity with legal input file hotels_tst2.txt
     */
    @Test
    public void testGetHotelsByProximityEmptyFile() {

        BoopingSite boopingTest = new BoopingSite(DATA_SET_FILE_2);
        Hotel[] wantedOutput = new Hotel[0];

        double legalLatitude = 34.23;
        double legalLongitude = 87.23;
        Hotel[] output = boopingTest.getHotelsByProximity(legalLatitude, legalLongitude);

        // should be empty for empty file.
        assertArrayEquals(wantedOutput, output);
    }

    /**
     * a test for the method getHotelsInCityByProximity with illegal input
     */
    @Test
    public void testGetHotelsByProximityIllegalInputs() {
        double illegalLatitude = -150.01; // from [-90.0,90.0]
        double illegalLongitude = 181.01; // from [-180.0,180.0]

        Hotel[] emptyWantedOutput = new Hotel[0];

        // ------- first test file ------- //

        // both are not legal
        BoopingSite boopingTest = new BoopingSite(DATA_SET_FILE_1);
        Hotel[] output = boopingTest.getHotelsByProximity(illegalLatitude, illegalLongitude);
        assertArrayEquals(emptyWantedOutput, output);

        // one is not legal
        double legalLatitude = 39.84; // from [-90.0,90.0]
        output = boopingTest.getHotelsByProximity(legalLatitude, illegalLongitude);
        assertArrayEquals(emptyWantedOutput, output);
        double legalLongitude = 9.23; // from [-180.0,180.0]
        output = boopingTest.getHotelsByProximity(illegalLatitude, legalLongitude);
        assertArrayEquals(emptyWantedOutput, output);

        // ------- second test file ------- //

        illegalLatitude = 93.01; // from [-90.0,90.0]
        illegalLongitude = 200.01; // from [-180.0,180.0]

        boopingTest = new BoopingSite(DATA_SET_FILE_2);
        output = boopingTest.getHotelsByProximity(illegalLatitude, illegalLongitude);
        assertArrayEquals(emptyWantedOutput, output);

        legalLatitude = 34.04; // from [-90.0,90.0]
        output = boopingTest.getHotelsByProximity(legalLatitude, illegalLongitude);
        assertArrayEquals(emptyWantedOutput, output);
        legalLongitude = -109.23; // from [-180.0,180.0]
        output = boopingTest.getHotelsByProximity(illegalLatitude, legalLongitude);
        assertArrayEquals(emptyWantedOutput, output);

        // ------- all dataset file ------- //

        illegalLatitude = -200.01; // from [-90.0,90.0]
        illegalLongitude = 1000.01; // from [-180.0,180.0]
        boopingTest = new BoopingSite(BIG_DATA_SET_FILE);

        output = boopingTest.getHotelsByProximity(illegalLatitude, illegalLongitude);
        assertArrayEquals(emptyWantedOutput, output);

        legalLatitude = 45.04; // from [-90.0,90.0]
        output = boopingTest.getHotelsByProximity(legalLatitude, illegalLongitude);
        assertArrayEquals(emptyWantedOutput, output);
        legalLongitude = 89.23; // from [-180.0,180.0]
        output = boopingTest.getHotelsByProximity(illegalLatitude, legalLongitude);
        assertArrayEquals(emptyWantedOutput, output);

    }

    //  --------- tests for getHotelsInCityByProximity --------------

    /**
     * a test for the method getHotelsByProximity with illegal input
     */
    @Test
    public void testGetHotelsInCityByProximityIllegalInputs() {
        double illegalLatitude = -90.01; // from [-90.0,90.0]
        double illegalLongitude = -180.01; // from [-180.0,180.0]

        Hotel[] emptyWantedOutput = new Hotel[0];

        // ------- first test file ------- //

        // both are not legal
        BoopingSite boopingTest = new BoopingSite(DATA_SET_FILE_1);

        String legalHotelName = HotelDataset.getHotels(BIG_DATA_SET_FILE)[0].getPropertyName();

        Hotel[] output = boopingTest.getHotelsInCityByProximity(legalHotelName, illegalLatitude, illegalLongitude);
        assertArrayEquals(emptyWantedOutput, output);

        // one is not legal
        double legalLatitude = 45.04; // from [-90.0,90.0]
        output = boopingTest.getHotelsInCityByProximity(legalHotelName, legalLatitude, illegalLongitude);
        assertArrayEquals(emptyWantedOutput, output);
        double legalLongitude = 89.23; // from [-180.0,180.0]
        output = boopingTest.getHotelsInCityByProximity(legalHotelName, illegalLatitude, legalLongitude);
        assertArrayEquals(emptyWantedOutput, output);

        String illegalHotelName1 = "2343";
        String illegalHotelName2 = "";

        output = boopingTest.getHotelsInCityByProximity(illegalHotelName1, legalLatitude, legalLongitude);
        assertArrayEquals(emptyWantedOutput, output);

        output = boopingTest.getHotelsInCityByProximity(illegalHotelName2, legalLatitude, legalLongitude);
        assertArrayEquals(emptyWantedOutput, output);


        // ------- second test file ------- //

        illegalLatitude = -180.0; // from [-90.0,90.0]
        illegalLongitude = 234.01; // from [-180.0,180.0]

        boopingTest = new BoopingSite(DATA_SET_FILE_2);
        output = boopingTest.getHotelsByProximity(illegalLatitude, illegalLongitude);
        assertArrayEquals(emptyWantedOutput, output);

        legalLatitude = 84.04; // from [-90.0,90.0]
        output = boopingTest.getHotelsByProximity(legalLatitude, illegalLongitude);
        assertArrayEquals(emptyWantedOutput, output);
        legalLongitude = -179.23; // from [-180.0,180.0]
        output = boopingTest.getHotelsByProximity(illegalLatitude, legalLongitude);
        assertArrayEquals(emptyWantedOutput, output);

        illegalHotelName1 = "999";
        illegalHotelName2 = "";

        output = boopingTest.getHotelsInCityByProximity(illegalHotelName1, legalLatitude, legalLongitude);
        assertArrayEquals(emptyWantedOutput, output);

        output = boopingTest.getHotelsInCityByProximity(illegalHotelName2, legalLatitude, legalLongitude);
        assertArrayEquals(emptyWantedOutput, output);

        // ------- all dataset file ------- //

        illegalLatitude = 91.1; // from [-90.0,90.0]
        illegalLongitude = -181.01; // from [-180.0,180.0]
        boopingTest = new BoopingSite(BIG_DATA_SET_FILE);

        output = boopingTest.getHotelsByProximity(illegalLatitude, illegalLongitude);
        assertArrayEquals(emptyWantedOutput, output);

        legalLatitude = 15.04; // from [-90.0,90.0]
        output = boopingTest.getHotelsByProximity(legalLatitude, illegalLongitude);
        assertArrayEquals(emptyWantedOutput, output);
        legalLongitude = -179.53; // from [-180.0,180.0]
        output = boopingTest.getHotelsByProximity(illegalLatitude, legalLongitude);
        assertArrayEquals(emptyWantedOutput, output);

        illegalHotelName1 = "4";
        illegalHotelName2 = "";

        output = boopingTest.getHotelsInCityByProximity(illegalHotelName1, legalLatitude, legalLongitude);
        assertArrayEquals(emptyWantedOutput, output);

        output = boopingTest.getHotelsInCityByProximity(illegalHotelName2, legalLatitude, legalLongitude);
        assertArrayEquals(emptyWantedOutput, output);
    }

    /**
     * a test for the method getHotelsInCityByProximity with empty file aka hotels_tst2.txt
     */
    @Test
    public void testGetHotelsInCityByProximityEmptyFile() {

        // empty file so we want empty array.

        Hotel[] emptyWantedOutput = new Hotel[0];
        BoopingSite boopingTest = new BoopingSite(DATA_SET_FILE_2);
        Hotel[] hotels = HotelDataset.getHotels();
        String legalHotelName = hotels[0].getPropertyName();

        double legalLatitude = 54.24;
        double legalLongitude = -34.54;

        Hotel[] output = boopingTest.getHotelsInCityByProximity(legalHotelName, legalLatitude, legalLongitude);
        assertArrayEquals(emptyWantedOutput, output);
    }

    /**
     * a test for the method getHotelsInCityByProximity with hotels_tst1.txt
     */
    @Test
    public void testGetHotelsInCityByProximityFile1() {

        BoopingSite boopingTest = new BoopingSite(DATA_SET_FILE_1);
        Hotel[] hotels = HotelDataset.getHotels();
        Hotel[] boopingSortOutput;

        double legalLatitude1 = 54.24;
        double legalLongitude1 = -34.54;

        double legalLatitude2 = -23.24;
        double legalLongitude2 = -94.54;

        double legalLatitude3 = -45.24;
        double legalLongitude3 = 150.54;

        // getting all cities.
        Map<String, Integer> cityMapWithHotelCounts = new HashMap<String, Integer>();
        for (Hotel hotel : hotels) {
            String hotelCity = hotel.getCity();
            if (!cityMapWithHotelCounts.containsKey(hotelCity)) {
                cityMapWithHotelCounts.put(hotelCity, 1);
            } else {
                int lastValue = cityMapWithHotelCounts.get(hotelCity);
                cityMapWithHotelCounts.put(hotelCity, lastValue + 1);
            }
        }

        int j;

        // go through each city and check the sorting.
        for (String city : cityMapWithHotelCounts.keySet()) {
            boopingSortOutput = boopingTest.getHotelsInCityByProximity(city, legalLatitude1, legalLongitude1);
            // check that all values are sorted, and city is city,
            for (int i = 0; i < boopingSortOutput.length - 1; i++) {
                j = i + 1;
                // check that hotel is in the city.
                assertEquals(city, boopingSortOutput[i].getCity());
                // check for the order of distance

                double distanceFromIndexI = getDistanceForTests(boopingSortOutput[i], legalLatitude1, legalLongitude1);
                double distanceFromIndexJ = getDistanceForTests(boopingSortOutput[j], legalLatitude1, legalLongitude1);
                // check for equal distances.
                if (distanceFromIndexI == distanceFromIndexJ) { // same distance, first alphabetic order
                    int firstPOI = boopingSortOutput[i].getNumPOI();
                    int secondPOI = boopingSortOutput[i].getNumPOI();
                    assertTrue(firstPOI >= secondPOI); // POI is decreasing
                } else {
                    assertTrue(distanceFromIndexI < distanceFromIndexJ); // increasing from distance
                }
            }
        }

        for (String city : cityMapWithHotelCounts.keySet()) {
            boopingSortOutput = boopingTest.getHotelsInCityByProximity(city, legalLatitude2, legalLongitude2);
            // check that all values are sorted, and city is city,
            for (int i = 0; i < boopingSortOutput.length - 1; i++) {
                j = i + 1;
                // check that hotel is in the city.
                assertEquals(city, boopingSortOutput[i].getCity());

                // check for the order of distance
                double distanceFromIndexI = getDistanceForTests(boopingSortOutput[i], legalLatitude2, legalLongitude2);
                double distanceFromIndexJ = getDistanceForTests(boopingSortOutput[j], legalLatitude2, legalLongitude2);
                // check for equal distances.
                if (distanceFromIndexI == distanceFromIndexJ) {
                    int firstPOI = boopingSortOutput[i].getNumPOI();
                    int secondPOI = boopingSortOutput[i].getNumPOI();
                    assertTrue(firstPOI >= secondPOI); // POI is decreasing
                } else {
                    assertTrue(distanceFromIndexI < distanceFromIndexJ); // increasing from distance
                }
            }
        }

        for (String city : cityMapWithHotelCounts.keySet()) {
            boopingSortOutput = boopingTest.getHotelsInCityByProximity(city, legalLatitude3, legalLongitude3);
            // check that all values are sorted, and city is city,
            for (int i = 0; i < boopingSortOutput.length - 1; i++) {
                j = i + 1;
                // check that hotel is in the city.
                assertEquals(city, boopingSortOutput[i].getCity());

                // check for the order of distance
                double distanceFromIndexI = getDistanceForTests(boopingSortOutput[i], legalLatitude3, legalLongitude3);
                double distanceFromIndexJ = getDistanceForTests(boopingSortOutput[j], legalLatitude3, legalLongitude3);
                // check for equal distances.
                if (distanceFromIndexI == distanceFromIndexJ) {
                    int firstPOI = boopingSortOutput[i].getNumPOI();
                    int secondPOI = boopingSortOutput[i].getNumPOI();
                    assertTrue(firstPOI >= secondPOI); // POI is decreasing
                } else {
                    assertTrue(distanceFromIndexI < distanceFromIndexJ); // increasing from distance
                }
            }
        }
    }

    /**
     * a test for the method getHotelsInCityByProximity with hotels_dataset.txt
     */
    @Test
    public void testGetHotelsInCityByProximityFileDataSet() {
        BoopingSite boopingTest = new BoopingSite(BIG_DATA_SET_FILE);
        Hotel[] hotels = HotelDataset.getHotels();
        Hotel[] boopingSortOutput;

        double legalLatitude1 = 80.24;
        double legalLongitude1 = 23.54;

        double legalLatitude2 = 43.24;
        double legalLongitude2 = -124.54;


        // getting all cities.
        Map<String, Integer> cityMapWithHotelCounts = new HashMap<String, Integer>();
        for (Hotel hotel : hotels) {
            String hotelCity = hotel.getCity();
            if (!cityMapWithHotelCounts.containsKey(hotelCity)) {
                cityMapWithHotelCounts.put(hotelCity, 1);
            } else {
                int lastValue = cityMapWithHotelCounts.get(hotelCity);
                cityMapWithHotelCounts.put(hotelCity, lastValue + 1);
            }
        }

        int j;

        // go through each city and check the sorting.
        for (String city : cityMapWithHotelCounts.keySet()) {
            boopingSortOutput = boopingTest.getHotelsInCityByProximity(city, legalLatitude1, legalLongitude1);
            // check that all values are sorted, and city is city,
            for (int i = 0; i < boopingSortOutput.length - 1; i++) {
                j = i + 1;
                // check that hotel is in the city.
                assertEquals(city, boopingSortOutput[i].getCity());

                // check for the order of distance
                double distanceFromIndexI = getDistanceForTests(boopingSortOutput[i], legalLatitude1, legalLongitude1);
                double distanceFromIndexJ = getDistanceForTests(boopingSortOutput[j], legalLatitude1, legalLongitude1);
                // check for equal distances.
                if (distanceFromIndexI == distanceFromIndexJ) {
                    int firstPOI = boopingSortOutput[i].getNumPOI();
                    int secondPOI = boopingSortOutput[i].getNumPOI();
                    assertTrue(firstPOI >= secondPOI); // POI is decreasing // POI is decreasing
                } else {
                    assertTrue(distanceFromIndexI < distanceFromIndexJ);
                }
            }
        }

        for (String city : cityMapWithHotelCounts.keySet()) {

            boopingSortOutput = boopingTest.getHotelsInCityByProximity(city, legalLatitude2, legalLongitude2);
            // check that all values are sorted, and city is city,
            for (int i = 0; i < boopingSortOutput.length - 1; i++) {
                j = i + 1;
                // check that hotel is in the city.
                assertEquals(city, boopingSortOutput[i].getCity());
                // check for the order of distance
                double distanceFromIndexI = getDistanceForTests(boopingSortOutput[i], legalLatitude2, legalLongitude2);
                double distanceFromIndexJ = getDistanceForTests(boopingSortOutput[j], legalLatitude2, legalLongitude2);
                // check for equal distances.
                if (distanceFromIndexI == distanceFromIndexJ) {
                    int firstPOI = boopingSortOutput[i].getNumPOI();
                    int secondPOI = boopingSortOutput[i].getNumPOI();
                    assertTrue(firstPOI >= secondPOI); // POI is decreasing
                } else {
                    assertTrue(distanceFromIndexI < distanceFromIndexJ);
                }
            }
        }
    }

    @Test
    public void testGettersNotNull() {
        BoopingSite boopingTest1 = new BoopingSite(DATA_SET_FILE_1);
        BoopingSite boopingTest2 = new BoopingSite(DATA_SET_FILE_2);
        BoopingSite boopingTest3 = new BoopingSite(BIG_DATA_SET_FILE);

        Hotel[] hotels1 = HotelDataset.getHotels(DATA_SET_FILE_1);
        Hotel[] hotels2 = HotelDataset.getHotels(DATA_SET_FILE_1);
        Hotel[] hotels3 = HotelDataset.getHotels(DATA_SET_FILE_1);

        for (Hotel hotel : hotels1) {
            String name = hotel.getCity();
            assertNotNull(boopingTest1.getHotelsInCityByRating(name));
            assertNotNull(boopingTest1.getHotelsInCityByProximity(name, -22.45, -134.65));
        }

        assertNotNull(boopingTest1.getHotelsByProximity(54.23, 23.45));
        assertNotNull(boopingTest2.getHotelsInCityByRating(hotels2[0].getCity()));
        assertNotNull(boopingTest3.getHotelsByProximity(14.23, -89.45));

        for (Hotel hotel : hotels3) {
            String name = hotel.getCity();
            assertNotNull(boopingTest3.getHotelsInCityByRating(name));
            assertNotNull(boopingTest3.getHotelsInCityByProximity(name, 42.45, 34.65));
        }
    }

    /*
     * method to check distance between two properties.
     * @param property the hotel to check to
     * @param hotel the hotel to check from
     * @return distance in double
     */
    private double getDistanceForTests(Hotel property, double latitude, double longitude) {

        double propertyLatitude = property.getLatitude();
        double propertyLongitude = property.getLongitude();

        return Math.pow(propertyLatitude - latitude, 2) + Math.pow(propertyLongitude - longitude, 2);
    }

}