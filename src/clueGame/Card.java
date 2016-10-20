package clueGame;

public class Card {

	private String cardName;
	private CardType type; 
	
	public Card(char type, String cardName)
	{
		this.cardName = cardName; 
		switch(type)
		{
		case 'P': 
		{
			this.type = CardType.PERSON;
			break; 
		}
		case 'R': 
		{
			this.type = CardType.ROOM;
			break; 
		}
		case 'W': 
		{
			this.type = CardType.WEAPON;
			break; 
		}
		default:
		{
			System.err.println("Check Card config file");
		}
		}
	}

	@Override
	public boolean equals(Object obj) {
		Card other = (Card) obj;
		if (type != other.type)
			return false;
		return true;
	}
	
	
	
}
