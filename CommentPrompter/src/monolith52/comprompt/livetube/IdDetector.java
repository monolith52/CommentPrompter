package monolith52.comprompt.livetube;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import monolith52.comprompt.util.SafeConnection;

public class IdDetector {
	
	public final static String HOST = "livetube.cc";
	public final static String ENCODING = "UTF-8";
	public final static Pattern IDPATTERN = Pattern.compile("stream_route: 'https?://[^/]+/stream/([a-z0-9]+)\\.[^.]+'");
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

	public static void main(String[] args) {
		IdDetector detector = new IdDetector("http://livetube.cc/%E3%81%97%E3%81%B6%E3%82%8A%E3%82%80/%E3%81%B3%E3%81%8A%E3%81%97%E3%82%87%EF%BD%83%EF%BD%8B%E3%80%80%E5%88%9D%E8%A6%8B(2)");
		try {
			String id = detector.detect();
			System.out.println("Livetube id detected: " + id);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
