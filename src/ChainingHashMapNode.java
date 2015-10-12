
public class ChainingHashMapNode<K extends Comparable<K>, V> {
	
	private HashMapNode<K, V> node;
	private ChainingHashMapNode<K, V> nextlink;
	
	public ChainingHashMapNode(HashMapNode node) {
		this.node = node;
		this.nextlink = null;
	}
	
	public HashMapNode<K, V> getNode() {
		return node;
	}
	public void setNode(HashMapNode node) {
		this.node = node;
	}
	
	public ChainingHashMapNode getNext() {
		return nextlink;
	}
	
	public void setNext(ChainingHashMapNode nextLink) {
		this.nextlink = nextLink;
	}
}
