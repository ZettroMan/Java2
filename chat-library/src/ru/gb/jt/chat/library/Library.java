package ru.gb.jt.chat.library;

import java.util.List;

public class Library {
    /*
/auth_request±login±password
/auth_accept±nickname
/auth_error
/broadcast±msg
/msg_format_error±msg
/user_list±user1±user2±user3±....
* */
    public static final String DELIMITER = "±";
    public static final String AUTH_REQUEST = "/auth_request";
    public static final String AUTH_ACCEPT = "/auth_accept";
    public static final String AUTH_DENIED = "/auth_denied";
    public static final String MSG_FORMAT_ERROR = "/msg_format_error";
    // если мы вдруг не поняли, что за сообщение и не смогли разобрать
    public static final String TYPE_BROADCAST = "/bcast";
    // то есть сообщение, которое будет посылаться всем
    public static final String TYPE_BCAST_CLIENT = "/client_bcast";
    //TYPE_MCAST_CLIENT - мультикаст - только определенным пользователям
    public static final String TYPE_MCAST_CLIENT = "/client_mcast";
    public static final String USER_LIST = "/user_list";

    public static String getTypeBcastClient(String msg) {
        return TYPE_BCAST_CLIENT + DELIMITER + msg;
    }

    public static String getTypeMcastClient(String msg, List<String> users) {
        StringBuilder result = new StringBuilder(TYPE_MCAST_CLIENT + DELIMITER + msg);
        for(String usr: users) {
            result.append(DELIMITER);
            result.append(usr);
        }
        return result.toString();
    }

    public static String getUserList(String users) {
        return USER_LIST + DELIMITER + users;
    }

    public static String getAuthRequest(String login, String password) {
        return AUTH_REQUEST + DELIMITER + login + DELIMITER + password;
    }

    public static String getAuthAccept(String nickname) {
        return AUTH_ACCEPT + DELIMITER + nickname;
    }

    public static String getAuthDenied() {
        return AUTH_DENIED;
    }

    public static String getMsgFormatError(String message) {
        return MSG_FORMAT_ERROR + DELIMITER + message;
    }

    public static String getTypeBroadcast(String src, String message) {
        return TYPE_BROADCAST + DELIMITER + System.currentTimeMillis() +
                DELIMITER + src + DELIMITER + message;
    }

}
