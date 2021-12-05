package br.ufpa.linc.MetriX.dao.prevayler;

import java.io.IOException;

import org.prevayler.Prevayler;

public class SnapshotTimer extends Thread {

	Prevayler prevayler;
	private boolean running;

	public SnapshotTimer(Prevayler prevayler) {
		this.prevayler = prevayler;
		this.running = true;
	}

	public void stopTimer() {
		this.running = false;
	}

	public void run() {
		super.run();

		try {
			while (running) {
				Thread.sleep(1000 * 5);
				prevayler.takeSnapshot();
				// System.out.println("Snapshot disparado as " + new
				// java.util.Date() + "...");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}