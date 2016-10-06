package clueGame;

public enum DoorDirection {
	UP('U'), DOWN('D'), LEFT('L'), RIGHT('R'), NONE('N');
	char value;	
	
	// Parameterized constructor
	DoorDirection(char c) {
		value = c;
	}
	
	// Return an enum member given a character representation
	static DoorDirection fromCharacter(char c) {
		for (DoorDirection dir : DoorDirection.values())
			if (dir.value == c)
				return dir;
		return null;
	}
}
