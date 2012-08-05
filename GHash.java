@SuppressWarnings({"unchecked"})
public class GHash<K,V> {
	public static void main(final String[] args) {
		testDefaultConstructor();
		testHashFilling();
		testHashResizing();
		System.out.println();
	}

	public static void testDefaultConstructor() {
		expectEqual(DEFAULT_INITIAL_CAPACITY, (new GHash()).capacity());
	}

	public static void testHashResizing() {
		GHash<String,String> growingHash = new GHash<String,String>(1);
		expectEqual(growingHash.size(), 0);
		expectEqual(growingHash.capacity(), 1);

		growingHash.put("a","b");
		expectEqual(growingHash.size(), 1);
		expectEqual(growingHash.capacity(), 2);

		growingHash.put("b","c");
		expectEqual(growingHash.size(), 2);
		expectEqual(growingHash.capacity(), 4);

		growingHash.put("c","d");
		expectEqual(growingHash.size(), 3);
		expectEqual(growingHash.capacity(), 4);

		growingHash.put("d","e");
		expectEqual(growingHash.size(), 4);
		expectEqual(growingHash.capacity(), 8);

		growingHash.put("e","f");
		expectEqual(growingHash.size(), 5);
		expectEqual(growingHash.capacity(), 8);
	}

	public static void testHashFilling() {
		for(int i=1; i<10; i++)
			testHash(i);
	}

	public static void testHash(int capacity) {
		GHash<String, String> ghash = new GHash<String, String>(capacity);
		ghash.put("a", "b").
					put("b", "c").
					put("c", "d").
					put("d", "e").
					put("e", "f");

		expectEqual(ghash.get("a"), "b");
		expectEqual(ghash.get("b"), "c");
		expectEqual(ghash.get("c"), "d");
		expectEqual(ghash.get("d"), "e");
		expectEqual(ghash.get("e"), "f");
	}

	public static void expectEqual(int actual, int expected) {
		if(actual == expected) {
			System.out.print(".");
		} else {
			System.out.println("failed actual:" + actual + " expected:" + expected);
		}
	}

	public static void expectEqual(String actual, String expected) {
		if(actual != null && expected != null && actual.equals(expected)) {
			System.out.print(".");
		} else {
			System.out.println("failed actual:" + actual + " expected:" + expected);
		}
	}

	static final int DEFAULT_INITIAL_CAPACITY = 16;		
	//static final int MAXIMUM_CAPACITY = 1 << 30;
	static final float DEFAULT_LOAD_FACTOR = 0.75f;
	private Entry[] table;
	private int size;

	public <K,V> GHash() {
		this(DEFAULT_INITIAL_CAPACITY);
	}

	public <K,V> GHash(int initialCapacity) {
		size = 0;
		table = new Entry[initialCapacity];
	}

	public GHash<K,V> put(K key, V value) {
		if(size + 1 > table.length * DEFAULT_LOAD_FACTOR) resize();
		putWithoutCapacityCheck(key, value);
		return this;
	}

	private void putWithoutCapacityCheck(K key, V value) {
		size++;
		int index = indexFor(key);
		Entry<K,V> previous = table[index];
		table[index] = new Entry<K,V>(key, value, table[index]);
	}

	public V get(K key) {
		Entry<K,V> e = table[key.hashCode()%table.length];
		for(;e != null; e = e.next) {
			if(e.key.equals(key)) return e.value;
		}
		return null;
	}

	public int size() {
		return size;
	}

	public int capacity() {
		return table.length;
	}

	private int indexFor(K key) {
		return key.hashCode() % table.length;
	}

	private void resize() {
		GHash<K,V> newHash = new GHash<K,V>(capacity() * 2);
		for(Entry<K,V> e : table) {
			for(; e != null; e = e.next) {
				newHash.putWithoutCapacityCheck(e.key, e.value);
			}
		}
		size = newHash.size();
		table = newHash.table;
	}

	public String toString() {
		StringBuilder result = new StringBuilder("{\n");
		int i = 0;
		for(Entry<K,V> e : table) {
			result.append(" [" + i++ + "]" );
			for(; e != null; e = e.next) {
				result.append(" " + e);
			}
			result.append("\n");
		}	
		result.append("}");
		return result.toString();
	}

	static class Entry<K,V> {
		final K key;
		final V value;
		Entry<K,V> next;
		
		Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}
		Entry(K key, V value, Entry<K,V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}
		public V getValue() { return value; }	
		public K getKey() { return key; }
		public boolean equals(Entry<K,V> other) {
			return key.equals(other.key) && value.equals(other.value);
		}	
		public String toString() { return "(" + key + " => " + value + ")"; }
	}	
}
