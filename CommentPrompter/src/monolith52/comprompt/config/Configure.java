package monolith52.comprompt.config;

import java.awt.Font;
import java.io.File;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;

import monolith52.comprompt.Application;
import monolith52.comprompt.ApplicationModel;
import monolith52.comprompt.util.ColorUtil;
import monolith52.comprompt.view.CommentViewModel;

public class Configure {
	
	protected final File CONFIG_FILE = new File("config.xml");
	
	ApplicationModel apm;
	CommentViewModel cvm;
	
	public CommentViewModel getCommentViewModel() {
		return cvm;
	}
	
	public ApplicationModel getApplicationModel() {
		return apm;
	}
	
	public Configure(Application app) {
		apm = new ApplicationModel(app);
		cvm = new CommentViewModel();
	}

	public void save() {
		try {
			Configurations configs = new Configurations();
			FileBasedConfigurationBuilder<XMLConfiguration> builder = configs.xmlBuilder(CONFIG_FILE);
			XMLConfiguration config = builder.getConfiguration();
			
			config.setProperty("fontFamily"	, cvm.getFont().getFamily());
			config.setProperty("fontStyle"	, cvm.getFont().getStyle());
			config.setProperty("fontSize"	, cvm.getFont().getSize());
			config.setProperty("fontColor"	, ColorUtil.toString(cvm.getFontColor()));
			config.setProperty("bgColor"	, ColorUtil.toString(cvm.getBgColor()));
			
			config.setProperty("windowWidth", apm.getWindowWidth());
			config.setProperty("windowHeight", apm.getWindowHeight());
			
			builder.save();
		} catch (org.apache.commons.configuration2.ex.ConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public void load() {
		XMLConfiguration config = null;
		try {
			Configurations configs = new Configurations();
			config = configs.xml(CONFIG_FILE);
		} catch (org.apache.commons.configuration2.ex.ConfigurationException e) {
			e.printStackTrace();
		}
		if (config == null) return;
		
		cvm.setFont(new Font(
				config.getString("fontFamily", cvm.getFont().getFamily()),
				config.getInt("fontStyle", cvm.getFont().getStyle()),
				config.getInt("fontSize", cvm.getFont().getSize())));
		cvm.setFontColor(
				ColorUtil.parseColor(
						config.getString("fontColor", ColorUtil.toString(cvm.getFontColor())), 
						cvm.getFontColor()));
		cvm.setBgColor(
				ColorUtil.parseColor(
						config.getString("bgColor", ColorUtil.toString(cvm.getBgColor())), 
						cvm.getBgColor()));
		
		apm.setWindowWidth(config.getInt("windowWidth", apm.getWindowWidth()));
		apm.setWindowHeight(config.getInt("windowHeight", apm.getWindowHeight()));
	}
}
