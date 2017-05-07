package softwareGame;

/**
 * Class that represent a player of the Domino Game.
 * It handles its name, a hand and the playing action.
 * @author	Patricia REINOSO
 * @version 1.0
 * @since	2017-03-08
 */

public class Player{

	private String name;
	private Hand hand = new Hand();

	/**
	 * Class constructor.
	 * @param name the name of the player.
	 */
	public Player(String name){
		setName(name);
	}

	/**
	 * Set the name of the player.
	 * @param name the name of the player.
	 */
	public void setName(String name){
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
	 * @param hand the hand that is assign to the player.
	 */
	public void setHand(Hand hand){
		this.hand = hand;
	}
	
	/**
	 * Indicate if the player won. A player won when his hand is empty.
	 * @return	true if the player won. False otherwise.
	 */
	public boolean isWin(){
		return (hand.isEmpty());
	}

}
