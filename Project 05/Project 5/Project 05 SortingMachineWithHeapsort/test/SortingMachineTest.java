import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Zheng Ji Tan(tan.955) & Beiwen Wu(wu.4742)
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     ***************** Test cases for constructors *****************
     */
    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    /*
     ***************** Kernel methods - Test cases for add *****************
     */
    @Test //boundary case
    public final void testAddtoEmpty() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        /*
         * Call method under test
         */
        m.add("green");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test //boundary case
    public final void testAddtoNonEmpty() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "blue");
        /*
         * Call method under test
         */
        m.add("green");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test //routine case
    public final void testAddtoNonEmptyFlipped() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "blue", "green");
        /*
         * Call method under test
         */
        m.add("green");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test //routine case
    public final void testAdd3toNonEmpty() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "1");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "1", "2", "3", "4");
        /*
         *
         *
         * Call method under test
         */
        m.add("2");
        m.add("3");
        m.add("4");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test //routine case
    public final void testAdd3toEmpty() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "1", "2", "3");
        /*
         * Call method under test
         */
        m.add("1");
        m.add("2");
        m.add("3");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test //routine case
    public final void testAdd3toEmptyNotInOrder() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "1", "2", "3");
        /*
         * Call method under test
         */
        m.add("2");
        m.add("1");
        m.add("3");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    /*
     ************* Kernel methods - Test cases for changeToExtractionMode *************
     */
    @Test //boundary case
    public final void testChangeToExtractionModeEmpty() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        /*
         * Call method under test
         */
        m.changeToExtractionMode();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test //boundary case
    public final void testChangeToExtractionModeNonEmpty() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "1");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "1");
        /*
         * Call method under test
         */
        m.changeToExtractionMode();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test //routine case
    public final void testChangeToExtractionModeLen48() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "1",
                "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
                "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
                "24", "25", "26", "27", "28", "29", "30", "31", "32", "33",
                "34", "35", "36", "37", "38", "39", "40", "41", "42", "43",
                "44", "45", "46", "47", "48");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
                "13", "14", "15", "16", "17", "18", "19", "20", "21", "22",
                "23", "24", "25", "26", "27", "28", "29", "30", "31", "32",
                "33", "34", "35", "36", "37", "38", "39", "40", "41", "42",
                "43", "44", "45", "46", "47", "48");
        /*
         * Call method under test
         */
        m.changeToExtractionMode();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    /*
     ***************** Kernel methods - Test cases for removeFirst *****************
     */
    @Test //boundary case
    public final void testremoveFirstLen1() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "1");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        String resultExpected = "1";
        /*
         * Call method under test
         */
        String result = m.removeFirst();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(resultExpected, result);
    }

    @Test //routine case
    public final void testremoveFirstLen18() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "1",
                "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
                "14", "15", "16", "17", "18");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
                "14", "15", "16", "17", "18");
        String resultExpected = "1";
        /*
         * Call method under test
         */
        String result = m.removeFirst();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(resultExpected, result);
    }

    @Test //challenging case
    public final void testremoveFirstThriceLen18() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "a",
                "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
                "o", "p", "q", "r");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
                "q", "r");
        String result1Expected = "a";
        String result2Expected = "b";
        String result3Expected = "c";
        /*
         * Call method under test
         */
        String result1 = m.removeFirst();
        String result2 = m.removeFirst();
        String result3 = m.removeFirst();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(result1Expected, result1);
        assertEquals(result2Expected, result2);
        assertEquals(result3Expected, result3);
    }

    /*
     ************* Kernel methods - Test cases for isInInsertionMode *************
     */

    @Test //boundary case
    public final void testIsInInsertionModeTrueEmpty() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        boolean resultExpected = true;
        /*
         * Call method under test
         */
        boolean result = m.isInInsertionMode();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(resultExpected, result);
    }

    @Test //boundary case
    public final void testIsInInsertionModeTrueNonEmpty() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "1");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "1");
        boolean resultExpected = true;
        /*
         * Call method under test
         */
        boolean result = m.isInInsertionMode();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(resultExpected, result);
    }

    @Test //boundary case
    public final void testIsInInsertionModeFalseEmpty() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        boolean resultExpected = false;
        /*
         * Call method under test
         */
        boolean result = m.isInInsertionMode();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(resultExpected, result);
    }

    @Test //boundary case
    public final void testIsInInsertionModeFalseNonEmpty() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "1");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "1");
        boolean resultExpected = false;
        /*
         * Call method under test
         */
        boolean result = m.isInInsertionMode();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(resultExpected, result);
    }

    /*
     ***************** Kernel methods - Test cases for order *****************
     */
    @Test //boundary case
    public final void testOrderEmptyInsertionMode() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        Comparator<String> resultExpected = ORDER;
        /*
         * Call method under test
         */
        Comparator<String> result = m.order();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(resultExpected, result);
    }

    @Test //boundary case
    public final void testOrderNonEmptyInsertionMode() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "1");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "1");
        Comparator<String> resultExpected = ORDER;
        /*
         * Call method under test
         */
        Comparator<String> result = m.order();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(resultExpected, result);
    }

    @Test //boundary case
    public final void testOrderEmptyNonInsertionMode() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        Comparator<String> resultExpected = ORDER;
        /*
         * Call method under test
         */
        Comparator<String> result = m.order();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(resultExpected, result);
    }

    @Test //boundary case
    public final void testOrderNonEmptyNonInsertionMode() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "1");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "1");
        Comparator<String> resultExpected = ORDER;
        /*
         * Call method under test
         */
        Comparator<String> result = m.order();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(resultExpected, result);
    }

    /*
     ***************** Kernel methods - Test cases for size *****************
     */

    @Test //boundary case
    public final void testSize0InsertionMode() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        int resultExpected = 0;
        /*
         * Call method under test
         */
        int result = m.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(resultExpected, result);
    }

    @Test //boundary case
    public final void testSize0NotInsertionMode() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        int resultExpected = 0;
        /*
         * Call method under test
         */
        int result = m.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(resultExpected, result);
    }

    @Test //routine case
    public final void testSize1InsertionMode() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "1");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "1");
        int resultExpected = 1;
        /*
         * Call method under test
         */
        int result = m.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(resultExpected, result);
    }

    @Test //routine case
    public final void testSize1NotInsertionMode() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "1");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "1");
        int resultExpected = 1;
        /*
         * Call method under test
         */
        int result = m.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(resultExpected, result);
    }

    @Test //routine case
    public final void testSize8InsertionMode() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "1",
                "2", "3", "4", "5", "6", "7", "8");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "1", "2", "3", "4", "5", "6", "7", "8");
        int resultExpected = 8;
        /*
         * Call method under test
         */
        int result = m.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(resultExpected, result);
    }

    @Test //routine case
    public final void testSize8NotInsertionMode() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "1",
                "2", "3", "4", "5", "6", "7", "8");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "1", "2", "3", "4", "5", "6", "7", "8");
        int resultExpected = 8;
        /*
         * Call method under test
         */
        int result = m.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(resultExpected, result);
    }

    @Test //routine case
    public final void testSize48InsertionMode() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "1",
                "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
                "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
                "24", "25", "26", "27", "28", "29", "30", "31", "32", "33",
                "34", "35", "36", "37", "38", "39", "40", "41", "42", "43",
                "44", "45", "46", "47", "48");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
                "13", "14", "15", "16", "17", "18", "19", "20", "21", "22",
                "23", "24", "25", "26", "27", "28", "29", "30", "31", "32",
                "33", "34", "35", "36", "37", "38", "39", "40", "41", "42",
                "43", "44", "45", "46", "47", "48");
        int resultExpected = 48;
        /*
         * Call method under test
         */
        int result = m.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(resultExpected, result);
    }

    @Test //routine case
    public final void testSize48NotInsertionMode() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "1",
                "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
                "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
                "24", "25", "26", "27", "28", "29", "30", "31", "32", "33",
                "34", "35", "36", "37", "38", "39", "40", "41", "42", "43",
                "44", "45", "46", "47", "48");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
                "13", "14", "15", "16", "17", "18", "19", "20", "21", "22",
                "23", "24", "25", "26", "27", "28", "29", "30", "31", "32",
                "33", "34", "35", "36", "37", "38", "39", "40", "41", "42",
                "43", "44", "45", "46", "47", "48");
        int resultExpected = 48;
        /*
         * Call method under test
         */
        int result = m.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(resultExpected, result);
    }

    @Test //routine case
    public final void testSize297InsertionMode() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "1",
                "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
                "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
                "24", "25", "26", "27", "28", "29", "30", "31", "32", "33",
                "34", "35", "36", "37", "38", "39", "40", "41", "42", "43",
                "44", "45", "46", "47", "48", "49", "50", "51", "52", "53",
                "54", "55", "56", "57", "58", "59", "60", "61", "62", "63",
                "64", "65", "66", "67", "68", "69", "70", "71", "72", "73",
                "74", "75", "76", "77", "78", "79", "80", "81", "82", "83",
                "84", "85", "86", "87", "88", "89", "90", "91", "92", "93",
                "94", "95", "96", "97", "98", "99", "100", "101", "102", "103",
                "104", "105", "106", "107", "108", "109", "110", "111", "112",
                "113", "114", "115", "116", "117", "118", "119", "120", "121",
                "122", "123", "124", "125", "126", "127", "128", "129", "130",
                "131", "132", "133", "134", "135", "136", "137", "138", "139",
                "140", "141", "142", "143", "144", "145", "146", "147", "148",
                "149", "150", "151", "152", "153", "154", "155", "156", "157",
                "158", "159", "160", "161", "162", "163", "164", "165", "166",
                "167", "168", "169", "170", "171", "172", "173", "174", "175",
                "176", "177", "178", "179", "180", "181", "182", "183", "184",
                "185", "186", "187", "188", "189", "190", "191", "192", "193",
                "194", "195", "196", "197", "198", "199", "200", "201", "202",
                "203", "204", "205", "206", "207", "208", "209", "210", "211",
                "212", "213", "214", "215", "216", "217", "218", "219", "220",
                "221", "222", "223", "224", "225", "226", "227", "228", "229",
                "230", "231", "232", "233", "234", "235", "236", "237", "238",
                "239", "240", "241", "242", "243", "244", "245", "246", "247",
                "248", "249", "250", "251", "252", "253", "254", "255", "256",
                "257", "258", "259", "260", "261", "262", "263", "264", "265",
                "266", "267", "268", "269", "270", "271", "272", "273", "274",
                "275", "276", "277", "278", "279", "280", "281", "282", "283",
                "284", "285", "286", "287", "288", "289", "290", "291", "292",
                "293", "294", "295", "296", "297");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
                "13", "14", "15", "16", "17", "18", "19", "20", "21", "22",
                "23", "24", "25", "26", "27", "28", "29", "30", "31", "32",
                "33", "34", "35", "36", "37", "38", "39", "40", "41", "42",
                "43", "44", "45", "46", "47", "48", "49", "50", "51", "52",
                "53", "54", "55", "56", "57", "58", "59", "60", "61", "62",
                "63", "64", "65", "66", "67", "68", "69", "70", "71", "72",
                "73", "74", "75", "76", "77", "78", "79", "80", "81", "82",
                "83", "84", "85", "86", "87", "88", "89", "90", "91", "92",
                "93", "94", "95", "96", "97", "98", "99", "100", "101", "102",
                "103", "104", "105", "106", "107", "108", "109", "110", "111",
                "112", "113", "114", "115", "116", "117", "118", "119", "120",
                "121", "122", "123", "124", "125", "126", "127", "128", "129",
                "130", "131", "132", "133", "134", "135", "136", "137", "138",
                "139", "140", "141", "142", "143", "144", "145", "146", "147",
                "148", "149", "150", "151", "152", "153", "154", "155", "156",
                "157", "158", "159", "160", "161", "162", "163", "164", "165",
                "166", "167", "168", "169", "170", "171", "172", "173", "174",
                "175", "176", "177", "178", "179", "180", "181", "182", "183",
                "184", "185", "186", "187", "188", "189", "190", "191", "192",
                "193", "194", "195", "196", "197", "198", "199", "200", "201",
                "202", "203", "204", "205", "206", "207", "208", "209", "210",
                "211", "212", "213", "214", "215", "216", "217", "218", "219",
                "220", "221", "222", "223", "224", "225", "226", "227", "228",
                "229", "230", "231", "232", "233", "234", "235", "236", "237",
                "238", "239", "240", "241", "242", "243", "244", "245", "246",
                "247", "248", "249", "250", "251", "252", "253", "254", "255",
                "256", "257", "258", "259", "260", "261", "262", "263", "264",
                "265", "266", "267", "268", "269", "270", "271", "272", "273",
                "274", "275", "276", "277", "278", "279", "280", "281", "282",
                "283", "284", "285", "286", "287", "288", "289", "290", "291",
                "292", "293", "294", "295", "296", "297");
        int resultExpected = 297;
        /*
         * Call method under test
         */
        int result = m.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(resultExpected, result);
    }

    @Test //routine case
    public final void testSize297NotInsertionMode() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "1",
                "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
                "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
                "24", "25", "26", "27", "28", "29", "30", "31", "32", "33",
                "34", "35", "36", "37", "38", "39", "40", "41", "42", "43",
                "44", "45", "46", "47", "48", "49", "50", "51", "52", "53",
                "54", "55", "56", "57", "58", "59", "60", "61", "62", "63",
                "64", "65", "66", "67", "68", "69", "70", "71", "72", "73",
                "74", "75", "76", "77", "78", "79", "80", "81", "82", "83",
                "84", "85", "86", "87", "88", "89", "90", "91", "92", "93",
                "94", "95", "96", "97", "98", "99", "100", "101", "102", "103",
                "104", "105", "106", "107", "108", "109", "110", "111", "112",
                "113", "114", "115", "116", "117", "118", "119", "120", "121",
                "122", "123", "124", "125", "126", "127", "128", "129", "130",
                "131", "132", "133", "134", "135", "136", "137", "138", "139",
                "140", "141", "142", "143", "144", "145", "146", "147", "148",
                "149", "150", "151", "152", "153", "154", "155", "156", "157",
                "158", "159", "160", "161", "162", "163", "164", "165", "166",
                "167", "168", "169", "170", "171", "172", "173", "174", "175",
                "176", "177", "178", "179", "180", "181", "182", "183", "184",
                "185", "186", "187", "188", "189", "190", "191", "192", "193",
                "194", "195", "196", "197", "198", "199", "200", "201", "202",
                "203", "204", "205", "206", "207", "208", "209", "210", "211",
                "212", "213", "214", "215", "216", "217", "218", "219", "220",
                "221", "222", "223", "224", "225", "226", "227", "228", "229",
                "230", "231", "232", "233", "234", "235", "236", "237", "238",
                "239", "240", "241", "242", "243", "244", "245", "246", "247",
                "248", "249", "250", "251", "252", "253", "254", "255", "256",
                "257", "258", "259", "260", "261", "262", "263", "264", "265",
                "266", "267", "268", "269", "270", "271", "272", "273", "274",
                "275", "276", "277", "278", "279", "280", "281", "282", "283",
                "284", "285", "286", "287", "288", "289", "290", "291", "292",
                "293", "294", "295", "296", "297");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
                "13", "14", "15", "16", "17", "18", "19", "20", "21", "22",
                "23", "24", "25", "26", "27", "28", "29", "30", "31", "32",
                "33", "34", "35", "36", "37", "38", "39", "40", "41", "42",
                "43", "44", "45", "46", "47", "48", "49", "50", "51", "52",
                "53", "54", "55", "56", "57", "58", "59", "60", "61", "62",
                "63", "64", "65", "66", "67", "68", "69", "70", "71", "72",
                "73", "74", "75", "76", "77", "78", "79", "80", "81", "82",
                "83", "84", "85", "86", "87", "88", "89", "90", "91", "92",
                "93", "94", "95", "96", "97", "98", "99", "100", "101", "102",
                "103", "104", "105", "106", "107", "108", "109", "110", "111",
                "112", "113", "114", "115", "116", "117", "118", "119", "120",
                "121", "122", "123", "124", "125", "126", "127", "128", "129",
                "130", "131", "132", "133", "134", "135", "136", "137", "138",
                "139", "140", "141", "142", "143", "144", "145", "146", "147",
                "148", "149", "150", "151", "152", "153", "154", "155", "156",
                "157", "158", "159", "160", "161", "162", "163", "164", "165",
                "166", "167", "168", "169", "170", "171", "172", "173", "174",
                "175", "176", "177", "178", "179", "180", "181", "182", "183",
                "184", "185", "186", "187", "188", "189", "190", "191", "192",
                "193", "194", "195", "196", "197", "198", "199", "200", "201",
                "202", "203", "204", "205", "206", "207", "208", "209", "210",
                "211", "212", "213", "214", "215", "216", "217", "218", "219",
                "220", "221", "222", "223", "224", "225", "226", "227", "228",
                "229", "230", "231", "232", "233", "234", "235", "236", "237",
                "238", "239", "240", "241", "242", "243", "244", "245", "246",
                "247", "248", "249", "250", "251", "252", "253", "254", "255",
                "256", "257", "258", "259", "260", "261", "262", "263", "264",
                "265", "266", "267", "268", "269", "270", "271", "272", "273",
                "274", "275", "276", "277", "278", "279", "280", "281", "282",
                "283", "284", "285", "286", "287", "288", "289", "290", "291",
                "292", "293", "294", "295", "296", "297");
        int resultExpected = 297;
        /*
         * Call method under test
         */
        int result = m.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(resultExpected, result);
    }

}
