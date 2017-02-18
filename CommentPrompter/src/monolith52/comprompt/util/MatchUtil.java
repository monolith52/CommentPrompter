package monolith52.comprompt.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchUtil {
	
	public static int findInteger1(Pattern pattern, String input) {
		Matcher matcher = pattern.matcher(input);
		if (!matcher.find() || matcher.groupCount() < 1) {
			return 0;
		}
		return Integer.parseInt(matcher.group(1));
	}

	public static String findGroup1(Pattern pattern, String input) {
		Matcher matcher = pattern.matcher(input);
		if (!matcher.find() || matcher.groupCount() < 1) {
			return null;
		}
		return matcher.group(1);
	}
}
