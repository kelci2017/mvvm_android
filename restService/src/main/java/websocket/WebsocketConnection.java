package websocket;

import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

/**
 * Created by Kelci on 6/26/2018.
 */

public class WebsocketConnection {

    private OkHttpClient client;

    boolean isSocketConnected = false;
    private String socketUrl;
    private WebSocket websocket;
    private StartSocketConnectionTimerTask startSocketConnectionTimerTask;
    private StopSocketConnectionTimerTask stopSocketConnectionTimerTask;
    private static int SOCKET_CONNECTION_INTERVAL = 5000;
    private static int SOCKET_CONNECTION_DELAY = 0;
    public static int NORMAL_CLOSE_CODE = 1000;
    public static String NORMAL_STOP_REASON = "NORMAL STOP REASON";
    Timer timer = new Timer();

    private void connectSocket (){

        if (websocket!=null) return;

        client = new OkHttpClient();
        try {
            Request request = new Request.Builder().url(socketUrl).build();
            WebsocketListener listener = new WebsocketListener(this);
            client.newWebSocket(request, listener);
        } catch (Exception e){
            e.printStackTrace();
            setSocketConnectStatus(false);
        }
    }

    private boolean isSocketConnected(){
        return this.isSocketConnected;
    }

    protected synchronized void setSocketConnectStatus(boolean isSocketConnected){
        this.isSocketConnected = isSocketConnected;
    }

    protected synchronized void setWebsocket (WebSocket websocket){
        this.websocket = websocket;
    }

    private class StartSocketConnectionTimerTask extends TimerTask{
        private String socketUrl;
        public StartSocketConnectionTimerTask(String socketUrl){
            this.socketUrl = socketUrl;
        }
        @Override
        public void run(){
            if (!isSocketConnected()) connectSocket();
        }
    }

    private class StopSocketConnectionTimerTask extends TimerTask{
        private String stopReason;
        public StopSocketConnectionTimerTask(String stopReason){
            this.stopReason = stopReason;
        }
        @Override
        public void run(){
            try{
                websocket.close(NORMAL_CLOSE_CODE, stopReason);
            } catch (Exception e){
                Log.i(getClass().getName(), "found exception when trying to close the websocket");
            }
        }
    }

    public void startSocketTimerTask(String socketUrl){

        this.socketUrl = socketUrl;

        cancelStopSocketTimerTask();

        startSocketConnectionTimerTask = new StartSocketConnectionTimerTask(socketUrl);

        timer.scheduleAtFixedRate(startSocketConnectionTimerTask, SOCKET_CONNECTION_DELAY, SOCKET_CONNECTION_INTERVAL);
    }

    public void stopSocketTimerTask(boolean cancelStartTimerTask, long delayTime, String stopReason){

       if (cancelStartTimerTask) cancelStartSocketTimerTask();

       if (stopReason == null) stopReason = NORMAL_STOP_REASON;

        stopSocketConnectionTimerTask = new StopSocketConnectionTimerTask(stopReason);

        timer.schedule(stopSocketConnectionTimerTask, delayTime);
    }
    private void cancelStopSocketTimerTask(){
        if (stopSocketConnectionTimerTask!=null) {
            try{
                stopSocketConnectionTimerTask.cancel();
                timer.purge();
                stopSocketConnectionTimerTask = null;
            } catch (Exception e){

            }

        }
    }
    private void cancelStartSocketTimerTask(){
        if (startSocketConnectionTimerTask!=null) {
            try{
                startSocketConnectionTimerTask.cancel();
                timer.purge();
                startSocketConnectionTimerTask = null;
            } catch (Exception e){

            }
        }
    }
}
