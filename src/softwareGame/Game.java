package softwareGame;

import tools.InvariantBrokenException;
import graphicInterface.GGame;
import graphicInterface.InterfaceGame;

/**
* Run the game between a player and the computer.
* @version 2.0
*
*/
public class Game implements InterfaceGame
{
	/**
	 * The graphical interface.
	 */
	GGame gGame;
	
	/**
	 * The stock
	 */
	 private Stock stock;
   
	 /**
	  * The board where dominos are put.
	  */
	 private Table table;
   
   /**
    * Player 1
    */
	 private Player player1;
   
   /**
    * Computer
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
	
	//Meaning of the states
	 String stateMeaning[] ={"double0","double1","double2","double3","double4",
			 "double5",
			 "double6",
			 "playerPlays",
			 "playerPlaysEmptyStock",
			 "computerPlays",
			 "computerPlaysDrawing",
			 "computerBlocked",
			 "computerPlaysEmptyStock, " ,
			 "playerPlaysComputerBlocked"};
	 
	 int indState = 6;
	 
	 public static final int INITIALPIECES = 7;
	 
	 public static final int TOTALPIECES = 28;
	 
	 public static final int MINSTATE = 0;
	 
	 public static final int MAXSTATE = 13;
	 
	 public static final boolean INVARIANT = MAXSTATE > MINSTATE && INITIALPIECES*2 <= TOTALPIECES && TOTALPIECES == Table.MAXSIZE && TOTALPIECES == Stock.MAXSIZE;
	 
	 public boolean invariant(){
		 return ((stock.getSize() + table.getSize() + player1.getHand().getSize() + pc.getHand().getSize() == TOTALPIECES) &&
				 stock.invariant() &&
				 table.invariant() &&
				 indState >= MINSTATE && indState  <= MAXSTATE
				 //player1.getHand().invariant() &&
				 //pc.getHand().invariant() &&
				 );
	 }
	 
	 /**
      * Constructor for 2 players.Create a graphical interface and send it a message to enter the player's name.
      */
	 public Game(){
		this.gGame = new GGame(this);
		gGame.setVisible(true);
		gGame.setMessage("Hello. Enter your name: ");
	 }
   
   /**
    * This method is called when an event is produced in the graphical interface.
    * @param val The state of the game.
    * @throws IllegalArgumentException if val is out of bounds.
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
    * This method is called when an event is produced in the graphical interface.
    * @param val The state of the game.
    * @throws IllegalStateException if val
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
                    case 6: case 5: case 4:case 3: case 2: case 1: case 0:
                        // The player clicks on correct double
                        if (domino.getLeftValue() == indState &&
                            domino.getRightValue() == indState){
                            setIndState(7);
                            treatAnswer(domino);
                        }
                        // The player clicks on a wrong domino
                        else {
                            gGame.setMessage("This choice is not good. Please click on double "+ indState + " or jump.");
                        }
                        break;
                    case 7: case 8: case 13:
                        treatDoubleAnswer(domino);
                        break;
                    default: 
                    	throw new IllegalStateException("Illegal state.");
                }
                break;

            case GGame.JUMP: // The player has clicked on the jump button	
                
				switch (indState){
                    case 13: case 8: case 6: case 5: case 4: case 3: case 2: case 1: case 0:
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
    * Create a stock, a board, two players (player and computer), initialize the 
    * graphical interface : hand, button and send it the first message.
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
    * Retrieve a end value of the table.
    * @param side The side to be considered : 1 or 2 (1: left, 2: right)
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
    * If it is ok, call treatAnswer method otherwise send a message.
    * @param d The selected domino.
    * @throws IllegalArgumentException if the domino is null or not valid.
    */
   public void treatDoubleAnswer(DominoInt d) throws IllegalArgumentException {
	   if (d == null || !d.invariant() ){
			throw new IllegalArgumentException("Illegal argument. Domino is null or invalid.");
		}
	   if  (table.isEmpty() || d.matches(getEnd(1),getEnd(2))){
		   treatAnswer(d);
	   }
	   else{
		   gGame.setMessage("This choice is not good.");
	   }
   }

   /**
    * When a player plays, we remove d from the hand of the player and from the 
	* graphical hand. Then put d on the board and on the graphic table. 
	* Check if the player wins. Otherwise, call computerDecide.
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
	* The player draw. If the stock is empty, the computer plays (state 9) 
	* otherwise the drawn domino is added to the hand and the graphic hand
    * and, then, the computer plays (state 8).
	* @throws IllegalStateException if state is not on the values [7,8].
	* @throws InvariantBrokenException if the Game state is not valid after execution.
	*/
	public void playerDraw() throws IllegalStateException, InvariantBrokenException{ 
	
        switch(indState){
            case 7:
                if (stock.isEmpty()){
                    setIndState(8);
                    setButtons(8);
                    gGame.setMessage("The stock is over. Please choose a domino or jump.");
                }
                else {
                    DominoInt d = stock.draw();
                    player1.getHand().add(d);
                    gGame.addDominoInHand(d);
                    gGame.setMessage("You draw " + d.getLeftValue() + " : " + d.getRightValue() + " . Please choose a domino or draw again.");
                    if (stock.isEmpty()){
                        setIndState(8);
                    }
                }
                break;
            case 8:
                setButtons(8);
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
	 * The computer plays in relationship with the state of the game.
	 * If state = n (with n =1,2,3,4,5,6) we look for a double n in the computer's hand.
	 * If yes, the computer plays else the player is asked to play the double domino (n-1).
	 * If n=0 we look for a double 0 in the computer's hand.
	 * If yes, the computer plays, otherwise the player is asked to play any other domino.
	 * If n=9, the pc can play a domino piece.
     * If n=10, the pc can not play. The pc draws from the stock.
	 * If n=11 the pc is blocked.
     * If n=12 the pc can play but the stock is empty.
     * @throws IllegalStateException if state is not on the values [0..6,9-12].
     * @throws InvariantBrokenException if the Game state is not valid after execution.
	 */
   	public void computerPlay() throws IllegalStateException, InvariantBrokenException {
   		
        System.out.println("\nstate:"+indState+ ". Computer plays");
        DominoInt d = null;

        switch (indState){
            // Searching for the first double
            case 1: case 2: case 3: case 4: case 5: case 6:
                d = pc.getHand().thereIs(indState,indState, true);
                // The PC has the double searched.
                if (d != null){
                    pc.getHand().delete(d); 
                    gGame.putDominoOnTable(d);
                    table.add(d);
                    
                    gGame.setMessage("The computer has played " + d.getLeftValue() + " : " + d.getRightValue() + ". Please choose a domino or draw." );
                    setButtons(7);
                    setIndState(7);
                }
                // The PC does not have the double searched.
                else{
                    setButtons(indState -1);
                    setIndState(indState - 1);
                    gGame.setMessage("Please click on double " + indState  + " or jump.");
                }
                break;
            // Searching for double 0
            case 0:
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
                setButtons(7);
                setIndState(7);
                break;
            case 9:
                // The PC has a domino piece to play.
                d = pc.getHand().thereIs(getEnd(1),getEnd(2),false);
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
		            setButtons(7);
		            setIndState(7);
				}
                break;
            case 10:
                // The PC draws
                gGame.setMessage("The PC draws.");
                DominoInt draw = stock.draw();
                pc.getHand().add(draw);
                setButtons(10);
                computerDecide(); 
                break;
            case 11:
                // The PC is blocked
                gGame.setMessage("The PC is blocked. Please click on a domino or jump.");
                setButtons(13);
                setIndState(13);
                break;
            case 12:
                // The PC can play. The Stock is empty
                d = pc.getHand().thereIs(getEnd(1),getEnd(2),false);
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
                    setButtons(8);
		            setIndState(8);                
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
	 * To verify that the player does not cheat when clicking on the Jump button.
	 * A player can Jump only when he/she does not possess the double searched,
	 * Or when he/she does not possess a piece that matches any of the ends of 
	 * the table and the stock is empty.
	 * @throws IllegalStateException if state is not on the values [0..6,7,8,13].
	 */
   public void treatJumpAnswer() throws IllegalStateException{
	   
        System.out.println("State: "+indState + ". Into jump player's process"); 
        switch (indState){
            // Searching for the first double
            case 0: case 1: case 2: case 3: case 4: case 5: case 6:
				
				if (player1.getHand().canPlay(indState,indState, true)){
					gGame.setMessage("This choice is not good. Please click on double " + indState + ".");
				}
                else{
                    System.out.println("The player can not play.");
                    setButtons(10);
                    computerDecide();
                }
                break;
            // The player's turn. The stock is empty
            case 8:
                if (player1.getHand().canPlay(getEnd(1),getEnd(2), false)){
                    gGame.setMessage("This choice is not good. Please click a domino.");
                    gGame.setEnabledJump(false);
                }
                else{
                    System.out.println("The player can not play.");
                    setButtons(10);
                    computerDecide();
                }
                break;
            // The player's turn. Computer block.
            case 13:
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
	 * put a domino piece on the table and change the indState properly.
	 * Send the appropriate message to the player and ask to click on the 
	 * Play PC button to validate the action.
	 * @throws IllegalStateException if state is not on the values [0..6,7,8,10,13].
	 * @throws InvariantBrokenException if the Game state is not valid after execution.
	 */
    public void computerDecide() throws IllegalStateException, InvariantBrokenException {
    	DominoInt d;
        switch(indState){
            // Searching for the first double to put on the table.
            case 0: case 1: case 2: case 3: case 4: case 5: case 6:
            	
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
            case 7: case 10: 
            	d = pc.getHand().thereIs(getEnd(1),getEnd(2),false);
            	if (d != null){
                    System.out.println("Preparation of the game of the PC. Domino : " + d);
                    gGame.setMessage("The computer will play " + d.getLeftValue() + " : " + d.getRightValue() + ". Click on Play PC to validate.");
                    if (stock.isEmpty()){
                        setIndState(12);
                    }
                    else{
                        setIndState(9);
                    }
                }
                else{
                    if (stock.isEmpty()){
                    	System.out.println("The PC can not play. The PC is blocked.");
                    	gGame.setMessage("The PC is blocked. Click on Play PC to validate.");
                        setIndState(11);
                    }
                    else{
                    	System.out.println("The PC can not play. The PC will draw.");
                    	gGame.setMessage("The PC will draw. Click on Play PC to validate.");
                        setIndState(10);
                    }
                }
                break;
            // The player played, the Stock is empty
            case 8: 
            	d = pc.getHand().thereIs(getEnd(1),getEnd(2),false);
            	if (d != null){
                    System.out.println("Preparation of the game of the PC. Domino : " + d);
                    gGame.setMessage("The computer will play " + d.getLeftValue() + " : " + d.getRightValue() + ". Click on Play PC to validate.");
                    setIndState(12);
                }
                else{
                	System.out.println("The PC can not play. The game is blocked");
                	gGame.setMessage("The PC is blocked. Click on Play PC to validate.");
                    setIndState(11);
                }
            	break;
            case 13:
            	d = pc.getHand().thereIs(getEnd(1),getEnd(2),false);
            	if (d != null){
                    System.out.println("Preparation of the game of the PC. Domino : " + d);
                    gGame.setMessage("The computer will play " + d.getLeftValue() + " : " + d.getRightValue() + ". Click on Play PC to validate.");
                    setIndState(12);
                }
                else{
                	System.out.println("The PC can not play. The PC is blocked.");
                	gGame.setMessage("The PC is blocked. Click on Play PC to validate.");
                    setIndState(11);
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
     * States 0-6, 8, 13 allow only to jump.
     * 7 allows only to draw.
     * 10 allows only to computer play.
     * -1 blocks all the buttons.
     * @param state value to indicate block or enable the appropriate buttons.
     * @throws IllegalStateException if state is not on the values [-1..13].
	 */
	public void setButtons(int state) throws IllegalStateException{
		switch (state){
            // Enable Jump button and clicking the dominoes. Block the rest.
            case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 8: case 13:
                gGame.setEnabledJump(true);
                gGame.setEnabledDraw(false);
                gGame.setEnabledPlayPC(false);
                gGame.setHandEnable(true);
                break;
            // Enable Draw button and clicking the dominoes. Block the rest.
            case 7:
                gGame.setEnabledJump(false);
                gGame.setEnabledDraw(true);
                gGame.setEnabledPlayPC(false);
                gGame.setHandEnable(true);
                break;
            // Enable Play PC button. Block the rest.
            case 10:
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


    public static void main(String [] args){
        Game game = new Game();    
    }
}


