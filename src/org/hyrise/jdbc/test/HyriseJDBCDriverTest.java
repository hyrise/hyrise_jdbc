/**
 * 
 */
package org.hyrise.jdbc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.commons.io.IOUtils;
import org.hyrise.jdbc.HyriseDriver;
import org.hyrise.jdbc.net.HTTPStreamParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author grund
 * 
 */
public class HyriseJDBCDriverTest {

	Connection c;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		Class.forName("org.hyrise.jdbc.HyriseDriver");
		Class.forName("org.hyrise.jdbc.helper.HyriseResult");
		c = DriverManager.getConnection("http://192.168.200.11:5001");

		ConsoleHandler h = new ConsoleHandler();
		h.setFormatter(new SimpleFormatter());
		h.setLevel(Level.ALL);

		Logger.getLogger(HyriseDriver.LOGGER_NAME).addHandler(h);
		Logger.getLogger(HyriseDriver.LOGGER_NAME).setLevel(Level.ALL);

		c.createStatement().executeQuery(getFile("queries/unloadAll.json"));
	}

	String getFile(String name) {
		try {
			File f = new File(name);
			FileInputStream s;
			s = new FileInputStream(f);
			String data = IOUtils.toString(s);
			s.close();
			return data;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		c.close();
	}

	@Test
	public void testComplexQuery() throws SQLException {
		Statement s = c.createStatement();
		ResultSet r = s.executeQuery(getFile("queries/index_join.json"));

		while (r.next()) {
			assertEquals(1, r.getInt(0));
		}

	}

	@Test
	public void testPreparedStatement() throws SQLException {
		PreparedStatement p = c
				.prepareStatement(getFile("queries/index_join_prepared_statement.json"));
		p.setInt(0, 9);
		p.setInt(1, 9);

		ResultSet r = p.executeQuery();
		assertEquals(false, r.next());

			}

	@Test
	public void testPreparedWithResult() throws SQLException {
		PreparedStatement p = c
				.prepareStatement(getFile("queries/index_join_prepared_statement.json"));
		p.setInt(0, 9);
		p.setInt(1, 9);
		
		p.clearParameters();
		p.setInt(0, 9);
		p.setInt(1, 2);

		ResultSet r = p.executeQuery();
		assertEquals(true, r.next());
		assertEquals(9, r.getInt(0));
		assertEquals(9, r.getInt("A_1"));
		assertEquals(true, r.next());
		assertEquals(2, r.getInt("B_1"));
		assertEquals(true, r.next());
		assertEquals(true, r.next());
		assertEquals(false, r.next());
	}

	@Test
	public void testSimpelNOP() {
		String NOP = "{\"priority\":10,\"performance\": false,\"operators\":{\"0\":{\"type\": \"NoOp\"}},\"edges\": [[\"0\", \"0\"]]}";

		try {
			Statement s = c.createStatement();
			ResultSet res = s.executeQuery(NOP);
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}
	
	
	@Test
	public void testHttpStreamParser() {
		String data = "HTTP/1.1 200 OK\r\n";
		data += "Content-Type: application/json\r\n";
		data += "Content-Length: 94\r\n";
		data += "Connection: Keep-Alive\r\n";
		data += "\r\n";
		data += "{\"error\":[\"Parsing: * Line 1, Column 1\\n  Syntax error: value, object or array expected.\\n\"]}\n";
		
		ByteBuffer buf = ByteBuffer.allocate(data.length());
		buf.put(data.getBytes());
		buf.flip();
		
		HTTPStreamParser p = new HTTPStreamParser();
		while(!p.done()) {
			p.read(buf);
		}
	}

	@Test
	public void testHttpStreamParserChunked() {
		String data = "HTTP/1.1 200 OK\r\n";
		data += "Content-Type: application/json\r\n";
		data += "Content-Length: 94\r\n";
		data += "Connection: Keep-Alive\r\n";
		data += "\r\n";
		data += "{\"error\":[\"Parsing: * Line 1, Column 1\\n  Syntax error: value, object or array expected.\\n\"]}\n";
		
		ByteBuffer buf = ByteBuffer.allocate(data.length() * 100);
		HTTPStreamParser p = new HTTPStreamParser();
		
		buf.put(data.substring(0, 30).getBytes());
		buf.flip();
		
		assertEquals(false, p.done());
		p.read(buf);
		assertEquals(false, p.done());
		buf.clear();
		buf.put(data.substring(30,70).getBytes());
		buf.flip();
		p.read(buf);
		assertEquals(false, p.done());
		buf.clear();
		buf.put(data.substring(70).getBytes());
		buf.flip();
		p.read(buf);
		assertEquals(true, p.done());
		
	}
	
}
