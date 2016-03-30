package roman.kleimenov.autocomplete.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class RWayTrieTest {
	
	@Test
	public void checkAddMethodInRWayTrieThatMustAdd3Words() {
		RWayTrie rWayTrie = new RWayTrie();
		rWayTrie.add(new Tuple("abc", 3));
		rWayTrie.add(new Tuple("abcd", 4));
		rWayTrie.add(new Tuple("new", 3));
		assertEquals(3, rWayTrie.size());
	}
	
	@Test
	public void checkThatTrieContainsWordInRWAyTrie() {
		RWayTrie rWayTrie = new RWayTrie();
		rWayTrie.add(new Tuple("abc", 3));
		rWayTrie.add(new Tuple("abcd", 4));
		assertTrue(rWayTrie.contains("abcd"));
	}
	
	@Test
	public void checkThatTrieDoesntContainsWordInRWayTrie() {
		RWayTrie rWayTrie = new RWayTrie();
		rWayTrie.add(new Tuple("abc", 3));
		rWayTrie.add(new Tuple("abcd", 4));
		assertFalse(rWayTrie.contains("nope"));
	}
	
	@Test
	public void checkSuccessfulDeleteWordFromTrie() {
		RWayTrie rWayTrie = new RWayTrie();
		rWayTrie.add(new Tuple("abc", 3));
		rWayTrie.add(new Tuple("abcd", 4));
		assertTrue(rWayTrie.delete("abc"));
		assertEquals(1, rWayTrie.size());
	}
	
	@Test
	public void checkUnseccessfulDeleteFromTrie() {
		RWayTrie rWayTrie = new RWayTrie();
		rWayTrie.add(new Tuple("abc", 3));
		rWayTrie.add(new Tuple("abcd", 4));
		assertFalse(rWayTrie.delete("someWord"));
		assertEquals(2, rWayTrie.size());
	}
	
	@Test
	public void addToTrie3WordsShouldGrowSizeTo3() {
		RWayTrie rWayTrie = new RWayTrie();
		rWayTrie.add(new Tuple("abc", 3));
		rWayTrie.add(new Tuple("abcd", 4));
		rWayTrie.add(new Tuple("new", 3));
		assertEquals(3, rWayTrie.size());
	}

	@Test
	public void returnSomeWordsWithPrefix() {
		String words = "abc abcd abce abcde abcdf";
		String resultWords = "abcd abcde abcdf";
		List<String> listToCompare = new ArrayList<String>();
		List<String> comparingList = new ArrayList<String>();
		for (String word : resultWords.split(" ")) {
			listToCompare.add(word);
		}
		
		RWayTrie rWayTrie = new RWayTrie();
		for(String word: words.split(" ")) {
			rWayTrie.add(new Tuple(word, word.length()));
		}
		
		for (String word : rWayTrie.wordsWithPrefix("abcd")) {
			comparingList.add(word);
		}
		assertTrue(listToCompare.containsAll(comparingList) && comparingList.containsAll(listToCompare));
	}
}
