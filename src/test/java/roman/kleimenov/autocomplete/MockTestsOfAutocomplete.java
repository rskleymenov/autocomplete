package roman.kleimenov.autocomplete;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class MockTestsOfAutocomplete {
	public static String WORDS = "firkin figure materials fire holiday" 
							+ " chat enough designed along among"
							+ " death writing speed html countries" 
							+ " loss face brand fire fireless";

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
}
