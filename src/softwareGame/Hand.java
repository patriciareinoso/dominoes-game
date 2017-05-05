package softwareGame;

/**
 * Class that represent a hand of the Domino Game.
 * Handle the list of domino pieces that belong to a player.
 * @author	Patricia REINOSO
 * @version 1.1.0
 * @since	2017-03-08
 */

import generics.templates.GenericDomino;
import generics.templates.GenericPair;

import java.util.ArrayList;
import java.util.Iterator;

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
	 * If the domino piece is not in the hand no exception is raised.
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
	 * Retrieve the hand of the player.
	 * @return the list corresponding to the hand.
	 */
	public ArrayList<DominoInt> getMyHand(){
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
	 * Set the hand from a given list of dominoes.
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
	 * Retrieve an appropiate domino piece to play.
	 * Method used by the computer. 
	 * Consider the fact that if none of the players posses any double
	 * to do the first move, the user player is the one who puts the first piece 
	 * on the table.
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
	 *@throws IllegalArgumentException if first is true and leftVal and rightVal are different.
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
	
	public static void main (String[] args){
		DominoInt dom1 = new DominoInt(5,5);
		DominoInt dom2 = new DominoInt(0,1);
		DominoInt dom3 = new DominoInt(2,0);
		DominoInt dom4 = new DominoInt(3,5);
		DominoInt dom5 = new DominoInt(2,0);
		DominoInt dom6 = new DominoInt(1,5);
		DominoInt dom7 = new DominoInt(4,4);
		
		Hand myHand = new Hand();
		Hand nullHand = null;
		Hand nullH = null;
		Hand emptyHand = new Hand();
		
		myHand.add(dom1);
		myHand.add(dom2);
		myHand.add(dom3);
		System.out.println(myHand.getMyHand());
		//myHand.add(null);
		//nullHand.add(dom1);
		
		myHand.delete(dom1);
		System.out.println(myHand.getMyHand());
		myHand.delete(dom2);
		System.out.println(myHand.getMyHand());
		myHand.add(dom2);
		System.out.println(myHand.getMyHand());
		myHand.delete(dom5);
		System.out.println(myHand.getMyHand());
		//myHand.add(dom2);
		//myHand.delete(null);
		myHand.delete(dom4);
		System.out.println(myHand.getMyHand());
		//myHand.delete(dom3);
		
		//System.out.println(nullHand.getMyHand());
		
		ArrayList<DominoInt> myHand2 = new ArrayList<DominoInt>();
		myHand.setMyHand(myHand2);
		System.out.println(myHand.getMyHand());
		//myHand.setMyHand(null);
		myHand.add(dom1);
		myHand.add(dom2);
		myHand.add(dom3);
		myHand.add(dom7);
		System.out.println(myHand.getMyHand());
		
		System.out.println(myHand.thereIs(1,1,true));
		System.out.println(myHand.thereIs(4,4,true));
		System.out.println(myHand.thereIs(4,1,false));
		System.out.println(myHand.thereIs(4,4,false));
		System.out.println(myHand.thereIs(6,6,false));
		//System.out.println(myHand.thereIs(4,1,true));
		System.out.println(myHand.canPlay(1,1,true));
		System.out.println(myHand.canPlay(4,4,true));
		System.out.println(myHand.canPlay(4,1,false));
		System.out.println(myHand.canPlay(4,4,false));
		System.out.println(myHand.canPlay(6,6,false));
		System.out.println(myHand.canPlay(4,1,true));
	}
	
	
}
