
public class HashMapNode <K extends Comparable<K>, V> {
	
    public K key;
    public V value;
	
	// construction
	public HashMapNode(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	public K getKey() {
		return key;
		
	}
	
	public V getValue() {
		return value;
	}
	
	public void setValue(V newValue) {
		value = newValue;
	}

}
