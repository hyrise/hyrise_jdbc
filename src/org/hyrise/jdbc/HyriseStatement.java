/**
 * 
 */
package org.hyrise.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.hyrise.jdbc.helper.HyriseResult;
import org.hyrise.jdbc.helper.HyriseResultException;
import org.hyrise.jdbc.helper.MethodNameInflection;
import org.hyrise.jdbc.net.HyriseResponseHandler;

/**
 * @author grund
 * 
 */
public class HyriseStatement implements Statement {

	final static Logger logger = Logger.getLogger(HyriseDriver.LOGGER_NAME);

	HyriseConnection connection;

	int maxRows = 0;
	
	int timeout = -1;
	
	boolean closed = false;

	// Temporary Result to be stored
	HyriseResult result = null;
	
	public HyriseStatement(HyriseConnection c) {
		connection = c;
		closed = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Wrapper#unwrap(java.lang.Class)
	 */
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
	 */
	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#executeQuery(java.lang.String)
	 */
	@Override
	public ResultSet executeQuery(String sql) throws SQLException {
		result = executeHyriseQuery(sql, false);
		return new HyriseResultSet(result, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#executeUpdate(java.lang.String)
	 */
	@Override
	public int executeUpdate(String sql) throws SQLException {
		HyriseResult res = executeHyriseQuery(sql, false);
		return (int) res.affectedRows();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#close()
	 */
	@Override
	public void close() throws SQLException {
		result = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#getMaxFieldSize()
	 */
	@Override
	public int getMaxFieldSize() throws SQLException {
		return Integer.MAX_VALUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#setMaxFieldSize(int)
	 */
	@Override
	public void setMaxFieldSize(int max) throws SQLException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#getMaxRows()
	 */
	@Override
	public int getMaxRows() throws SQLException {
		return maxRows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#setMaxRows(int)
	 */
	@Override
	public void setMaxRows(int max) throws SQLException {
		maxRows = max;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#setEscapeProcessing(boolean)
	 */
	@Override
	public void setEscapeProcessing(boolean enable) throws SQLException {
		logger.warning(MethodNameInflection.methodName()
				+ " not supported in Statement");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#getQueryTimeout()
	 */
	@Override
	public int getQueryTimeout() throws SQLException {
		return timeout;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#setQueryTimeout(int)
	 */
	@Override
	public void setQueryTimeout(int seconds) throws SQLException {
		timeout = seconds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#cancel()
	 */
	@Override
	public void cancel() throws SQLException {
		logger.warning(MethodNameInflection.methodName()
				+ " not supported in Statement");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#getWarnings()
	 */
	@Override
	public SQLWarning getWarnings() throws SQLException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#clearWarnings()
	 */
	@Override
	public void clearWarnings() throws SQLException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#setCursorName(java.lang.String)
	 */
	@Override
	public void setCursorName(String name) throws SQLException {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#execute(java.lang.String)
	 */
	@Override
	public boolean execute(String sql) throws SQLException {
		result = executeHyriseQuery(sql, false);
		
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#getResultSet()
	 */
	@Override
	public ResultSet getResultSet() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#getUpdateCount()
	 */
	@Override
	public int getUpdateCount() throws SQLException {
		return result.affectedRows();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#getMoreResults()
	 */
	@Override
	public boolean getMoreResults() throws SQLException {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#setFetchDirection(int)
	 */
	@Override
	public void setFetchDirection(int direction) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#getFetchDirection()
	 */
	@Override
	public int getFetchDirection() throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#setFetchSize(int)
	 */
	@Override
	public void setFetchSize(int rows) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#getFetchSize()
	 */
	@Override
	public int getFetchSize() throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#getResultSetConcurrency()
	 */
	@Override
	public int getResultSetConcurrency() throws SQLException {
		return ResultSet.CONCUR_READ_ONLY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#getResultSetType()
	 */
	@Override
	public int getResultSetType() throws SQLException {
		return ResultSet.TYPE_FORWARD_ONLY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#addBatch(java.lang.String)
	 */
	@Override
	public void addBatch(String sql) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#clearBatch()
	 */
	@Override
	public void clearBatch() throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#executeBatch()
	 */
	@Override
	public int[] executeBatch() throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#getConnection()
	 */
	@Override
	public Connection getConnection() throws SQLException {
		return connection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#getMoreResults(int)
	 */
	@Override
	public boolean getMoreResults(int current) throws SQLException {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#getGeneratedKeys()
	 */
	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#executeUpdate(java.lang.String, int)
	 */
	@Override
	public int executeUpdate(String sql, int autoGeneratedKeys)
			throws SQLException {
		return executeUpdate(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#executeUpdate(java.lang.String, int[])
	 */
	@Override
	public int executeUpdate(String sql, int[] columnIndexes)
			throws SQLException {
		return executeUpdate(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#executeUpdate(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public int executeUpdate(String sql, String[] columnNames)
			throws SQLException {
		return executeUpdate(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#execute(java.lang.String, int)
	 */
	@Override
	public boolean execute(String sql, int autoGeneratedKeys)
			throws SQLException {
		return execute(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#execute(java.lang.String, int[])
	 */
	@Override
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		return execute(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#execute(java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean execute(String sql, String[] columnNames)
			throws SQLException {
		return execute(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#getResultSetHoldability()
	 */
	@Override
	public int getResultSetHoldability() throws SQLException {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#isClosed()
	 */
	@Override
	public boolean isClosed() throws SQLException {
		return result != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#setPoolable(boolean)
	 */
	@Override
	public void setPoolable(boolean poolable) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#isPoolable()
	 */
	@Override
	public boolean isPoolable() throws SQLException {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#closeOnCompletion()
	 */
	@Override
	public void closeOnCompletion() throws SQLException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Statement#isCloseOnCompletion()
	 */
	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		return true;
	}

	/**
	 * 
	 * @param query
	 * @param discard
	 * @return
	 * @throws SQLException
	 */
	HyriseResult executeHyriseQuery(String query, boolean discard)
			throws SQLException {
		try {
			HttpPost pm = preparePostRequest(query);
			HyriseResponseHandler rh = new HyriseResponseHandler(discard);
			return connection.client.execute(pm, rh);
		} catch (IOException e) {
			throw new SQLException(e);
		}

	}

	/**
	 * Prepares the post request for the connection
	 * 
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	HttpPost preparePostRequest(String query) throws SQLException {

		// Prepare the Post Ojject
		HttpPost pm = new HttpPost(connection.url + "/query");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("query", query));

		// Updating the request config parameters
		if (timeout > 0) {
			RequestConfig config = RequestConfig.custom()
					.setConnectTimeout(timeout)
					.setConnectionRequestTimeout(timeout).build();
			pm.setConfig(config);
		}

		// Check for running transactions and set the session context number
		if (connection.runningTransaction) {
			nvps.add(new BasicNameValuePair("session_context", String
					.valueOf(connection.sessionContext)));
		} else {
			nvps.add(new BasicNameValuePair("autocommit", String
					.valueOf(connection.getAutoCommit())));
		}

		// Handle Limit
		if (getMaxRows() > 0) {
			nvps.add(new BasicNameValuePair("limit", String
					.valueOf(getMaxRows())));
		}

		// FIXME: This can be dangerous
		nvps.add(new BasicNameValuePair("offset", "0"));

		pm.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		return pm;
	}
}
