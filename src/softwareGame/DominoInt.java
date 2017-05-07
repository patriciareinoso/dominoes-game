package softwareGame;

/**
 * Represent each piece of the Domino Game. 
 * Extends {@link generics.templates.GenericDomino} in order to create domino chips
 * of a standard domino set.<br>
 * Implements the {@link graphicInterface.InterfaceDomino} in order to use the graphical interface.
 * The value of each side of the Domino piece is between MIN and MAX.<br>
 * Domino x:y is equal to Domino y:x <br>
 * 
 * @author	Patricia REINOSO
 * @version 2.1.0
 * @since	2017-03-08
 */

import graphicInterface.InterfaceDomino;
import generics.templates.*;
import tools.InvariantBrokenException;

public class DominoInt extends GenericDomino implements InterfaceDomino{

	/**
	 * Minimum value of a side of a domino on the standard domino set.
	 */
	public static final int MIN = 0;
	
	/**
	 * Maximum value of a side of a domino on the standard domino set .
	 */
	public static final int MAX = 6;
	
	/**
	 * The range of domino values.
	 */
	public static final int RANGE = MAX - MIN +1;
	
	/**
	 * The total number of dominoes.
	 */
	public static final int TOTAL = ((RANGE)*(RANGE+1))/2;
	
	/**
	 * The class invariant checks that the {@link MAX} value is bigger than the {@link #MIN} value.
	 */
	public static final boolean INVARIANT = (MAX > MIN);
	
	/**
	 * Class constructor.
	 * Construct a domino from a given left and right values.
	 * Check if the class invariant was not broken.<br>
	 *
	 * @param leftValue  the first value of the piece.
	 * @param rightValue the second value of the piece.
	 * @throws IllegalArgumentException when leftValue or rightValue are out range {@link #MIN} .. {@link #MAX}
	 * @throws InvariantBrokenException if the domino state is not valid after execution.
	 */
	public DominoInt(Integer leftValue, Integer rightValue) throws IllegalArgumentException, InvariantBrokenException{
		
		super(MIN,MIN);
		if (leftValue < MIN || leftValue > MAX || rightValue < MIN || rightValue > MAX){
			throw new IllegalArgumentException("Illegal arguments.");
		}
		setFirst(leftValue);
		setSecond(rightValue);
		if (!invariant()){
			throw new InvariantBrokenException("Invariant broken.");
		}
	}

	/**
	 * Retrieve the left value of the domino piece. 
	 * Implement the method {@link graphicInterface.InterfaceDomino#getLeftValue()}
	 * in order to use the graphical interface. 
	 * The left value corresponds to {@link generics.templates.GenericPair#first}.<br>
	 * 
	 * @return the left value of the piece.
	 */
	public int getLeftValue(){
		int val = (Integer) getFirst();
		return val;
	}

	/**
	 * Implement the method {@link graphicInterface.InterfaceDomino#getRightValue()}
	 * in order to use the graphical interface. 
	 * The left value corresponds to {@link generics.templates.GenericPair#second}.<br>
	 * 
	 * @return the right value of the piece.
	 */
	public int getRightValue(){
		int val = (Integer) getSecond();
		return val;
	}

	/**
	 * Check that the left and right values are in allowed range {@link #MIN} .. {@link #MAX}.<br>
	 * 
	 * @return True if the domino is in a valid state. False otherwise.
	 */
	public boolean invariant()  {
		
		if (getLeftValue() <= MAX && 
			getLeftValue() >= MIN &&
			getRightValue() <= MAX &&
			getRightValue() >= MIN){
			return true;
		}
		return false;
	}
	
	/**
	 * Set the left value of the Domino.
	 * Override {@link generics.templates.GenericPair#setFirst(Object)} method to handle exceptions.<br>
	 * 
	 * @param obj Object assign to the left value.
	 * @throws IllegalArgumentException if obj is null, is not an Integer or
	 * is out of the range {@link #MIN} .. {@link #MAX}.
	 */
	@Override
	public void setFirst(Object obj) throws IllegalArgumentException{
		if (obj == null || !(obj instanceof Integer)){
			throw new IllegalArgumentException("Illegal arguments.");
		}
		Integer val = (Integer)obj;
		if (val < MIN   || val > MAX){
			throw new IllegalArgumentException("Illegal arguments.");
		}
		this.first = val;
		
	}

	/**
	 * Set the right value of the Domino.
	 * Override {@link generics.templates.GenericPair#setSecond(Object)} method to handle exceptions.<br>
	 * 
	 * @param obj Object assign to the right value.
	 * @throws IllegalArgumentException if obj is null, is not an Integer or
	 * is out of the range {@link #MIN} .. {@link #MAX}.
	 */
	@Override
	public void setSecond(Object obj) throws IllegalArgumentException{
		if (obj == null || !(obj instanceof Integer)){
			throw new IllegalArgumentException("Illegal arguments.");
		}
		Integer val = (Integer)obj;
		if (val < MIN   || val > MAX){
			throw new IllegalArgumentException("Illegal arguments.");
		}
		this.second = val;
		
	}

}


