package abstractions;

/**
 * A 2-tuple (pair) of <em> things </em> using an interface rather than a generic class.<br>
 * The genericity is simulated  by defining elements to be <code>Objects</code>
 * <br><br>
 * 
 * For teaching  advanced OO concepts in Java - genericity
 * 
 * @version 1
 * @author J Paul Gibson
 *
 */
public interface PairSpecification {
	
	/**
	 * Swap the first and second elements of the pair of values
	 */
	public	void swap();
	
	/**
	 * @return first element
	 */
	public Object getFirst();

	/**
	 * @return second element
	 */
	public Object getSecond();
	
	/**
	 * Setter method for the first object
	 * @param o is the new value for the first element
	 */
	public void setFirst(Object o);
	
	/**
	 * Setter method for the second object
	 * @param o is the new value for the first element
	 */
	public void setSecond(Object o);
	
	@Override
	public String toString ();
	
	@Override
	public boolean equals(Object obj);
	
}
