package generics.abstractions;

/**
 * Abstract class that implements {@link PairSpecification}.
 * Represent a pair od objects.<br>
 * 
 * @author Patricia REINOSO
 * @version 1.0.0
 * @since 2017-04-26
 * */
public abstract class PairAbstraction implements PairSpecification{

	/**
	 * Getter function of the first value.<br>
	 * @return the first value.
	 */
	public abstract Object getFirst();
	
	
	/**
	 * Getter function of the first value.<br>
	 * @return the first value.
	 */
	public abstract Object getSecond();
	
	/**
	 * Setter function of the first value.<br>
	 * @param o object to set on the first value.
	 */
	public abstract void setFirst(Object o);
	
	/**
	 * Setter function of the second value.<br>
	 * @param o object to set on the second value.
	 */
	public abstract void setSecond(Object o);
	
	
	/**
	 * Swap the left and right values of the pair.
	 */
	public	void swap(){
		
		Object temp = getFirst();
		setFirst(getSecond());
		setSecond(temp);
	}
	
	/**
	 * String representation of the class.<br>
	 * 
	 * @return String representation of domino using " , " to separate the left 
	 * and right integer value characters
	 */
	public String toString (){
		
		return "("+getFirst()+", "+getSecond()+")";
		
	}
	
	/** 
	 * Check if 2 pairs are equal.<br>
	 * 
	 * @param obj is compared to.
	 * @return false if the object is null or not a pair, and 
	 *         true if the first values are equal and the second values are equal.
	 */ 
	public boolean equals(Object obj){
			
		 if (this == obj)
	           return true;
		 if (obj == null)
	           return false;
		 if (!(obj instanceof PairAbstraction))
	           return false;

		 PairAbstraction other = (PairAbstraction) obj;

		 return (other.getFirst().equals(getFirst()) && other.getSecond().equals(getSecond()));
			
	 }
}
