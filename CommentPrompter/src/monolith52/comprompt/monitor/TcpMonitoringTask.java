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

import monolith52.comprompt.view.Entry;

public class TcpMonitoringTask extends MonitoringTaskImpl {

	protected final static String ENCODING = "UTF-8";
	protected final static int LENGTH_LIMIT = 1024;

	boolean running = false;
	protected int port;
	private ServerSocket server;
	List<Reciever> recievers = new ArrayList<Reciever>();
	
	public TcpMonitoringTask(int port) {
		this.port = port;
	}
	
	@Override
	public void run() {
		running = true;
		try {
			server = new ServerSocket(port, 0, InetAddress.getLoopbackAddress());
		} catch (IOException e) {
			error(e);
			return;
		}
		
		System.out.println("started server for: " + port);
		monitoringListeners.forEach(l -> l.monitoringStarted("TCP port " + port));
		while (running) {
			try {
				Socket sock = server.accept();
				synchronized (recievers) {
					Reciever reciever = new Reciever(sock);
					recievers.add(reciever);
					new Thread(reciever).start();
				}
			} catch (SocketException e) {
				// 明示的であれ外部要因であれ切断された場合ここから抜ける
				break;
			} catch (IOException e) {
				error(e);
				break;
			}
		}
		System.out.println("Stopped server for: " + port);
	}
	
	public void error(Exception e) {
		e.printStackTrace();
		monitoringListeners.forEach(l -> l.monitoringFailed(e.getMessage()));
		running = false;
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
			
			System.out.println("Started reciever from: " + this);
			try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), ENCODING))) {
				String line;
				while ((line = br.readLine()) != null) {
					if (!running) break;
					
					Entry entry = new Entry();
					entry.setText(line.substring(0, Math.min(line.length(), TcpMonitoringTask.LENGTH_LIMIT)));
					entryFoundListeners.forEach(l -> l.entriesFound(Arrays.asList(new Entry[]{entry})));
				}
			} catch (SocketException e) {
				// 明示的であれ外部要因であれ切断された場合ここから抜ける
			} catch (IOException e) {
				error(e);
			}
			
			synchronized(recievers) {
				recievers.remove(this);
			}
			System.out.println("Stopped reciever for: " + this);
		}
		
		protected boolean checkRejection() {
			// ローカルホストのみ接続可能
			return !InetAddress.getLoopbackAddress().equals(socket.getLocalAddress());
		}
		
		public void error(Exception e) {
			e.printStackTrace();
			monitoringListeners.forEach(l -> l.monitoringFailed(e.getMessage()));
			running = false;
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
