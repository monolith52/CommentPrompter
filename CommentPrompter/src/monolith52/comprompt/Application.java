package monolith52.comprompt;

import java.awt.Cursor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;

import monolith52.comprompt.config.ApplicationMenu;
import monolith52.comprompt.config.Configure;
import monolith52.comprompt.livetube.CommentChecker;
import monolith52.comprompt.livetube.IdDetector;

public class Application extends JFrame {
	private static final long serialVersionUID = 1L;
	protected static DataFlavor IMPORTABLE_FLAVOR = new DataFlavor(String.class, "text/plain");
	
	Configure config;
	ApplicationMenu menu;
	CommentView commentView;
	CommentChecker commentChecker;
	String targetUrl;
	Object targetUrlLock = new Object();
	
	public Application() {
	}
	
	public void init() {
		// 設定ファイルからウィンドウyサイズを読めなかった場合のデフォルトサイズ指定
		setSize(400, 300);
		
		config = new Configure(this);
		config.load();
		
		setTitle("Comment Prompter");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowHandler());
		setVisible(true);
		
		commentView = new CommentView(config.getCommentViewModel());
		add(commentView);
		new Thread(commentView).start();

		menu = new ApplicationMenu(config);
		menu.init();
		setJMenuBar(menu);

		setTransferHandler(new DropHandler());
	}
	
	protected class WindowHandler extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent event) {
			// アプリケーション終了前にウインドウサイズを設定ファイルに保存する
			config.save();
			
			super.windowClosing(event);
		}
	}
	
	protected class DropHandler extends TransferHandler {
		private static final long serialVersionUID = 1L;
		
		protected Object importUrlText(TransferSupport support) {
			try {
				return support.getTransferable().getTransferData(IMPORTABLE_FLAVOR);
			} catch (UnsupportedFlavorException | IOException e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		public boolean canImport(TransferSupport support) {
			return Arrays.stream(support.getDataFlavors()).anyMatch(f -> IMPORTABLE_FLAVOR.equals(f));
		}

		@Override
		public boolean importData(TransferSupport support) {
			Object data = importUrlText(support);
			if (data == null || !(data instanceof String)) {
				System.out.println("invalid import data flavors: " + data.getClass().getName());
				return false;
			}
			
			System.out.println("Accept import data: " + data);
			final String url = (String)data;
			new Thread(new Runnable(){
				@Override
				public void run() {
					startTask(url);
				}
			}).start();
			return true;
		}
	}
	
	public void startTask(String url) {
		
		// TODO: 同じURLでの連投をはじいているがユーザに分かりにくい　他の方法をに変更するべき
		synchronized (targetUrlLock) {
			if (url.equals(targetUrl)) return;
			targetUrl = url;
			if (commentChecker != null) {
				commentChecker.stop();
				commentChecker = null;
			}
		}
		
		try {
			// 画面表示をクリア
			commentView.reset();
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			// IDを取得
			IdDetector detector = new IdDetector(url);
			String id = detector.detect();
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			
			// コメントの取得
			// すでにGUIスレッドではないのでコメントの取得は別スレッドでstartする必要がない
			synchronized (targetUrlLock) {
				if (!url.equals(targetUrl)) return;
				commentChecker = new CommentChecker(id);
			}
			commentChecker.addCommentFoundListener(commentView);
			commentChecker.run();
			
		} catch (IOException e) {
			assert false: "id detection failed.";
		}

	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				Application app = new Application();
				app.init();
			}
		});
	}

}
