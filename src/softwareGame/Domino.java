package softwareGame;

/**
 * Represent each piece of the Domino Game. 
 * @author	Patricia REINOSO
 * @version 1.0
 * @since	2017-03-08
 */

import graphicInterface.InterfaceDomino;
import generics.templates.*;

public class Domino extends GenericDomino implements InterfaceDomino{

	/**
	 * Class constructor.
	 * @param leftValue  the first value of the piece.
	 * @param rightValue the second value of the piece.
	 */
	public Domino(Integer leftValue, Integer rightValue){
		super(leftValue,rightValue);
	}

	/**
	 * Retrieve the left value of the domino piece.
	 * @return the left value of the piece.
	 */
	public int getLeftValue(){
		int val = (Integer) getFirst();
		return val;
	}

	/**
	 * Retrieve the right value of the domino piece
	 * @return the right value of the piece.
	 */
	public int getRightValue(){
		int val = (Integer) getSecond();
		return val;
	}


	public static void main (String[] args){
		Domino dom1 = new Domino(5,5);
		Domino dom2 = new Domino(0,1);
		Domino dom3 = new Domino(1,0);
		Domino dom4 = new Domino(3,5);
		
		System.out.println(dom1);
		System.out.println(dom2.getLeftValue());
		System.out.println(dom2.getRightValue());
		
		System.out.println(dom2.equals(dom3));
		dom4.swap();
		System.out.println(dom4);
	}

}


