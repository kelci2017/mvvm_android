package websocket;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Created by Kelci on 6/26/2018.
 */

public class WebsocketListener extends WebSocketListener {

    public static String SOCKET_MESSAGE_ACTION = "SOCKET ACTION";
    public static String SOCKET_MESSAGE_KEY = "SOCKET MESSAGE KEY";
    private static Context context;
    private WebsocketConnection websocketConnection;

    public WebsocketListener(WebsocketConnection websocketConnection){
        this.websocketConnection = websocketConnection;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        Log.i(getClass().getName(), "the websocket is open");
        websocketConnection.setSocketConnectStatus(true);
        websocketConnection.setWebsocket(webSocket);
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Log.i(getClass().getName(), "the message from server is: " + text);
        Intent intent = new Intent(SOCKET_MESSAGE_ACTION);
        intent.putExtra(SOCKET_MESSAGE_KEY,text);
        context.sendBroadcast(intent);
    }
    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {

    }
    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        Log.i(getClass().getName(), "the websocket is closed");
        webSocket.close(WebsocketConnection.NORMAL_CLOSE_CODE, null);
        if (websocketConnection!=null){
            websocketConnection.setSocketConnectStatus(false);
            websocketConnection.setWebsocket(null);
        }
    }
    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        Log.i(getClass().getName(), "the websocket is failed to get connected");
        if (websocketConnection!=null){
            websocketConnection.setSocketConnectStatus(false);
            websocketConnection.setWebsocket(null);
        }
    }

    public static void setApplicationContext(Context applicationContext){
        context = applicationContext;
    }
}
