package softwareGame;

/**
 * Class that represent a hand of the Domino Game.
 * Handle the list of domino pieces that belong to a player.
 * @author	Patricia REINOSO
 * @version 1.0
 * @since	2017-03-08
 */

import java.util.ArrayList;
import java.util.Iterator;

public class Hand {

	private ArrayList<Domino> myHand = new ArrayList<Domino>();

	/**
	 * Class constructor.
	 */
	public Hand(){}
	
	/**
	 * Add a domino piece to the hand.
	 * @param domino the domino piece to be added.
	 */
	public void add(Domino domino){
		myHand.add(domino);
	}

	/**
	 * Remove a domino piece from the hand.
	 * @param domino the domino piece to remove from the hand.
	 */
	public void delete(Domino domino){
		if (myHand.remove(domino)){
		}
		else{
			for(Iterator<Domino> iter = myHand.listIterator(); iter.hasNext(); ){
				Domino d = iter.next();
				if (d.equals(domino)){
					myHand.remove(d);
					break;
				}
			}
		}
	}

	/**
	 * Retrieve the hand of the player.
	 * @return the list corresponding to the hand.
	 */
	public ArrayList<Domino> getMyHand(){
		return myHand;
	}

	/**
	 * Retrieve the size of the hand.
	 * @return the size of the hand.
	 */
	public int getSize(){
		return myHand.size();
	}

	/**
	 * Indicate if the hand is empty or not.
	 * @return true if the hand is empty, false otherwise.
	 */
	public boolean isEmpty(){
		return (myHand.isEmpty());
	}

	/**
	 * Set the hand
	 * @param myHand the hand to be set
	 */
	public void setMyHand(ArrayList<Domino> myHand){
		this.myHand = myHand;
	}

	/**
	 * Retrieve an appropiate domino piece to play.
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
	 */
	public Domino thereIs(int leftVal, int rightVal, boolean first){

		for(Iterator<Domino> iter = myHand.listIterator(); iter.hasNext(); ){
			Domino d = iter.next();
			if (first && d.isDouble() && d.getLeftValue() == leftVal){
				return d;
			}
			if (!first &&   ((d.getLeftValue() == leftVal) || 
							(d.getLeftValue() == rightVal) ||
							(d.getRightValue() == leftVal) || 
							(d.getRightValue() == rightVal))){
				return d;			
			}
		}
		return null;
	}

	/**
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
		else if (!first){
			for(Iterator<Domino> iter = myHand.listIterator(); iter.hasNext(); ){
				Domino d = iter.next();
				if ((d.getLeftValue() == leftVal)  || 
					(d.getLeftValue() == rightVal) ||
					(d.getRightValue() == leftVal) || 
					(d.getRightValue() == rightVal)){
					return true;			
				}
			}
		}
		else if (first && (leftVal == rightVal)){
			for(Iterator<Domino> iter = myHand.listIterator(); iter.hasNext(); ){
				Domino d = iter.next();
				if (d.getLeftValue() == leftVal && d.getRightValue()==rightVal){
					return true;
				}
			}
		}
		return false;
	}

}
