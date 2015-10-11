import java.util.ArrayList;
import java.util.List;

public class HashMap<K extends Comparable<K>, V> {
	
	private int totalNodes;
	private int hashMapSize;
	private int multiplier;
	private int modulus;
	private HashMapNode<K, V>[] items;
	private HashMapNode<K, V> DEFUNCT = new HashMapNode<>(null, null);
	private int putCollisions = 0;
	private int totalCollisions = 0;
	private int maxCollisions = 0;
	private int maxCollisionsThisPut = 0;
	
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
		items = (HashMapNode<K, V>[]) new HashMapNode[hashMapSize];
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
		List<K> keyList = new ArrayList<K>();
		for (int i = 0; i < hashMapSize; i++) {
			if (items[i] != null && items[i] != DEFUNCT) {
				keyList.add(items[i].key);
			}
		}
		return keyList;
	}
	
	public V put(K key, V value) throws Exception {
		int index = hash(key)%hashMapSize;
		int originalIndex = index;
		while (items[index] != null && items[index] != DEFUNCT && index != originalIndex-1) {
			index++;
			totalCollisions++;
			maxCollisionsThisPut++;
			if (index == hashMapSize-1) {
				index = 0;
			}
		}
		if (items[index] == null || items[index] == DEFUNCT) {
			if (index != hash(key)%hashMapSize) {
				putCollisions++;
			}
			HashMapNode<K, V> newNode = new HashMapNode(key, value);
			items[index] = newNode;
			totalNodes++;
			if (maxCollisionsThisPut > maxCollisions) {
				maxCollisions = maxCollisionsThisPut;
			}
			maxCollisionsThisPut = 0;
			return null;
		}
		else {
			maxCollisions = hashMapSize;
			maxCollisionsThisPut = 0;
			throw new Exception();
		}
		
		
	}
	
	public V get(K key) {
		int index = hash(key)%hashMapSize;
		int originalIndex = index;
		while (index != originalIndex-1) {
			if (items[index] == null || items[index] == DEFUNCT) {
				return null;
			}
			if (items[index].getKey() == key) {
				return items[index].getValue();
			}
			if (index == hashMapSize-1) {
				index = 0;
			}
			index++;
		}
		return null;
	}
	
	public V remove(K key) {
		int index = hash(key)%hashMapSize;
		int originalIndex = index;
		while (items[index].getKey() != key && index != originalIndex-1) {
			index++;
			if (index == hashMapSize-1) {
				index = 0;
			}
		}
		if (items[index].getKey() == key) {
			 	V originalValue = items[index].getValue();
				items[index] = DEFUNCT;
				totalNodes--;
				return originalValue;
		}
		return null;
	}
	
	public int putCollisions() {
		return putCollisions;
		
	}
	
	public int totalCollisions() {
		return totalCollisions;
	}
	
	public int maxCollisions() {
		return maxCollisions;
	}
	
	public void resetStatistics() {
		putCollisions = 0;
		totalCollisions = 0;
		maxCollisions = 0;
	}
}

