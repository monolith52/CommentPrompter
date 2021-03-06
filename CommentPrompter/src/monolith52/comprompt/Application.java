package monolith52.comprompt;

import java.awt.Cursor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;

import monolith52.comprompt.config.ApplicationMenu;
import monolith52.comprompt.config.Configure;
import monolith52.comprompt.livetube.Streaming;
import monolith52.comprompt.livetube.StreamingListener;
import monolith52.comprompt.livetube.StreamingTask;
import monolith52.comprompt.monitor.MonitoringListener;
import monolith52.comprompt.monitor.MonitoringTask;
import monolith52.comprompt.view.CommentView;

public class Application extends JFrame {
	private static final long serialVersionUID = 1L;
	
	protected final static File LAUNCH_LOCK_FILE = new File(".CommentPrompter.lock");
	protected final static DataFlavor IMPORTABLE_FLAVOR = new DataFlavor(String.class, "text/plain");
	protected final static String TITLE = "Comment Prompter";
	protected final static String TITLE_SUCCESS = " > ";
	protected final static String TITLE_FAILED = " [停止中] ";
	
	FileChannel lockFileChannel;
	FileLock lockFileLock;
	Configure config;
	ApplicationMenu menu;
	CommentView commentView;
	MonitoringTask monitoringTask;
	Object monitoringTaskLock = new Object();
	
	public Application() {
		// 設定ファイルからウィンドウyサイズを読めなかった場合のデフォルトサイズ指定
		// ウインドウサイズは設定ファイルが読まれると自動的にに変更される
		setSize(ApplicationModel.DEFAULT_SIZE);
	}
	
	public void init() {
		
		config = new Configure(this);
		config.load();
		
		setTitle(TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowHandler());
		setVisible(true);
		
		commentView = new CommentView(config.getCommentViewModel());
		add(commentView);
		new Thread(commentView).start();

		menu = new ApplicationMenu(this, config);
		menu.init();
		setJMenuBar(menu);

		setTransferHandler(new DropHandler());
	}
	
	public boolean tryLockForLaunch() {
		try {
			lockFileChannel = FileChannel.open(LAUNCH_LOCK_FILE.toPath(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
			LAUNCH_LOCK_FILE.deleteOnExit();
			lockFileLock = lockFileChannel.tryLock();
			if (lockFileLock == null) return false;
			return true;
		} catch (IOException e) {
			try {
				if (lockFileChannel != null) lockFileChannel.close();
				return false;
			} catch (IOException ee) {
				return false;
			}
		}
	}
	
	public void unlockForLaunch() {
		try {
			if (lockFileLock != null) lockFileLock.release();
		} catch (IOException e) {}
		try {
		if (lockFileChannel != null) lockFileChannel.close();
		} catch (IOException e) {}
	}
	
	protected class WindowHandler extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent event) {
			// アプリケーション終了前にウインドウサイズを設定ファイルに保存する
			config.save();
			
			// 起動ロックを解除
			unlockForLaunch();
			
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
			startStreamingTask((String)data);
			return true;
		}
	}

	class SteamingHandler implements StreamingListener {
		@Override
		public void streamingDetected(Streaming streaming) {
			SwingUtilities.invokeLater(() -> {
				setTitle(formatTitle(streaming.getTitle(), false));
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			});
		}
		@Override
		public void detectionFailed(String msg) {
			SwingUtilities.invokeLater(() -> {
				setTitle(formatTitle(msg, true));
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			});
		}
	}
	
	class MonitoringHandler implements MonitoringListener {
		@Override
		public void monitoringStarted(String msg) {
			SwingUtilities.invokeLater(() -> {
				setTitle(formatTitle(msg, false));
			});
		}
		@Override
		public void monitoringFailed(String msg) {
			SwingUtilities.invokeLater(() -> {
				setTitle(formatTitle(msg, true));
			});
		}
	}
	
	protected String formatTitle(String msg, boolean isFailed) {
		StringBuffer buffer = new StringBuffer(TITLE);
		
		if (isFailed) return buffer.append(TITLE_FAILED).append(msg).toString();
		
		if (msg != null) {
			return buffer.append(TITLE_SUCCESS).append(msg).toString();
		}
		
		return buffer.toString();
	}
	
	public void startStreamingTask(String url) {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		StreamingTask task = new StreamingTask(url);
		task.addEntryFoundListener(commentView);
		task.addStreamingListener(new SteamingHandler());
		task.addMonitoringListener(new MonitoringHandler());
		synchronized (monitoringTaskLock) {
			if (monitoringTask != null) {
				monitoringTask.stop();
			}
			monitoringTask = task;		
			commentView.reset();
			new Thread(monitoringTask).start();
		}
	}
	
	public void startMonitoringTask(MonitoringTask task) {
		task.addEntryFoundListneer(commentView);
		task.addMonitoringListener(new MonitoringHandler());
		synchronized (monitoringTaskLock) {
			if (monitoringTask != null) {
				monitoringTask.stop();
			}
			monitoringTask = task;		
			commentView.reset();
			new Thread(monitoringTask).start();
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Application app = new Application();
			// 起動ロックを確保できなければ終了
			if (!app.tryLockForLaunch()) {
				System.out.println("Application exited due to double launching");
				return;
			}
			app.init();
			if (args.length > 0) app.startStreamingTask(args[0]);
		});
	}

}
