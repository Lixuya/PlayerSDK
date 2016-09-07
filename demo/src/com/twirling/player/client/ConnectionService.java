package com.twirling.player.client;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.twirling.player.Constants;
import com.twirling.player.config.PreferenceKeys;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * <p>Created by Melle Dijkstra on 18-4-2016</p>
 * TODO: CREATE DOCUMENTATION!!!
 */
public class ConnectionService extends Service {

    // BROADCAST MESSAGES
    public static final String CONNECTED = "nl.melledijkstra.musicplayerclient.CONNECTED";
    public static final String DISCONNECTED = "nl.melledijkstra.musicplayerclient.DISCONNECTED";
    public static final String MESSAGERECEIVED = "nl.melledijkstra.musicplayerclient.MRECEIVED";
    public static final String UPDATE = "nl.melledijkstra.musicplayerclient.UPDATE";
    public static final String CONNECTFAILED = "nl.melledijkstra.musicplayerclient.CONNECTFAILED";
    public static final String TAG = "PlayerSDK";

    private SharedPreferences settings;

    public Socket mSocket;
    // Input and out streams for communication
    protected BufferedReader mIn;
    protected PrintWriter mOut;
    private InfoListener listener2;

    private final IBinder mBinder = new LocalBinder();

    NotificationManager notifyman;

    @Override
    public void onCreate() {
        Log.v(TAG, "Service - onCreate");
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        notifyman = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        listener2 = new InfoListener(ConnectionService.this);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAG, "Service - onStartCommand");
        // Check if already connected
        return START_NOT_STICKY;
    }

    public boolean isConnected() {
        return listener2.isConnected() && mOut != null;
    }

    public void sendMessage(JSONObject message) {
        if (isConnected()) {
            Log.v(TAG, "Sending: " + message.toString());
            mOut.println(message.toString());
            mOut.flush();
        } else {
            Log.e(TAG, "Not connected, couldn't send message" + message.toString());
            disconnect();
        }
    }

    public void connect() {
        if (!isConnected()) {
            Thread connectThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    // 1
                    String ip = settings.getString(PreferenceKeys.HOST_IP, Constants.DEFAULT_IP);
                    int port = settings.getInt(PreferenceKeys.HOST_PORT, Constants.DEFAULT_PORT);
                    IPListener listener1 = new IPListener(ip, port);
                    ip = listener1.listen();
                    //
                    LocalBroadcastManager.getInstance(ConnectionService.this).sendBroadcast(new Intent(CONNECTED));
                    //
                    SendClient sendClient = new SendClient();
                    sendClient.setIp(ip);
                    sendClient.sendMessage(ConnectionService.this);

                    // 3
                    listener2.setIp(ip);
                    listener2.setPort(10002);
                    listener2.setTimeout(Constants.DEFAULT_TIMEOUT);
                    mSocket = listener2.listen();
                    try {
                        mIn = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
                        mOut = new PrintWriter(mSocket.getOutputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String inputMsg = null;
                    int j = 0;
                    if (mIn != null && j < 10) {
                        try {
                            while ((inputMsg = mIn.readLine()) != null && j < 20) {
                                Log.v("www", "Message received: " + inputMsg);
                                Intent i = new Intent(ConnectionService.MESSAGERECEIVED);
                                i.putExtra("msg", inputMsg);
                                LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(i);
                                Log.v("www", "BROADCAST send: " + i.toString());
                                j++;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    disconnect();
                }
            });
            connectThread.start();
        }
    }

    /**
     * Disconnects with the server, then broadcasts a message that service disconnected with server so they can react on the event
     */
    public void disconnect() {
        if (mSocket != null && mSocket.isConnected()) {
            Log.v(TAG, "Disconnecting socket!! (DISCONNECT)");
            try {
                mSocket.close();
                mOut.close();
                mIn.close();
                mSocket = null;
                mOut = null;
                mIn = null;
            } catch (IOException e) {
                Log.v(TAG, "Could not dispose mSocket, mOut or mIn - Exception: " + e.getMessage());
            }
        }
        // Broadcast that the connection is disconnected to everyone who's listening
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(DISCONNECTED));
        stopSelf();
    }

    public void sendDebugMessage(String msg) {
        if (isConnected()) {
            mOut.println(msg);
            mOut.flush();
            Log.v(TAG, "Message '" + msg + "' send");
        } else {
            Log.v(TAG, "not connected");
        }
    }

    public String getRemoteIp() {
        if (isConnected()) {
            return mSocket.getRemoteSocketAddress().toString().replace("/", "");
        } else {
            disconnect();
            return null;
        }
    }

    // This binder gives the service to the binding object
    public class LocalBinder extends Binder {
        public ConnectionService getService() {
            return ConnectionService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "Service - onBind");
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.i(TAG, "Service - onRebind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, getClass().getSimpleName() + " - onUnBind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "Service - onDestroy");
        Toast.makeText(this, "Service - Destroyed", Toast.LENGTH_SHORT).show();
        disconnect();
        super.onDestroy();
    }
}
