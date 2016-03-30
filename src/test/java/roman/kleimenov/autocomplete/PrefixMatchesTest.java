package roman.kleimenov.autocomplete;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import roman.kleimenov.autocomplete.implementation.Tuple;

@RunWith(MockitoJUnitRunner.class)
public class PrefixMatchesTest {
	public static String WORDS = "firkin figure materials fire holiday" + " chat enough designed along among"
			+ " death writing speed html countries" + " loss face brand fire fireless";

	@Mock
	private Trie trie;

	@InjectMocks
	private PrefixMatches prefixMatches;
	
	@Test
	public void prefixMatchesShouldInvokeTrieAddMethod20Times() {
		prefixMatches.add(WORDS);
		verify(trie, times(20)).add(any(Tuple.class));
	}

	@Test
	public void prefixMatchesShouldInvokeTrieAddMethod5TimesInOrderToLengthOfWords() {
		prefixMatches.add("death mockito kleimenov fireless firkin a cb");
		verify(trie, times(5)).add(any(Tuple.class));
	}

	@Test
	public void prefixMatchesShoudInvokeTrieSizeMethod2Times() {
		prefixMatches.add(WORDS);
		verify(trie, times(2)).size();
	}

	@Test
	public void prefixMatchesShoudInvokeTrieContainsMethodOnce() {
		prefixMatches.contains("word");
		verify(trie, times(1)).contains(any(String.class));
	}

	@Test
	public void prefixMatchesShouldInvokeTrieDeleteMethodOnlyOnce() {
		prefixMatches.delete("materials");
		verify(trie, times(1)).delete("materials");
	}

	@Test
	public void prefixMatchesShouldInvokeTrieSizeMethodOnlyOnce() {
		prefixMatches.size();
		verify(trie, times(1)).size();
	}

	@Test(expected = IllegalArgumentException.class)
	public void wordsWithPrefixMethodShoudRiseException() {
		prefixMatches.wordsWithPrefix("a");
		prefixMatches.wordsWithPrefix("", 50);
	}
	
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
}
