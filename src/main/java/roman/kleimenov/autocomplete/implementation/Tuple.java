package roman.kleimenov.autocomplete.implementation;

/**
 * Class Tuple used to save term and weight of term that used in RWayTrie class.
 * The Tuple objects encapsulates information that need to RWayTrie structures
 * and these objects are mutable and don't have some functionality that can
 * manage this objects. Tuple class is standard entity class that have only
 * fields and standard methods like hashCode, equals, toString.
 * 
 * @author Roman Kleimenov
 * @version     %I%, %G%
 * @since       1.0
 */
public class Tuple {

	/**
	 * Field that contain some word
	 */
	private String term;

	/**
	 * Weight of word
	 */
	private int weight;

	public Tuple() {
	}

	public Tuple(String word, int weight) {
		this.term = word;
		this.weight = weight;
	}

	public String getWord() {
		return term;
	}

	public void setWord(String word) {
		this.term = word;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + weight;
		result = prime * result + ((term == null) ? 0 : term.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tuple other = (Tuple) obj;
		if (weight != other.weight)
			return false;
		if (term == null) {
			if (other.term != null)
				return false;
		} else if (!term.equals(other.term))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tuple [word=" + term + ", weight=" + weight + "]";
	}

}
