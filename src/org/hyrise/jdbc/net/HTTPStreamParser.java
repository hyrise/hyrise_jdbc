package org.hyrise.jdbc.net;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.HashMap;

public class HTTPStreamParser {

	// Decoder
	CharsetDecoder dec = Charset.forName("UTF-8").newDecoder();

	// HTTP Status Code
	int status;
	
	// HTTP Header
	HashMap<String, String> headers = new HashMap<String, String>();
	
	StringBuffer body = new StringBuffer();

	// Current parser state
	ParseState parseState = ParseState.STATUS;
	
	StringBuffer lastLine = new StringBuffer();
	
	boolean done = false;
	
	enum ParseState {
		STATUS,
		HEADER,
		BODY		
	} 
	
	
	static final String SEPARATOR = "\r\n";
	static final char SEP_1 = '\r';
	static final char SEP_2 = '\n';
	
	int lineEnd = 0;
	
	int bodyRead = 0;
	int bodySize = 0;
	
	
	public void reset() {
		parseState = ParseState.STATUS;
		lastLine = new StringBuffer();
		bodyRead = 0;
		done = false;
	}
	
	
	public void read(ByteBuffer buf) {
		
		// If nothing is in the buffer, return
		if (!buf.hasRemaining())
			return;
		
		
		// Read all the bytes and build the data
		byte next;
		while (buf.hasRemaining()) {
			
			next = buf.get();
			
			switch (parseState) {
			case STATUS:
				
				// Increment the line end phase so that we know when we are done
				if (lineEnd == 0 && next == SEP_1) {
					lineEnd = 1;
				} else if (lineEnd == 1 && next == SEP_2) {
					lineEnd = 0;
					String tmp = lastLine.toString();
					String[] parts = tmp.split(" ");
					if (parts.length > 1) {
						status = Integer.parseInt(parts[1]);
					}					
					parseState = ParseState.HEADER;
					lastLine = new StringBuffer();
				} else {
					lastLine.append((char) next);
				}
				break;
			case HEADER:
				
				// Check for line endings
				if (lineEnd == 0 && next == SEP_1) {
					lineEnd = 1;
				} else if (lineEnd == 1 && next == SEP_2) {
					lineEnd = 0;
					String tmp = lastLine.toString();
					if (tmp.length() > 0) {
						String[] parts = tmp.split(":");
						headers.put(parts[0].trim(), parts[1].trim());
						lastLine = new StringBuffer();
					} else {

						// Update the body size
						bodySize = Integer.parseInt(headers.get("Content-Length"));
						parseState = ParseState.BODY;
						lastLine = new StringBuffer();
					}					
				} else {
					lastLine.append((char)next);
				}
				break;
				
			case BODY:
				lastLine.append((char) next);
				++bodyRead;
				if (bodyRead >= bodySize) {
					body = lastLine;
					done = true;
				}
				break;
			}
		}
	}
	
	public String getBody() {
		return body.toString();
	}
	
	public int getStatus() {
		return status;
	}
	
	public boolean done() {
		return done;
	}

}

