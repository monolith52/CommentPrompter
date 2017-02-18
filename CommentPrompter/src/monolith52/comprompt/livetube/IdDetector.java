package monolith52.comprompt.livetube;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import monolith52.comprompt.util.SafeConnection;

public class IdDetector {
	
	public final static String HOST = "livetube.cc";
	public final static String ENCODING = "UTF-8";
	public final static Pattern IDPATTERN = Pattern.compile("stream_route: 'http://[^/]+/stream/([a-z0-9]+).f4m'");
	protected String url;
	
	public IdDetector(String url) {
		this.url = url;
	}
	
	public String detect() throws IOException {
		String id = null;
		
		String response = SafeConnection.getPage(url, HOST, ENCODING);
		Matcher match = IDPATTERN.matcher(response);
		if(!match.find()) {
			throw new IOException("invalid contents");
		}
		return match.group(1);
	}

	public static void main(String[] args) {
		IdDetector detector = new IdDetector("http://livetube.cc/SaltyLitchi/%E9%85%8D%E4%BF%A1%E5%A4%96%E9%85%8D%E4%BF%A1(24)");
		try {
			String id = detector.detect();
			System.out.println("Livetube id detected: " + id);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
