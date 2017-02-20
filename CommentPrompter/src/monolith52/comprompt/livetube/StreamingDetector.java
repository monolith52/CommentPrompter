package monolith52.comprompt.livetube;

import java.io.IOException;
import java.util.regex.Pattern;

import monolith52.comprompt.util.MatchUtil;
import monolith52.comprompt.util.SafeConnection;

public class StreamingDetector {
	
	protected final static String HOST = "livetube.cc";
	protected final static String ENCODING = "UTF-8";
	protected final static Pattern ID_PATTERN = Pattern.compile("<meta itemprop=\"image\" content=\"/stream/([a-z0-9]+)\\.snapshot\\.jpg\">");
	protected final static Pattern TITLE_PATTERN = Pattern.compile("<title>Livetube.cc : ([^<]+)</title>");
	protected String url;
	
	public StreamingDetector(String url) {
		this.url = url;
	}
	
	public Streaming detect() throws IOException {
		Streaming streaming = new Streaming();
		
		String response = SafeConnection.getPage(url, HOST, ENCODING);
		
		String id = MatchUtil.findGroup1(ID_PATTERN, response);
		if (id == null) {
			throw new IOException("invalid contents");
		}
		streaming.setId(id);
		streaming.setTitle(MatchUtil.findGroup1(TITLE_PATTERN, response));
		
		return streaming;
	}
}
