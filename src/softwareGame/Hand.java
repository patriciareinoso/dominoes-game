package softwareGame;

/**
* Class that represent a hand of the Domino Game.
* Handle the list of domino pieces that belong to a player.
* @author	Patricia REINOSO
* @version 1.1.1
* @since	2017-03-08
*/

import java.util.ArrayList;

public class Hand {
	
	
	private ArrayList<DominoInt> myHand; 

	/**
	 * Class constructor.
	 * A hand is initialized as and empty list of dominoes.
	 */
	public Hand(){
		myHand = new ArrayList<DominoInt>();
	}

	/**
	 * Add a domino piece to the hand.
	 * The domino pice to add must not be null.<br>
	 * 
	 * @param domino the domino piece to be added.
	 * @throws IllegalArgumentException if the domino is null or not valid.
	 */
	public void add(DominoInt domino) throws IllegalArgumentException{
		if (domino == null || !domino.invariant()){
			throw new IllegalArgumentException("Illegal argument. Domino is null or invalid.");
		}
		myHand.add(domino);
	}

	/**
	 * Remove a domino piece from the hand.
	 * If the domino piece is not in the hand no exception is raised.<br>
	 * 
	 * @param domino the domino piece to remove from the hand.
	 * @throws IllegalArgumentException if the domino is null or not valid.
	 * @throws IllegalStateException if the Hand is empty.
	 */
	public void delete(DominoInt domino) throws IllegalArgumentException, IllegalStateException{
		if (domino == null || !domino.invariant()){
			throw new IllegalArgumentException("Illegal argument. Domino is null or invalid.");
		}
		if (myHand.isEmpty()){
			throw new IllegalStateException("Illegal state. The Hand is empty.");
		}
		myHand.remove(domino);
	}

	/**
	 * @return the list corresponding to the hand.
	 */
	public ArrayList<DominoInt> getMyHand(){
		return myHand;
	}
	
	/**
	 * @return String representation of the hand.  It consists on the list of
	 * dominoes separated by ",".
	 */
	public String toString(){
		return myHand.toString();
	}

	/**
	 * @return the size of the hand.
	 */
	public int getSize(){
		return myHand.size();
	}

	/**
	 * @return true if the hand is empty, false otherwise.
	 */
	public boolean isEmpty(){
		return (myHand.isEmpty());
	}

	/**
	 * Set the hand from a given list of dominoes.<br>
	 * 
	 * @param myHand the hand to be set.
	 * @throws IllegalArgumentException if myHand is null.
	 */
	public void setMyHand(ArrayList<DominoInt> myHand) throws IllegalArgumentException {
		if (myHand == null){
			throw new IllegalArgumentException("Illegal argument. The hand is null.");
		}
		this.myHand = myHand;
	}

	/**
	 * Retrieve an appropriate domino piece to play.
	 * Method used by the computer. 
	 * Take into account the fact that if none of the players possess any double
	 * to do the first move, the first piece is not placed by the computer.
	 * If it is the first piece to put on the table, the domino searched is a 
	 * double and leftVal and rightVal are equal. 
	 * If not, leftVal and rightVal represent the ends of the table, and one of
	 * the values of the domino searched should match any of these 2 values.
	 * 
	 * @param  leftVal	first value searched on the pieces of the hand.
	 * @param  rightVal second value searched on the pieces of the hand.
	 * @param  first	true if it is the first piece to put on the table. 
	 *					False otherwise
	 * @return			an appropriate domino piece to play. If there is no 
	 *					appropriate piece to play, null.
	 * @throws IllegalArgumentException if first is true and leftVal and rightVal are different.
	 */
	public DominoInt thereIs(int leftVal, int rightVal, boolean first) throws IllegalStateException{

		if (first){
			if (leftVal!=rightVal){
				throw new IllegalArgumentException("Illegal argument.");
			}
			for(DominoInt d: myHand){
				if (d.isDouble() && d.matches(leftVal)){
					return d;
				}
			}
		}
		else{
			for(DominoInt d: myHand){
				if (d.matches(leftVal, rightVal)){
					return d;	
				}
			}
		}
		return null;
	}

	/**
	 * Prevents the player from Jumping when he/she can play.
	 * Indicate if on the hand exists an appropiate domino piece to play.
	 * If it is the first piece to put on the table, and leftVal and rightVal 
	 * are equal, it means that the domino piece has to be a double.
	 * If it is the first piece to put on the table, and leftVal and rightVal
	 * are different, it means that no player has a double piece to start,
	 * therefore, any piece can be played.
	 * If it is not the first piece to put on the table, leftVal and rightVal 
	 * represent the ends of the table, the player can play if there exist at
	 * least one piece whose values match leftVal or rightVal.
	 *
	 * @param  leftVal	first value searched on the pieces of the hand.
	 * @param  rightVal second value searched on the pieces of the hand.
	 * @param  first	true if it is the first piece to put on the table. 
	 *					False otherwise.
	 * @return			true if there exists an appropriate domino piece to 
	 *					play, false otherwise.
	 */

	public boolean canPlay(int leftVal, int rightVal, boolean first){
		if (first && (leftVal != rightVal)){
			return true;
		}
		else if (first && (leftVal == rightVal)){
			for(DominoInt d: myHand){
				if (d.isDouble() && d.matches(leftVal)){
					return true;	
				}
			}
		}
		else if (!first){
			for(DominoInt d: myHand){
				if (d.matches(leftVal, rightVal)){
					return true;	
				}
			}
		}
		return false;
	}
	
}
