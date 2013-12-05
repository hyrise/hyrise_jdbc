package org.hyrise.jdbc.helper;

import org.codehaus.jackson.JsonNode;


public class HyriseResultException extends Exception {

	private static final String ERROR_FIELD = "error";
	private static final long serialVersionUID = -5234762193008394570L;

	private static String transformNode(JsonNode root) {
		StringBuffer b = new StringBuffer();
		for(int i=0; i < root.get(ERROR_FIELD).size(); ++i) {
			b.append(root.get(ERROR_FIELD).get(i));
			b.append("\n");
		}
		return b.toString();
	}
	
	public HyriseResultException(JsonNode root) {
		super(HyriseResultException.transformNode(root));
	}
	
}
