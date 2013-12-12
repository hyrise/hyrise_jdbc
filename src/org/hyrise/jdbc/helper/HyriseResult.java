package org.hyrise.jdbc.helper;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hyrise.jdbc.HyriseDriver;

public class HyriseResult {

	final static Logger logger = Logger.getLogger(HyriseDriver.LOGGER_NAME);

	JsonNode root;

	HashMap<String, Integer> nameOffset = null;
	ArrayList<String> header = null;

	long numRows = -1;

	public HyriseResult(String data) throws JsonProcessingException,
			IOException, HyriseResultException {
		this(data, false);
	}

	public HyriseResult(InputStream inputStream, boolean forget)
			throws JsonProcessingException, IOException, HyriseResultException {
		if (!forget) {
			ObjectMapper om = new ObjectMapper();
			root = om.readTree(inputStream);

			logger.log(Level.FINEST, root.toString());

			// If we have an error throw away
			if (root.get("error") != null) {
				throw new HyriseResultException(root);
			}
		}
	}

	public HyriseResult(String data, boolean forget)
			throws JsonProcessingException, IOException, HyriseResultException {

		if (!forget) {
			ObjectMapper om = new ObjectMapper();
			root = om.readTree(data);

			logger.log(Level.FINEST, root.toString());

			// If we have an error throw away
			if (root.get("error") != null) {
				throw new HyriseResultException(root);
			}

		}
	}

	/**
	 * 
	 * @param label
	 * @return
	 */
	public int getColumnIndex(String label) {
		if (header == null)
			getHeader();

		return nameOffset.get(label);
	}

	public int affectedRows() {
		if (root == null)
			return 1;

		return root.path("affectedRows").getIntValue();
	}

	public int numRows() {
		if (numRows == -1) {
			if (root.get("rows") != null)
				numRows = root.path("rows").size();
			else
				numRows = 0;
		}
		return (int) numRows;
	}

	public Integer getIntegerFrom(int row, int col) {
		return root.get("rows").get(row).get(col).getIntValue();
	}

	public String getStringFrom(int row, int col) {
		return root.get("rows").get(row).get(col).toString();
	}

	public String getStringFrom(int row, String col) {
		if (header == null)
			getHeader();
		return root.get("rows").get(row).get(nameOffset.get(col)).toString();
	}

	public boolean getBoolFrom(int row, int col) {
		return root.get("rows").get(row).get(col).getBooleanValue();
	}

	public double getDoubleFrom(int row, int col) {
		return root.get("rows").get(row).get(col).getDoubleValue();
	}

	public Long getLongFrom(int row, int col) {
		return root.get("rows").get(row).get(col).getLongValue();
	}

	public BigDecimal getDecimalFrom(int row, int col) {
		return root.get("rows").get(row).get(col).getDecimalValue();
	}

	public long getSessionContext() {
		if (!isRunningTX())
			return 0;
		else
			return root.get("session_context").getLongValue();
	}

	public boolean isRunningTX() {
		return root.get("session_context") != null;
	}

	public ArrayList<String> getHeader() {

		if (header != null) {
			return header;
		}

		ArrayList<String> result = new ArrayList<String>();
		nameOffset = new HashMap<String, Integer>();

		int offset = 0;
		for (Iterator<JsonNode> iterator = root.get("header").iterator(); iterator
				.hasNext();) {
			JsonNode node = (JsonNode) iterator.next();
			result.add(node.getTextValue().trim());
			nameOffset.put(node.getTextValue().trim(), offset++);
		}

		header = result;
		return header;
	}

}
