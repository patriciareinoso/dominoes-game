package softwareGame;

import tools.InvariantBrokenException;
import graphicInterface.GGame;
import graphicInterface.InterfaceGame;

/**
* Represent a 2-player traditional domino game.
* With a Stock of {@link Stock#MAXSIZE} domino pieces.
* At the beginning of the game {@link #INITIALPIECES} are given to each player.
* The first player that runs out of dominoes win.
* The player may continue drawing from the stock until it is empty (even if he/
* she possess an appropriate domino piece to play).
* The player may jump only when the stock is empty and does not possess an 
* appropriate domino piece to play. And when looking for the first double domino to 
* put on the table. <br>
* 
* @author Patricia REINOSO
* @since 2017-03-08
* @version 2.1.1
*
*/
public class Game implements InterfaceGame
{
	/**
	 * The graphical interface.
	 */
	GGame gGame;
	
	/**
	 * The stock of the game.
	 */
	 private Stock stock;
   
	 /**
	  * The board where dominoes are put.
	  */
	 private Table table;
   
   /**
    * Player 1. The user playing.
    */
	 private Player player1;
   
   /**
    * Computer.
    */
	 private Player pc;
   
   /**States:
    * 0-6 search if double domino
    * 7 player plays
    * 8 player plays with empty stock
    * 9 computer plays
    * 10 computer plays with drawing from stock
    * 11 computer blocked
    * 12 computer plays with empty stock
    * 13 player plays while computer is blocked
    */
	 
	 /**
	  * State of the game. Looking for the double 0 domino piece.
	  */
	 public static final int DOUBLE0 = 0;
	 
	 /**
	  * State of the game. Looking for the double 1 domino piece.
	  */
	 public static final int DOUBLE1 = 1;
	 
	 /**
	  * State of the game. Looking for the double 2 domino piece.
	  */
	 public static final int DOUBLE2 = 2;
	
	 /**
	  * State of the game. Looking for the double 3 domino piece.
	  */
	 public static final int DOUBLE3 = 3;
	 
	 /**
	  * State of the game. Looking for the double 4 domino piece.
	  */
	 public static final int DOUBLE4 = 4;
	 
	 /**
	  * State of the game. Looking for the double 5 domino piece.
	  */
	 public static final int DOUBLE5 = 5;
	 
	 /**
	  * Initial state of the game. Looking for the double 6 domino piece.
	  */
	 public static final int DOUBLE6 = 6;
	 
	 /**
	  * State of the game. It is the player's turn. The player may play or draw.
	  */
	 public static final int PLAYER_PLAYS = 7;
	 
	 /**
	  * State of the game. It is the player's turn. Indicates that the
	  * player can not draw from the stock, and jump only if the does not 
	  * posses an appropriate domino piece to play.
	  */
	 public static final int PLAYER_PLAYS_EMPTY_STOCK = 8;
	 
	 /**
	  * State of the game. It is the PC's turn. It may play or draw.
	  */
	 public static final int PC_PLAYS = 9;
	 
	 /**
	  * State of the game. It is the PC's turn. It does not possess an appropriate
	  * domino piece to play, so it needs to draw from the stock.
	  */
	 public static final int PC_PLAYS_DRAWS = 10;
	 
	 /**
	  * State of the game. It is the PC's turn. The stock is empty. It does
	  * not possess an appropriate domino piece to play.
	  */
	 public static final int PC_BLOCKED = 11;
	 
	 /**
	  * State of the game. It is the PC's turn. The pc tries to find an 
	  * appropriate domino piece to play.
	  */
	 public static final int PC_PLAYS_EMPTY_STOCK = 12;
	 
	 /**
	  * State of the game. It is the Players's turn. If the player can not play,
	  * the game is Block.
	  */
	 public static final int PLAYER_PLAYS_PC_BLOCKED = 13;
	 
	 
	 /**
	  * Represent the different states of the game. May take values in the range
	  * {@link #MINSTATE} .. {@link Game#MAXSTATE}.
	  */
	 int indState = DOUBLE6;
	 
	 /**
	  * Amount of domino pieces that are drawn to each player at the beginning 
	  * of the game.
	  * */
	 public static final int INITIALPIECES = 7;
	 
	 /**
	  * Amount of domino pieces on the domino game. 
	  * Must be consistent to the amount of different domino pieces that can be
	  * created ({@link DominoInt#TOTAL}.
	  * */
	 public static final int TOTALPIECES = DominoInt.TOTAL;
	 
	 /**
	  * Minimum value taken by {@link #indState}.
	  * */
	 public static final int MINSTATE = 0;
	 
	 /**
	  * Maximum value taken by {@link #indState}.
	  * May change if a new state is created.
	  * */
	 public static final int MAXSTATE = 13;
	 
	 /**	
	  * The class invariant checks that the {@link #MAXSTATE} value is bigger than 
	  * the {@link #MINSTATE} value, {@link #INITIALPIECES} value is appropiate to  
	  * {@link #TOTALPIECES}, and that the {@link #TOTALPIECES}, {@link Table#MAXSIZE}, 
	  * {@link Stock#MAXSIZE} and {@link DominoInt#TOTAL} are consistent.
	  */
	 public static final boolean INVARIANT = MAXSTATE > MINSTATE && 
			 							    INITIALPIECES*2 <= TOTALPIECES && 
			 							    TOTALPIECES == DominoInt.TOTAL && 
			 							    TOTALPIECES == Table.MAXSIZE && 
			 							    TOTALPIECES == Stock.MAXSIZE;
	 
	 /**
      * Constructor for a 2 players game.
      * Create a graphical interface and ask the user to introduce a name.
      */
	 public Game(){
		this.gGame = new GGame(this);
		gGame.setVisible(true);
		gGame.setMessage("Hello. Enter your name: ");
	 }
   
	 /**
	  * Set the state of the game.<br>
	  * 
	  * @param val The state of the game.
	  * @throws IllegalArgumentException if val is out of bounds {@link #MINSTATE} .. {@link #MAXSTATE}.
	  * @throws InvariantBrokenException if the Game state is not valid after execution.
	  */
	 public void setIndState(int val) throws IllegalArgumentException, InvariantBrokenException{
		 if (val < MINSTATE || val > MAXSTATE){
			 throw new IllegalArgumentException("Illegal argument.");
		 } 
		 this.indState = val;
		 if (!invariant()){
			 throw new InvariantBrokenException("Invariant broken.");
		 }
	 }
   
    /**
    * Method called when an event is produced in the graphical interface.<br>
    * If val corresponds to {@link graphicInterface.GGame#DATA_NAME}, the player
    * has inserted his/her name. The game is initialized.<br>
    * If val corresponds to {@link graphicInterface.GGame#PLAY}, the player has
    * clicked on a domino pieces. The event is treated depending on {@link #indState}.<br>
    * If val corresponds to {@link graphicInterface.GGame#JUMP}, the player has
    * clicked on the Jump button. Check if the event is valid according to {@link #indState}.<br>
    * If val corresponds to {@link graphicInterface.GGame#DRAW}, the player has
    * clicked on the Draw button. <br>
    * if val corresponds to {@link graphicInterface.GGame#VALIDPCPLAY}, the player
    * has clicked on the Play PC button. Call methods to execute PC actions.<br>
    * Other values are considered invalid.<br>
    * 
    * @param val indicate the action done by the user player.
    * @throws IllegalStateException if val does not correspond to the events 
    * 		  in {@link graphicInterface.GGame} 
    * @throws InvariantBrokenException if the Game state is not valid after execution.
    */
	 public void receivedMessage(int val) throws IllegalStateException, InvariantBrokenException {
	   
		 System.out.println( "\ntype received message  "+ val +" for state "+indState);
		 switch (val) {
        
		 case GGame.DATA_NAME: // Indicate the player has entered his name
			 String name = gGame.getPlayerName(); 
			 System.out.println( "\nName:  "+ name);
             initialize(name);
	   		 break;
		 case GGame.PLAY: // The player has clicked on a domino

			 DominoInt domino = new DominoInt(gGame.getDomino().getLeftValue(),gGame.getDomino().getRightValue());
			 System.out.println("domino played : " + domino);
			 switch (indState){
			 	case DOUBLE6: case DOUBLE5: case DOUBLE4: case DOUBLE3: case DOUBLE2: case DOUBLE1: case DOUBLE0:
			 		// The player clicks on correct double
                    if (domino.getLeftValue() == indState &&
                    	domino.getRightValue() == indState){
                        setIndState(PLAYER_PLAYS);
                        treatAnswer(domino);
                    }
                    // The player clicks on a wrong domino
                	else {
                		gGame.setMessage("This choice is not good. Please click on double "+ indState + " or jump.");
                	}
                    break;
			 	case PLAYER_PLAYS: case PLAYER_PLAYS_EMPTY_STOCK: case PLAYER_PLAYS_PC_BLOCKED:
			 		treatDoubleAnswer(domino);
			 		break;
			 	default: 
			 		throw new IllegalStateException("Illegal state.");
			 }
			 break;

		 case GGame.JUMP: // The player has clicked on the jump button	
			 switch (indState){
			 	case PLAYER_PLAYS_PC_BLOCKED: case PLAYER_PLAYS_EMPTY_STOCK: case DOUBLE6: case DOUBLE5: case DOUBLE4: case DOUBLE3: case DOUBLE2: case DOUBLE1: case DOUBLE0:
			 		treatJumpAnswer();
			 		break;
			 	default: 
			 		throw new IllegalStateException("Illegal state.");
                }
                break;
               
		 case GGame.DRAW: // The player has clicked on the draw button
			 playerDraw();
			 break;
		 case GGame.VALIDPCPLAY: // The player has clicked on the play PC button 
			 computerPlay();
			 break;	
		 default:
			 throw new IllegalStateException("Illegal state.");
		 }
		 if (!invariant()){
			 throw new InvariantBrokenException("Invariant broken.");
		 } 
	 }

   /**
    * Set the initial conditions of the game.
    * Create a stock, a board, two players (player and computer), initialize the 
    * graphical interface : hand, button and send it the first message.<br>
    * 
    * @param name The name of the player
    * @throws IllegalArgumentException if name is null.
    * @throws InvariantBrokenException if the Game state is not valid after execution.
    */
	 public void initialize(String name) throws IllegalArgumentException, InvariantBrokenException{
		 if (name == null){
			 throw new IllegalArgumentException("Illegal argument.");
		 }
		 this.stock = Stock.getInstance();
         this.table = Table.getInstance();
         this.player1 = new Player(name);
         this.pc = new Player("PC");

         System.out.println( "\nStock:  "+ stock);
         System.out.println( "\ntable:  "+ table);
         System.out.println( "\nplayer:  "+ player1.getName());
         System.out.println( "\npc:  "+ pc.getName());

         DominoInt domino;
         for(int i = 0; i < INITIALPIECES ; i++){
        	 domino = stock.draw();
        	 player1.getHand().add(domino);
			 gGame.addDominoInHand(domino);
             domino = stock.draw();
             pc.getHand().add(domino);
         }
         System.out.println( "\nplayer hand :  "+ player1.getHand());
         System.out.println( "\npc hand:  "+ pc.getHand());
         gGame.setMessage("Hello " + name + " good luck.  Please click on double 6 or jump."); 
         gGame.setEnabledJump(true);
         if (!invariant()){
        	 throw new InvariantBrokenException("Invariant broken.");
         } 
	 }
   
	 /**
      * Retrieve a end value of the table object. Use {@link Table#getEndValues()}.
      * side is equal to 1 corresponds to the left side of the table.
      * side is equal to 2 corresponds to the right side of the table <br>
      * 
      * @param side The side to be considered : 1 or 2.
      * @return 	  The extremity value of the domino on the considered side 
	  * 			  the table. -1 if the table is empty.
	  * @throws IllegalArgumentException if side is not either 1 nor 2.
      */
	 public int getEnd(int side) throws IllegalArgumentException {
		 if (side != 1 && side != 2){
			 throw new IllegalArgumentException("Illegal argument. Side must be 1 or 2.");
		 }
		 if (table.isEmpty()){
			 return -1;
		 }
		 if (side == 1){
			 return table.getEndValues().getLeftValue();
		 }
		 else if (side == 2){
			 return table.getEndValues().getRightValue();
		 }
		 return -1;
	 }
   
	 /**
	  * Take the selected domino, verify if can be put on the table.
      * If it is ok, call {@link #treatAnswer(DominoInt)}. Otherwise send a message
      * to the player.<br>
      * 
      * @param d The selected domino.
      * @throws IllegalArgumentException if the domino is null or not valid.
      */
	 public void treatDoubleAnswer(DominoInt d) throws IllegalArgumentException {
		 if (d == null || !d.invariant() ){
			 throw new IllegalArgumentException("Illegal argument. Domino is null or invalid.");
		 }
		 if (table.isEmpty() || d.matches(getEnd(1),getEnd(2))){
			 treatAnswer(d);
		 }
		 else{
			 gGame.setMessage("This choice is not good.");
		 }
	 }

	 /**
      * When a player plays, d is removed from the hand of the player and put on
      * the table. 
	  * Check if the player wins. Otherwise, call {@link #computerDecide()}. <br>
	  * 
      * @param d The domino selected by the player.
      * @throws IllegalArgumentException if the domino is null or not valid.
      * @throws InvariantBrokenException if the Game state is not valid after execution.
      */
	 public void treatAnswer(DominoInt d) throws IllegalArgumentException, InvariantBrokenException{
		 if (d == null || !d.invariant()){
			 throw new IllegalArgumentException("Illegal argument. Domino is null or invalid.");
		 }
         setButtons(-1);	
         gGame.removeDominoFromHand(d);
         gGame.putDominoOnTable(d);
         player1.getHand().delete(d);
         table.add(d);
         System.out.println("\ntable :  " + table);
         System.out.println("\ntable ends :  " + getEnd(1) + " - " + getEnd(2));
         System.out.println("\nplayer's hand :  " + player1.getHand());
        
         if (!invariant()){
        	 throw new InvariantBrokenException("Invariant broken.");
         } 
        
         if (player1.isWin()){
        	 gGame.setMessage("CONGRATULATIONS. You WIN.");
         }
         else{
        	 computerDecide();
         }
	 }
 
	 /**
	  * Method called when the player clicks on the Draw button.
	  * A domino piece from the stock is given to the player even if he/she
	  * possess an appropriate domino piece to play.
	  * If the stock is empty, the computer plays.
	  * otherwise the drawn domino is added to the hand and wait for the 
	  * next action of the player.
	  * The player may continue drawing from the stock until it is empty.<br>
      * 
	  * @throws IllegalStateException if state is not {@link #PLAYER_PLAYS} or
	  * 	    {@link #PLAYER_PLAYS_EMPTY_STOCK}
	  * @throws InvariantBrokenException if the Game state is not valid after execution.
	  */
	 public void playerDraw() throws IllegalStateException, InvariantBrokenException{ 
	
		 switch(indState){
		 	case PLAYER_PLAYS:
		 		if (stock.isEmpty()){
		 			setIndState(PLAYER_PLAYS_EMPTY_STOCK);
                    setButtons(PLAYER_PLAYS_EMPTY_STOCK);
                    gGame.setMessage("The stock is over. Please choose a domino or jump.");
                }
                else {
                    DominoInt d = stock.draw();
                    player1.getHand().add(d);
                    gGame.addDominoInHand(d);
                    gGame.setMessage("You draw " + d.getLeftValue() + " : " + d.getRightValue() + " . Please choose a domino or draw again.");
                    if (stock.isEmpty()){
                        setIndState(PLAYER_PLAYS_EMPTY_STOCK);
                    }
                }
                break;
            case PLAYER_PLAYS_EMPTY_STOCK:
                setButtons(PLAYER_PLAYS_EMPTY_STOCK);
                gGame.setMessage("The stock is over. Please choose a domino or jump.");
                break;
            default: 
            	throw new IllegalStateException("Illegal state.");
        }
        if (!invariant()){
			throw new InvariantBrokenException("Invariant broken.");
		} 
	}
	 
	 /**
	  * The computer plays according to the state of the game.
	  * If {@link #indState} is equal to {@link #DOUBLE1} , 
	  *    {@link #indState} is equal to {@link #DOUBLE2} , 
	  *    {@link #indState} is equal to {@link #DOUBLE3} , 
	  *    {@link #indState} is equal to {@link #DOUBLE4} , 
	  *    {@link #indState} is equal to {@link #DOUBLE5} , 
	  *    {@link #indState} is equal to {@link #DOUBLE6} we look for a double n on the PC's hand.
	  *    If the double is found, it is played. Otherwise the PC jump and it is
	  *    time to find the next double.<br>
	  *   
	  * If {@link #indState} == {@link #DOUBLE0} we look for double 0 on the PC's hand.
	  *    If found, it is played. Otherwise, the PC jump and its the player's turn.<br>
	  * 
	  * If {@link #indState} == {@link #PC_PLAYS}, the PC has a domino to play.
	  *    If in fact it can not play, an IllegalStateException is raised.
	  *    If the PC runs out of dominoes, indicates the winning state.<br>
	  * 
	  * If {@link #indState} == {@link #PC_PLAYS_DRAWS}, the PC can not play. The PC draws.
	  *    and tries to play again.<br>
	  * 
	  * If {@link #indState} == {@link #PC_BLOCKED}, the PC is blocked. It is the
	  *    player's turn.<br>
	  * 
	  * If {@link #indState} == {@link #PC_PLAYS_EMPTY_STOCK}, the PC can play a domino.
	  *    If in fact it can not play, an IllegalStateException is raised.
	  *    If the PC runs out of dominoes, indicates the winning state.<br>
	  * 
	  * Other values for {@link #indState} are considered illegal states.
	  * Set the buttons according to the next expected event.
	  * <br>
	  * 
      * @throws IllegalStateException if state is different from {@link #DOUBLE0},
      * {@link #DOUBLE1},{@link #DOUBLE2},{@link #DOUBLE3},{@link #DOUBLE4},
      * {@link #DOUBLE5},{@link #DOUBLE6},{@link #PC_PLAYS},{@link #PC_PLAYS_DRAWS},
      * {@link #PC_BLOCKED}, {@link #PC_PLAYS_EMPTY_STOCK}.
      * @throws InvariantBrokenException if the Game state is not valid after execution.
	  */
	 public void computerPlay() throws IllegalStateException, InvariantBrokenException {
   		
		 System.out.println("\nstate:"+indState+ ". Computer plays");
         DominoInt d = null;

         switch (indState){
            // Searching for the first double
         	case DOUBLE1: case DOUBLE2: case DOUBLE3: case DOUBLE4: case DOUBLE5: case DOUBLE6:
         		d = pc.getHand().thereIs(indState,indState, true);
                // The PC has the double searched.
         		if (d != null){
         			pc.getHand().delete(d); 
                    gGame.putDominoOnTable(d);
                    table.add(d);
                    
                    gGame.setMessage("The computer has played " + d.getLeftValue() + " : " + d.getRightValue() + ". Please choose a domino or draw." );
                    setButtons(PLAYER_PLAYS);
                    setIndState(PLAYER_PLAYS);
                }
                // The PC does not have the double searched.
                else{
                	/* Takes advantage of the fact that the constants corresponding to the
                	 * states of the game are consecutive */
                    setButtons(indState -1);
                    setIndState(indState - 1);
                    gGame.setMessage("Please click on double " + indState  + " or jump.");
                }
                break;
            // Searching for double 0
            case DOUBLE0:
                d = pc.getHand().thereIs(indState,indState, true);
                // The PC has the double 0.
                if (d != null){
                    pc.getHand().delete(d); 
                    gGame.putDominoOnTable(d);
                    table.add(d);
                    
                    gGame.setMessage("The computer has played " + d.getLeftValue() + " : " + d.getRightValue() + ". Please choose a domino or draw." );
                }
                // The PC does not have the double 0.
                else{
                    gGame.setMessage("Please click on a domino.");
                }
                setButtons(PLAYER_PLAYS);
                setIndState(PLAYER_PLAYS);
                break;
            case PC_PLAYS:
                // The PC has a domino piece to play.
                d = pc.getHand().thereIs(getEnd(1),getEnd(2),false);
                if (d == null){
                	throw new IllegalStateException("Illegal state");
                }
                pc.getHand().delete(d); 
                gGame.putDominoOnTable(d);
                table.add(d);
                
                // The PC just put its last domino piece on the board.
				if (pc.isWin()){
					gGame.setMessage("The computer has played " + d.getLeftValue() + " : " + d.getRightValue() + ". The PC WIN." );
		            setButtons(-1);
				}
				else {
		            gGame.setMessage("The computer has played " + d.getLeftValue() + " : " + d.getRightValue() + ". Please choose a domino or draw." );
		            setButtons(PLAYER_PLAYS);
		            setIndState(PLAYER_PLAYS);
				}
                break;
            case PC_PLAYS_DRAWS:
                // The PC draws
                gGame.setMessage("The PC draws.");
                DominoInt draw = stock.draw();
                pc.getHand().add(draw);
                setButtons(PC_PLAYS_DRAWS);
                computerDecide(); 
                break;
            case PC_BLOCKED:
                // The PC is blocked
                gGame.setMessage("The PC is blocked. Please click on a domino or jump.");
                setButtons(PLAYER_PLAYS_PC_BLOCKED);
                setIndState(PLAYER_PLAYS_PC_BLOCKED);
                break;
            case PC_PLAYS_EMPTY_STOCK:
                // The PC can play. The Stock is empty
                d = pc.getHand().thereIs(getEnd(1),getEnd(2),false);
                if (d == null){
                	throw new IllegalStateException("Illegal state");
                }
                gGame.putDominoOnTable(d);
                pc.getHand().delete(d); 
                table.add(d);
                
                // The PC just put its last domino piece on the board.
				if (pc.isWin()){
					gGame.setMessage("The computer has played " + d.getLeftValue() + " : " + d.getRightValue() + ". The PC WIN." );
		            setButtons(-1);
				}
				else {
		            gGame.setMessage("The computer has played " + d.getLeftValue() + " : " + d.getRightValue() + ". Please choose a domino or jump." );
                    setButtons(PLAYER_PLAYS_EMPTY_STOCK);
		            setIndState(PLAYER_PLAYS_EMPTY_STOCK);                
				}
                break;
            default: 
            	throw new IllegalStateException("Illegal state.");
        }

         System.out.println("\ntable :  " + table);
         System.out.println("\ntable ends :  " + getEnd(1) + " - " + getEnd(2));
         System.out.println("\nPC hand :" + pc.getHand());
         if (!invariant()){
        	 throw new InvariantBrokenException("Invariant broken.");
         } 
   }

	/**
	 * Verify that the player does not cheat when clicking on the Jump button.
	 * A player can Jump only when he/she does not possess the double searched,
	 * Or when he/she does not possess a piece that matches any of the ends of 
	 * the table and the stock is empty.<br>
	 * 
	 * @throws IllegalStateException if state is different from {@link #DOUBLE0},
     * {@link #DOUBLE1},{@link #DOUBLE2},{@link #DOUBLE3},{@link #DOUBLE4},
     * {@link #DOUBLE5},{@link #DOUBLE6},{@link #PLAYER_PLAYS},
     * {@link #PLAYER_PLAYS_EMPTY_STOCK},{@link #PLAYER_PLAYS_PC_BLOCKED}.
	 */
   public void treatJumpAnswer() throws IllegalStateException{
	   
        System.out.println("State: "+indState + ". Into jump player's process"); 
        switch (indState){
            // Searching for the first double
            case DOUBLE0: case DOUBLE1: case DOUBLE2: case DOUBLE3: case DOUBLE4: case DOUBLE5: case DOUBLE6:
				
				if (player1.getHand().canPlay(indState,indState, true)){
					gGame.setMessage("This choice is not good. Please click on double " + indState + ".");
				}
                else{
                    System.out.println("The player can not play.");
                    setButtons(PC_PLAYS_DRAWS);
                    computerDecide();
                }
                break;
            // The player's turn. The stock is empty
            case PLAYER_PLAYS_EMPTY_STOCK:
                if (player1.getHand().canPlay(getEnd(1),getEnd(2), false)){
                    gGame.setMessage("This choice is not good. Please click a domino.");
                    gGame.setEnabledJump(false);
                }
                else{
                    System.out.println("The player can not play.");
                    setButtons(PC_PLAYS_DRAWS);
                    computerDecide();
                }
                break;
            // The player's turn. Computer block.
            case PLAYER_PLAYS_PC_BLOCKED:
                if (player1.getHand().canPlay(getEnd(1),getEnd(2), false)){
                    gGame.setMessage("This choice is not good. Please click a domino.");
                    gGame.setEnabledJump(false);
                }
                else{
                    System.out.println("The game is blocked. END OF THE GAME.");
                    gGame.setMessage("The game is blocked. END OF THE GAME.");
                    setButtons(-1);
                }
                break;
            default:
            	throw new IllegalStateException("Illegal state.");

        }
    }

	/**
	 * Prepare the game for the PC. Verify if it is possible for the PC to 
	 * put a domino piece on the table and change the {@link #indState} properly.
	 * Send the appropriate message to the player and ask to click on the 
	 * Ask the user to click on Play PC button to execute the action.<br>
	 * 
	 * Only states {@link #DOUBLE0}, {@link #DOUBLE1},{@link #DOUBLE2},
	 * {@link #DOUBLE3},{@link #DOUBLE4}, {@link #DOUBLE5},{@link #DOUBLE6}, 
	 * {@link #PLAYER_PLAYS}, {@link #PC_PLAYS_DRAWS}, 
	 * {@link #PLAYER_PLAYS_EMPTY_STOCK}, {@link #PLAYER_PLAYS_PC_BLOCKED} are valid. 
	 * If the PC can play, let it put the domino piece.
	 * Otherwise, its the player turn.<br>
	 * 
	 * @throws IllegalStateException if state is different from {@link #DOUBLE0},
     * {@link #DOUBLE1},{@link #DOUBLE2},{@link #DOUBLE3},{@link #DOUBLE4},
     * {@link #DOUBLE5},{@link #DOUBLE6}, {@link #PLAYER_PLAYS}, {@link #PC_PLAYS_DRAWS},
     * {@link #PLAYER_PLAYS_EMPTY_STOCK}, {@link #PLAYER_PLAYS_PC_BLOCKED}.
     * @throws InvariantBrokenException if the Game state is not valid after execution.
	 */
    public void computerDecide() throws IllegalStateException, InvariantBrokenException {
    	DominoInt d;
        switch(indState){
            // Searching for the first double to put on the table.
            case DOUBLE0: case DOUBLE1: case DOUBLE2: case DOUBLE3: case DOUBLE4: case DOUBLE5: case DOUBLE6:
            	
            	d = pc.getHand().thereIs(indState,indState,true);
                if (d != null){
                    System.out.println("Preparation of the game of the PC. Domino : " + d);
                    gGame.setMessage("The computer will play " + d.getLeftValue() + " : " + d.getRightValue() + ". Click on Play PC to validate.");
                }
                else{
                	System.out.println("The PC can not play. The PC will jump.");
                	gGame.setMessage("The computer has not double " + indState + ". Click on Play PC to validate.");
                }
                break;

            // The player played, it is the PC's turn
            case PLAYER_PLAYS: case PC_PLAYS_DRAWS: 
            	d = pc.getHand().thereIs(getEnd(1),getEnd(2),false);
            	if (d != null){
                    System.out.println("Preparation of the game of the PC. Domino : " + d);
                    gGame.setMessage("The computer will play " + d.getLeftValue() + " : " + d.getRightValue() + ". Click on Play PC to validate.");
                    if (stock.isEmpty()){
                        setIndState(PC_PLAYS_EMPTY_STOCK);
                    }
                    else{
                        setIndState(PC_PLAYS);
                    }
                }
                else{
                    if (stock.isEmpty()){
                    	System.out.println("The PC can not play. The PC is blocked.");
                    	gGame.setMessage("The PC is blocked. Click on Play PC to validate.");
                        setIndState(PC_BLOCKED);
                    }
                    else{
                    	System.out.println("The PC can not play. The PC will draw.");
                    	gGame.setMessage("The PC will draw. Click on Play PC to validate.");
                        setIndState(PC_PLAYS_DRAWS);
                    }
                }
                break;
            // The player played, the Stock is empty
            case PLAYER_PLAYS_EMPTY_STOCK: 
            	d = pc.getHand().thereIs(getEnd(1),getEnd(2),false);
            	if (d != null){
                    System.out.println("Preparation of the game of the PC. Domino : " + d);
                    gGame.setMessage("The computer will play " + d.getLeftValue() + " : " + d.getRightValue() + ". Click on Play PC to validate.");
                    setIndState(PC_PLAYS_EMPTY_STOCK);
                }
                else{
                	System.out.println("The PC can not play. The game is blocked");
                	gGame.setMessage("The PC is blocked. Click on Play PC to validate.");
                    setIndState(PC_BLOCKED);
                }
            	break;
            case PLAYER_PLAYS_PC_BLOCKED:
            	d = pc.getHand().thereIs(getEnd(1),getEnd(2),false);
            	if (d != null){
                    System.out.println("Preparation of the game of the PC. Domino : " + d);
                    gGame.setMessage("The computer will play " + d.getLeftValue() + " : " + d.getRightValue() + ". Click on Play PC to validate.");
                    setIndState(PC_PLAYS_EMPTY_STOCK);
                }
                else{
                	System.out.println("The PC can not play. The PC is blocked.");
                	gGame.setMessage("The PC is blocked. Click on Play PC to validate.");
                    setIndState(PC_BLOCKED);
                }
                break;
            default:
            	throw new IllegalStateException("Illegal state");
            	
        }   
        gGame.setEnabledPlayPC(true);
        
        if (!invariant()){
    		throw new InvariantBrokenException("Invariant broken.");
    	} 
    }

	/**
	 * Enable the Jump, Draw, Play PC and Hand buttons according to the state.
     * 
     * If {@link #indState} is equal to {@link #DOUBLE1} , 
	 *    {@link #indState} is equal to {@link #DOUBLE2} , 
	 *    {@link #indState} is equal to {@link #DOUBLE3} , 
	 *    {@link #indState} is equal to {@link #DOUBLE4} , 
	 *    {@link #indState} is equal to {@link #DOUBLE5} , 
	 *    {@link #indState} is equal to {@link #DOUBLE6} , 
	 *    {@link #indState} is equal to {@link #PLAYER_PLAYS_EMPTY_STOCK} or
	 *    {@link #indState} is equal to {@link #PLAYER_PLAYS_PC_BLOCKED} enables only 
	 *    the jump button and clicking the dominoes.Block the rest.<br>
	 *    
	 * If {@link #indState} is equal to {@link #PLAYER_PLAYS}, enable Draw button and 
	 * 	  clicking the dominoes. Block the rest.<br>
	 *   
	 * If {@link #indState} is equal to {@link #PC_PLAYS_DRAWS}, enable Play PC button. 
	 *    Block the rest. <br>
	 * 
	 * If {@link #indState} is equal to -1, block all the buttons. 
	 *    Block the rest.<br>
	 *    
	 * Other values for state are considered illegal.<br>
     * 
     * @param state value to indicate block or enable the appropriate buttons.
     * @throws IllegalStateException if state is not on the values [-1..13].
	 */
	public void setButtons(int state) throws IllegalStateException{
		switch (state){
            // Enable Jump button and clicking the dominoes. Block the rest.
            case DOUBLE0: case DOUBLE1: case DOUBLE2: case DOUBLE3: case DOUBLE4: case DOUBLE5: case DOUBLE6: case PLAYER_PLAYS_EMPTY_STOCK: case PLAYER_PLAYS_PC_BLOCKED:
                gGame.setEnabledJump(true);
                gGame.setEnabledDraw(false);
                gGame.setEnabledPlayPC(false);
                gGame.setHandEnable(true);
                break;
            // Enable Draw button and clicking the dominoes. Block the rest.
            case PLAYER_PLAYS:
                gGame.setEnabledJump(false);
                gGame.setEnabledDraw(true);
                gGame.setEnabledPlayPC(false);
                gGame.setHandEnable(true);
                break;
            // Enable Play PC button. Block the rest.
            case PC_PLAYS_DRAWS:
                gGame.setEnabledJump(false);
                gGame.setEnabledDraw(false);
                gGame.setEnabledPlayPC(true);
                gGame.setHandEnable(false);
                break;
            // Block all the buttons.
            case -1:
                gGame.setEnabledJump(false);
                gGame.setEnabledDraw(false);
                gGame.setEnabledPlayPC(false);
                gGame.setHandEnable(false);
                break;
            // Invalid state.
            default: 
            	throw new IllegalStateException("Illegal state");

		}
	}

	 /**
	  * Check that the amount of pieces that are distributed on the stock,
	  * the table, and the hands of the players, keeps constant to {@link #TOTALPIECES}.
	  * Check that the stock's and table's invariant are not broken.
	  * 
	  * @return True if the Game is in a valid state. False otherwise.
	  * */
	 public boolean invariant(){
		 return ((stock.getSize() + table.getSize() + player1.getHand().getSize() + pc.getHand().getSize() == TOTALPIECES) &&
				 stock.invariant() &&
				 table.invariant() &&
				 indState >= MINSTATE && indState  <= MAXSTATE
				 //player1.getHand().invariant() &&
				 //pc.getHand().invariant() &&
				 );
		 		//if the table is not empty, indState different from 0..6
	 }
	 
    public static void main(String [] args){
        Game game = new Game();    
    }
}


