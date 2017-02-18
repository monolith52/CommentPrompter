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
			IdDetector detector = new IdDetector("http://livetube.cc/%E3%81%97%E3%81%B6%E3%82%8A%E3%82%80/%E3%81%B3%E3%81%8A%E3%81%97%E3%82%87%EF%BD%83%EF%BD%8B%E3%80%80%E5%88%9D%E8%A6%8B(2)");
			id = detector.detect();
			CommentChecker checker = new CommentChecker(id);
			checker.addCommentFoundListener(commentView);
			checker.run();
		} catch (IOException e) {
			assert false: "id detection failed.";
		}
	}

}
