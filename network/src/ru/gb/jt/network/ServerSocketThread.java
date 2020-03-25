package ru.gb.jt.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServerSocketThread extends Thread {
    private final int port;
    private final int timeout;
    private final ServerSocketThreadListener listener;

    public ServerSocketThread(ServerSocketThreadListener listener, String name, int port, int timeout) {
        super(name);
        this.port = port;
        this.timeout = timeout;
        this.listener = listener;
        start();
    }

    @Override
    public void run() {
        try (ServerSocket server = new ServerSocket(port)) {
            server.setSoTimeout(timeout);
            while (!isInterrupted()) {
                Socket socket;
                try {
                    socket = server.accept();
                } catch (SocketTimeoutException e) {
                    listener.onServerTimeout();
                    continue;
                }
                listener.onSocketAccepted(socket);
            }
        } catch (IOException e) {
            listener.onServerException(this, e);
        }
    }
}
