package clueGame;

public class Solution {

	private Card weapon;
	private Card player; 
	private Card room; 
	
	public Solution()
	{
		
	}
	
	public Solution(Card weapon, Card player, Card room){
		this.weapon = weapon; 
		this.player = player;
		this.room = room; 
	}

	public Card getWeapon() {
		return weapon;
	}

	public Card getPlayer() {
		return player;
	}

	public Card getRoom() {
		return room;
	}
	
	
}
