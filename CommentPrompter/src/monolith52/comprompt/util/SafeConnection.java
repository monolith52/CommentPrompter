package monolith52.comprompt.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class SafeConnection {
	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
	
	public static String getPage(String url, String hostbase, String encoding) throws IOException {
		URL urlobj;
		try {
			urlobj = new URL(url);
		} catch (MalformedURLException e) {
			throw new IOException(e);
		}
		
		if (urlobj == null || 
				urlobj.getHost() == null || !urlobj.getHost().endsWith(hostbase)) {
			assert false: "invalid url";
		}
		
		// ループした場合の攻撃防止
		try {Thread.sleep(200);} catch (InterruptedException e) {}
		System.out.println("HTTP GET: " + url);
		
		HttpURLConnection con = (HttpURLConnection)urlobj.openConnection();
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.connect();
		
		if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
			assert false: "http error: " + con.getResponseCode();
		}
		
		return IOUtils.toString(con.getInputStream(), encoding);
	}
}
