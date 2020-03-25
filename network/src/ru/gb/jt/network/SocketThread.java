package ru.gb.jt.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketThread extends Thread {

    private final Socket socket;
    private DataOutputStream out;
    private SocketThreadListener listener;

    public SocketThread(SocketThreadListener listener, String name, Socket socket) {
        super(name);
        this.socket = socket;
        this.listener = listener;
        start();
    }

    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            while (!isInterrupted()) {
                String msg = in.readUTF();
                listener.onReceiveString(msg);
            }
        } catch (IOException e) {
            if(!isInterrupted()) {
                listener.onSocketException(this, e);
            }
        }
    }

    public synchronized boolean sendMessage(String msg) {
        try {
            out.writeUTF(msg);
            out.flush();
            return true;
        } catch (IOException e) {
            listener.onSocketException(this, e);
            return false;
        }
    }

    public Socket getSocket() {
        return socket;
    }

}
