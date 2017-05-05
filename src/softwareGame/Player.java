package softwareGame;

/**
 * Class that represent a player of the Domino Game.
 * It handles its name, a hand and the playing action.
 * @author	Patricia REINOSO
 * @version 1.1.0
 * @since	2017-03-08
 */

public class Player{

	private String name;
	private Hand hand;

	/**
	 * Class constructor.
	 * @param name the name of the player.
	 */
	public Player(String name){
		setName(name);
		hand = new Hand();
	}

	/**
	 * Set the name of the player.
	 * @param name the name of the player.
	 * @throws IllegalArgumentException if myHand is null.
	 */
	public void setName(String name) throws IllegalArgumentException{
		if (name == null){
			throw new IllegalArgumentException("Illegal argument. The name is null.");
		}
		this.name = name;
	}

	/**
	 * @return the name of the player.
	 */
	public String getName(){
		return name;
	}

	/**
	 * @return the hand of the player.
	 */
	public Hand getHand(){
		return hand;
	}

	/**
	 * Set the hand of the player.
	 * The hand must not be null.
	 * @param hand the hand that is assign to the player.
	 * @throws IllegalArgumentException if hand is null.
	 */
	public void setHand(Hand hand) throws IllegalArgumentException{
		if (hand == null){
			throw new IllegalArgumentException("Illegal argument. The hand is null.");
		}
		this.hand = hand;
	}
	
	/**
	 * Indicate if the player won. A player won when his hand is empty.
	 * @return	true if the player won. False otherwise.
	 */
	public boolean isWin(){
		return (hand.isEmpty());
	}
	
	public String toString(){
		return ("Player: "+ getName() + " | Hand: "+ getHand());
	}
	
}
