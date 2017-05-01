package softwareGame;

/**
 * Class that represent the board of the Domino Game.
 * Handle the list of pieces that have been played
 * @author	Patricia REINOSO
 * @version 1.0
 * @since	2017-03-08
 */

import java.util.LinkedList;

public class Table {

	private LinkedList<Domino> pieces = new LinkedList<Domino>();

	/**
	 * Class constructor.
	 */
	public Table(){}

	/**
	 * Add a domino piece on the left end of the table.
	 * @param  domino the domino piece to add.
	 */
	public void addLeft(Domino domino){
		pieces.addFirst(domino);
	}

	/**
	 * Add a domino piece on the right end of the table.
	 * @param  domino the domino piece to add.
	*/
	public void addRight(Domino domino){
		pieces.addLast(domino);
	}

	/**
	 * Add a domino piece to the table.
	 * It is possible to add a piece to the table if it at least one of the
	 * values of the domino piece corresponds to the end values of the table.
	 * @param  domino the domino piece to add.
	 * @return        true if it was possible to add the piece. False otherwise.
	 */
	public boolean add(Domino domino){
		if (isEmpty()){
			pieces.add(domino);
			return true;
		}

		Domino end = getEndValues();
		int leftEnd, rightEnd;
		leftEnd = end.getLeftValue();
		rightEnd = end.getRightValue();

		if (domino.getLeftValue()==leftEnd){
			domino.switchSide();
			addLeft(domino);
			return true;
		}
		else if (domino.getLeftValue()==rightEnd){
			addRight(domino);
			return true;
		}
		else if (domino.getRightValue()==leftEnd){
			addLeft(domino);
			return true;
		}
		else if (domino.getRightValue()==rightEnd){
			domino.switchSide();
			addRight(domino);
			return true;
		}
		return false;

	}

	/**
	 * Get the end values of the table.
	 * The end values of the table are the left value of the first domino piece
	 * of the table and the right value of the last domino piece of the table.
	 * @return 	a domino piece with the end values of the table. Null if the
	 * 			table is empty.
	 */
	public Domino getEndValues(){
		if (isEmpty()){
			return null;
		}
		Domino domino;
		int first, last;
		first = pieces.getFirst().getLeftValue();
		last = pieces.getLast().getRightValue();
		domino = new Domino(first, last);
		return domino;
	}

	/**
	 * @return the size of the table.
	 */	
	public int getSize(){
		return pieces.size();
	}

	/**
	 * Indicate if the table is empty. A table is empty if the size is 0. 
	 *	@return true if the table is empty. False otherwise.
	 */	
	public boolean isEmpty(){
		return (pieces.isEmpty());
	}

	/**
	 * Retrieve the list of pieces on the table.
	 *	@return the list of pieces on the table.
	 */	
	public LinkedList<Domino> getPieces(){
		return pieces;
	}
}
