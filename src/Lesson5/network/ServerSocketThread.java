package Lesson5.network;

public class ServerSocketThread extends Thread {
    private int port;

    public ServerSocketThread(String name, int port) {
        super(name);
        this.port = port;
        start();
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            System.out.println("Server socket thread is working");
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                interrupt();
            }
        }
    }
}
