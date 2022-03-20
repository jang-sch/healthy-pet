package a6.jforsythe.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientThread implements Runnable{

    public static ArrayList<ClientThread> clients = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUserName;

    public ClientThread(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUserName = bufferedReader.readLine();
            // if client username is bad return and don't listen to them
            if(clientUserName != null && clientUserName.matches("^\\w{2,9}[a-zA-Z0-9]$") &&
                    !clientUserName.equalsIgnoreCase("null")) {
                clients.add(this);
                broadcastMessage("+" + clientUserName);
                sendCurrentClientNames();
            } else {
                closeEverything();
            }
        } catch(IOException e){
            closeEverything();
        }
    }

    private void sendCurrentClientNames(){
        for(ClientThread clientThread : ClientThread.clients){
            try {
                if(clientThread != this) {
                    bufferedWriter.write("+" + clientThread.clientUserName);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void run() {
        String messageFromClient = "";
        while(socket.isConnected()){
            try{
                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);

            }catch (IOException e) {
                closeEverything();
                break;
            }
        }
    }

    public void broadcastMessage(String messageToSend){
        if(messageToSend != null) System.out.println(messageToSend);

        for (ClientThread client : ClientThread.clients) {
            try {
                    client.bufferedWriter.write(messageToSend);
                    client.bufferedWriter.newLine();
                    client.bufferedWriter.flush();
            } catch (Exception e) {
                closeEverything();
                break;
            }
        }
    }

    public void removeClientHandler(){
        boolean removed = clients.remove(this);
        if(removed) {
            broadcastMessage("-" + clientUserName);
        }
    }

    public void closeEverything(){
        removeClientHandler();
        try{
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
            if(socket != null){
                socket.close();
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
