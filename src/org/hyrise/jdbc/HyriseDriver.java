package org.hyrise.jdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;


/**
 * This is the main class for driving a "JDBC" connection to Hyrise. Basically
 * it goals to wrap the HTTP connection
 * 
 */
public class HyriseDriver implements Driver {
	
	public static final String LOGGER_NAME = "HyriseJDBC";
	

	static {
		try {
			
			DriverManager.registerDriver(new HyriseDriver());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public HyriseDriver() {
		Logger.getLogger(LOGGER_NAME);
	}
	
	@Override
	public Connection connect(String url, Properties info) throws SQLException {
		return new HyriseConnection(url, info);
	}

	@Override
	public boolean acceptsURL(String url) throws SQLException {
		return url.startsWith("http://");
	}

	@Override
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMajorVersion() {
		return 0;
	}

	@Override
	public int getMinorVersion() {
		return 1;
	}

	/**
	 * HYRISE is not SQL'92 entry level compliant and we therefore are not JDBC compliant
	 */
	@Override
	public boolean jdbcCompliant() {
		return false;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return Logger.getGlobal();
	}

}
