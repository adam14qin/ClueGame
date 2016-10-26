package clueGame;

public class Solution {

	private String weapon;
	private Player player; 
	private String room; 
	
	public Solution()
	{
		
	}
	
	public Solution(String weapon, Player player, String room){
		this.weapon = weapon; 
		this.player = player;
		this.room = room; 
	}

	public String getWeapon() {
		return weapon;
	}

	public Player getPlayer() {
		return player;
	}

	public String getRoom() {
		return room;
	}
	
	
}
