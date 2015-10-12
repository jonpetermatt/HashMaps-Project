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
	
	public boolean isEmpty() {
		if (totalNodes == 0) {
			return true;
		}
		return false;
	}
	
	public int[] getFullestBuckets() {
		int[] fullestBucket = new int[2];
		for (int i = 0; i < hashMapSize; i++) {
			if (items[i] != null) {
				int bucketSize = 0;
				ChainingHashMapNode watchedChain = items[i];
				while (watchedChain.getNext() != null) {
					bucketSize++;
					watchedChain = watchedChain.getNext();
				}
				bucketSize++;
				if (fullestBucket[0] < bucketSize) {
					fullestBucket[0] = bucketSize;
					fullestBucket[1] = i;
				}
			}
		}
		return fullestBucket;
	}
	
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
	
	public V put(K key, V value) {
		int index = hash(key)%hashMapSize;
		HashMapNode<K, V> newNode = new HashMapNode(key, value);
		ChainingHashMapNode newChain = new ChainingHashMapNode<>(newNode);
		if (items[index] == null) {		
			items[index] = newChain;
		}
		ChainingHashMapNode watchedChain = items[index];
		while (watchedChain.getNext() != null && watchedChain.getNode() != DEFUNCT) {
			watchedChain = watchedChain.getNext();
		}
		if (watchedChain.getNode() == DEFUNCT) {
			watchedChain.setNode(newNode);
			return null;
		}
		watchedChain.setNext(newChain);
		return null;
	}
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
