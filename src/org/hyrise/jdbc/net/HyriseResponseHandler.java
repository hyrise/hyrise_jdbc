/**
 * 
 */
package org.hyrise.jdbc.net;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.hyrise.jdbc.helper.HyriseResult;
import org.hyrise.jdbc.helper.HyriseResultException;

/**
 * @author grund
 *
 */
public class HyriseResponseHandler implements ResponseHandler<HyriseResult> {

	boolean discard = false;
	
	public HyriseResponseHandler() {
	}
	
	public HyriseResponseHandler(boolean d) {
		discard = d;
	}
	
	@Override
	public HyriseResult handleResponse(HttpResponse resp) throws ClientProtocolException,
			IOException {
		
		if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			try {
				return new HyriseResult(resp.getEntity().getContent(), discard);
			} catch (IllegalStateException | HyriseResultException e) {
				e.printStackTrace();
			}
		} else
			System.err.println(resp.getStatusLine()
					.getReasonPhrase());
		return null;
	}

}
