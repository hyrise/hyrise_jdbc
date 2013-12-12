package org.hyrise.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import org.hyrise.jdbc.helper.HyriseResultException;
import org.hyrise.jdbc.helper.MethodNameInflection;

public class HyriseCallableStatement extends HyrisePreparedStatement implements
		CallableStatement {

	String endpoint = null;
	
	
	public HyriseCallableStatement(HyriseConnection c, String sql) {
		super(c, null);
		String[] parts = sql.split("\n");
		this.sql = parts[1];
		this.endpoint = parts[0];		
	}

	@Override
	public ResultSet executeQuery() throws SQLException {
		try {
			result = doRawSocketPost(endpoint, sql, false);
			return new HyriseResultSet(result, this);
		} catch (IOException | HyriseResultException e) {
			throw new SQLException(e);
		}
		
	}
	
	@Override
	public void registerOutParameter(int parameterIndex, int sqlType)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void registerOutParameter(int parameterIndex, int sqlType, int scale)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public boolean wasNull() throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public String getString(int parameterIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public boolean getBoolean(int parameterIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public byte getByte(int parameterIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public short getShort(int parameterIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public int getInt(int parameterIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public long getLong(int parameterIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public float getFloat(int parameterIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public double getDouble(int parameterIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public BigDecimal getBigDecimal(int parameterIndex, int scale)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public byte[] getBytes(int parameterIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Date getDate(int parameterIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Time getTime(int parameterIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Timestamp getTimestamp(int parameterIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Object getObject(int parameterIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Object getObject(int parameterIndex, Map<String, Class<?>> map)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Ref getRef(int parameterIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Blob getBlob(int parameterIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Clob getClob(int parameterIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Array getArray(int parameterIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Timestamp getTimestamp(int parameterIndex, Calendar cal)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void registerOutParameter(int parameterIndex, int sqlType,
			String typeName) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void registerOutParameter(String parameterName, int sqlType)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void registerOutParameter(String parameterName, int sqlType,
			int scale) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void registerOutParameter(String parameterName, int sqlType,
			String typeName) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public URL getURL(int parameterIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void setURL(String parameterName, URL val) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void setNull(String parameterName, int sqlType) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setBoolean(String parameterName, boolean x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setByte(String parameterName, byte x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setShort(String parameterName, short x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setInt(String parameterName, int x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setLong(String parameterName, long x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setFloat(String parameterName, float x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setDouble(String parameterName, double x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setBigDecimal(String parameterName, BigDecimal x)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setString(String parameterName, String x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setBytes(String parameterName, byte[] x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setDate(String parameterName, Date x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setTime(String parameterName, Time x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setTimestamp(String parameterName, Timestamp x)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setAsciiStream(String parameterName, InputStream x, int length)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setBinaryStream(String parameterName, InputStream x, int length)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setObject(String parameterName, Object x, int targetSqlType,
			int scale) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setObject(String parameterName, Object x, int targetSqlType)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setObject(String parameterName, Object x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setCharacterStream(String parameterName, Reader reader,
			int length) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setDate(String parameterName, Date x, Calendar cal)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setTime(String parameterName, Time x, Calendar cal)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setTimestamp(String parameterName, Timestamp x, Calendar cal)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setNull(String parameterName, int sqlType, String typeName)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public String getString(String parameterName) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public boolean getBoolean(String parameterName) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public byte getByte(String parameterName) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public short getShort(String parameterName) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public int getInt(String parameterName) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public long getLong(String parameterName) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public float getFloat(String parameterName) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public double getDouble(String parameterName) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public byte[] getBytes(String parameterName) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public Date getDate(String parameterName) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public Time getTime(String parameterName) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public Timestamp getTimestamp(String parameterName) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public Object getObject(String parameterName) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public BigDecimal getBigDecimal(String parameterName) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public Object getObject(String parameterName, Map<String, Class<?>> map)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public Ref getRef(String parameterName) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public Blob getBlob(String parameterName) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public Clob getClob(String parameterName) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public Array getArray(String parameterName) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public Date getDate(String parameterName, Calendar cal) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public Time getTime(String parameterName, Calendar cal) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public Timestamp getTimestamp(String parameterName, Calendar cal)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public URL getURL(String parameterName) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public RowId getRowId(int parameterIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public RowId getRowId(String parameterName) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setRowId(String parameterName, RowId x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setNString(String parameterName, String value)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setNCharacterStream(String parameterName, Reader value,
			long length) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setNClob(String parameterName, NClob value) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setClob(String parameterName, Reader reader, long length)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setBlob(String parameterName, InputStream inputStream,
			long length) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setNClob(String parameterName, Reader reader, long length)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public NClob getNClob(int parameterIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public NClob getNClob(String parameterName) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void setSQLXML(String parameterName, SQLXML xmlObject)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public SQLXML getSQLXML(int parameterIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public SQLXML getSQLXML(String parameterName) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public String getNString(int parameterIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public String getNString(String parameterName) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Reader getNCharacterStream(int parameterIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Reader getNCharacterStream(String parameterName) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Reader getCharacterStream(int parameterIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Reader getCharacterStream(String parameterName) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void setBlob(String parameterName, Blob x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setClob(String parameterName, Clob x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setAsciiStream(String parameterName, InputStream x, long length)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setBinaryStream(String parameterName, InputStream x, long length)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setCharacterStream(String parameterName, Reader reader,
			long length) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setAsciiStream(String parameterName, InputStream x)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setBinaryStream(String parameterName, InputStream x)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setCharacterStream(String parameterName, Reader reader)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setNCharacterStream(String parameterName, Reader value)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setClob(String parameterName, Reader reader)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setBlob(String parameterName, InputStream inputStream)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public void setNClob(String parameterName, Reader reader)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");

	}

	@Override
	public <T> T getObject(int parameterIndex, Class<T> type)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public <T> T getObject(String parameterName, Class<T> type)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

}
