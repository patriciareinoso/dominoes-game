package softwareGame;

/**
 * Represent each piece of the Domino Game. 
 * @author	Patricia REINOSO
 * @version 1.0
 * @since	2017-03-08
 */

import graphicInterface.InterfaceDomino;

public class Domino implements InterfaceDomino{

	private int leftValue;
	private int rightValue;

	/**
	 * Class constructor.
	 * @param leftValue  the first value of the piece.
	 * @param rightValue the second value of the piece.
	 */
	public Domino(int leftValue, int rightValue){
		setLeftValue(leftValue);
		setRightValue(rightValue);
	}

	/**
	 * Check if 2 domino pices are equal. Two domino pieces are considered 
	 * equal if they have the same values independently of the side.
	 * @param domino	the domino to which it is compared.
	 * @return 			ture if the dominos are equal, false otherwise.
	 */
	public boolean equals(Domino domino){

		if ((domino.getLeftValue() == getLeftValue()) && 
			(domino.getRightValue() == getRightValue())){
			return true;
		}
		domino.switchSide();
		if ((domino.getLeftValue() == getLeftValue()) && 
			(domino.getRightValue() == getRightValue())){
			domino.switchSide();
			return true;
		}
		domino.switchSide();
		return false;
	}

	/**
	 * Retrieve the left value of the domino piece.
	 * @return the left value of the piece.
	 */
	public int getLeftValue(){
		return leftValue;
	}

	/**
	 * Retrieve the right value of the domino piece
	 * @return the right value of the piece.
	 */
	public int getRightValue(){
		return rightValue;
	}

	/**
	 * Set the left of the domino piece
	 * @param leftValue the integer value to assign to the piece.
	 */
	public void setLeftValue(int leftValue){
		this.leftValue = leftValue;
	}

	/**
	 * Set the right of the domino piece
	 * @param rightValue the integer value to assign to the piece.
	 */
	public void setRightValue(int rightValue){
		this.rightValue = rightValue;
	}

	/**
	 * Swap the values on the right side and the left side of the domino piece.
	 */
	public void switchSide(){
		int aux;
		aux = getLeftValue();
		setLeftValue(getRightValue());
		setRightValue(aux);
	}

	/**
	 * Retrieve the string representation of the domino piece
	 * @return the string representation of the domino piece
	 */
	public String toString(){
		return "[ " + getLeftValue() + " : " + getRightValue() + " ]";
	}

	/**
	 * Check if the piece is a double. A piece is a double if 
	 * the left value is equal to the right side.
	 * @return true if it is double. False otherwise.
	 */
	public boolean isDouble(){
		return (getLeftValue() == getRightValue());
	}

}


