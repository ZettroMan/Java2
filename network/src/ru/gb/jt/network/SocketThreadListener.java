package ru.gb.jt.network;

public interface SocketThreadListener {
    void onReceiveString(String msg);
    void onSocketException(SocketThread thread, Exception exception);
}
