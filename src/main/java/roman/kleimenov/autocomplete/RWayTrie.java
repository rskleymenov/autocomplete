package roman.kleimenov.autocomplete;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Class RWayTrie is the implementation of Trie interface that represents
 * RWayTrie structure. This data structure supporting String keys, for fast
 * retrieval of values associated with string keys. In comparison to a Map, has
 * additional (fast) functions like list of keys with prefix and listing all
 * keys in sorted order.
 * 
 * @author Roman Kleimenov
 * @version %I%, %G%
 * @since 1.0
 */
public class RWayTrie implements Trie {
	private static int R = 26; // base, ASCII characters
	private static char FIRST_ALPHABET_ELEMENT = 'a';
	private Node root; // root of trie-node

	/**
	 * Static nested class to depict the idea of a Trie Node
	 */
	private static class Node {
		private Integer value;
		private Node[] next = new Node[R];
	}

	public void add(Tuple tuple) {
		root = add(root, tuple.getWord(), tuple.getWeight(), 0);
	}

	/**
	 * Method that add key-value to the trie. This is recursive method that
	 * build sequence of nodes that has linked between each other
	 * 
	 * @param node	node that contain pointer to next node and some value (may be null)
	 * @param key	word that need to add into trie
	 * @param val	value of current word
	 * @param d		d symbol of key
	 * @return		last added node
	 */
	private Node add(Node node, String key, Integer val, int d) {
		if (node == null) {
			node = new Node();
		}
		if (d == key.length()) { //
			node.value = val;
			return node;
		}
		int c =  key.charAt(d) - FIRST_ALPHABET_ELEMENT; // choosing subtree
		node.next[c] = add(node.next[c], key, val, d + 1);
		return node;
	}

	/**
	 * Method that retrieve value of word
	 * 
	 * @param key	key to retrieve the some value of word
	 * @return		value of word
	 */
	private Integer get(String key) {
		Node node = get(root, key, 0);
		if (node == null) {
			return null;
		}
		return node.value;
	}

	/**
	 * Method that retrieve value of word
	 * 
	 * @param node	node that contain pointer to next node and some value (may be null)
	 * @param key	word that need to add into trie
	 * @param d		d symbol of key
	 * @return		last added node
	 */
	private Node get(Node node, String key, int d) {
		if (node == null) {
			return null;
		}
		if (d == key.length()) {
			return node;
		}
		int c = key.charAt(d) - FIRST_ALPHABET_ELEMENT;
		return get(node.next[c], key, d + 1);
	}

	public boolean contains(String word) {
		if (get(word) != null) {
			return true;
		}
		return false;
	}

	public boolean delete(String word) {
		if (!contains(word)) {
			return false;
		} else {
			root = delete(root, word, 0);
			return true;
		}

	}

	/**
	 * Method that delete some word from RWayTrie
	 * 
	 * @param node	node that contain pointer to next node and some value (may be null)
	 * @param key	word that need to add into trie
	 * @param d		d symbol of key
	 * @return		last node
	 */
	private Node delete(Node node, String key, int d) {
		if (node == null) {
			return null;
		}
		if (d == key.length()) {
			node.value = null;
		} else {
			int c = key.charAt(d) - FIRST_ALPHABET_ELEMENT;
			node.next[c] = delete(node.next[c], key, d + 1);
		}
		if (null != node.value) {
			return node;
		}

		for (char c = 0; c < R; c++) {
			if (null != node.next[c]) {
				return node;
			}
		}
		return null;
	}

	public Iterable<String> words() {
		return wordsWithPrefix("");
	}

	public Iterable<String> wordsWithPrefix(String pref) {
		Queue<String> queue = new LinkedList<String>();
		Node node = get(root, pref, 0);
		collectBFS(node, pref, queue);
		return queue;
	}

	/**
	 * Search in depth
	 * 
	 * @param node		node of trie
	 * @param prefix	prefix of word
	 * @param queue		queue of words
	 */
	@SuppressWarnings("unused")
	private void collect(Node node, String prefix, Queue<String> queue) {
		if (node == null) {
			return;
		}
		if (node.value != null) {
			queue.add(prefix);
		}

		for (char c = 0; c < R; c++) {
			collect(node.next[c], prefix + c, queue);
		}
	}

	/**
	 * Breadth-first search
	 * 
	 * @param node		node of trie
	 * @param prefix	prefix of word
	 * @param queue		queue of words
	 */
	private void collectBFS(Node root, String pref, Queue<String> q) {
		if (root == null) {
			return;
		}
		Queue<Node> queueOfNodes = new LinkedList<Node>();
		queueOfNodes.add(root);

		Queue<String> queueOfPref = new LinkedList<String>();
		queueOfPref.add(pref);

		while (!queueOfNodes.isEmpty()) {
			Node node = queueOfNodes.remove();
			String prefFromQueue = queueOfPref.remove();

			if (node.value != null) {
				q.offer(prefFromQueue);
			}

			for (int c = 0; c < R; c++) {
				if (node.next[c] != null) {
					queueOfNodes.offer(node.next[c]);
					String newPref = new StringBuilder(prefFromQueue).append((char) (FIRST_ALPHABET_ELEMENT + c)).toString();
					queueOfPref.offer(newPref);
				}
			}
		}
	}

	public int size() {
		return size(root);
	}

	/**
	 * Get size of trie
	 * 
	 * @param node node of trie
	 * @return size of trie
	 */
	private int size(Node node) {
		int counter = 0;
		if (node == null) {
			return 0;
		}

		if (node.value != null) {
			counter++;
		}

		for (char c = 0; c < R; c++) {
			counter += size(node.next[c]);
		}

		return counter;
	}

}
