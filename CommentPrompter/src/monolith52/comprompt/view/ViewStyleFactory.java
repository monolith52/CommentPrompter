package monolith52.comprompt.view;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ViewStyleFactory {
	final static ViewStyleFactory factory = new ViewStyleFactory();
	Map<String, String> directory = new HashMap<String, String>();
	
	private ViewStyleFactory() {
		directory.put(DefaultBottomViewStyle.ID, 	DefaultBottomViewStyle.class.getName());
		directory.put(DefaultTopViewStyle.ID, 		DefaultTopViewStyle.class.getName());
	}
	
	public static Map<String, String> getDirectory() {
		return factory.directory;
	}
	
	public static ViewStyle getInstanceFor(String key) {
		try {
			String name = factory.directory.get(key);
			if (name == null) return null;
			Class<?> klass = Class.forName(name);
			Constructor<?> constructor = klass.getConstructor();
			Object obj = constructor.newInstance();
			if (!(obj instanceof ViewStyle)) return null;
			return (ViewStyle)obj;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | 
				IllegalArgumentException | InvocationTargetException | 
				NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ViewStyle getInstanceFor(String key, ViewStyle defaultViewStyle) {
		ViewStyle viewStyle = getInstanceFor(key);
		return viewStyle != null ? viewStyle : defaultViewStyle;
	}
}
