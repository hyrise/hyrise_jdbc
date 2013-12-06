/**
 * 
 */
package org.hyrise.jdbc;

import java.io.IOException;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.logging.Logger;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.hyrise.jdbc.helper.MethodNameInflection;

/**
 * @author grund
 * 
 */
public class HyriseConnection implements Connection {

	// This is the string we used to explicitly commit a transaction
	static final String COMMIT_OP = "{\"performance\":false,priority:1,\"operators\":{\"c\":{\"type\":\"Commit\"}},\"edges\":[[\"c\",\"c\"]]}";

	// Declares an invalid session context
	static final long INVALID_SESSION = 0;

	// Local logger
	final static Logger logger = Logger.getLogger(HyriseDriver.LOGGER_NAME);

	boolean autoCommit = true;

	boolean closed = true;

	boolean runningTransaction = false;

	long sessionContext = INVALID_SESSION;

	String url;

	Properties clientProperties;

	// This is the actual connection object
	CloseableHttpClient client;

	public HyriseConnection(String url, Properties info) {

		this.url = url;
		clientProperties = info;

		// We can use maximal 20 threads for a single connection
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(20);
		cm.setDefaultMaxPerRoute(20);

		// Build the client
		client = HttpClients.custom().setConnectionManager(cm).build();

	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Management Methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String nativeSQL(String sql) throws SQLException {
		throw new SQLException("Calling native SQL is not Supported");
	}

	@Override
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		this.autoCommit = autoCommit;
	}

	@Override
	public boolean getAutoCommit() throws SQLException {
		return this.autoCommit;
	}

	@Override
	public void commit() throws SQLException {
		// TODO Auto-generated method stub
	}

	@Override
	public void rollback() throws SQLException {
		throw new SQLException("Rollback not supported");
	}

	@Override
	public void close() throws SQLException {
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			closed = true;
		}
	}

	@Override
	public boolean isClosed() throws SQLException {
		return closed;
	}

	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		return new HyriseDatabaseMetaData();
	}

	@Override
	public void setReadOnly(boolean readOnly) throws SQLException {
		logger.warning("Read Only Mode is ignored");
	}

	@Override
	public boolean isReadOnly() throws SQLException {
		logger.warning("Read Only Mode is ignored");
		return false;
	}

	@Override
	public void setCatalog(String catalog) throws SQLException {
		logger.warning("Set Catalog is ignored and not supported");
	}

	@Override
	public String getCatalog() throws SQLException {
		logger.warning("Get Catalog is ignored and not supported");
		return "HYRISE";
	}

	@Override
	public void setTransactionIsolation(int level) throws SQLException {
		logger.warning("Setting transaction isolation level is not supported");
	}

	@Override
	public int getTransactionIsolation() throws SQLException {
		logger.warning("getting transaction isolation level is not supported");
		return 0;
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearWarnings() throws SQLException {
		// TODO Auto-generated method stub

	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Statement Methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	void beginTransaction() throws SQLException {
		setAutoCommit(false);
		runningTransaction = true;
	}

	@Override
	public Statement createStatement() throws SQLException {
		return new HyriseStatement(this);
	}

	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return new HyrisePreparedStatement(this, sql);
	}

	@Override
	public CallableStatement prepareCall(String sql) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency)
			throws SQLException {
		logger.warning("Create() - Not all parameters supported, handle with care...");
		return createStatement();
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType,
			int resultSetConcurrency) throws SQLException {
		logger.warning("Prepare() - Not all parameters supported, handle with care...");
		return prepareStatement(sql);
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType,
			int resultSetConcurrency) throws SQLException {
		logger.warning("Call() - Not all parameters supported, handle with care...");
		return prepareCall(sql);
	}

	@Override
	public Statement createStatement(int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		logger.warning("Create() - Not all parameters supported, handle with care...");
		return createStatement();
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		logger.warning("Prepare() - Not all parameters supported, handle with care...");
		return prepareStatement(sql);
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		logger.warning("Call() - Not all parameters supported, handle with care...");
		return prepareCall(sql);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
			throws SQLException {
		logger.warning("Prepare() - Not all parameters supported, handle with care...");
		return prepareStatement(sql);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
			throws SQLException {
		logger.warning("Prepare() - Not all parameters supported, handle with care...");
		return prepareStatement(sql);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, String[] columnNames)
			throws SQLException {
		logger.warning("Prepare() - Not all parameters supported, handle with care...");
		return prepareStatement(sql);
	}

	@Override
	public boolean isValid(int timeout) throws SQLException {
		return !closed;
	}

	@Override
	public void setClientInfo(String name, String value)
			throws SQLClientInfoException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setClientInfo(Properties properties)
			throws SQLClientInfoException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getClientInfo(String name) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSchema(String schema) throws SQLException {
		logger.warning("Cannot set schema, not supported - " + schema);
	}

	/**
	 * Currently there is only a default Schema
	 */
	@Override
	public String getSchema() throws SQLException {
		return "HYRISE";
	}

	@Override
	public void abort(Executor executor) throws SQLException {
		logger.warning("abort(Executor) - not supported");
	}

	@Override
	public void setNetworkTimeout(Executor executor, int milliseconds)
			throws SQLException {
		logger.warning("setNetworkTimeout() - not supported");
	}

	@Override
	public int getNetworkTimeout() throws SQLException {
		logger.warning("getNetworkTimeout() - not supported");
		return 0;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Unsupported Methods
	@Override
	public void setHoldability(int holdability) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public int getHoldability() throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Savepoint setSavepoint() throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Savepoint setSavepoint(String name) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void rollback(Savepoint savepoint) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Clob createClob() throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Blob createBlob() throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public NClob createNClob() throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Array createArrayOf(String typeName, Object[] elements)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Struct createStruct(String typeName, Object[] attributes)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

}
