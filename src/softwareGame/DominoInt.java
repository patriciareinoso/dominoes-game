package softwareGame;

/**
 * Represent each piece of the Domino Game. 
 * Extends the GenericDomino class in order to create dominoes chips
 * with integer number from 0 to 6.
 * Implements the InterfaceDomino interface in order to use the graphical interface.
 * @author	Patricia REINOSO
 * @version 2.0
 * @since	2017-03-08
 */

import graphicInterface.InterfaceDomino;
import generics.templates.*;

public class DominoInt extends GenericDomino implements InterfaceDomino{

	/**
	 * Class constructor.
	 * Uses the superclass'constructor.
	 * @param leftValue  the first value of the piece.
	 * @param rightValue the second value of the piece.
	 */
	public DominoInt(Integer leftValue, Integer rightValue){
		super(leftValue,rightValue);
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


	public static void main (String[] args){
		DominoInt dom1 = new DominoInt(5,5);
		DominoInt dom2 = new DominoInt(0,1);
		DominoInt dom3 = new DominoInt(1,0);
		DominoInt dom4 = new DominoInt(3,5);
		
		System.out.println(dom1);
		System.out.println(dom2.getLeftValue());
		System.out.println(dom2.getRightValue());
		
		System.out.println(dom2.equals(dom3));
		dom4.swap();
		System.out.println(dom4);
	}

}


