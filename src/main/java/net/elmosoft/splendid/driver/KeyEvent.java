package net.elmosoft.splendid.driver;

import org.openqa.selenium.Keys;


public enum KeyEvent
{
	/*----------------- Enum Values -----------------*/

	ADD("Add", Keys.ADD, java.awt.event.KeyEvent.VK_ADD),
	ALT("Alt", Keys.ALT, java.awt.event.KeyEvent.VK_ALT),
	ARROW_DOWN("Arrow Down", Keys.ARROW_DOWN, java.awt.event.KeyEvent.VK_DOWN),
	ARROW_LEFT("Arrow Left", Keys.ARROW_LEFT, java.awt.event.KeyEvent.VK_LEFT),
	ARROW_RIGHT("Arrow Right", Keys.ARROW_RIGHT, java.awt.event.KeyEvent.VK_RIGHT),
	ARROW_UP("Arrow Up", Keys.ARROW_UP, java.awt.event.KeyEvent.VK_UP),
	BACKSPACE("Backspace", Keys.BACK_SPACE, java.awt.event.KeyEvent.VK_BACK_SPACE),
	CANCEL("Cancel", Keys.CANCEL, java.awt.event.KeyEvent.VK_CANCEL),
	CLEAR("Clear", Keys.CLEAR, java.awt.event.KeyEvent.VK_CLEAR),
	CONTROL("Control", Keys.CONTROL, java.awt.event.KeyEvent.VK_CONTROL),
	DECIMAL("Decimal", Keys.DECIMAL, java.awt.event.KeyEvent.VK_DECIMAL),
	DELETE("Delete", Keys.DELETE, java.awt.event.KeyEvent.VK_DELETE),
	DIVIDE("Divide", Keys.DIVIDE, java.awt.event.KeyEvent.VK_DIVIDE),
	DOWN("Down", Keys.DOWN, java.awt.event.KeyEvent.VK_DOWN),
	END("End", Keys.END, java.awt.event.KeyEvent.VK_END),
	ENTER("Enter", Keys.ENTER, java.awt.event.KeyEvent.VK_ENTER),
	EQUALS("Equals", Keys.EQUALS, java.awt.event.KeyEvent.VK_EQUALS),
	ESCAPE("Escape", Keys.ESCAPE, java.awt.event.KeyEvent.VK_ESCAPE),
	F1("F1", Keys.F1, java.awt.event.KeyEvent.VK_F1),
	F10("F10", Keys.F10, java.awt.event.KeyEvent.VK_F10),
	F11("F11", Keys.F11, java.awt.event.KeyEvent.VK_F11),
	F12("F12", Keys.F12, java.awt.event.KeyEvent.VK_F12),
	F2("F2", Keys.F2, java.awt.event.KeyEvent.VK_F2),
	F3("F3", Keys.F3, java.awt.event.KeyEvent.VK_F3),
	F4("F4", Keys.F4, java.awt.event.KeyEvent.VK_F4),
	F5("F5", Keys.F5, java.awt.event.KeyEvent.VK_F5),
	F6("F6", Keys.F6, java.awt.event.KeyEvent.VK_F6),
	F7("F7", Keys.F7, java.awt.event.KeyEvent.VK_F7),
	F8("F8", Keys.F8, java.awt.event.KeyEvent.VK_F8),
	F9("F9", Keys.F9, java.awt.event.KeyEvent.VK_F9),
	HELP("Help", Keys.HELP, java.awt.event.KeyEvent.VK_HELP),
	HOME("Home", Keys.HOME, java.awt.event.KeyEvent.VK_HOME),
	INSERT("Insert", Keys.INSERT, java.awt.event.KeyEvent.VK_INSERT),
	LEFT("Left", Keys.LEFT, java.awt.event.KeyEvent.VK_LEFT),
	META("Meta", Keys.META, java.awt.event.KeyEvent.VK_META),
	MULTIPLY("Multiply", Keys.MULTIPLY, java.awt.event.KeyEvent.VK_MULTIPLY),
	NUMPAD0("Numpad 0", Keys.NUMPAD0, java.awt.event.KeyEvent.VK_NUMPAD0),
	NUMPAD1("Numpad 1", Keys.NUMPAD1, java.awt.event.KeyEvent.VK_NUMPAD1),
	NUMPAD2("Numpad 2", Keys.NUMPAD2, java.awt.event.KeyEvent.VK_NUMPAD2),
	NUMPAD3("Numpad 3", Keys.NUMPAD3, java.awt.event.KeyEvent.VK_NUMPAD3),
	NUMPAD4("Numpad 4", Keys.NUMPAD4, java.awt.event.KeyEvent.VK_NUMPAD4),
	NUMPAD5("Numpad 5", Keys.NUMPAD5, java.awt.event.KeyEvent.VK_NUMPAD5),
	NUMPAD6("Numpad 6", Keys.NUMPAD6, java.awt.event.KeyEvent.VK_NUMPAD6),
	NUMPAD7("Numpad 7", Keys.NUMPAD7, java.awt.event.KeyEvent.VK_NUMPAD7),
	NUMPAD8("Numpad 8", Keys.NUMPAD8, java.awt.event.KeyEvent.VK_NUMPAD8),
	NUMPAD9("Numpad 9", Keys.NUMPAD9, java.awt.event.KeyEvent.VK_NUMPAD9),
	PAGE_DOWN("Page Down", Keys.PAGE_DOWN, java.awt.event.KeyEvent.VK_PAGE_DOWN),
	PAGE_UP("Page Up", Keys.PAGE_UP, java.awt.event.KeyEvent.VK_PAGE_UP),
	PAUSE("Pause", Keys.PAUSE, java.awt.event.KeyEvent.VK_PAUSE),
	RIGHT("Right", Keys.RIGHT, java.awt.event.KeyEvent.VK_RIGHT),
	SEMICOLON("Semicolon", Keys.SEMICOLON, java.awt.event.KeyEvent.VK_SEMICOLON),
	SEPARATOR("Separator", Keys.SEPARATOR, java.awt.event.KeyEvent.VK_SEPARATOR),
	SHIFT("Shift", Keys.SHIFT, java.awt.event.KeyEvent.VK_SHIFT),
	SPACE("Space", Keys.SPACE, java.awt.event.KeyEvent.VK_SPACE),
	SUBTRACT("Subtract", Keys.SUBTRACT, java.awt.event.KeyEvent.VK_SUBTRACT),
	TAB("Tab", Keys.TAB, java.awt.event.KeyEvent.VK_TAB),
	UP("Up", Keys.UP, java.awt.event.KeyEvent.VK_UP),
	A("A", null, java.awt.event.KeyEvent.VK_A),
	B("B", null, java.awt.event.KeyEvent.VK_B),
	C("C", null, java.awt.event.KeyEvent.VK_C),
	D("D", null, java.awt.event.KeyEvent.VK_D),
	E("E", null, java.awt.event.KeyEvent.VK_E),
	F("F", null, java.awt.event.KeyEvent.VK_F),
	G("G", null, java.awt.event.KeyEvent.VK_G),
	H("H", null, java.awt.event.KeyEvent.VK_H),
	I("I", null, java.awt.event.KeyEvent.VK_I),
	J("J", null, java.awt.event.KeyEvent.VK_J),
	K("K", null, java.awt.event.KeyEvent.VK_K),
	L("L", null, java.awt.event.KeyEvent.VK_L),
	M("M", null, java.awt.event.KeyEvent.VK_M),
	N("N", null, java.awt.event.KeyEvent.VK_N),
	O("O", null, java.awt.event.KeyEvent.VK_O),
	P("P", null, java.awt.event.KeyEvent.VK_P),
	Q("Q", null, java.awt.event.KeyEvent.VK_Q),
	R("R", null, java.awt.event.KeyEvent.VK_R),
	S("S", null, java.awt.event.KeyEvent.VK_S),
	T("T", null, java.awt.event.KeyEvent.VK_T),
	U("U", null, java.awt.event.KeyEvent.VK_U),
	V("V", null, java.awt.event.KeyEvent.VK_V),
	W("W", null, java.awt.event.KeyEvent.VK_W),
	X("X", null, java.awt.event.KeyEvent.VK_X),
	Y("Y", null, java.awt.event.KeyEvent.VK_Y),
	Z("Z", null, java.awt.event.KeyEvent.VK_Z),
	NUM_1("1", null, java.awt.event.KeyEvent.VK_1),
	NUM_2("2", null, java.awt.event.KeyEvent.VK_2),
	NUM_3("3", null, java.awt.event.KeyEvent.VK_3),
	NUM_4("4", null, java.awt.event.KeyEvent.VK_4),
	NUM_5("5", null, java.awt.event.KeyEvent.VK_5),
	NUM_6("6", null, java.awt.event.KeyEvent.VK_6),
	NUM_7("7", null, java.awt.event.KeyEvent.VK_7),
	NUM_8("8", null, java.awt.event.KeyEvent.VK_8),
	NUM_9("9", null, java.awt.event.KeyEvent.VK_9),
	NUM_0("0", null, java.awt.event.KeyEvent.VK_0);

	/*----------------- Enum Properties -----------------*/

	private final String	name;
	private final Keys		seleniumKey;
	private final int		robotKeyValue;

	/*----------------- Enum Constructors -----------------*/

	/**
	 * KeyEvent constructor
	 * 
	 * @param name
	 *            A String representing the name of the KeyEvent.
	 * @param seleniumKey
	 *            An enum value representing the KeyEvent in Selenium.
	 * @param robotKeyValue
	 *            An integer representing the Java Robot key value for the KeyEvent.
	 */
	private KeyEvent(final String name, final Keys seleniumKey, final int robotKeyValue)
	{
		this.name = name;
		this.seleniumKey = seleniumKey;
		this.robotKeyValue = robotKeyValue;
	}

	/*----------------- Getter Functions -----------------*/

	/**
	 * Returns the enum id of the MouseEvent, which is represented by the name of the enum declaration
	 * 
	 * @return The enum id of the MouseEvent, which is represented by the name of the enum declaration
	 */
	public String getEnumId()
	{
		return name();
	}

	/**
	 * Returns the event name of the MouseEvent, which is represented by the string value passed into the constructor of each MouseEvent
	 * 
	 * @return The event name of the MouseEvent, which is represented by the string value passed into the constructor of each MouseEvent
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Returns the Selenium representation for the KeyEvent.
	 * 
	 * @return Keys - The Selenium representation for the KeyEvent.
	 */
	public Keys getSeleniumKey()
	{
		return seleniumKey;
	}

	/**
	 * Returns the Java Robot representation for the KeyEvent.
	 * 
	 * @return Keys - The Java Robot representation for the KeyEvent.
	 */
	public int getRobotKey()
	{
		return robotKeyValue;
	}

	/*----------------- Basic Object Functions -----------------*/

	@Override
	public String toString()
	{
		return getName();
	}
}
