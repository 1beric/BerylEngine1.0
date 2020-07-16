package tools.io;

import java.io.ByteArrayInputStream;

public class BerylInputStream extends ByteArrayInputStream {

	private static final byte BACKSPACE = 8;
	
	public BerylInputStream() {
		super(new byte[0]);
	}
	
	public BerylInputStream(byte[] buf) {
		super(buf);
	}

	public BerylInputStream(byte[] buf, int offset, int length) {
		super(buf, offset, length);
	}
	
	public String stringify() {
		String str = "";
		while (true) {
			byte read = -1;
			read = (byte)read();
			if (read == -1) 								break;
			else if (isStringCharacter((char)read)) 		str += (char)read;
			else if (read == BACKSPACE && str.length() > 0) str = str.substring(0,str.length()-1);  
		}
		return str;
	}
	
	private boolean isStringCharacter(char ch) {
		return Character.isAlphabetic(ch) ||  Character.isDigit(ch) ||  Character.isWhitespace(ch);
	}

}
