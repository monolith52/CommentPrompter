package monolith52.comprompt.livetube;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import monolith52.comprompt.Comment;
import monolith52.comprompt.util.MatchUtil;
import monolith52.comprompt.util.SafeConnection;

public class CommentChecker implements Runnable {

	public final static String HOST = "livetube.cc";
	public final static String ENCODING = "UTF-8";
	
	protected final static String DELIMITER_ENTRY	= "<div style=\"background-color: #eee;\">";
	protected final static Pattern PATTERN_NUMBER 	= Pattern.compile("\r\n([0-9]+) : ");
	protected final static Pattern PATTERN_NAME 	= Pattern.compile("<span style=\"font-size:1em;color:green; font-weight:bolder;\">(.+)</span>");
	protected final static Pattern PATTERN_DATETIME = Pattern.compile("<span style=\"\">([0-9]{1,2}/[0-9]{1,2} [0-9]{1,2}:[0-9]{2}:[0-9]{2})</span><br />");
	protected final static Pattern PATTERN_TEXT 	= Pattern.compile("<span style=\"font-weight:bold;margin-bottom:0px; padding-bottom:0px;\">(.*)</span><br clear=\"left\" />");
	
	protected String id;
	protected boolean isRunning = false;
	protected int currentNumber = 0;

	protected List<CommentFoundListener> commentFoundListeners = new ArrayList<CommentFoundListener>();
	
	public CommentChecker(String id) {
		this.id = id;
	}
	
	public void addCommentFoundListener(CommentFoundListener listener) {
		commentFoundListeners.add(listener);
	}
	
	public void removeCommentFoundListener(CommentFoundListener listener) {
		commentFoundListeners.remove(listener);
	}
	
	private String getTargetUrl(String id) {
		String url = "http://livetube.cc/stream/" + id + ".comments";
		if (currentNumber > 0) {
			url += "." + currentNumber;
		}
		return url;
	}
	
	private Comment parseComment(String response) {
		Comment comment = new Comment();
		comment.setNumber(MatchUtil.findInteger1(PATTERN_NUMBER, response));
		comment.setName(MatchUtil.findGroup1(PATTERN_NAME, response));
		comment.setDatetime(MatchUtil.findGroup1(PATTERN_DATETIME, response));
		comment.setText(MatchUtil.findGroup1(PATTERN_TEXT, response));
		return comment;
	}
	
	private List<Comment> parseComments(String response) {
		List<Comment> comments = new ArrayList<Comment>();
		
		// エントリーごとに分割
		Stream<String> entries = Arrays.stream(response.split(DELIMITER_ENTRY));
		
		// 要素の抜き出し
		// 先頭にはエントリが含まれないので１件スキップしている
		entries.skip(1).forEach(entry -> {
			comments.add(parseComment(entry));
		});
		
		return comments;
	}
	
	protected void updateCurrentNumber(List<Comment> comments) {
		if (comments == null || comments.size() == 0) return;
		
		Optional<Comment> latest = comments.stream().max(Comparator.comparingInt(comment -> comment.getNumber()));
		if (latest.isPresent()) {
			currentNumber = Math.max(currentNumber, latest.get().getNumber());
			System.out.println("Current number is updated to: " + currentNumber);
		}
	}

	@Override
	public void run() {
		isRunning = true;
		while (isRunning) {
			try {
				String response = SafeConnection.getPage(getTargetUrl(id), HOST, ENCODING);
				if (!isRunning) break;
				
				List<Comment> comments = parseComments(response);
				updateCurrentNumber(comments);
				commentFoundListeners.forEach(listener -> listener.commentFound(comments));
				
				try {Thread.sleep(3000);} catch (InterruptedException e) {}
			} catch (IOException e) {
				try {Thread.sleep(500);} catch (InterruptedException ee) {}
				e.printStackTrace();
				stop();
			}
		}
		
		System.out.println("Stopped checker for: " + id);
	}
	
	public void stop() {
		System.out.println("Stopping checker for: " + id);
		isRunning = false;
	}
}
