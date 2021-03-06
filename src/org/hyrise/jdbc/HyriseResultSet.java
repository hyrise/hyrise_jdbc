/**
 * 
 */
package org.hyrise.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringBufferInputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import org.hyrise.jdbc.helper.HyriseResult;
import org.hyrise.jdbc.helper.MethodNameInflection;

/**
 * @author grund
 * 
 */
public class HyriseResultSet implements ResultSet {

	HyriseStatement stmt;
	HyriseResult result = null;
	int cursor = -1;
	int size = 0;

	// Helper for last NUll Call
	boolean lastNull = false;

	public HyriseResultSet(HyriseResult r, HyriseStatement stmt) {
		this.stmt = stmt;
		result = r;
		cursor = -1;
		size = result.numRows();
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
	public boolean next() throws SQLException {
		if (++cursor < size)
			return true;
		else
			return false;
	}

	@Override
	public void close() throws SQLException {
		size = 0;
	}

	@Override
	public boolean wasNull() throws SQLException {
		return lastNull;
	}

	@Override
	public String getString(int columnIndex) throws SQLException {
		return result.getStringFrom(cursor, columnIndex);
	}

	@Override
	public boolean getBoolean(int columnIndex) throws SQLException {
		return result.getBoolFrom(cursor, columnIndex);
	}

	@Override
	public byte getByte(int columnIndex) throws SQLException {
		return result.getIntegerFrom(cursor, columnIndex).byteValue();
	}

	@Override
	public short getShort(int columnIndex) throws SQLException {
		return result.getIntegerFrom(cursor, columnIndex).shortValue();
	}

	@Override
	public int getInt(int columnIndex) throws SQLException {
		return result.getIntegerFrom(cursor, columnIndex);
	}

	@Override
	public long getLong(int columnIndex) throws SQLException {
		return result.getLongFrom(cursor, columnIndex);
	}

	@Override
	public float getFloat(int columnIndex) throws SQLException {
		return (float) result.getDoubleFrom(cursor, columnIndex);
	}

	@Override
	public double getDouble(int columnIndex) throws SQLException {
		return result.getDoubleFrom(cursor, columnIndex);
	}

	@Override
	public BigDecimal getBigDecimal(int columnIndex, int scale)
			throws SQLException {
		return result.getDecimalFrom(cursor, columnIndex);
	}

	@Override
	public byte[] getBytes(int columnIndex) throws SQLException {
		return getString(columnIndex).getBytes();
	}

	@Override
	public Date getDate(int columnIndex) throws SQLException {
		String s = getString(columnIndex);
		return Date.valueOf(s);
	}

	@Override
	public Time getTime(int columnIndex) throws SQLException {
		String s = getString(columnIndex);
		return Time.valueOf(s);
	}

	@Override
	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		return Timestamp.valueOf(getString(columnIndex));
	}

	@Override
	public InputStream getAsciiStream(int columnIndex) throws SQLException {
		return new StringBufferInputStream(getString(columnIndex));
	}

	@Override
	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public InputStream getBinaryStream(int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public String getString(String columnLabel) throws SQLException {
		return getString(result.getColumnIndex(columnLabel));
	}

	@Override
	public boolean getBoolean(String columnLabel) throws SQLException {
		return getBoolean(result.getColumnIndex(columnLabel));
	}

	@Override
	public byte getByte(String columnLabel) throws SQLException {
		return getByte(result.getColumnIndex(columnLabel));
	}

	@Override
	public short getShort(String columnLabel) throws SQLException {
		return getShort(result.getColumnIndex(columnLabel));
	}

	@Override
	public int getInt(String columnLabel) throws SQLException {
		return getInt(result.getColumnIndex(columnLabel));
	}

	@Override
	public long getLong(String columnLabel) throws SQLException {
		return getLong(result.getColumnIndex(columnLabel));
	}

	@Override
	public float getFloat(String columnLabel) throws SQLException {
		return getFloat(result.getColumnIndex(columnLabel));
	}

	@Override
	public double getDouble(String columnLabel) throws SQLException {
		return getDouble(result.getColumnIndex(columnLabel));
	}

	@Override
	public BigDecimal getBigDecimal(String columnLabel, int scale)
			throws SQLException {
		return getBigDecimal(result.getColumnIndex(columnLabel));
	}

	@Override
	public byte[] getBytes(String columnLabel) throws SQLException {
		return getBytes(result.getColumnIndex(columnLabel));
	}

	@Override
	public Date getDate(String columnLabel) throws SQLException {
		return getDate(result.getColumnIndex(columnLabel));
	}

	@Override
	public Time getTime(String columnLabel) throws SQLException {
		return getTime(result.getColumnIndex(columnLabel));
	}

	@Override
	public Timestamp getTimestamp(String columnLabel) throws SQLException {
		return getTimestamp(result.getColumnIndex(columnLabel));
	}

	@Override
	public InputStream getAsciiStream(String columnLabel) throws SQLException {
		return getAsciiStream(result.getColumnIndex(columnLabel));
	}

	@Override
	public InputStream getUnicodeStream(String columnLabel) throws SQLException {
		return getUnicodeStream(result.getColumnIndex(columnLabel));
	}

	@Override
	public InputStream getBinaryStream(String columnLabel) throws SQLException {
		return getBinaryStream(result.getColumnIndex(columnLabel));
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		return null;
	}

	@Override
	public void clearWarnings() throws SQLException {
	}

	@Override
	public String getCursorName() throws SQLException {
		return "HyriseResultSet";
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(int columnIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Object getObject(String columnLabel) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public int findColumn(String columnLabel) throws SQLException {
		return result.getColumnIndex(columnLabel);
	}

	@Override
	public Reader getCharacterStream(int columnIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Reader getCharacterStream(String columnLabel) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		return getBigDecimal(columnIndex, 10);
	}

	@Override
	public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
		return getBigDecimal(result.getColumnIndex(columnLabel));
	}

	@Override
	public boolean isBeforeFirst() throws SQLException {
		return cursor < 0;
	}

	@Override
	public boolean isAfterLast() throws SQLException {
		return cursor >= size;
	}

	@Override
	public boolean isFirst() throws SQLException {
		return cursor == 0;
	}

	@Override
	public boolean isLast() throws SQLException {
		return cursor + 1 == size;
	}

	@Override
	public void beforeFirst() throws SQLException {
		cursor = -1;
	}

	@Override
	public void afterLast() throws SQLException {
		cursor = size;
	}

	@Override
	public boolean first() throws SQLException {
		cursor = 0;
		return true;
	}

	@Override
	public boolean last() throws SQLException {
		cursor = size - 1;
		return true;
	}

	@Override
	public int getRow() throws SQLException {
		return cursor;
	}

	@Override
	public boolean absolute(int row) throws SQLException {
		if (row >= size)
			return false;

		cursor = row;
		return true;
	}

	@Override
	public boolean relative(int rows) throws SQLException {
		cursor += rows;
		return true;
	}

	@Override
	public boolean previous() throws SQLException {
		cursor--;

		return true;
	}

	@Override
	public void setFetchDirection(int direction) throws SQLException {
	}

	@Override
	public int getFetchDirection() throws SQLException {
		return ResultSet.FETCH_UNKNOWN;
	}

	@Override
	public void setFetchSize(int rows) throws SQLException {
	}

	@Override
	public int getFetchSize() throws SQLException {
		return Integer.MAX_VALUE;
	}

	@Override
	public int getType() throws SQLException {
		return ResultSet.TYPE_SCROLL_INSENSITIVE;
	}

	@Override
	public int getConcurrency() throws SQLException {
		return ResultSet.CONCUR_READ_ONLY;
	}

	@Override
	public boolean rowUpdated() throws SQLException {
		return true;
	}

	@Override
	public boolean rowInserted() throws SQLException {
		return true;
	}

	@Override
	public boolean rowDeleted() throws SQLException {
		return true;
	}

	@Override
	public void updateNull(int columnIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateByte(int columnIndex, byte x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateShort(int columnIndex, short x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateInt(int columnIndex, int x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateLong(int columnIndex, long x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateFloat(int columnIndex, float x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateDouble(int columnIndex, double x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateBigDecimal(int columnIndex, BigDecimal x)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateString(int columnIndex, String x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateBytes(int columnIndex, byte[] x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateDate(int columnIndex, Date x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateTime(int columnIndex, Time x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateTimestamp(int columnIndex, Timestamp x)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x, int length)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x, int length)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x, int length)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateObject(int columnIndex, Object x, int scaleOrLength)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateObject(int columnIndex, Object x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateNull(String columnLabel) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateBoolean(String columnLabel, boolean x)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateByte(String columnLabel, byte x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateShort(String columnLabel, short x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateInt(String columnLabel, int x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateLong(String columnLabel, long x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateFloat(String columnLabel, float x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateDouble(String columnLabel, double x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateBigDecimal(String columnLabel, BigDecimal x)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateString(String columnLabel, String x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateBytes(String columnLabel, byte[] x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateDate(String columnLabel, Date x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateTime(String columnLabel, Time x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateTimestamp(String columnLabel, Timestamp x)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x, int length)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x, int length)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader,
			int length) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateObject(String columnLabel, Object x, int scaleOrLength)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateObject(String columnLabel, Object x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void insertRow() throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateRow() throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void deleteRow() throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void refreshRow() throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void cancelRowUpdates() throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void moveToInsertRow() throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void moveToCurrentRow() throws SQLException {
	}

	@Override
	public Statement getStatement() throws SQLException {
		return stmt;
	}

	@Override
	public Object getObject(int columnIndex, Map<String, Class<?>> map)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Ref getRef(int columnIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Blob getBlob(int columnIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Clob getClob(int columnIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Array getArray(int columnIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Object getObject(String columnLabel, Map<String, Class<?>> map)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Ref getRef(String columnLabel) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Blob getBlob(String columnLabel) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Clob getClob(String columnLabel) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Array getArray(String columnLabel) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
		return getDate(columnIndex);
	}

	@Override
	public Date getDate(String columnLabel, Calendar cal) throws SQLException {
		return getDate(columnLabel);
	}

	@Override
	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		return getTime(columnIndex);
	}

	@Override
	public Time getTime(String columnLabel, Calendar cal) throws SQLException {
		return getTime(columnLabel);
	}

	@Override
	public Timestamp getTimestamp(int columnIndex, Calendar cal)
			throws SQLException {
		return getTimestamp(columnIndex);
	}

	@Override
	public Timestamp getTimestamp(String columnLabel, Calendar cal)
			throws SQLException {
		return getTimestamp(columnLabel);
	}

	@Override
	public URL getURL(int columnIndex) throws SQLException {
		try {
			return new URL(getString(columnIndex));
		} catch (MalformedURLException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public URL getURL(String columnLabel) throws SQLException {
		return getURL(result.getColumnIndex(columnLabel));
	}

	@Override
	public void updateRef(int columnIndex, Ref x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateRef(String columnLabel, Ref x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateBlob(int columnIndex, Blob x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateBlob(String columnLabel, Blob x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateClob(int columnIndex, Clob x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateClob(String columnLabel, Clob x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateArray(int columnIndex, Array x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateArray(String columnLabel, Array x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public RowId getRowId(int columnIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public RowId getRowId(String columnLabel) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public int getHoldability() throws SQLException {
		return ResultSet.CLOSE_CURSORS_AT_COMMIT;
	}

	@Override
	public boolean isClosed() throws SQLException {
		return false;
	}

	@Override
	public void updateNString(int columnIndex, String nString)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateNString(String columnLabel, String nString)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateNClob(String columnLabel, NClob nClob)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public NClob getNClob(int columnIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public NClob getNClob(String columnLabel) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateSQLXML(int columnIndex, SQLXML xmlObject)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateSQLXML(String columnLabel, SQLXML xmlObject)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public String getNString(int columnIndex) throws SQLException {
		return getString(columnIndex);
	}

	@Override
	public String getNString(String columnLabel) throws SQLException {
		return getString(columnLabel);
	}

	@Override
	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x, long length)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader,
			long length) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x, long length)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x, long length)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x, long length)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x, long length)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x,
			long length) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader,
			long length) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream, long length)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream,
			long length) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateClob(int columnIndex, Reader reader, long length)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateClob(String columnLabel, Reader reader, long length)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateNClob(int columnIndex, Reader reader, long length)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateNClob(String columnLabel, Reader reader, long length)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateClob(String columnLabel, Reader reader)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public void updateNClob(String columnLabel, Reader reader)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

	@Override
	public <T> T getObject(String columnLabel, Class<T> type)
			throws SQLException {
		throw new SQLException(MethodNameInflection.methodName()
				+ " not supported in this driver version!");
	}

}
