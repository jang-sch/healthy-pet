package a6.jforsythe.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    public void startServer(){
        try{
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                ClientThread client = new ClientThread(socket);

                Thread thread = new Thread(client);
                thread.start();
            }
        } catch (IOException e){
            e.printStackTrace();
            closeServerSocket();
        }
    }

    public void closeServerSocket(){
        try {
            if(serverSocket != null){
                serverSocket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = new ServerSocket(3390);
        Server server = new Server(serverSocket);
        System.out.println("Server is running on port 3390");
        server.startServer();
    }
}
