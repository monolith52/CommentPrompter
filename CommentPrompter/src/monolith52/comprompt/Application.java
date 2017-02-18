package monolith52.comprompt;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import monolith52.comprompt.livetube.CommentChecker;
import monolith52.comprompt.livetube.IdDetector;

public class Application extends JFrame {
	private static final long serialVersionUID = 1L;
	public Application() {
	}
	
	public void init() {
		setTitle("Comment Prompter");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		Application app = new Application();
		CommentView commentView = new CommentView();

		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				app.init();
				app.add(commentView);
				new Thread(commentView).start();
			}
		});

		String id = null;
		try {
			IdDetector detector = new IdDetector("http://livetube.cc/YuiTomo/YuiTomo%E3%81%AETheWildEight(3)");
			id = detector.detect();
			CommentChecker checker = new CommentChecker(id);
			checker.addCommentFoundListener(commentView);
			checker.run();
		} catch (IOException e) {
			assert false: "id detection failed.";
		}
	}

}
