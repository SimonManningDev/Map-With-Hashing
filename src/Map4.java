import java.util.Iterator;
import java.util.NoSuchElementException;

import components.array.Array;
import components.array.Array1L;
import components.map.Map;
import components.map.Map2;
import components.map.MapSecondary;

/**
 * {@code Map} represented as a hash table using {@code Map}s for the buckets,
 * with implementations of primary methods.
 *
 * @param <K>
 *            type of {@code Map} domain (key) entries
 * @param <V>
 *            type of {@code Map} range (associated value) entries
 * @convention <pre>
 * |$this.hashTable.entries| > 0  and
 * for all i: integer, pf: PARTIAL_FUNCTION, x: K
 *     where (0 <= i  and  i < |$this.hashTable.entries|  and
 *            <pf> = $this.hashTable.entries[i, i+1)  and
 *            x is in DOMAIN(pf))
 *   ([computed result of x.hashCode()] mod |$this.hashTable.entries| = i))  and
 * |$this.hashTable.examinableIndices| = |$this.hashTable.entries|  and
 * $this.size = sum i: integer, pf: PARTIAL_FUNCTION
 *     where (0 <= i  and  i < |$this.hashTable.entries|  and
 *            <pf> = $this.hashTable.entries[i, i+1))
 *   (|pf|)
 * </pre>
 * @correspondence <pre>
 * this = union i: integer, pf: PARTIAL_FUNCTION
 *            where (0 <= i  and  i < |$this.hashTable.entries|  and
 *                   <pf> = $this.hashTable.entries[i, i+1))
 *          (pf)
 * </pre>
 *
 * @author Simon Manning & Nam Nguyen
 *
 */
public class Map4<K, V> extends MapSecondary<K, V> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Default size of hash table.
     */
    private static final int DEFAULT_HASH_TABLE_SIZE = 101;

    /**
     * Buckets for hashing.
     */
    private Array<Map<K, V>> hashTable;

    /**
     * Total size of abstract {@code this}.
     */
    private int size;

    /**
     * Computes {@code a} mod {@code b} as % should have been defined to work.
     *
     * @param a
     *            the number being reduced
     * @param b
     *            the modulus
     * @return the result of a mod b, which satisfies 0 <= {@code mod} < b
     * @requires b > 0
     * @ensures <pre>
     * 0 <= mod  and  mod < b  and
     * there exists k: integer (a = k * b + mod)
     * </pre>
     */
    private static int mod(int a, int b) {
        assert b > 0 : "Violation of: b > 0";

        /*
         * if a >= 0 then a % b is the correct clock arithmetic of a mod b
         * otherwise , if a < 0 then a % b is a value that is the correct clock
         * arithmetic of a mod b minus b.
         *
         * Therefore, if a >= 0, return a % b and if a < 0, return a % b + b
         */

        int mod = a % b;
        if (mod < 0) {
            mod += b;
        }

        return mod;

        // return ( a < 0 ) ? a % b : a % b + b;
    }

    /**
     * Creator of initial representation.
     *
     * @param hashTableSize
     *            the size of the hash table
     * @requires hashTableSize > 0
     * @ensures <pre>
     * |$this.hashTable.entries| = hashTableSize  and
     * for all i: integer
     *     where (0 <= i  and  i < |$this.hashTable.entries|)
     *   ($this.hashTable.entries[i, i+1) = <{}>  and
     *    i is in $this.hashTable.examinableIndices)  and
     * $this.size = 0
     * </pre>
     */
    private void createNewRep(int hashTableSize) {
        /*
         * hashTable.entries needs to be the size of {@code hashTableSize}.
         *
         * Each entry must be empty and of type Map2
         *
         * Simply call the constructor with the {@code hashTableSize} as the
         * parameter
         */
        this.hashTable = new Array1L<Map<K, V>>(hashTableSize);
        for (int i = 0; i < hashTableSize; i++) {
            this.hashTable.setEntry(i, new Map2<K, V>());
        }
        this.size = 0;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Map4() {
        /*
         * Create a new $this.hashTable with the default hash table size
         */
        this.createNewRep(DEFAULT_HASH_TABLE_SIZE);

    }

    /**
     * Constructor resulting in a hash table of size {@code hashTableSize}.
     *
     * @param hashTableSize
     *            size of hash table
     * @requires hashTableSize > 0
     * @ensures this = {}
     */
    public Map4(int hashTableSize) {
        /*
         * Create a new $this.hashTable with the size of {@code hashTableSize}.
         */
        this.createNewRep(hashTableSize);

    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final Map<K, V> newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep(DEFAULT_HASH_TABLE_SIZE);
    }

    @Override
    public final void transferFrom(Map<K, V> source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof Map4<?, ?> : ""
                + "Violation of: source is of dynamic type Map4<?,?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type Map4<?,?>, and
         * the ?,? must be K,V or the call would not have compiled.
         */
        Map4<K, V> localSource = (Map4<K, V>) source;
        this.hashTable = localSource.hashTable;
        this.size = localSource.size;
        localSource.createNewRep(DEFAULT_HASH_TABLE_SIZE);
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void add(K key, V value) {
        assert key != null : "Violation of: key is not null";
        assert value != null : "Violation of: value is not null";
        assert !this.hasKey(key) : "Violation of: key is not in DOMAIN(this)";

        /*
         * The hash code of the key will return a value that groups it with
         * other similar keys. In the map at the entry location that is the hash
         * code of the key, add the pair.
         */
        int properPlace = mod(key.hashCode(), this.hashTable.length());
        // locating appropriate index in the hash table
        this.hashTable.entry(properPlace).add(key, value);
        // Entering the entry at the appropriate location in the hash table

        this.size++; // incrementing the size of the hashTable upon each new addition

    }

    @Override
    public final Pair<K, V> remove(K key) {
        assert key != null : "Violation of: key is not null";
        assert this.hasKey(key) : "Violation of: key is in DOMAIN(this)";

        /*
         * The hash code of the key will return a value that groups it with
         * other similar keys. In the map at the entry location that is the hash
         * code of the key, remove the pair.
         */
        int properEntry = mod(key.hashCode(), this.hashTable.length());
        // locating appropriate index in the hash table
        this.size--;
        // decrementing size of the hash table of each remove() call
        return this.hashTable.entry(properEntry).remove(key);
        // returning the pair associated with the particular index in the hash table
    }

    @Override
    public final Pair<K, V> removeAny() {
        assert this.size() > 0 : "Violation of: this /= empty_set";
        /*
         * Along the array of maps, choose a random map. Check if the random map
         * is empty, if so, choose a different map. Upon selection of a map with
         * at least one key, remove a random key.
         */

        int count = 0;
        while (!(this.hashTable.entry(count).size() > 0)) {

            count++;
        }
        this.size--;
        // decrementing size of hashtable upon each removeAny() call
        return this.hashTable.entry(count).removeAny();
        // returning appropriate pair based on the index of the hashtable found
    }

    @Override
    public final V value(K key) {
        assert key != null : "Violation of: key is not null";
        assert this.hasKey(key) : "Violation of: key is in DOMAIN(this)";

        /*
         * Find the correct entry of the key... return its value
         */
        int properEntry = mod(key.hashCode(), this.hashTable.length());

        return this.hashTable.entry(properEntry).value(key);
        // returning appropriate value based on given key
    }

    @Override
    public final boolean hasKey(K key) {

//        assert this.hashTable.mayBeExamined(mod(key.hashCode(), this.hashTable
//                .length())) : "Violation of : this key is unexaminable";

        /*
         * The key should be stored in a specific map...
         */
        int properEntry = mod(key.hashCode(), this.hashTable.length());

        return this.hashTable.entry(properEntry).hasKey(key);
    }

    @Override
    public final int size() {

        return this.size; // returning size of the hash table
    }

    @Override
    public final Iterator<Pair<K, V>> iterator() {
        return new Map4Iterator();
    }

    /**
     * Implementation of {@code Iterator} interface for {@code Map4}.
     */
    private final class Map4Iterator implements Iterator<Pair<K, V>> {

        /**
         * Number of elements seen already (i.e., |~this.seen|).
         */
        private int numberSeen;

        /**
         * Bucket from which current bucket iterator comes.
         */
        private int currentBucket;

        /**
         * Bucket iterator from which next element will come.
         */
        private Iterator<Pair<K, V>> bucketIterator;

        /**
         * No-argument constructor.
         */
        Map4Iterator() {
            this.numberSeen = 0;
            this.currentBucket = 0;
            this.bucketIterator = Map4.this.hashTable.entry(0).iterator();
        }

        @Override
        public boolean hasNext() {
            return this.numberSeen < Map4.this.size;
        }

        @Override
        public Pair<K, V> next() {
            assert this.hasNext() : "Violation of: ~this.unseen /= <>";
            if (!this.hasNext()) {
                /*
                 * Exception is supposed to be thrown in this case, but with
                 * assertion-checking enabled it cannot happen because of assert
                 * above.
                 */
                throw new NoSuchElementException();
            }
            this.numberSeen++;
            if (this.bucketIterator.hasNext()) {
                return this.bucketIterator.next();
            }
            while (!this.bucketIterator.hasNext()) {
                this.currentBucket++;
                this.bucketIterator = Map4.this.hashTable
                        .entry(this.currentBucket).iterator();
            }
            return this.bucketIterator.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException(
                    "remove operation not supported");
        }

    }

}
