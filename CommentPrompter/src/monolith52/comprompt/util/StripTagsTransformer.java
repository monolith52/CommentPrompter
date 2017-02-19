package monolith52.comprompt.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StripTagsTransformer {
	protected final static Pattern PATTERN_REFERENCE = Pattern.compile("&#(\\d+);|&#([\\da-fA-F]+);");
	protected final static Pattern PATTERN_BR_TAGS = Pattern.compile("<\\s*[bB][rR]\\s*/?\\s*>");
	protected final static Pattern PATTERN_TAGS = Pattern.compile("<[^>]*>");
	
	public static String decode(String str) {
	    Matcher matcher = PATTERN_REFERENCE.matcher(str);
	    StringBuffer sb = new StringBuffer();
	    Character buf;
	    while(matcher.find()){
	        if(matcher.group(1) != null){
	            buf = new Character(
	                      (char)Integer.parseInt(matcher.group(1)));
	        }else{
	            buf = new Character(
	                      (char)Integer.parseInt(matcher.group(2), 16));
	        }
	        matcher.appendReplacement(sb, buf.toString());
	    }
	    matcher.appendTail(sb);
	    return sb.toString();
	}
	
	public static String stripTags(String input) {
		return PATTERN_TAGS.matcher(PATTERN_BR_TAGS.matcher(input).replaceAll(" ")).replaceAll("");
	}
	
}
