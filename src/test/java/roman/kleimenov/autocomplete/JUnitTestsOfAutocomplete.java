package roman.kleimenov.autocomplete;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class JUnitTestsOfAutocomplete {
	public static String WORDS = "firkin figure materials fire holiday" 
			+ " chat enough designed along among"
			+ " death writing speed html countries" 
			+ " loss face brand fire fireless";
	@Test
	public void prefixMatchesShouldAddOnly5WordsFrom10() {
		String tenWordsForTest = "loss face brand fire fireless loss face brand fire fireless";
		assertEquals(5, new PrefixMatches().add(tenWordsForTest));
	}
	
	@Test
	public void prefixMatchesShouldAddOnlyWordsThatLongerTwoSymbol() {
		String wordsToAddWithSmallLength = "a b c d e ab cd ef abc cde";
		assertEquals(2, new PrefixMatches().add(wordsToAddWithSmallLength));
	}

	@Test
	public void prefixMatchesShouldReturnTrueToInvokeContainsMethodWithWordInTrie() {
		PrefixMatches prefixM = new PrefixMatches();
		prefixM.add(WORDS);
		assertTrue(prefixM.contains("brand"));
	}
	
	@Test
	public void prefixMatchesShouldReturnFalseWhenInvokeContainsMethodWithWordsNotAddedInDictionary() {
		PrefixMatches prefixM = new PrefixMatches();
		prefixM.add(WORDS);
		assertFalse(prefixM.contains("noWordInDictionary"));
	}
	
	@Test
	public void prefixMatchesDeleteMethodShouldReturnTrueWhenDeleteWordFromDictionary() {
		PrefixMatches prefixM = new PrefixMatches();
		prefixM.add(WORDS);
		assertTrue(prefixM.delete("materials"));
	}

	@Test
	public void prefixMatchesShouldReturnFalseWhenTriesToDeleteWordThanNotInDictionary() {
		PrefixMatches prefixM = new PrefixMatches();
		prefixM.add(WORDS);
		assertFalse(prefixM.delete("wordNotInDictionary"));
	}
	
	@Test
	public void prefixMathcesShouldReturn19SizeOfDictionary() {
		PrefixMatches prefixM = new PrefixMatches();
		prefixM.add(WORDS);
		assertEquals(19, prefixM.size());
	}
	
	@Test
	public void wordsWithPrefixMustReturnCurrentList() {
		List<String> listToCompare = new ArrayList<String>();
		List<String> comparingList = new ArrayList<String>();
		for (String word : "abc abcd abce abcde abcdf".split(" ")) {
			listToCompare.add(word);
		}
		PrefixMatches prefixM = new PrefixMatches();
		prefixM.add("abc abcd abce abcde abcdf abcdfe");
		for (String word : prefixM.wordsWithPrefix("abc", 3)) {
			comparingList.add(word);
		}
		
		assertTrue(listToCompare.containsAll(comparingList) && comparingList.containsAll(listToCompare));
		comparingList = new ArrayList<String>();
		for (String word : prefixM.wordsWithPrefix("abc")) {
			comparingList.add(word);
		}
		assertTrue(listToCompare.containsAll(comparingList) && comparingList.containsAll(listToCompare));
	}
	

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
