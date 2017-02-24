package monolith52.comprompt.config;

import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.io.IOUtils;

import monolith52.comprompt.Application;
import monolith52.comprompt.ApplicationModel;
import monolith52.comprompt.util.ColorUtil;
import monolith52.comprompt.view.CommentViewModel;
import monolith52.comprompt.view.ViewStyleFactory;

public class Configure {
	protected final String INITIAL_CONFIG_FILE = "/config.xml";
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
			
			config.setProperty("fontFamily", cvm.getFont().getFamily());
			config.setProperty("fontStyle", cvm.getFont().getStyle());
			config.setProperty("fontSize", cvm.getFont().getSize());
			config.setProperty("fontColor", ColorUtil.toString(cvm.getFontColor()));
			config.setProperty("bgColor", ColorUtil.toString(cvm.getBgColor()));
			config.setProperty("viewStyle", cvm.getViewStyle().getId());
			config.setProperty("antialias", cvm.isAntialias());
			
			config.setProperty("windowWidth", apm.getWindowWidth());
			config.setProperty("windowHeight", apm.getWindowHeight());
			config.setProperty("tcpServerPort", apm.getTcpServerPort());
			
			builder.save();
		} catch (org.apache.commons.configuration2.ex.ConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public void load() {
		if (!CONFIG_FILE.exists()) copyInitialFile();
		
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
		cvm.setViewStyle(
				ViewStyleFactory.getInstanceFor(
						config.getString("viewStyle", cvm.getViewStyle().getId()),
						cvm.getViewStyle()));
		cvm.setAntialias(config.getBoolean("antialias", cvm.isAntialias()));
		
		apm.setWindowWidth(config.getInt("windowWidth", apm.getWindowWidth()));
		apm.setWindowHeight(config.getInt("windowHeight", apm.getWindowHeight()));
		apm.setTcpServerPort(config.getInt("tcpServerPort", apm.getTcpServerPort()));
	}
	
	protected void copyInitialFile() {
		try (InputStream in = getClass().getResourceAsStream(INITIAL_CONFIG_FILE)) {
			try (OutputStream out = new FileOutputStream(CONFIG_FILE)) {
				IOUtils.copy(in, out);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
