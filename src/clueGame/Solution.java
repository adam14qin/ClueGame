package clueGame;

public class Solution {

	private Card weapon;
	private Card player; 
	private Card room; 
	private typeSolution type; 
	public enum typeSolution {
		SUGGESTION, ACCUSATION
	}
	
	public Solution()
	{
		
	}
	
	public Solution(Card weapon, Card player, Card room){
		this.weapon = weapon; 
		this.player = player;
		this.room = room; 
	}
	
	public Solution(Card weapon, Card player, Card room, typeSolution type){
		this.weapon = weapon; 
		this.player = player;
		this.room = room; 
		this.type = type; 
	}

	public typeSolution getType()
	{
		return type; 
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

	@Override
	public String toString() {
		return "Solution [weapon=" + weapon + ", player=" + player + ", room=" + room + "]";
	}

	@Override
	public boolean equals(Object obj) {
		Solution other = (Solution) obj;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		if (weapon == null) {
			if (other.weapon != null)
				return false;
		} else if (!weapon.equals(other.weapon))
			return false;
		return true;
	}
	
	
	
	
	
}
