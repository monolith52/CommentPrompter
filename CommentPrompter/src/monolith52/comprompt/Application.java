package monolith52.comprompt;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import monolith52.comprompt.livetube.CommentChecker;
import monolith52.comprompt.livetube.IdDetector;

public class Application extends JFrame {
	private static final long serialVersionUID = 1L;

	public Application() {
		setTitle("Comment Prompter");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				new Application();
				
				String id = null;
				try {
					IdDetector detector = new IdDetector("http://livetube.cc/kaike/%E4%B8%89%E5%9B%BD%E5%BF%9713%EF%BC%B0%EF%BC%AB%E5%8A%89%E5%BA%A6");
					id = detector.detect();
					
					CommentChecker checker = new CommentChecker(id);
					new Thread(checker).start();
				} catch (IOException e) {
					assert false: "id detection failed.";
				}
				
			}
		});
		
	}

}
