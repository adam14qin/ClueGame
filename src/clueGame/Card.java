package clueGame;

public class Card {

	private String cardName;
	private CardType type; 
	
	public Card(CardType type, String cardName) 
	{
		this.cardName = cardName; 
		this.type = type; 
	}

	@Override
	public boolean equals(Object obj) {
		Card other = (Card) obj;
		if (type != other.type)
			return false;
		if(!cardName.equals(other.cardName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Card [cardName=" + cardName + ", type=" + type + "]\n";
	}
	
	public String getCardName() {
		return cardName; 
	}
	
}
