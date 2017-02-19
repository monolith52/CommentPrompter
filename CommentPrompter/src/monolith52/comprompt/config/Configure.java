package monolith52.comprompt.config;

import java.awt.Font;
import java.io.File;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;

import monolith52.comprompt.CommentViewModel;
import monolith52.comprompt.util.ColorUtil;

public class Configure {
	
	protected final File CONFIG_FILE = new File("config.xml");
	
	CommentViewModel commentViewModel = new CommentViewModel();
	
	public CommentViewModel getCommentViewModel() {
		return commentViewModel;
	}

	public void save() {
		try {
			Configurations configs = new Configurations();
			FileBasedConfigurationBuilder<XMLConfiguration> builder = configs.xmlBuilder(CONFIG_FILE.getAbsolutePath());
			XMLConfiguration config = builder.getConfiguration();
			config.setProperty("fontFamily"	, commentViewModel.getFont().getFamily());
			config.setProperty("fontStyle"	, commentViewModel.getFont().getStyle());
			config.setProperty("fontSize"	, commentViewModel.getFont().getSize());
			config.setProperty("fontColor"	, ColorUtil.toString(commentViewModel.getFontColor()));
			config.setProperty("bgColor"	, ColorUtil.toString(commentViewModel.getBgColor()));
			builder.save();
		} catch (org.apache.commons.configuration2.ex.ConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public void load() {
		XMLConfiguration config = null;
		try {
			Configurations configs = new Configurations();
			config = configs.xml(CONFIG_FILE.getAbsolutePath());
		} catch (org.apache.commons.configuration2.ex.ConfigurationException e) {
			e.printStackTrace();
		}
		if (config == null) return;
		
		final CommentViewModel cvm = commentViewModel;
		cvm.setFont(new Font(
				config.getString("fontFamily", cvm.getFont().getFamily()),
				config.getInt("fontStyle", cvm.getFont().getStyle()),
				config.getInt("fontSize", cvm.getFont().getSize())));
		cvm.setFontColor(
				ColorUtil.parseColor(config.getString("fontColor",
						ColorUtil.toString(cvm.getFontColor())), cvm.getFontColor()));
		cvm.setBgColor(
				ColorUtil.parseColor(config.getString("bgColor",
						ColorUtil.toString(cvm.getBgColor())), cvm.getBgColor()));
	}
}
