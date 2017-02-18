package monolith52.comprompt.livetube;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import monolith52.comprompt.util.SafeConnection;

public class IdDetector {
	
	public final static String HOST = "livetube.cc";
	public final static String ENCODING = "UTF-8";
	public final static Pattern IDPATTERN = Pattern.compile("<meta itemprop=\"image\" content=\"/stream/([a-z0-9]+)\\.snapshot\\.jpg\">");
	protected String url;
	
	public IdDetector(String url) {
		this.url = url;
	}
	
	public String detect() throws IOException {
		String response = SafeConnection.getPage(url, HOST, ENCODING);
		Matcher match = IDPATTERN.matcher(response);
		if(!match.find()) {
			throw new IOException("invalid contents");
		}
		return match.group(1);
	}
}
