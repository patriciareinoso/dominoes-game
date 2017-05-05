package softwareGame;

/**
 * Representation of the pieces on stock of the game.
 * @author	Patricia REINOSO
 * @version 2.0
 * @since	2017-03-08
 */

import java.util.ArrayList;
import java.util.Random;

public class Stock {

	private ArrayList<DominoInt> pieces;

   /**
	* Class constructor.
	* Create the 28 pieces of the game and add them the stock.
	*/
	private Stock(){
		
		pieces = new ArrayList<DominoInt>();
		
		for (int i = 0; i < 7 ; i++){

			for(int j = i ; j < 7; j++){
				pieces.add(new DominoInt(i,j));
			}
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

	/**
	 * Get a domino from the stock. The piece is selected.
	 * randomly and the removed from the stock.
	 * @return the piece drawed from the stock.
	 */
	public DominoInt draw(){
		Random randomGen =  new Random();
		int randomInt = randomGen.nextInt(getSize());
		DominoInt domino = pieces.get(randomInt);
		pieces.remove(randomInt);
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

}
