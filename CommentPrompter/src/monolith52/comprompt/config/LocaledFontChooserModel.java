package monolith52.comprompt.config;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.SortedMap;
import java.util.stream.Stream;

import com.l2fprod.common.swing.FontChooserModel;
import com.l2fprod.common.swing.plaf.FontChooserUI;

public class LocaledFontChooserModel implements FontChooserModel {

	private static char LOCAL_TEST_CHARACTOR = 'あ';
	private static final int[] DEFAULT_FONT_SIZES = { 6, 8, 10, 11, 12, 14, 16, 18, 20, 24, 28, 32, 40, 44, 48, 52, 56,
			60, 64, 68, 72 };

	private final String[] fontFamilies;
	private final String[] charSets;
	private final String previewMessage;

	protected boolean isLocaledFont(String family) {
		Font font = new Font(family, Font.PLAIN, 12);
		return font.canDisplay(LOCAL_TEST_CHARACTOR);
	}
	
	public LocaledFontChooserModel() {
		// 日本語を表示可能なフォントファミリーのみをリストアップする
		Stream<String> families = Arrays.stream(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
		fontFamilies = families.filter(this::isLocaledFont).sorted().toArray(String[]::new);

		SortedMap<String, Charset> map = Charset.availableCharsets();
		charSets = new String[map.size()];
		int i = 0;
		for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); i++) {
			charSets[i] = (String) iter.next();
		}

		ResourceBundle bundle = ResourceBundle.getBundle(FontChooserUI.class.getName() + "RB");
		previewMessage = bundle.getString("FontChooserUI.previewText");
	}

	public String[] getFontFamilies(String charSetName) {
		return fontFamilies;
	}

	public int[] getDefaultSizes() {
		return DEFAULT_FONT_SIZES;
	}

	public String[] getCharSets() {
		return charSets;
	}

	public String getPreviewMessage(String charSetName) {
		return previewMessage;
	}

}
