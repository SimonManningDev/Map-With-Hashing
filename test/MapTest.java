import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.map.Map;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Simon Manning & Nam Nguyen
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     * Test for Default Constructor.
     */
    @Test
    public final void testDefaultConstructor() {
        Map<String, String> s = this.constructorTest();
        Map<String, String> r = this.constructorRef();

        assertEquals(s, r);
    }

    /**
     * Test for Constructor.
     */
    @Test
    public final void testConstructor1() {
        Map<String, String> s = this.createFromArgsTest("A", "5", "B", "6");
        Map<String, String> r = this.createFromArgsRef("A", "5", "B", "6");

        assertEquals(s, r);
    }

    /**
     * Test for Constructor.
     */
    @Test
    public final void testConstructor2() {
        Map<String, String> s = this.createFromArgsTest();
        Map<String, String> r = this.createFromArgsRef();

        assertEquals(s, r);
    }

    /**
     * Test for add.
     */
    @Test
    public void addTest1() {
        Map<String, String> map = this.constructorTest();
        map.add("First", "1st");
        map.add("Second", "2nd");
        map.add("Third", "3rd");

        Map<String, String> intent = this.constructorRef();
        intent.add("First", "1st");
        intent.add("Second", "2nd");
        intent.add("Third", "3rd");

        assertTrue(map.equals(intent));
    }

    /**
     * Test for add.
     */
    @Test
    public void addTest2() {
        Map<String, String> map = this.constructorTest();
        map.add("First", "1st");
        map.add("Second", "2nd");
        map.add("Third", "3rd");
        map.add("", "");

        Map<String, String> intent = this.constructorRef();
        intent.add("First", "1st");
        intent.add("Second", "2nd");
        intent.add("Third", "3rd");
        intent.add("", "");

        assertTrue(map.equals(intent));

    }

    /**
     * Test for add.
     */
    @Test
    public final void addEmpty() {
        Map<String, String> s = this.createFromArgsTest();
        Map<String, String> r = this.createFromArgsTest("1", "2");

        s.add("1", "2");
        assertEquals(s, r);
    }

    /**
     * Test for add.
     */
    @Test
    public final void addNonEmpty() {
        Map<String, String> s = this.createFromArgsTest("1", "2");
        Map<String, String> r = this.createFromArgsTest("1", "2", "3", "4");

        s.add("3", "4");
        assertEquals(s, r);
    }

    /**
     * Test for remove.
     */
    @Test
    public void removeTest1() {

        Map<String, String> map = this.constructorTest();
        map.add("First", "1st");
        map.add("Second", "2nd");
        map.add("Third", "3rd");

        Map<String, String> intent = this.constructorRef();
        intent.add("First", "1st");
        intent.add("Second", "2nd");
        intent.add("Third", "3rd");

        map.remove("Second");
        intent.remove("Second");

        assertTrue(map.equals(intent));

    }

    /**
     * Test for remove.
     */
    @Test
    public void removeTest2() {
        Map<String, String> map = this.constructorTest();
        map.add("First", "1st");
        map.add("Second", "2nd");
        map.add("Third", "3rd");
        map.add("", "");

        Map<String, String> intent = this.constructorRef();
        intent.add("First", "1st");
        intent.add("Second", "2nd");
        intent.add("Third", "3rd");
        intent.add("", "");

        map.remove("");
        intent.remove("");

        assertTrue(map.equals(intent));
    }

    /**
     * Test for remove.
     */
    @Test
    public void removeTest3() {

        Map<String, String> map = this.constructorTest();
        map.add("First", "1st");
        map.add("Second", "2nd");
        map.add("Third", "3rd");
        map.add("", "");

        Map<String, String> intent = this.constructorRef();
        intent.add("First", "1st");
        intent.add("Second", "2nd");
        intent.add("Third", "3rd");
        intent.add("", "");

        map.remove("First");
        intent.remove("First");

        assertTrue(map.equals(intent));

    }

    /**
     * Test for remove.
     */
    @Test
    public final void removeNonEmpty() {
        Map<String, String> s = this.createFromArgsTest("1", "2", "3", "4");
        Map<String, String> r = this.createFromArgsTest("1", "2");

        s.remove("3");
        assertEquals(s, r);
    }

    /**
     * Test for remove.
     */
    @Test
    public final void removeToEmpty() {
        Map<String, String> s = this.createFromArgsTest("1", "2");
        Map<String, String> r = this.createFromArgsTest();

        s.remove("1");
        assertEquals(s, r);
    }

    /**
     * Test for removeAny.
     */
    @Test
    public void removeAnyTest1() {

        Map<String, String> map = this.constructorTest();
        map.add("First", "1st");
        map.add("Second", "2nd");
        map.add("Third", "3rd");

        Map<String, String> intent = this.constructorRef();
        intent.add("First", "1st");
        intent.add("Second", "2nd");
        intent.add("Third", "3rd");

        assertTrue(intent.hasKey(map.removeAny().key()));
    }

    /**
     * Test for removeAny.
     */
    @Test
    public void removeAnyTest2() {

        Map<String, String> map = this.constructorTest();
        map.add("First", "1st");
        map.add("Second", "2nd");
        map.add("Third", "3rd");
        map.add("", "");

        Map<String, String> intent = this.constructorRef();
        intent.add("First", "1st");
        intent.add("Second", "2nd");
        intent.add("Third", "3rd");
        intent.add("", "");

        assertTrue(intent.hasKey(map.removeAny().key()));
    }

    /**
     * Test for removeAny.
     */
    @Test
    public void removeAnyTest3() {

        Map<String, String> map = this.constructorTest();
        map.add("First", "1st");
        map.add("Second", "2nd");
        map.add("Third", "3rd");
        map.add("", "");
        map.add("Fourth", "4th");

        Map<String, String> intent = this.constructorRef();
        intent.add("First", "1st");
        intent.add("Second", "2nd");
        intent.add("Third", "3rd");
        intent.add("", "");
        intent.add("Fourth", "4th");

        assertTrue(intent.hasKey(map.removeAny().key()));
    }

    /**
     * Test for removeAny.
     */
    @Test
    public final void removeAnyNonEmpty() {
        Map<String, String> s = this.createFromArgsTest("1", "2", "3", "4");
        Map<String, String> r = this.createFromArgsTest("1", "2", "3", "4");

        s.removeAny();
        r.removeAny();
        assertEquals(s, r);
    }

    /**
     * Test for removeAny.
     */
    @Test
    public final void removeAnyEmpty() {
        Map<String, String> s = this.createFromArgsTest("1", "2");
        Map<String, String> r = this.createFromArgsTest("1", "2");

        s.removeAny();
        r.removeAny();
        assertEquals(s, r);
    }

    /**
     * Ensure the pairs are not removed in alphabetical order.
     */

    @Test
    public void sizeAfterAdd() {
        Map<String, String> map = this.constructorTest();
        map.add("First", "1st");
        map.add("Second", "2nd");
        map.add("Third", "3rd");

        Map<String, String> intent = this.constructorRef();
        intent.add("First", "1st");
        intent.add("Second", "2nd");
        intent.add("Third", "3rd");

        int mapSize = map.size();
        int intentSize = intent.size();

        assertTrue(mapSize == intentSize);
    }

    /**
     * Test for size.
     */
    @Test
    public void sizeNonEmpty1() {
        Map<String, String> map = this.constructorTest();
        map.add("First", "1st");
        map.add("Second", "2nd");
        map.add("Third", "3rd");
        map.add("", "");

        Map<String, String> intent = this.constructorRef();
        intent.add("First", "1st");
        intent.add("Second", "2nd");
        intent.add("Third", "3rd");
        intent.add("", "");

        assertTrue(map.size() == intent.size());
    }

    /**
     * Test for size.
     */
    @Test
    public void sizeNonEmpty2() {
        Map<String, String> map = this.constructorTest();

        map.add("", "");

        Map<String, String> intent = this.constructorRef();

        intent.add("", "");

        assertTrue(map.size() == intent.size());
    }

    /**
     * Test for size.
     */
    @Test
    public void sizeEmpty() {
        Map<String, String> s = this.createFromArgsTest();
        int sizeTest = s.size();
        int sizeRef = 0;
        assertEquals(sizeTest, sizeRef);
    }

    /**
     * Test for size.
     */
    @Test
    public void sizeAfterRemove() {
        Map<String, String> s = this.createFromArgsTest("1", "2", "3", "4");
        assertEquals(s.size(), 2);
        s.remove("1");
        assertEquals(s.size(), 1);
    }

    /**
     * Test for size.
     */
    @Test
    public void sizeAfterRemoveAny() {
        Map<String, String> s = this.createFromArgsTest("1", "2", "3", "4");
        assertEquals(s.size(), 2);
        s.removeAny();
        assertEquals(s.size(), 1);
    }

    /**
     * Test for hasKey.
     */
    @Test
    public void hasKeyNonEmptyTrue() {
        Map<String, String> map = this.constructorTest();
        map.add("First", "1st");
        map.add("Second", "2nd");
        map.add("Third", "3rd");

        Map<String, String> intent = this.constructorRef();
        intent.add("First", "1st");
        intent.add("Second", "2nd");
        intent.add("Third", "3rd");

        boolean mapHasKey = map.hasKey("Third");
        boolean intentHasKey = intent.hasKey("Third");

        assertTrue(mapHasKey == intentHasKey);

    }

    /**
     * Test for hasKey.
     */
    @Test
    public void hasKeyNonEmptyFalse() {
        Map<String, String> map = this.constructorTest();
        map.add("First", "1st");
        map.add("Second", "2nd");
        map.add("Third", "3rd");

        Map<String, String> intent = this.constructorRef();
        intent.add("First", "1st");
        intent.add("Second", "2nd");
        intent.add("Third", "3rd");

        boolean mapHasKey = map.hasKey("Fourth");
        boolean intentHasKey = intent.hasKey("Fourth");

        assertTrue(mapHasKey == intentHasKey);
    }

    /**
     * Test for hasKey.
     */
    @Test
    public void hasKeyEmptyKey() {

        Map<String, String> map = this.constructorTest();
        map.add("First", "1st");
        map.add("Second", "2nd");
        map.add("Third", "3rd");
        map.add("", "");

        Map<String, String> intent = this.constructorRef();
        intent.add("First", "1st");
        intent.add("Second", "2nd");
        intent.add("Third", "3rd");
        intent.add("", "");

        assertTrue(map.hasKey("") == intent.hasKey(""));
    }

    /**
     * Test for hasKey.
     */
    @Test
    public void hasKeyEmpty() {

        Map<String, String> s = this.constructorTest();
        assertTrue(!s.hasKey("1") && !s.hasKey("3") && !s.hasKey("2"));

    }

    /**
     * Test for value.
     */
    @Test
    public void valueTest1() {
        Map<String, String> map = this.constructorTest();
        map.add("First", "1st");
        map.add("Second", "2nd");
        map.add("Third", "3rd");

        Map<String, String> intent = this.constructorRef();
        intent.add("First", "1st");
        intent.add("Second", "2nd");
        intent.add("Third", "3rd");

        assertTrue(map.value("First").equals(intent.value("First")));
    }

    /**
     * Test for value.
     */
    @Test
    public void valueTest2() {

        Map<String, String> map = this.constructorTest();
        map.add("First", "1st");
        map.add("Second", "2nd");
        map.add("Third", "3rd");
        map.add("", "");

        Map<String, String> intent = this.constructorRef();
        intent.add("First", "1st");
        intent.add("Second", "2nd");
        intent.add("Third", "3rd");
        intent.add("", "");

        assertTrue(map.value("").equals(intent.value("")));
    }

    /**
     * Test for value.
     */
    @Test
    public void valueTest3() {

        Map<String, String> map = this.constructorTest();
        map.add("First", "1st");
        map.add("Second", "2nd");
        map.add("Third", "3rd");
        map.add("", "");

        Map<String, String> intent = this.constructorRef();
        intent.add("First", "1st");
        intent.add("Second", "2nd");
        intent.add("Third", "3rd");
        intent.add("", "");

        assertTrue(map.value("Second").equals(intent.value("Second")));
    }

    /**
     * Test for value.
     */
    @Test
    public void valueTest4() {

        Map<String, String> s = this.createFromArgsTest("1", "2", "3", "4");
        assertEquals(s.value("1"), "2");
        assertEquals(s.value("3"), "4");

    }

}
