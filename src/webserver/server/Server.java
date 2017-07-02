package webserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by 4P on 2017/7/2.
 */
public class Server {
    public  Server(){
        HttpParam.initMimeMap();
        HttpParam.initStatusMap();
    }
    public void startServer(){
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(Param.SERVER_PORT);

            while(true){
                Socket skt = ss.accept();
                System.out.println("An connection accepted");
                UserThread userThread = new UserThread(skt);
                new Thread(userThread).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
