package ru.gb.jt.network;

import java.net.Socket;

public interface ServerSocketThreadListener {
    void onServerTimeout();
    void onSocketAccepted(Socket socket);
    void onServerException(ServerSocketThread thread, Throwable exception);
}
