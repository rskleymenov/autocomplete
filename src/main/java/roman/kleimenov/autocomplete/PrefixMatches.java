package roman.kleimenov.autocomplete;

import java.util.Iterator;

import roman.kleimenov.autocomplete.exceptions.NoElementException;
import roman.kleimenov.autocomplete.implementation.RWayTrie;
import roman.kleimenov.autocomplete.implementation.Tuple;

/**
 * Class that contain in-memory dictionary and manipulate with it. Dictionary is
 * implemented as RWayTrie.
 * 
 * @author Roman Kleimenov
 * @version %I%, %G%
 * @since 1.0
 */
public class PrefixMatches {

	private Trie trie;

	public PrefixMatches() {
		trie = new RWayTrie();
	}

	public PrefixMatches(Trie trie) {
		this.trie = trie;
	}

	public Trie getTrie() {
		return trie;
	}

	public void setTrie(Trie trie) {
		this.trie = trie;
	}

	/**
	 * Create in-memory dictionary of words, gets as argument word, string,
	 * array of string/words. If string comes it will be split by space. There
	 * are only words that longer 2 symbols will be added to dictionary.
	 * Punctuation is not available.
	 * 
	 * @param strings
	 *            word, array of words, string, array of strings
	 * @return number of actually added words
	 */
	public int add(String... strings) {
		int initialValueOfWords = trie.size();
		for (String inputStrings : strings) {
			String[] words = inputStrings.split("\\s+");
			for (String word : words) {
				if (word.length() > 2) {
					trie.add(new Tuple(word, word.length()));
				}
			}
		}
		return trie.size() - initialValueOfWords;
	}

	/**
	 * If the word is available in the dictionary current method returns true,
	 * else - false;
	 * 
	 * @param word
	 *            word to search
	 * @return true - if trie contain this word, else - false
	 */
	public boolean contains(String word) {
		return trie.contains(word);
	}

	/**
	 * Delete word from trie-dictionary
	 * 
	 * @param word
	 *            word to delete
	 * @return successfulness of operation
	 */
	public boolean delete(String word) {
		return trie.delete(word);
	}

	/**
	 * Return the size of trie-dictionary
	 * 
	 * @return size
	 */
	public int size() {
		return trie.size();
	}

	/**
	 * Method that return k-different words length from the minimum pref only if
	 * pref is longer than one symbol
	 * 
	 * @param pref
	 *            pref to search
	 * @param k
	 *            k-different words
	 * @return list of words
	 */
	public Iterable<String> wordsWithPrefix(final String pref, final int k) {
		if (pref.length() < 2) {
			throw new IllegalArgumentException("pref must be larger than one symbol!");
		}
		return new Iterable<String>() {
			public Iterator<String> iterator() {
				return new PrefixMatchesIterator(trie.wordsWithPrefix(pref), k);
			}
		};
	}

	private class PrefixMatchesIterator implements Iterator<String> {
		private Iterator<String> iterator;
		private int kLengths;
		private String currentWord;
		private boolean getNextFlag;

		public PrefixMatchesIterator(Iterable<String> iterable, int k) {
			this.iterator = iterable.iterator();
			this.kLengths = k;
		}

		public boolean hasNext() {
			if (getNextFlag) {
				return true;
			} else {
				while (iterator.hasNext()) {
					currentWord = iterator.next();
					if (currentWord.length() <= kLengths + 2) {
						getNextFlag = true;
						return true;
					}
				}
			}
			return false;
		}

		public String next() {
			if (hasNext()) {
				getNextFlag = false;
			} else {
				getNextFlag = true;
				throw new NoElementException();
			}
			return currentWord;
		}

	}

	/**
	 * Method that return k-different words length from the minimum pref only if
	 * pref is longer than one symbol and k = 3
	 * 
	 * @param pref
	 *            pref to search
	 * @return list of words
	 */
	public Iterable<String> wordsWithPrefix(String pref) {
		return wordsWithPrefix(pref, 3);
	}
}
