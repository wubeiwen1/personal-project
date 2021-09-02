import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Zheng Ji Tan(tan.955) & Beiwen Wu(wu.4742)
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /*
     ***************** Test cases for no argument constructor *****************
     */
    @Test
    public final void testNoArgumentConstructor() {
        /*
         * Set up variables and Call method under test
         */
        Set<String> n = this.constructorTest();
        Set<String> nExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /*
     ***************** Kernel methods - Test cases for add *****************
     */
    @Test //boundary case
    public final void testAddtoEmpty() {
        /*
         * Set up variables
         */
        Set<String> n = this.createFromArgsTest();
        Set<String> nExpected = this.createFromArgsRef("1");
        /*
         * Call method under test
         */
        n.add("1");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test //boundary case
    public final void testAddtoNonEmpty() {
        /*
         * Set up variables
         */
        Set<String> n = this.createFromArgsTest("1");
        Set<String> nExpected = this.createFromArgsRef("1", "2");
        /*
         * Call method under test
         */
        n.add("2");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test //routine case
    public final void testAddto5Empty() {
        /*
         * Set up variables
         */
        Set<String> n = this.createFromArgsTest();
        Set<String> nExpected = this.createFromArgsRef("1", "2", "3", "4", "5");
        /*
         * Call method under test
         */
        n.add("1");
        n.add("2");
        n.add("3");
        n.add("4");
        n.add("5");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test //routine case
    public final void testAddto5NonEmpty() {
        /*
         * Set up variables
         */
        Set<String> n = this.createFromArgsTest("1");
        Set<String> nExpected = this.createFromArgsRef("1", "2", "3", "4", "5",
                "6");
        /*
         * Call method under test
         */
        n.add("2");
        n.add("3");
        n.add("4");
        n.add("5");
        n.add("6");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test //routine case
    public final void testAddNotInOrder() {
        /*
         * Set up variables
         */
        Set<String> n = this.createFromArgsTest();
        Set<String> nExpected = this.createFromArgsRef("1", "4", "2", "3", "5");
        /*
         * Call method under test
         */
        n.add("1");
        n.add("2");
        n.add("3");
        n.add("4");
        n.add("5");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test //challenging case
    public final void testAdd5to101() {
        /*
         * Set up variables
         */
        Set<String> n = this.createFromArgsTest("1", "2", "3", "4", "5", "6",
                "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
                "18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
                "28", "29", "30", "31", "32", "33", "34", "35", "36", "37",
                "38", "39", "40", "41", "42", "43", "44", "45", "46", "47",
                "48", "49", "50", "51", "52", "53", "54", "55", "56", "57",
                "58", "59", "60", "61", "62", "63", "64", "65", "66", "67",
                "68", "69", "70", "71", "72", "73", "74", "75", "76", "77",
                "78", "79", "80", "81", "82", "83", "84", "85", "86", "87",
                "88", "89", "90", "91", "92", "93", "94", "95", "96", "97",
                "98", "99", "100", "101");
        Set<String> nExpected = this.createFromArgsRef("1", "2", "3", "4", "5",
                "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
                "17", "18", "19", "20", "21", "22", "23", "24", "25", "26",
                "27", "28", "29", "30", "31", "32", "33", "34", "35", "36",
                "37", "38", "39", "40", "41", "42", "43", "44", "45", "46",
                "47", "48", "49", "50", "51", "52", "53", "54", "55", "56",
                "57", "58", "59", "60", "61", "62", "63", "64", "65", "66",
                "67", "68", "69", "70", "71", "72", "73", "74", "75", "76",
                "77", "78", "79", "80", "81", "82", "83", "84", "85", "86",
                "87", "88", "89", "90", "91", "92", "93", "94", "95", "96",
                "97", "98", "99", "100", "101", "300", "505", "399", "888",
                "103548");
        /*
         * Call method under test
         */
        n.add("300");
        n.add("505");
        n.add("399");
        n.add("888");
        n.add("103548");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /*
     ***************** Kernel methods - Test cases for remove *****************
     */
    @Test // boundary case
    public final void testRemove1From1() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("abc");
        Set<String> sRef = this.createFromArgsRef();
        /*
         * Call method under test
         */
        String result = s.remove("abc");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sRef, s);
        assertEquals("abc", result);
    }

    @Test // routine case
    public final void testRemove1From3() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("abc", "cde", "efg");
        Set<String> sRef = this.createFromArgsRef("cde", "efg");
        /*
         * Call method under test
         */
        String result = s.remove("abc");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sRef, s);
        assertEquals("abc", result);
    }

    @Test // challenging case
    public final void testRemove3From3() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("abc", "cde", "efg");
        Set<String> sRef = this.createFromArgsRef();
        /*
         * Call method under test
         */
        String result1 = s.remove("abc");
        String result2 = s.remove("cde");
        String result3 = s.remove("efg");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sRef, s);
        assertEquals("abc", result1);
        assertEquals("cde", result2);
        assertEquals("efg", result3);
    }

    @Test // challenging case
    public final void testRemove5From101() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("1", "2", "3", "4", "5", "6",
                "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
                "18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
                "28", "29", "30", "31", "32", "33", "34", "35", "36", "37",
                "38", "39", "40", "41", "42", "43", "44", "45", "46", "47",
                "48", "49", "50", "51", "52", "53", "54", "55", "56", "57",
                "58", "59", "60", "61", "62", "63", "64", "65", "66", "67",
                "68", "69", "70", "71", "72", "73", "74", "75", "76", "77",
                "78", "79", "80", "81", "82", "83", "84", "85", "86", "87",
                "88", "89", "90", "91", "92", "93", "94", "95", "96", "97",
                "98", "99", "100", "101");
        Set<String> sRef = this.createFromArgsRef("2", "3", "4", "5", "6", "7",
                "8", "9", "10", "11", "12", "13", "14", "16", "17", "18", "19",
                "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
                "30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
                "40", "41", "42", "43", "44", "45", "46", "47", "48", "50",
                "51", "52", "53", "54", "55", "56", "57", "58", "59", "60",
                "61", "62", "63", "64", "65", "66", "67", "68", "69", "70",
                "71", "72", "73", "74", "75", "76", "77", "78", "79", "80",
                "81", "82", "83", "84", "85", "86", "87", "89", "90", "91",
                "92", "93", "94", "95", "96", "97", "98", "99", "100");
        /*
         * Call method under test
         */
        String result1 = s.remove("1");
        String result2 = s.remove("15");
        String result3 = s.remove("49");
        String result4 = s.remove("88");
        String result5 = s.remove("101");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sRef, s);
        assertEquals("1", result1);
        assertEquals("15", result2);
        assertEquals("49", result3);
        assertEquals("88", result4);
        assertEquals("101", result5);
    }

    /*
     ***************** Kernel methods - Test cases for removeAny *****************
     */
    @Test // boudary case
    public final void testRemoveAnyFrom1() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("CSE");
        Set<String> sRef = this.createFromArgsRef("CSE");
        /*
         * Call method under test
         */
        String str = s.removeAny();
        /*
         * Assert that sRef contains str
         */
        assertTrue(sRef.contains(str));
        /*
         * Remove str from sRef
         */
        String strExpected = sRef.remove(str);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sRef, s);
        assertEquals(strExpected, str);
    }

    @Test // routine case
    public final void testRemoveAny1From3() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("abc", "bcd", "efg");
        Set<String> sRef = this.createFromArgsRef("abc", "bcd", "efg");
        /*
         * Call method under test
         */
        String str = s.removeAny();
        /*
         * Assert that sRef contains str
         */
        assertTrue(sRef.contains(str));
        /*
         * Remove str from sRef
         */
        String strExpected = sRef.remove(str);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sRef, s);
        assertEquals(strExpected, str);
    }

    @Test // routine case
    public final void testRemoveAny3From3() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("abc", "bcd", "efg");
        Set<String> sRef = this.createFromArgsRef("abc", "bcd", "efg");
        /*
         * Call method under test
         */
        String str1 = s.removeAny();
        String str2 = s.removeAny();
        String str3 = s.removeAny();
        /*
         * Assert that sRef contains str
         */
        assertTrue(sRef.contains(str1));
        assertTrue(sRef.contains(str2));
        assertTrue(sRef.contains(str3));
        /*
         * Remove str from sRef
         */
        String str1Expected = sRef.remove(str1);
        String str2Expected = sRef.remove(str2);
        String str3Expected = sRef.remove(str3);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sRef, s);
        assertEquals(str1Expected, str1);
        assertEquals(str2Expected, str2);
        assertEquals(str3Expected, str3);
    }

    @Test // routine case
    public final void testRemoveAnyFrom101() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("1", "2", "3", "4", "5", "6",
                "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
                "18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
                "28", "29", "30", "31", "32", "33", "34", "35", "36", "37",
                "38", "39", "40", "41", "42", "43", "44", "45", "46", "47",
                "48", "49", "50", "51", "52", "53", "54", "55", "56", "57",
                "58", "59", "60", "61", "62", "63", "64", "65", "66", "67",
                "68", "69", "70", "71", "72", "73", "74", "75", "76", "77",
                "78", "79", "80", "81", "82", "83", "84", "85", "86", "87",
                "88", "89", "90", "91", "92", "93", "94", "95", "96", "97",
                "98", "99", "100", "101");
        Set<String> sRef = this.createFromArgsRef("1", "2", "3", "4", "5", "6",
                "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
                "18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
                "28", "29", "30", "31", "32", "33", "34", "35", "36", "37",
                "38", "39", "40", "41", "42", "43", "44", "45", "46", "47",
                "48", "49", "50", "51", "52", "53", "54", "55", "56", "57",
                "58", "59", "60", "61", "62", "63", "64", "65", "66", "67",
                "68", "69", "70", "71", "72", "73", "74", "75", "76", "77",
                "78", "79", "80", "81", "82", "83", "84", "85", "86", "87",
                "88", "89", "90", "91", "92", "93", "94", "95", "96", "97",
                "98", "99", "100", "101");
        /*
         * Call method under test
         */
        String str1 = s.removeAny();
        /*
         * Assert that sRef contains str
         */
        assertTrue(sRef.contains(str1));
        /*
         * Remove str from sRef
         */
        String str1Expected = sRef.remove(str1);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sRef, s);
        assertEquals(str1Expected, str1);
    }

    /*
     ***************** Kernel methods - Test cases for contains *****************
     */
    @Test //boundary case
    public final void testContainsTrueLength1() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("abc");
        Set<String> sRef = this.createFromArgsRef("abc");
        /*
         * Call method under test
         */
        boolean result = s.contains("abc");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sRef, s);
        assertEquals(true, result);
    }

    @Test //boundary case
    public final void testContainsFalseLength1() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("abc");
        Set<String> sRef = this.createFromArgsRef("abc");
        /*
         * Call method under test
         */
        boolean result = s.contains("a");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sRef, s);
        assertEquals(false, result);
    }

    @Test //boundary case
    public final void testContainsFalseEmpty() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest();
        Set<String> sRef = this.createFromArgsRef();
        /*
         * Call method under test
         */
        boolean result = s.contains("abc");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sRef, s);
        assertEquals(false, result);
    }

    @Test //routine case
    public final void testContainsTrueLength101() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("1", "2", "3", "4", "5", "6",
                "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
                "18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
                "28", "29", "30", "31", "32", "33", "34", "35", "36", "37",
                "38", "39", "40", "41", "42", "43", "44", "45", "46", "47",
                "48", "49", "50", "51", "52", "53", "54", "55", "56", "57",
                "58", "59", "60", "61", "62", "63", "64", "65", "66", "67",
                "68", "69", "70", "71", "72", "73", "74", "75", "76", "77",
                "78", "79", "80", "81", "82", "83", "84", "85", "86", "87",
                "88", "89", "90", "91", "92", "93", "94", "95", "96", "97",
                "98", "99", "100", "101");
        Set<String> sRef = this.createFromArgsRef("1", "2", "3", "4", "5", "6",
                "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
                "18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
                "28", "29", "30", "31", "32", "33", "34", "35", "36", "37",
                "38", "39", "40", "41", "42", "43", "44", "45", "46", "47",
                "48", "49", "50", "51", "52", "53", "54", "55", "56", "57",
                "58", "59", "60", "61", "62", "63", "64", "65", "66", "67",
                "68", "69", "70", "71", "72", "73", "74", "75", "76", "77",
                "78", "79", "80", "81", "82", "83", "84", "85", "86", "87",
                "88", "89", "90", "91", "92", "93", "94", "95", "96", "97",
                "98", "99", "100", "101");
        /*
         * Call method under test
         */
        boolean result1 = s.contains("55");
        boolean result2 = s.contains("89");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sRef, s);
        assertEquals(true, result1);
        assertEquals(true, result2);
    }

    @Test //routine case
    public final void testContainsFalseLength101() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("1", "2", "3", "4", "5", "6",
                "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
                "18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
                "28", "29", "30", "31", "32", "33", "34", "35", "36", "37",
                "38", "39", "40", "41", "42", "43", "44", "45", "46", "47",
                "48", "49", "50", "51", "52", "53", "54", "55", "56", "57",
                "58", "59", "60", "61", "62", "63", "64", "65", "66", "67",
                "68", "69", "70", "71", "72", "73", "74", "75", "76", "77",
                "78", "79", "80", "81", "82", "83", "84", "85", "86", "87",
                "88", "89", "90", "91", "92", "93", "94", "95", "96", "97",
                "98", "99", "100", "101");
        Set<String> sRef = this.createFromArgsRef("1", "2", "3", "4", "5", "6",
                "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
                "18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
                "28", "29", "30", "31", "32", "33", "34", "35", "36", "37",
                "38", "39", "40", "41", "42", "43", "44", "45", "46", "47",
                "48", "49", "50", "51", "52", "53", "54", "55", "56", "57",
                "58", "59", "60", "61", "62", "63", "64", "65", "66", "67",
                "68", "69", "70", "71", "72", "73", "74", "75", "76", "77",
                "78", "79", "80", "81", "82", "83", "84", "85", "86", "87",
                "88", "89", "90", "91", "92", "93", "94", "95", "96", "97",
                "98", "99", "100", "101");
        /*
         * Call method under test
         */
        boolean result = s.contains("505");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sRef, s);
        assertEquals(false, result);
    }

    /*
     ***************** Kernel methods - Test cases for size *****************
     */
    @Test //boundary case
    public final void testSize0() {
        /*
         * Set up variables
         */
        Set<String> n = this.createFromArgsTest();
        Set<String> nExpected = this.createFromArgsRef();
        int sizeExpected = 0;
        /*
         * Call method under test
         */
        int size = n.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(sizeExpected, size);
    }

    @Test //boundary case
    public final void testSize1() {
        /*
         * Set up variables
         */
        Set<String> n = this.createFromArgsTest("1");
        Set<String> nExpected = this.createFromArgsRef("1");
        int sizeExpected = 1;
        /*
         * Call method under test
         */
        int size = n.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(sizeExpected, size);
    }

    @Test //routine case
    public final void testSize8() {
        /*
         * Set up variables
         */
        Set<String> n = this.createFromArgsTest("1", "2", "3", "4", "5", "6",
                "7", "8");
        Set<String> nExpected = this.createFromArgsRef("1", "2", "3", "4", "5",
                "6", "7", "8");
        int sizeExpected = 8;
        /*
         * Call method under test
         */
        int size = n.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(sizeExpected, size);
    }

    @Test //routine case
    public final void testSize273() {
        /*
         * Set up variables
         */
        Set<String> n = this.createFromArgsTest("1", "2", "3", "4", "5", "6",
                "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
                "18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
                "28", "29", "30", "31", "32", "33", "34", "35", "36", "37",
                "38", "39", "40", "41", "42", "43", "44", "45", "46", "47",
                "48", "49", "50", "51", "52", "53", "54", "55", "56", "57",
                "58", "59", "60", "61", "62", "63", "64", "65", "66", "67",
                "68", "69", "70", "71", "72", "73", "74", "75", "76", "77",
                "78", "79", "80", "81", "82", "83", "84", "85", "86", "87",
                "88", "89", "90", "91", "92", "93", "94", "95", "96", "97",
                "98", "99", "100", "101", "102", "103", "104", "105", "106",
                "107", "108", "109", "110", "111", "112", "113", "114", "115",
                "116", "117", "118", "119", "120", "121", "122", "123", "124",
                "125", "126", "127", "128", "129", "130", "131", "132", "133",
                "134", "135", "136", "137", "138", "139", "140", "141", "142",
                "143", "144", "145", "146", "147", "148", "149", "150", "151",
                "152", "153", "154", "155", "156", "157", "158", "159", "160",
                "161", "162", "163", "164", "165", "166", "167", "168", "169",
                "170", "171", "172", "173", "174", "175", "176", "177", "178",
                "179", "180", "181", "182", "183", "184", "185", "186", "187",
                "188", "189", "190", "191", "192", "193", "194", "195", "196",
                "197", "198", "199", "200", "201", "202", "203", "204", "205",
                "206", "207", "208", "209", "210", "211", "212", "213", "214",
                "215", "216", "217", "218", "219", "220", "221", "222", "223",
                "224", "225", "226", "227", "228", "229", "230", "231", "232",
                "233", "234", "235", "236", "237", "238", "239", "240", "241",
                "242", "243", "244", "245", "246", "247", "248", "249", "250",
                "251", "252", "253", "254", "255", "256", "257", "258", "259",
                "260", "261", "262", "263", "264", "265", "266", "267", "268",
                "269", "270", "271", "272", "273");
        Set<String> nExpected = this.createFromArgsRef("1", "2", "3", "4", "5",
                "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
                "17", "18", "19", "20", "21", "22", "23", "24", "25", "26",
                "27", "28", "29", "30", "31", "32", "33", "34", "35", "36",
                "37", "38", "39", "40", "41", "42", "43", "44", "45", "46",
                "47", "48", "49", "50", "51", "52", "53", "54", "55", "56",
                "57", "58", "59", "60", "61", "62", "63", "64", "65", "66",
                "67", "68", "69", "70", "71", "72", "73", "74", "75", "76",
                "77", "78", "79", "80", "81", "82", "83", "84", "85", "86",
                "87", "88", "89", "90", "91", "92", "93", "94", "95", "96",
                "97", "98", "99", "100", "101", "102", "103", "104", "105",
                "106", "107", "108", "109", "110", "111", "112", "113", "114",
                "115", "116", "117", "118", "119", "120", "121", "122", "123",
                "124", "125", "126", "127", "128", "129", "130", "131", "132",
                "133", "134", "135", "136", "137", "138", "139", "140", "141",
                "142", "143", "144", "145", "146", "147", "148", "149", "150",
                "151", "152", "153", "154", "155", "156", "157", "158", "159",
                "160", "161", "162", "163", "164", "165", "166", "167", "168",
                "169", "170", "171", "172", "173", "174", "175", "176", "177",
                "178", "179", "180", "181", "182", "183", "184", "185", "186",
                "187", "188", "189", "190", "191", "192", "193", "194", "195",
                "196", "197", "198", "199", "200", "201", "202", "203", "204",
                "205", "206", "207", "208", "209", "210", "211", "212", "213",
                "214", "215", "216", "217", "218", "219", "220", "221", "222",
                "223", "224", "225", "226", "227", "228", "229", "230", "231",
                "232", "233", "234", "235", "236", "237", "238", "239", "240",
                "241", "242", "243", "244", "245", "246", "247", "248", "249",
                "250", "251", "252", "253", "254", "255", "256", "257", "258",
                "259", "260", "261", "262", "263", "264", "265", "266", "267",
                "268", "269", "270", "271", "272", "273");
        int sizeExpected = 273;
        /*
         * Call method under test
         */
        int size = n.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(sizeExpected, size);
    }
}
