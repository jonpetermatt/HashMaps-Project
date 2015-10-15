import java.util.ArrayList;
import java.util.List;

public class ChainingHashMap<K extends Comparable<K>, V> {

	private int totalNodes;
	private int hashMapSize;
	private int multiplier;
	private int modulus;
	private ChainingHashMapNode<K, V>[] items;
	private HashMapNode<K, V> DEFUNCT = new HashMapNode<>(null, null);
	
	// construct a HashMap with 4000 places and given hash parameters
	public ChainingHashMap(int multiplier, int modulus) {		
		hashMapSize = 4000;
		this.modulus = modulus;
		this.multiplier = multiplier;
		items = new ChainingHashMapNode[hashMapSize];
	}
	
	// construct a HashMap with given capacity and given hash parameters
	public ChainingHashMap(int hashMapSize, int multiplier, int modulus) {
		this.hashMapSize = hashMapSize;
		this.modulus = modulus;
		this.multiplier = multiplier;
		items = new ChainingHashMapNode[hashMapSize];
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
	//returns an int array of size 2 that contains the number of the fullest bucket as well as how many nodes are in the bucket
	//searches through items to find buckets
	//if a bucket is found create int bucketSize that equals 1 and ChainingHashMapNode watchedChain
	//if the next node is not null increase bucketSize by one and changed watchedNode to the next one
	//if fullestBucket is smaller than bucketSize than replace current values with the location as well as size of the current bucket
	//return fullestBucket
	public int[] getFullestBuckets() {
		int[] fullestBucket = new int[2];
		for (int i = 0; i < hashMapSize; i++) {
			if (items[i] != null) {
				int bucketSize = 1;
				ChainingHashMapNode watchedChain = items[i];
				while (watchedChain.getNext() != null) {
					bucketSize++;
					watchedChain = watchedChain.getNext();
				}
				if (fullestBucket[0] < bucketSize) {
					fullestBucket[0] = bucketSize;
					fullestBucket[1] = i;
				}
			}
		}
		return fullestBucket;
	}
	
	//returns a list of keys
	//goes through all the buckets and get get all the non DEFUNCT and non null nodes' keys
	public List<K> keys() {
		List<K> keyList = new ArrayList<K>();
		for (int i = 0; i < hashMapSize; i++) {
			if (items[i] != null) {
				ChainingHashMapNode watchedChain = items[i];
				while (watchedChain.getNext() != null) {
					keyList.add((K) watchedChain.getNode().getKey());
					watchedChain = watchedChain.getNext();
				}
				keyList.add((K) watchedChain.getNode().getKey());
			}
		}
		return keyList;
	}
	//put's a node in the hashmap 
	//returns the replaced value if there is one, else it returns null
	//creates an int index from hashing the key
	//if the hash map is empty at the index than it creates a new bucket and puts the chain in the chain
	//than it loops through the chain until the next node is null or the current node is DEFUNCT
	//within the loop the current node's key is compared with the given key
	//if the key is the same then replace the current value with the new one
	//if a watched node is DEFUNCT then replace it with the new value and key
	//else set the new node as the next node
	public V put(K key, V value) {
		int index = hash(key)%hashMapSize;
		HashMapNode<K, V> newNode = new HashMapNode(key, value);
		ChainingHashMapNode newChain = new ChainingHashMapNode<>(newNode);
		if (items[index] == null) {		
			items[index] = newChain;
		}
		ChainingHashMapNode watchedChain = items[index];
		while (watchedChain.getNext() != null && watchedChain.getNode() != DEFUNCT) {
			if (watchedChain.getNode().getKey() == key) {
				HashMapNode returnedValue = watchedChain.getNode();
				watchedChain.setNode(newNode);
				return (V) returnedValue.getValue();
			}
			watchedChain = watchedChain.getNext();
		}
		if (watchedChain.getNode() == DEFUNCT) {
			watchedChain.setNode(newNode);
			return null;
		}
		
		watchedChain.setNext(newChain);
		return null;
	}
	//returns the value of a node
	//seaches through the hashmap just like in put but looks for a matching key
	//if the key matches it returns the value at that node
	//else returns null
	public V get(K key) {
		int index = hash(key)%hashMapSize;
		ChainingHashMapNode watchedChain = items[index];
		while (watchedChain.getNode().getKey() != key && watchedChain.getNext() != null) {
			watchedChain = watchedChain.getNext();
		}
		if (watchedChain.getNode().getKey() == key) {
			return (V) (watchedChain.getNode()).getValue();
		}
		return null;
	}
	//removes a node in index and replaces it with DEFUNCT
	//searches through index just like put
	//else return null
	public V remove(K key) {
		int index = hash(key)%hashMapSize;
		ChainingHashMapNode watchedChain = items[index];
		while (watchedChain.getNode().getKey() != key && watchedChain.getNext() != null) {
			watchedChain = watchedChain.getNext();
		}
		if (watchedChain.getNode().getKey() == key) {
			V returnedValue = (V) watchedChain.getNode().getValue();
			watchedChain.setNode(DEFUNCT);
			return returnedValue;
		}
		return null;
	}
}
