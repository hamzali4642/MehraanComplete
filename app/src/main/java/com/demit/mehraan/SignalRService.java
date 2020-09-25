package com.demit.mehraan;

import android.app.Service;
import android.content.Intent;

import android.content.SharedPreferences;
import android.net.Credentials;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;


import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;
import com.microsoft.signalr.OnClosedCallback;

import java.util.concurrent.ExecutionException;

import io.reactivex.Single;

/*import microsoft.aspnet.signalr.client.Credentials;
import microsoft.aspnet.signalr.client.Platform;
import microsoft.aspnet.signalr.client.SignalRFuture;
import microsoft.aspnet.signalr.client.http.Request;
import microsoft.aspnet.signalr.client.http.android.AndroidPlatformComponent;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;
import microsoft.aspnet.signalr.client.hubs.SubscriptionHandler1;
import microsoft.aspnet.signalr.client.transport.ClientTransport;
import microsoft.aspnet.signalr.client.transport.ServerSentEventsTransport;*/


public class SignalRService extends Service {

 //   private HubConnection mHubConnection;
   // private HubProxy mHubProxy;
    private Handler mHandler; // to display Toast message
    private final IBinder mBinder = new LocalBinder(); // Binder given to clients
    SharedPreferences mSharedPreference;
    HubConnection hubConnection;
    String token;
    String serverUrl = "https://mehraan-oh3.conveyor.cloud/MessageHub";
    public SignalRService() {
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        hubConnection = HubConnectionBuilder.create(serverUrl)
                .withAccessTokenProvider(Single.defer(() -> {
                    // Your logic here.
                    return Single.just(token);
                })).build();

        mHandler = new Handler(Looper.getMainLooper());

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int result = super.onStartCommand(intent, flags, startId);
        startSignalR();
        return result;
    }
    @Override
    public void onDestroy() {
       hubConnection.stop();
        super.onDestroy();
    }
    @Override
    public IBinder onBind(Intent intent) {
        // Return the communication channel to the service.
        startSignalR();
        return mBinder;
    }
    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public SignalRService getService() {
            // Return this instance of SignalRService so clients can call public methods
            return SignalRService.this;
        }
    }
    /**
     * method for clients (activities)
     */
    public void sendMessage(String message) {
       // String SERVER_METHOD_SEND = "SendDM";
        if (hubConnection.getConnectionState()== HubConnectionState.CONNECTED){
        hubConnection.send("Send",message);}
        if (hubConnection.getConnectionState()== HubConnectionState.DISCONNECTED){
            hubConnection = HubConnectionBuilder.create(serverUrl)
                    .withAccessTokenProvider(Single.defer(() -> {
                        // Your logic here.
                        return Single.just(token);
                    })).build();
            hubConnection.start().blockingAwait();

            hubConnection.send("Send",message);}

      //  mHubProxy.invoke(SERVER_METHOD_SEND, message,"asd");
    }
    public class CustomMessage {
        public String UserName;
        public String Message;
    }
    private void startSignalR() {
token=mSharedPreference.getString("token","");
         hubConnection= HubConnectionBuilder.create(serverUrl)
                .withAccessTokenProvider(Single.defer(() -> {
                    // Your logic here.
                    return Single.just(token);
                })).build();

        hubConnection.start().blockingAwait();
        if (hubConnection.getConnectionState()== HubConnectionState.CONNECTED){
            hubConnection.send("Join");
            hubConnection.send("Send","Hello Lun");
            hubConnection.on("Send", (message) -> {
                System.out.println("New Message: " + message);
            }, String.class);
        }
        hubConnection.onClosed(new OnClosedCallback() {
            @Override
            public void invoke(Exception exception) {
                Log.d("wtf","i am closed");
            }
        });

       /* Platform.loadPlatformComponent(new AndroidPlatformComponent());

        Credentials credentials = new Credentials() {
            @Override
            public void prepareRequest(Request request) {
                String token=mSharedPreference.getString("token", null);

                request.addHeader("Authorization", token);
            }
        };*/

      //  mHubConnection = new HubConnection(serverUrl,true);
     //  mHubConnection.setCredentials(credentials);
      //   String SERVER_HUB_CHAT = "MessageHub";

      //  mHubProxy = mHubConnection.createHubProxy();

/*        ClientTransport clientTransport = new ServerSentEventsTransport(mHubConnection.getLogger());
        SignalRFuture<Void> signalRFuture = mHubConnection.start(clientTransport);

        try {
            signalRFuture.get();
            Log.d("hhHH","ME YAHAN AYE");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Log.d("hhHH","ME YAHAN AYEar"+e.toString());


            return;
        }

        String HELLO_MSG = "Hello from Android!";
        sendMessage(HELLO_MSG);

        String CLIENT_METHOD_BROADAST_MESSAGE = "SendDirectMessage";
        mHubProxy.on(CLIENT_METHOD_BROADAST_MESSAGE,
                new SubscriptionHandler1<CustomMessage>() {
                    @Override
                    public void run(final CustomMessage msg) {
                        final String finalMsg = msg.UserName + " says " + msg.Message;
                        // display Toast message
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), finalMsg, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                , CustomMessage.class);*/

        String serverUrl = "https://mehraan-oh3.conveyor.cloud/MessageHub";
    /*     mHubConnection= HubConnectionBuilder.create(serverUrl).build();
        if(mHubConnection.getConnectionState()== HubConnectionState.DISCONNECTED){
            mHubConnection.start();

        }
        if(mHubConnection.getConnectionState()== HubConnectionState.CONNECTED){
            Log.d("ayee","connected");
        }
        mHubConnection.send("Join");
*/
    }

}
