package softwareGame;

/**
 * Represent each piece of the Domino Game. 
 * Extends the GenericDomino class in order to create dominoes chips
 * of a standard domino set.
 * Implements the InterfaceDomino interface in order to use the graphical interface.
 * The value of each side of the Domino piece is between 0 and 6.
 * Domino x:y is equal to Domino y:x
 * @author	Patricia REINOSO
 * @version 2.1.0
 * @since	2017-03-08
 */

import graphicInterface.InterfaceDomino;
import generics.templates.*;
import tools.InvariantBrokenException;

public class DominoInt extends GenericDomino implements InterfaceDomino{

	/**
	 * Minimum value of a side of a domino on the standard domino set 
	 */
	public static final int MIN = 0;
	
	/**
	 * Maximum value of a side of a domino on the standard domino set 
	 */
	public static final int MAX = 6;
	
	public static final boolean INVARIANT = (MAX > MIN);
	/**
	 * Class constructor.
	 * Verifies if the arguments are valid
	 * Constructs the DominoInt piece
	 * Check if the invariant was not broken
	 * @param leftValue  the first value of the piece.
	 * @param rightValue the second value of the piece.
	 * @throw IllegalArgumentException when leftValue or rightValue are out bounds.
	 * @throw InvariantBrokenException if the domino state is not valid after execution.
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
	 * Which corresponds to the first value of the GenericDomino class.
	 * @return the left value of the piece.
	 */
	public int getLeftValue(){
		int val = (Integer) getFirst();
		return val;
	}

	/**
	 * Retrieve the right value of the domino piece
	 * Which corresponds to the second value of the GenericDomino class.
	 * @return the right value of the piece.
	 */
	public int getRightValue(){
		int val = (Integer) getSecond();
		return val;
	}

	/**
	 * Check that the left and right values are in allowed range 
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
	 * Override the GenericPair method to handle exceptions.
	 * @param obj Object assign to the left value.
	 * @throws IllegalArgumentException if obj is null, is not an Integer or
	 * is out of bound.
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
	 * Set the left value of the Domino.
	 * Override the GenericPair method to handle exceptions.
	 * @param obj Object assign to the left value.
	 * @throws IllegalArgumentException if obj is null, is not an Integer or
	 * is out of bound.
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


