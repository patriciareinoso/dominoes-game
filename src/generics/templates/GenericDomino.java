package generics.templates;


/**
 * Extends {@link GenericPair} class.
 * Represent a generic domino piece. 
 * Domino x:y is equal to Domino y:x.
 * Domino is considered a double if x:x.<br>
 * 
 * @author Patricia REINOSO
 * @version 1.0.0
 * @since 2017-04-26
 */
public class GenericDomino<T> extends GenericPair<T> {
	
	
	/**
	 * Construct a Generic Pair of objects from a given first and second value.
	 * Use the superclass constructor.<br>
	 * 
	 * @param fst for the first value
	 * @param snd for the second value
	 */
	public GenericDomino(T fst, T snd){
		super(fst,snd);
	}

	/**
	 * Check if a domino is a double.
	 * A domino is a doble if the first and the second value are equal.<br>
	 * 
	 * @return True if the domino is a double. False otherwise. 
	 */
	public boolean isDouble(){
		return (getFirst().equals(getSecond()));
	}

	/**
	 * Check if 2 dominoes are equal. <br>
	 * 
	 * @param obj is compared to this
	 * @return false if the object is null or not a domino, and
	 *         true if the two values of both dominoes are the same even if 
	 *         they are swapped.
	 */
	@Override
	public boolean equals(Object obj){
		if (this == obj)
	           return true;
		 if (obj == null)
	           return false;
		 if (!this.getClass().equals(obj.getClass()))
	           return false;

		 GenericDomino other = (GenericDomino) obj;

		 return ((other.getFirst().equals(getFirst()) && other.getSecond().equals(getSecond())) ||
				 (other.getFirst().equals(getSecond()) && other.getSecond().equals(getFirst())));
			
	 }
	
	/**
	 * Check if one of the sides of the domino matches (is equal to) a given value.<br>
	 * 
	 * @param val value to compare with.
	 * @return True if there is a match. False otherwise. 
	 */
	public boolean matches(T val){
		return (getFirst().equals(val) || getSecond().equals(val));
	}
	
	/**
	 * Check if any of the sides of the domino matches (is equal to) any of
	 * the 2 given values.<br>
	 * 
	 * @param val1 first value to compare with.
	 * @param val2 second value to compare with.
	 * @return True if there is a match. False otherwise. 
	 */
	public boolean matches(T val1, T val2){
		return (getFirst().equals(val1) || getSecond().equals(val1) ||
				getFirst().equals(val2) || getSecond().equals(val2));
	}

	
}
