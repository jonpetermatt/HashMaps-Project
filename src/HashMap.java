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
	public HashMap(int multiplier, int modulus) {		
		hashMapSize = 4000;
		this.modulus = modulus;
		this.multiplier = multiplier;
		items = new HashMapNode[hashMapSize];
	}
	
	// construct a HashMap with given capacity and given hash parameters
	public HashMap(int hashMapSize, int multiplier, int modulus) {
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
	//isEmpty (returns true if empty else false
	public boolean isEmpty() {
		if (totalNodes == 0) {
			return true;
		}
		return false;
	}
	//returns a list of keys
	//goes through items from 0 to the hash map size and adds the keys of non null and non DEFUNCT points
	public List<K> keys() {
		List<K> keyList = new ArrayList<K>();
		for (int i = 0; i < hashMapSize; i++) {
			if (items[i] != null && items[i] != DEFUNCT) {
				keyList.add(items[i].key);
			}
		}
		return keyList;
	}
	//put's a node in the hashmap 
	//returns the replaced value if there is one, else it returns null
	//if there are no free spots in the map then throw an exception
	//creates an int index from hash and the key value
	//loop through items starting from the point index until null, DEFUNCT or a matching key is found
	//Increments one at a time 
	//or if the index is now one less than the original index
	//if null, DEFUNCT or the key is found than replace it with the new node
	//if the index is equal to one less than the index then throw an exception
	//every time there is no match increase totalCollisions and MaxCollisionsThisPut
	//if MaxCollisionsThisPut is greater than maxCollisions than set maxCollisions to MaxCollisionsThisPut
	//if index is not equal to originalIndex than increment putCollisions 
	public V put(K key, V value) {
		int index = hash(key)%hashMapSize;
		int originalIndex = index;
		while (items[index] != null && items[index] != DEFUNCT && index != originalIndex-1) {
			if (items[index].getKey() == key) {
				V returnedValue = items[index].getValue();
				items[index].setValue(value);
				return returnedValue;
			}
			index++;
			totalCollisions++;
			maxCollisionsThisPut++;
			if (index == hashMapSize-1) {
				index = 0;
			}
		}
		if (index != originalIndex) {
			putCollisions++;
		}
		if (items[index] == null || items[index] == DEFUNCT) {
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
			return null;
		}
		
		
		
	}
	//returns the value of the matching key if the key is found else return null
	//searches through index like in put
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
	//removes a node in index and replaces it with DEFUNCT
	//searches through index just like put
	public V remove(K key) {
		int index = hash(key)%hashMapSize;
		int originalIndex = index;
		while (index != originalIndex-1) {
			if (items[index] == null || items[index] == DEFUNCT) {
				return null;
			}
			if (items[index].getKey() == key) {
				V returningValue = items[index].getValue();
				items[index] = DEFUNCT;
				return returningValue;
			}
			if (index == hashMapSize-1) {
				index = 0;
			}
			index++;
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

