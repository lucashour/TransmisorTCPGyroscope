package com.ciaa_poncho.lucashour.transmisortcpgyroscope;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TcpAsyncSend extends AsyncTask<Void, Void, Void> {

    private String message;
    private Socket socket;

    public TcpAsyncSend(Socket socket,String message){
        this.socket = socket;
        this.message = message;
    }

     protected Void doInBackground(Void... params) {
         sendData();
         return null;
    }

    private boolean sendData(){
        try {
            DataOutputStream outToServer = new DataOutputStream(this.socket.getOutputStream());
            outToServer.writeBytes(this.message);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
