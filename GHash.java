public class GHash<K,V> {
	public static void main(final String[] args) {
		GHash<String, String> ghash = new GHash<String, String>(4);
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
		System.out.println();
		System.out.println(ghash);
	}

	public static void expectEqual(String actual, String expected) {
		if(actual != null && expected != null && actual.equals(expected)) {
			System.out.print(".");
		} else {
			System.out.println("failed expected:" + expected + " actual:" + actual);
		}
	}

	static final int DEFAULT_INITIAL_CAPACITY = 16;		
	//static final int MAXIMUM_CAPACITY = 1 << 30;
	//static final float DEFAULT_LOAD_FACTOR = 0.75f;
	Entry[] table;
	//int size;

	public <K,V> GHash() {
		//size = 0;
		table = new Entry[DEFAULT_INITIAL_CAPACITY];
	}

	public <K,V> GHash(int initialCapacity) {
		table = new Entry[initialCapacity];
	}

	public GHash<K,V> put(K key, V value) {
		int index = key.hashCode() % table.length;
		Entry<K,V> previous = table[index];
		table[index] = new Entry<K,V>(key, value, table[index]);
		return this;
	}

	public V get(K key) {
		return (V)table[key.hashCode()%table.length].value;
	}

	public String toString() {
		StringBuilder result = new StringBuilder("{\n");
		for(Entry<K,V> e : table) {
			result.append(e);
			for(e = e.next; e != null; e = e.next) {
				result.append(" " + e + "\n");
			}
			result.append("\n");
		}	
		//result.delete(result.length()-2, result.length());
		result.append("\n}");
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
