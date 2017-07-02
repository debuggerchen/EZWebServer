package webserver.server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by 4P on 2017/7/2.
 */
public class UserThread implements Runnable{
    private Socket socket;
    private Request req;
    private Response resp;

    public UserThread(Socket socket){
        this.socket = socket;
    }


    private void readRequest(){
        try {
            InputStream is = socket.getInputStream();
//            int size = is.available();
//            if(size<=0)return;

            byte[] buf = new byte[Param.SOCKET_BUFFER];
            int size = is.read(buf);
            String request = "";

//            while(size== Param.SOCKET_BUFFER){
//                System.out.println("size="+size);

                request+=new String(buf,0,size);
//                System.out.println("size="+size);
//                size = is.read(buf);
//                System.out.println("size="+size);
//            }
            System.out.println("InputStream Size:"+size);
//            String request = new String(buf);
            System.out.println("req="+request);

            req = new Request();
            Scanner cin = new Scanner(request);
            String header="";
            while (header.equals("") && cin.hasNextLine())
                header = cin.nextLine();

            //First Line
            Scanner in  = new Scanner(header);
            in.useDelimiter(" ");
            req.setMethod(in.next());
            req.setUri(in.next());
            req.setHttpVersion(in.next());

            //Header
            String key="",value="";
            in.useDelimiter(":");
            while(cin.hasNext()){
                header = cin.nextLine();

                if(header.equals(""))break;
                in  = new Scanner(header);
                if(in.hasNext()){
                    key = in.next();
                    value = in.next();
                }
                if(!key.equals("")) {
                    req.setHeader(key,value.trim());
                }
            }

            //Request Body
            String data = "";
//            System.out.println("Reading DATA");
            while(cin.hasNext()){
                header=cin.nextLine();
                data+=header+"\r\n";
            }

            req.setData(data);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        readRequest();
        Handler handler = new Handler(req);
        Response resp = handler.handle();
        OutputStream os=null;
        try {
            os = socket.getOutputStream();
            os.write(resp.getBytes(req.getMethod()==HttpParam.METHOD_HEAD?2:1));
            os.flush();
            os.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        }

    }

}
