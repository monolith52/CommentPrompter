package monolith52.comprompt.monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import monolith52.comprompt.Comment;
import monolith52.comprompt.livetube.CommentFoundListener;

public class TcpMonitor implements Runnable {

	protected final static String ENCODING = "UTF-8";
	protected final static int LENGTH_LIMIT = 1024;

	private boolean running = false;
	protected int port;
	private ServerSocket server;
	List<Reciever> recievers = new ArrayList<Reciever>();
	List<CommentFoundListener> foundListeners = new ArrayList<CommentFoundListener>();
	
	public TcpMonitor(int port) {
		this.port = port;
	}
	
	public void addFoundListneer(CommentFoundListener listener) {
		foundListeners.add(listener);
	}
	
	@Override
	public void run() {
		running = true;
		try {
			server = new ServerSocket(port, 0, InetAddress.getLoopbackAddress());
		} catch (IOException e) {
			// TODO: リスナーで通知する必要あり
			e.printStackTrace();
			assert true;
		}
		
		while (running) {
			try {
				System.out.println("started server for: " + port);
				Socket sock = server.accept();
				synchronized (recievers) {
					Reciever reciever = new Reciever(sock);
					recievers.add(reciever);
					new Thread(reciever).start();
				}
			} catch (SocketException e) {
				// TODO: closeが呼ばれた場合のみ安全に抜けていい
				e.printStackTrace();
			} catch (IOException e) {
				// TODO: リスナーで通知する必要あり
				e.printStackTrace();
				assert true;
			}
		}
		System.out.println("Stopped server for: " + port);
	}
	
	public void stop() {
		System.out.println("Stopping server for: " + port);
		running = false;
		try {
			if (server != null) server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		synchronized (recievers) {
			recievers.forEach(r -> r.stop());
		}
	}

	class Reciever implements Runnable {
		boolean running = false;
		Socket socket;
		
		public Reciever(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			running = true;
			
			if (checkRejection()) {
				System.out.println("Rejected reciever from: " + this);
			}
			
			try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), ENCODING))) {
				String line;
				while ((line = br.readLine()) != null) {
					if (!running) break;
					
					Comment comment = new Comment();
					comment.setText(line.substring(0, Math.min(line.length(), TcpMonitor.LENGTH_LIMIT)));
					foundListeners.forEach(l -> l.commentFound(Arrays.asList(new Comment[]{comment})));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Stopped reciever for: " + this);
		}
		
		protected boolean checkRejection() {
			// ローカルホストのみ接続可能
			return !InetAddress.getLoopbackAddress().equals(socket.getLocalAddress());
		}
		
		public void stop() {
			System.out.println("Stopping reciever for: " + this);
			running = false;
			try {
				if (socket != null) socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
