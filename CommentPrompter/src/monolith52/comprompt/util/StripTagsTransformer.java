package monolith52.comprompt.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StripTagsTransformer {
	protected final static Pattern PATTERN_BR_TAGS = Pattern.compile("<\\s*[bB][rR]\\s*/?\\s*>");
	protected final static Pattern PATTERN_TAGS = Pattern.compile("<[^>]*>");
	
	public static String stripTags(String input) {
		return PATTERN_TAGS.matcher(PATTERN_BR_TAGS.matcher(input).replaceAll(" ")).replaceAll("");
	}
	
}
