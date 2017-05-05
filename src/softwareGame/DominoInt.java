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
	 * Minimun value of a side of a domino on the standard domino set 
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
		super(leftValue,rightValue);
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

	public static void main (String[] args){
		DominoInt dom1 = new DominoInt(5,5);
		DominoInt dom2 = new DominoInt(0,1);
		DominoInt dom3 = new DominoInt(1,0);
		DominoInt dom4 = new DominoInt(3,5);
		GenericDomino<Integer> dom7 = new GenericDomino<Integer>(0,1);
		
		System.out.println(dom1);
		System.out.println(dom2.getLeftValue());
		System.out.println(dom2.getRightValue());
		
		System.out.println(dom2.equals(dom3));
		dom4.swap();
		System.out.println(dom4);
		DominoInt dom5 = new DominoInt(5,1);
		DominoInt dom6 = new DominoInt(0,1);
		System.out.println(dom5);
		System.out.println(dom3.equals(dom7));
		
	}

}


