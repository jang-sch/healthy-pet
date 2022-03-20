package a6.mwisehart.fxchat.fxchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientListener extends Thread
{
    private final Socket socket;
    private BufferedReader bufferedReader;
    private CanWriteMessage canWriteMessage;

    public ClientListener(Socket socket, CanWriteMessage canWriteMessage)
    {
        this.socket = socket;
        this.canWriteMessage = canWriteMessage;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        String messageFromServer;

        while (socket.isConnected()){
            try{
                messageFromServer = bufferedReader.readLine();
                System.out.println(messageFromServer);
                canWriteMessage.writeMessage(messageFromServer);
            } catch (IOException e){
                closeEverything();
                break;
            }
        }

    }

    private void closeEverything()
    {
        try{
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(socket != null){
                socket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
