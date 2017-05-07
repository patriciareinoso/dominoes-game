package softwareGame;

import graphicInterface.GGame;
import graphicInterface.InterfaceGame;

/**
* Run the game between a player and the computer.
* @version march 2016
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
			 "double5","double6","blockedComputer","play","blockedPlayer",
			 "win","blockedGame","NoDoubleFirstDomino"};
	 int indState = 6;
	
   /**
    * Constructor for 2 players.Create a graphical interface and send it a message to enter the player's name.
    */
   public Game(){
        this.gGame = new GGame(this);
        gGame.setVisible(true);
		gGame.setMessage("Hello. Enter your name: ");
   }
   
    public void setIndState(int value){
        this.indState = value;
   }
   /**
    * This method is called when an event is produced in the graphical interface.
    * @param val The state of the game.
    */
   public void receivedMessage(int val)
   {
        System.out.println( "\ntype received message  "+ val +" for state "+indState);
        switch (val)
        {
            case GGame.DATA_NAME: // Indicate the player has entered his name
                String name = gGame.getPlayerName(); 
				System.out.println( "\nName:  "+ name);
                initialize(name);
	   			break;
            case GGame.PLAY: // The player has clicked on a domino

                Domino domino = new Domino(gGame.getDomino().getLeftValue(),gGame.getDomino().getRightValue());
                System.out.println("domino played : " + domino);
                switch (indState)
                {
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
                    default: System.out.println("state no valid");
                }
                break;

            case GGame.JUMP: // The player has clicked on the jump button	
                
				switch (indState){
                    case 13: case 8: case 6: case 5: case 4: case 3: case 2: case 1: case 0:
                        treatJumpAnswer();
                        break;
                    default: System.out.println("state no valid");
                }
                break;
               
            case GGame.DRAW: // The player has clicked on the draw button
                playerDraw();
                break;
            case GGame.VALIDPCPLAY: // The player has clicked on the play PC button 
                computerPlay();
                break;	
        }
   
	}

   /**
    * Create a stock, a board, two players (player and computer), initialise the 
    * graphical interface : hand, button and send it the first message.
    * @param name The name of the player
    */
	public void initialize(String name){
	  
        this.stock = new Stock();
        this.table = new Table();
        this.player1 = new Player(name);
        this.pc = new Player("PC");

		System.out.println( "\nStock:  "+ stock.getPieces());
		System.out.println( "\ntable:  "+ table);
		System.out.println( "\nplayer:  "+ player1.getName());
        System.out.println( "\npc:  "+ pc.getName());

        Domino domino;
        for(int i = 0; i < 6 ; i++){
            domino = stock.draw();
            player1.getHand().add(domino);
			gGame.addDominoInHand(domino);
            domino = stock.draw();
            pc.getHand().add(domino);
        }
		System.out.println( "\nplayer hand :  "+ player1.getHand().getMyHand());
		System.out.println( "\npc hand:  "+ pc.getHand().getMyHand());
		gGame.setMessage("Hello " + name + " good luck.  Please click on double 6 or jump."); 
        gGame.setEnabledJump(true);
	}
   
   /**
    * Retrieve a end value of the table.
    * @param side The side to be considered : 1 or 2 (1: left, 2: right)
    * @return 	  The extremity value of the domino on the considered side 
	* 			  the table. -1 if the table is empty.
    */
   public int getEnd(int side)
   {
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
    */
   public void treatDoubleAnswer(Domino d)
   {
        if  (table.isEmpty() ||
            (d.getLeftValue() == getEnd(1))  ||
             (d.getLeftValue() == getEnd(2))  ||
             (d.getRightValue() == getEnd(1)) ||
             (d.getRightValue() == getEnd(2)))
            treatAnswer(d);
        else{
            gGame.setMessage("This choice is not good.");
        }
   }
 
   
   /**
    * When a player plays, we remove d from the hand of the player and from the 
	* graphical hand. Then put d on the board and on the graphic table. 
	* Check if the player wins. Otherwise, call computerDecide.
    * @param d The domino selected by the player.
    */
    public void treatAnswer(Domino d)
    {
        setButtons(-1);	
        gGame.removeDominoFromHand(d);
        gGame.putDominoOnTable(d);
        player1.getHand().delete(d);
        table.add(d);
        System.out.println("\ntable :  " + table.getPieces());
        System.out.println("\ntable ends :  " + getEnd(1) + " - " + getEnd(2));
        System.out.println("\nplayer's hand :  " + player1.getHand().getMyHand());
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
	*
	*/
	public void playerDraw(){ 
	
        switch(indState){
            case 7:
                if (stock.isEmpty()){
                    setIndState(8);
                    setButtons(8);
                    gGame.setMessage("The stock is over. Please choose a domino or jump.");
                }
                else {
                    Domino d = stock.draw();
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
	 */
   	public void computerPlay( )
   	{
        System.out.println("state:"+indState+ ". computer plays");
        Domino d = null;

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
                Domino draw = stock.draw();
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
            default: System.out.println("state no valid");
        }

	System.out.println("\ntable :  " + table.getPieces());
    System.out.println("\ntable ends :  " + getEnd(1) + " - " + getEnd(2));
    System.out.println("\nPC hand :" + pc.getHand().getMyHand());
   }

	/**
	 * To verify that the player does not cheat when clicking on the Jump button.
	 * A player can Jump only when he/she does not posses the double searched,
	 * Or when he/she does not posses a piece that matches any of the ends of 
	 * the table and the stock is empty.
	 */
   public void treatJumpAnswer()
   {
        System.out.println("State: "+indState + ". Into jump player's process"); 
        switch (indState){
            // Searching for the first double
            case 0: case 1: case 2: case 3: case 4: case 5: case 6:
				System.out.println(player1.getHand().canPlay(indState,indState, true));
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
                System.out.println("state no valid");

        }
    }

	/**
	 * Prepare the game for the PC. Verify if it is possible for the PC to 
	 * put a domino piece on the table and change the indState propperly.
	 * Send the appropriate message to the player and ask to click on the 
	 * Play PC button to validate the action.
	 */
    public void computerDecide(){
        
        switch(indState){
            // Searching for the first double
            case 0: case 1: case 2: case 3: case 4: case 5: case 6:
                if (pc.getHand().canPlay(indState,indState,true)){
                    Domino d = pc.getHand().thereIs(indState,indState,true);
                    System.out.println("Preparation of the game of the PC. Domino : " + d);
                    gGame.setMessage("The computer will play " + d.getLeftValue() + " : " + d.getRightValue() + ". Click on Play PC to validate.");
                }
                else{
                    gGame.setMessage("The computer has not double " + indState + ". Click on Play PC to validate.");
                    System.out.println("The PC can not play. The PC will jump.");
                }
                break;

            // The player played, it is the PC's turn
            case 7: case 10: 
                if (pc.getHand().canPlay(getEnd(1),getEnd(2),false)){
                    Domino d = pc.getHand().thereIs(getEnd(1),getEnd(2),false);
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
                        gGame.setMessage("The PC is blocked. Click on Play PC to validate.");
                        System.out.println("The PC can not play. The PC is blocked.");
                        setIndState(11);
                    }
                    else{
                        gGame.setMessage("The PC will draw. Click on Play PC to validate.");
                        System.out.println("The PC can not play. The PC will draw.");
                        setIndState(10);
                    }
                }
                break;
            // The player played, the Stock is empty
            case 8: 
                if (pc.getHand().canPlay(getEnd(1),getEnd(2),false)){
                    Domino d = pc.getHand().thereIs(getEnd(1),getEnd(2),false);
                    System.out.println("Preparation of the game of the PC. Domino : " + d);
                    gGame.setMessage("The computer will play " + d.getLeftValue() + " : " + d.getRightValue() + ". Click on Play PC to validate.");
                    setIndState(12);
                }
                else{
                    gGame.setMessage("The PC is blocked. Click on Play PC to validate.");
                    System.out.println("The PC can not play. The game is blocked");
                    setIndState(11);
                }
            case 13:
                if (pc.getHand().canPlay(getEnd(1),getEnd(2),false)){
                    Domino d = pc.getHand().thereIs(getEnd(1),getEnd(2),false);
                    System.out.println("Preparation of the game of the PC. Domino : " + d);
                    gGame.setMessage("The computer will play " + d.getLeftValue() + " : " + d.getRightValue() + ". Click on Play PC to validate.");
                    setIndState(12);
                }
                else{
                    gGame.setMessage("The PC is blocked. Click on Play PC to validate.");
                    System.out.println("The PC can not play. The PC is blocked.");
                    setIndState(11);
                }
                break;
        }   
        gGame.setEnabledPlayPC(true);
    }

	/**
	 * Enable the Jump, Draw, Play PC and Hand buttons according to the state.
     * 0-6, 8, 13 allows only to jump.
     * 7 allows only to draw.
     * 10 allows only to computer play.
     * -1 blocks all the buttons.
     * @param state value to indicate the appropiate action.
	 */
	public void setButtons(int state) {
		switch (state){
            // Allow play or jump
            case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 8: case 13:
                gGame.setEnabledJump(true);
                gGame.setEnabledDraw(false);
                gGame.setEnabledPlayPC(false);
                gGame.setHandEnable(true);
                break;
            // Allow to play or draw
            case 7:
                gGame.setEnabledJump(false);
                gGame.setEnabledDraw(true);
                gGame.setEnabledPlayPC(false);
                gGame.setHandEnable(true);
                break;
            // Allow computer play
            case 10:
                gGame.setEnabledJump(false);
                gGame.setEnabledDraw(false);
                gGame.setEnabledPlayPC(true);
                gGame.setHandEnable(false);
                break;
            // Block all the buttons
            case -1:
                gGame.setEnabledJump(false);
                gGame.setEnabledDraw(false);
                gGame.setEnabledPlayPC(false);
                gGame.setHandEnable(false);
                break;

            default: System.out.println("state no valid");

		}
	}


    public static void main(String [] args){
        Game game = new Game();    
    }
}


