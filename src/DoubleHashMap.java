import java.util.ArrayList;
import java.util.List;

public class DoubleHashMap<K extends Comparable<K>, V> {
	
	private int totalNodes;
	private int hashMapSize;
	private int multiplier;
	private int modulus;
	private int secondaryModulus;
	private HashMapNode<K, V>[] items;
	private HashMapNode<K, V> DEFUNCT = new HashMapNode<>(null, null);
	private int putCollisions = 0;
	private int totalCollisions = 0;
	private int maxCollisions = 0;
	private int maxCollisionsThisPut = 0;
	private int putFailures = 0;
	
	// updated construction
	// construct a HashMap with 4000 places and given hash parameters
	public DoubleHashMap(int multiplier, int modulus, int secondaryModulus) {
		hashMapSize = 4000;
		this.multiplier = multiplier;
		this.modulus = modulus;
		this.secondaryModulus = secondaryModulus;
		items = new HashMapNode[hashMapSize];
	}
	
	// construct a HashMap with given capacity and given hash parameters
	public DoubleHashMap(int hashMapSize,int multiplier, int modulus, int secondaryModulus) {
		this.hashMapSize = hashMapSize;
		this.multiplier = multiplier;
		this.modulus = modulus;
		this.secondaryModulus = secondaryModulus;
		items = new HashMapNode[hashMapSize];
	}
	
	// hashing
	public int hash(K key) {
		int hashKey = 0;
		hashKey = Math.abs(multiplier*key.hashCode())%modulus;
		return hashKey;
	}
	
	public int secondaryHash(K key) {
		return secondaryModulus - Math.abs(key.hashCode())%secondaryModulus;
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
		int secondaryIndex = secondaryHash(key)%hashMapSize;
		int tryCounter = 0;
		while (items[index] != null && items[index] != DEFUNCT && tryCounter != hashMapSize) {
			index = index + secondaryIndex;
			tryCounter++;
			if (index >= hashMapSize) {
				index = index - hashMapSize;
			}
			totalCollisions++;
			maxCollisionsThisPut = maxCollisionsThisPut + secondaryIndex; 
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
			putFailures++;
			throw new RuntimeException("Double Hashing failed to find a free position");
		}	
	}
	public V get(K key) {
		int index = hash(key)%hashMapSize;
		int secondaryIndex = secondaryHash(key)%hashMapSize;
		int tryCounter = 1;
		while (tryCounter < hashMapSize) {
			if (items[index] == null || items[index] == DEFUNCT) {
				return null;
			}
			if (items[index].getKey() == key) {
				return items[index].getValue();
			}
			index = index + secondaryIndex;
			if (index >= hashMapSize) {
				index = index - hashMapSize;
			}
		}
		return null;
	}
	public V remove(K key) {
		int index = hash(key)%hashMapSize;
		int secondaryIndex = secondaryHash(key)%hashMapSize;
		int tryCounter = 1;
		while (items[index].getKey() != key && tryCounter < hashMapSize) {
			index = index + secondaryIndex;
			if (index >= hashMapSize) {
				index = index - hashMapSize;
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
	public int putFailures() {
		return putFailures;
	}
}
