package softwareGame;

/**
 * Represent the board of the standard domino Game.
 * The maximum number of domino pieces that it may contain must
 * be consistent with {@link DominoInt#TOTAL}.
 * Handle the list of domino pieces that have been played.
 * This class is implemented following the Singleton design pattern in order to guarantee the creation
 * of only one instance of the class.<br>
 * 
 * @author	Patricia REINOSO
 * @version 2.1.1
 * @since	2017-03-08
 */

import graphicInterface.BadMatchException;
import graphicInterface.InterfaceDomino;
import java.util.LinkedList;
import tools.InvariantBrokenException;

public class Table {

	private LinkedList<DominoInt> pieces; 
	
	/**
	 * Minimum value of the size of the table. Value reach when the table is empty. 
	 */
	public static final int MINSIZE = 0;
	
	/**
	 * Maximum value of the size of the hand. Value reach when all the
	 * domino pieces has been placed on the table.
	 * Must be consistent to the total amount of domino pieces {@link DominoInt#TOTAL} .
	 */
	public static final  int MAXSIZE = DominoInt.TOTAL;
	
	/**
	 * The class invariant checks that {@link #MAXSIZE} value is bigger than the 
	 * {@link #MINSIZE} and that the {@link #MAXSIZE} and {@link DominoInt#TOTAL} 
	 * are consistent.<br>
	 */
	public static final boolean INVARIANT = (MAXSIZE > MINSIZE) && (MAXSIZE == DominoInt.TOTAL);

	/**
	 * Class constructor.
	 * Create an empty board.
	 */
	private Table(){
		pieces = new LinkedList<DominoInt>();
	}
	
	/**
	 * Initializes the unique instance of the Table.
	 * */
	private static class TableHolder{
		private static final Table INSTANCE = new Table();
	}
	
	/**
	 * @return the unique instance of Table.
	 */
	public static Table getInstance(){
		return TableHolder.INSTANCE;
	}

	/**
	 * Add a domino piece on the left end of the table.
	 * The right side of the domino must match the left end of the board.<br>
	 * 
	 * @param  domino the domino piece to add.
	 * @throws BadMatchException if Domino does not match with table left side.
	 * @throws IllegalArgumentException if the domino is null or not valid.
	 * @throws InvariantBrokenException if the table state is not valid after execution.
	 */
	public void addLeft(DominoInt domino)  throws BadMatchException, IllegalArgumentException, InvariantBrokenException{
		if (domino == null || !domino.invariant()){
			throw new IllegalArgumentException("Illegal argument. Domino is null or invalid.");
		}
		if (isEmpty()){
			pieces.addLast(domino);
			return;
		}
		if (domino.getRightValue()!=(getEndValues().getLeftValue())){
			throw new BadMatchException();
		}
		pieces.addFirst(domino);
		if (!invariant()){
			throw new InvariantBrokenException("Invariant broken.");
		}
	}

	/**
	 * Add a domino piece on the right end of the table.
	 * The left side of the domino must match the right end of the board.<br>
	 * 
	 * @param  domino the domino piece to add.
	 * @throws BadMatchException if Domino does not match with table right side.
	 * @throws IllegalArgumentException if the domino is null or not valid.
	 * @throws InvariantBrokenException if the table state is not valid after execution.
	*/
	public void addRight(DominoInt domino)  throws BadMatchException, IllegalArgumentException, InvariantBrokenException{
		if (domino == null || !domino.invariant()){
			throw new IllegalArgumentException("Illegal argument. Domino is null or invalid.");
		}
		if (isEmpty()){
			pieces.addLast(domino);
			return;
		}
		if ((domino.getLeftValue()!=(getEndValues().getRightValue()))){
			throw new BadMatchException();
		}
		pieces.addLast(domino);
		if (!invariant()){
			throw new InvariantBrokenException("Invariant broken.");
		}
	}

	/**
	 * Add a domino piece to the table.
	 * It is possible to add a piece to the table if it at least one of the
	 * sides of the domino piece corresponds to the end values of the table.
	 * This method is consistent with the method {@link graphicInterface.GGame#putDominoOnTable(InterfaceDomino d)} .
	 * If the board is empty, the domino is added.
	 * If both sides of domino match the end values of the table. Priority is given
	 * to the left side of the domino.<br>
	 * 
	 * @param  domino the domino piece to add.
	 * @return        true if it was possible to add the piece. False otherwise.
	 * @throws IllegalArgumentException if the domino is null or not valid.
	 * @throws InvariantBrokenException if the table state is not valid after execution.
	 */
	public boolean add(DominoInt domino)  throws IllegalArgumentException, InvariantBrokenException{
		if (domino == null || !domino.invariant()){
			throw new IllegalArgumentException("Illegal argument. Domino is null or invalid.");
		}
		if (isEmpty()){
			pieces.add(domino);
			if (!invariant()){
				throw new InvariantBrokenException("Invariant broken.");
			}
			return true;
		}
		
		DominoInt end = getEndValues();
		int leftEnd, rightEnd;
		leftEnd = end.getLeftValue();
		rightEnd = end.getRightValue();

		if (domino.getLeftValue()==leftEnd){
			domino.swap();
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
			domino.swap();
			addRight(domino);
			return true;
		}
		return false;

	}

	/**
	 * Return the end values of the table in a Domino object.
	 * The left side of the Domino represents the left end of the table.
	 * The right side of the Domino represents the right end of the table.
	 * The left end of the table is the left side of the first domino on the table list.
	 * The right end of the table is the right side of the last domino on the table list.<br>
	 * 
	 * @return 	a domino piece with the end values of the table. Null if the
	 * 			table is empty.
	 */
	public DominoInt getEndValues(){
		if (isEmpty()){
			return null;
		}
		DominoInt domino;
		int first, last;
		first = pieces.getFirst().getLeftValue();
		last = pieces.getLast().getRightValue();
		domino = new DominoInt(first, last);
		return domino;
	}

	/**
	 * @return the size of the table.
	 */	
	public int getSize(){
		return pieces.size();
	}

	/**
	 * Indicate if the table is empty. A table is empty if the size is 0. <br>
	 * 
	 * @return true if the table is empty. False otherwise.
	 */	
	public boolean isEmpty(){
		return (pieces.isEmpty());
	}

	/**
	 *	@return the list of domino pieces on the table.
	 */	
	public LinkedList<DominoInt> getPieces(){
		return pieces;
	}
	
	/**
	 * @return String representation of the table. It consists on the list of
	 * dominoes separated by ",".
	 */
	public String toString(){
		return pieces.toString();
	}
	
	/**
	 * Check if the size of the table is between the limits {@link #MINSIZE} .. {@link #MAXSIZE}.
	 * Check if all the domino pieces on the table are valid. <br>
	 * 
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
