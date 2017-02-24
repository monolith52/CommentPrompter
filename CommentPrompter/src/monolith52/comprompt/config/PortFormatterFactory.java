package monolith52.comprompt.config;

import java.text.NumberFormat;

import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

class PortFormatterFactory extends DefaultFormatterFactory {
	private static final long serialVersionUID = 1L;
	private static final NumberFormatter numberFormatter = new NumberFormatter();
	
	public static AbstractFormatter getInstance() {
		numberFormatter.setValueClass(Integer.class);
	    ((NumberFormat) numberFormatter.getFormat()).setGroupingUsed(false);
	    return numberFormatter;
	}

	public PortFormatterFactory() {
		super(getInstance(), getInstance(), getInstance());
	}
}
