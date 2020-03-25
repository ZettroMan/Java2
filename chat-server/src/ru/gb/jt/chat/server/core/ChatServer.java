package ru.gb.jt.chat.server.core;

import ru.gb.jt.network.ServerSocketThread;
import ru.gb.jt.network.ServerSocketThreadListener;
import ru.gb.jt.network.SocketThread;
import ru.gb.jt.network.SocketThreadListener;

import java.net.Socket;
import java.util.Vector;

public class ChatServer implements ServerSocketThreadListener, SocketThreadListener {

    ServerSocketThread server;
    Vector<SocketThread> clientThreads = new Vector<>();

    public void start(int port) {
        if (server == null || !server.isAlive()) {
            System.out.println("Server started at port: " + port);
            server = new ServerSocketThread(this, "Server", port, 2000);
        } else {
            System.out.println("Server already started!");
        }
    }

    public void stop() {
        if (server != null && server.isAlive()) {
            System.out.println("Server stopped");
            server.interrupt(); //null.interrupt();
        } else {
            System.out.println("Server is not running");
        }
    }

    private void putLog(String msg) {
        System.out.println(msg);
    }

    /**
     * Server Socket Thread methods
     */

    @Override
    public void onServerTimeout() {
    }

    @Override
    public void onSocketAccepted(Socket socket) {
        putLog("Client connected");
        String name = "Socket Thread " + socket.getInetAddress() + ":" + socket.getPort();
        System.out.println("Socket accepted: " + socket.getInetAddress() + ":" + socket.getPort());
        clientThreads.add(new SocketThread(this, name, socket));
    }

    @Override
    public void onServerException(ServerSocketThread thread, Throwable exception) {
        exception.printStackTrace();
    }

    /**
     * Socket Thread methods
     */

    @Override
    public void onReceiveString(String msg) {
        for (SocketThread t : clientThreads) {
            if (t.isAlive()) {
                t.sendMessage(msg);
            }
        }
    }

    @Override
    public void onSocketException(SocketThread thread, Exception exception) {
        clientThreads.remove(thread);
        System.out.println("Client disconnected.");
        //exception.printStackTrace();
    }
}
