import java.util.ArrayList;
import java.util.List;

public class HashMap<K extends Comparable<K>, V> {
	
	private int totalNodes;
	private int hashMapSize;
	private int multiplier;
	private int modulus;
	private HashMapNode<K, V>[] items;
	private HashMapNode<K, V> DEFUNCT = new HashMapNode<>(null, null);
	
	// construct a HashMap with 4000 places and given hash parameters
	public  HashMap(int multiplier, int modulus) {		
		hashMapSize = 4000;
		this.modulus = modulus;
		this.multiplier = multiplier;
		items = new HashMapNode[hashMapSize];
	}
	
	// construct a HashMap with given capacity and given hash parameters
	public  HashMap(int hashMapSize, int multiplier, int modulus) {
		this.hashMapSize = hashMapSize;
		this.modulus = modulus;
		this.multiplier = multiplier;
		items = new HashMapNode[hashMapSize];
	}
	
	// hashing
	public int hash(K key) {
		int hashKey = 0;
		hashKey = Math.abs(multiplier*key.hashCode())%modulus;
		return hashKey;
	}
	
	// size (return the number of nodes currently stored in the map)
	public int size() {
		return totalNodes;
	}
	
	public boolean isEmpty() {
		if (totalNodes == 0) {
			return true;
		}
		return false;
	}
	
	public List<K> keys() {
		ArrayList<K> keyList = new ArrayList<K>();
		for (int i = 0; i < hashMapSize; i++) {
			if (items[i] != null) {
				keyList.add(items[i].key);
			}
		}
		return keyList;
	}
	
	public V put(K key, V value) {
		int index = hash(key)%hashMapSize;
		V temp = items[index].getValue();
		items[index].setValue(value);
		return temp;
	}
	
	public V get(K key) {
		int index = hash(key)%hashMapSize;
		int originalIndex = index;
		while (items[index].getKey() != key || items[index].getKey() == null || index != originalIndex-1) {
			index++;
			if (index == hashMapSize-1) {
				index = 0;
			}
		}
		if (items[index].getKey() == key) {
			return items[index].getValue();
		}
		return null;
	}
	
	public V remove(K key) {
		int index = hash(key)%hashMapSize;
		V temp = items[index].getValue();
		items[index] = DEFUNCT;
		return temp;
	}

	
}
