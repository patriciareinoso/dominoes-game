package softwareGame;

/**
 * Representation of the pieces on stock of a standard domino game.
 * @author	Patricia REINOSO
 * @version 2.1.0
 * @since	2017-03-08
 */

import java.util.ArrayList;
import java.util.Random;
import tools.InvariantBrokenException;

public class Stock {

	private ArrayList<DominoInt> pieces;
	
	/**
	 * Minimum value of a side of a domino to add on the stock.
	 * Must be consistent with the minimum value of a side on the
	 * class DominoInt. 
	 */
	public static final int MINDOM = DominoInt.MIN;
	
	/**
	 * Maximum value of a side of a domino to add on the stock.
	 * Must be consistent with the maximum value of a side on the
	 * class DominoInt. 
	 */
	public static final int MAXDOM = DominoInt.MAX;
	
	/**
	 * Minimum value of the size of the stock. When it is empty. 
	 */
	public static final int MINSIZE = 0;
	
	/**
	 * Maximum value of the size of the stock is when none of the
	 * domino pieces has been drawn to the players.
	 */
	public static final  int MAXSIZE = 28;
	
	public static final boolean INVARIANT = (MAXDOM > MINDOM) && (MAXSIZE > MINSIZE) && MAXSIZE == Game.TOTALPIECES;

   /**
	* Class constructor.
	* Create the 28 standard pieces of the game and add them the stock.
	* @throws InvariantBrokenException if the Stock state is not valid after execution.
	*/
	private Stock() throws InvariantBrokenException{
		
		pieces = new ArrayList<DominoInt>();
		
		for (int i = MINDOM; i < MAXDOM + 1 ; i++){

			for(int j = i ; j < MAXDOM + 1; j++){
				pieces.add(new DominoInt(i,j));
			}
		}
		if (!invariant()){
			throw new InvariantBrokenException("Invariant broken.");
		}
	}
	
	private static class StockHolder{
		private static final Stock INSTANCE = new Stock();
	}
	
	public static Stock getInstance(){
		return StockHolder.INSTANCE;
	}

	/**
	 * @return the list of domino pieces on the stock.
	 */
	public ArrayList<DominoInt> getPieces(){
		return pieces;
	}
	
	public String toString(){
		return pieces.toString();
	}

	/**
	 * Get a domino from the stock. The piece is selected
	 * randomly and the removed from the stock.
	 * @return the piece drawn from the stock.
	 * @throws IllegalStateException if the Stock is empty.
	 * @throws InvariantBrokenException if the Stock state is not valid after execution.
	 */
	public DominoInt draw() throws IllegalStateException, InvariantBrokenException{
		if (isEmpty()){
			throw new IllegalStateException("Illegal state. The Stock is empty.");
		}
		Random randomGen =  new Random();
		int randomInt = randomGen.nextInt(getSize());
		DominoInt domino = pieces.get(randomInt);
		pieces.remove(randomInt);
		if (!invariant()){
			throw new InvariantBrokenException("Invariant broken.");
		}
		return domino;
	}

	/**
	 * Retrieve the size of the stock.
	 * @return the integer size of the stock.
	 */
	public int getSize(){
		return pieces.size();
	}

	/**
	 * Check if the stock is empty. A stock is empty if its size is 0.
	 * @return true if the stock is empty, false otherwise.
	 */
	public boolean isEmpty(){
		return (pieces.isEmpty());
	}
	
	/**
	 * Check if the size of the stock is between the limits.
	 * Check if all the pieces on the Stock are valid. 
	 * @return True if the stock is in a valid state. False otherwise.
	 */
	public boolean invariant()  {
		
		if ((getSize() <= MAXSIZE) && (getSize() >= MINSIZE)){
			for(DominoInt d: pieces){
				if (!d.invariant()){
					return false;
				}
			}
			return true;			
		}
		return false;
	}

}
