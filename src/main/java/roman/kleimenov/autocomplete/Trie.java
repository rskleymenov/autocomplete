package roman.kleimenov.autocomplete;

/**
 * Interface Trie contains declaration of methods that manage state of RWayTrie.
 * These methods helps to represent an in-memory dictionary that based on
 * RWayTrie structure and help to improve performance of dictionary;
 * 
 * @author Roman Kleimenov
 * @version %I%, %G%
 * @since 1.0
 */
public interface Trie {

	/**
	 * Method that adds key-value to the RWayTrie structure
	 * 
	 * @param tuple
	 *            word - weight value
	 */
	public void add(Tuple tuple);

	/**
	 * Method that checks word in the trie
	 * 
	 * @param word
	 *            word to check
	 * @return contains or not
	 */
	public boolean contains(String word);

	/**
	 * Method that delete some word from the trie
	 * 
	 * @param word
	 *            the word to delete
	 * @return success of operation
	 */
	public boolean delete(String word);

	/**
	 * Iterator through all words
	 * 
	 * @return list of words
	 */
	public Iterable<String> words();

	/**
	 * Iterator through all words
	 * 
	 * @param pref
	 *            beginning of word
	 * @return list of words
	 */
	public Iterable<String> wordsWithPrefix(String pref);

	/**
	 * Return size of trie
	 * 
	 * @return size of trie
	 */
	public int size();

}
