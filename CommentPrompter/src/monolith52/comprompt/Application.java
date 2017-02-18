package monolith52.comprompt;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;

import monolith52.comprompt.livetube.CommentChecker;
import monolith52.comprompt.livetube.IdDetector;

public class Application extends JFrame {
	private static final long serialVersionUID = 1L;
	
	CommentView commentView;
	CommentChecker commentChecker;
	String targetUrl;
	Object targetUrlLock = new Object();
	
	public Application() {
	}
	
	public void init() {
		setTitle("Comment Prompter");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		commentView = new CommentView();
		add(commentView);
		new Thread(commentView).start();

		setTransferHandler(new DropHandler());
	}
	
	protected class DropHandler extends TransferHandler {
		private static final long serialVersionUID = 1L;
		
		protected Object importUrlText(TransferSupport support) {
			DataFlavor flavor = new DataFlavor(String.class, "text/plain");
			try {
				return support.getTransferable().getTransferData(flavor);
			} catch (UnsupportedFlavorException | IOException e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		public boolean canImport(TransferSupport support) {
			DataFlavor flavor = new DataFlavor(String.class, "text/plain");
			return Arrays.stream(support.getDataFlavors()).anyMatch(f -> flavor.equals(f));
		}

		@Override
		public boolean importData(TransferSupport support) {
			Object data = importUrlText(support);
			String url = null;
			
			if (data != null && data instanceof String) url = (String)data;
			if (url == null) {
				System.out.println("invalid import data flavors: " + data.getClass().getName());
				return false;
			}
			
			System.out.println("Accept import data: " + url);
			final String target = url;
			new Thread(new Runnable(){
				@Override
				public void run() {
					start(target);
				}
			}).start();
			return true;
		}
	}
	
	public void start(String url) {
		synchronized (targetUrlLock) {
			if (url.equals(targetUrl)) return;
			targetUrl = url;
			if (commentChecker != null) {
				commentChecker.stop();
				commentChecker = null;
			}
		}
		
		try {
			IdDetector detector = new IdDetector(url);
			String id = detector.detect();
			synchronized (targetUrlLock) {
				if (!url.equals(targetUrl)) return;
				commentChecker = new CommentChecker(id);
				commentChecker.addCommentFoundListener(commentView);
			}
			
			// すでにGUIスレッドではないので別スレッドでstartする必要がない
			commentChecker.run();
		} catch (IOException e) {
			assert false: "id detection failed.";
		}

	}
	
	public static void main(String[] args) {
		Application app = new Application();

		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				app.init();
			}
		});
	}

}
