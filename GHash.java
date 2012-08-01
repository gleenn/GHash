public class GHash<K,V> {
	public static void main(final String[] args) {
		GHash<String, String> ghash = new GHash<String, String>();
		ghash.put("a", "b").put("b", "c");
		expectEqual(ghash.get("b"), "c");
		expectEqual(ghash.get("a"), "b");
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

	public GHash<K,V> put(K key, V value) {
		table[key.hashCode()%table.length] = new Entry<K,V>(key, value);
		return this;
	}

	public V get(K key) {
		return table[key.hashCode()%table.length].value;
	}

	public String toString() {
		String result = "{";
		for(Entry e : table) {
			result += e + ", ";
		}	
		result = result.substring(0, result.length()-2);
		result += "}";
		return result;
	}

	static class Entry<K,V> {
		public K key;
		public V value;
		Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}
		//public V getValue() { return value; }	
		//public K getKey() { return key; }
		public boolean equals(Entry<K,V> other) {
			return key.equals(other.key) && value.equals(other.value);
		}	
		public String toString() { return key + "=" + value; }
	}	
}
