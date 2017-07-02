package webserver.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by 4P on 2017/7/2.
 */
public class Handler {
    private Request req;
    private Response resp;

    public Handler(Request req){
        this.req = req;
        resp = new Response();
        resp.setHttpVersion(HttpParam.HTTP_VERSION);
    }

    private void doGet(){
        System.out.println("Got GET Request");
        byte[] data = readFile();
        resp.setData(data);
        resp.setData_length(data.length);
    }

    private void doPost(){
        System.out.println("Got POST Request");
        System.out.println("POST Data:");
        System.out.println(req.getData());
        System.out.println("Finish POST Data Part.");
        byte[] data = readFile();
        resp.setData(data);
        resp.setData_length(data.length);
    }

    private void doHead(){
        System.out.println("Got HEAD Request");
        byte[] data = readFile();
        resp.setData(data);
        resp.setData_length(data.length);
    }
//    private byte[] readHead()  {
//        File file = new File(Param.WEBROOT,req.getUri());
//        if(req.getUri().trim().equals("/")){
//            file = new File(Param.WEBROOT,'/'+ Param.DEFAULT_PAGE);
//        }
//        FileInputStream fis;
//        int size=0;
//        if(file.exists()){
//            try {
//                fis = new FileInputStream(file);
//                byte[] buffer = new byte[size];
//                resp.setStatus(HttpParam.RESPONSE_OK);
//                String postfix = req.getUri().substring(req.getUri().lastIndexOf('.')+1);
//                if(HttpParam.MIMEMAP.containsKey(postfix)){
//                    resp.setHeaders("Content-Type",HttpParam.MIMEMAP.get(postfix));
//                }
//                return buffer;
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                //Internal Server Implement
//                file = new File(Param.WEBROOT,Param.DEFAULT_500_PAGE);
//                resp.setStatus(HttpParam.RESPONSE_INTERNAL_SERVER_ERROR);
//                String postfix = Param.DEFAULT_500_PAGE.substring(Param.DEFAULT_500_PAGE.lastIndexOf('.')+1);
//                if(HttpParam.MIMEMAP.containsKey(postfix)){
//                    resp.setHeaders("Content-Type",HttpParam.MIMEMAP.get(postfix));
//                }
//            }
//        }
//        else{
//            //Return 404
//            file = new File(Param.WEBROOT,Param.DEFAULT_404_PAGE);
//            resp.setStatus(HttpParam.RESPONSE_NOT_FOUND);
//
//            String postfix = Param.DEFAULT_404_PAGE.substring(Param.DEFAULT_404_PAGE.lastIndexOf('.')+1);
//            if(HttpParam.MIMEMAP.containsKey(postfix)){
//                resp.setHeaders("Content-Type",HttpParam.MIMEMAP.get(postfix));
//            }
//        }
//        return new byte[0];
//
//    }

    private byte[] readFile()  {
        File file = new File(Param.WEBROOT,req.getUri());
        if(req.getUri().trim().equals("/")){
            file = new File(Param.WEBROOT,'/'+ Param.DEFAULT_PAGE);
        }
        FileInputStream fis;
        int size;
        if(file.exists()){
            try {
                fis = new FileInputStream(file);
                size = fis.available();
                byte[] buffer = new byte[size];
                fis.read(buffer);
                resp.setStatus(HttpParam.RESPONSE_OK);

                String postfix = req.getUri().substring(req.getUri().lastIndexOf('.')+1);
                if(HttpParam.MIMEMAP.containsKey(postfix)){
                    resp.setHeaders("Content-Type",HttpParam.MIMEMAP.get(postfix));
                }

                return buffer;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                //Internal Server Implement
                file = new File(Param.WEBROOT,Param.DEFAULT_500_PAGE);
                resp.setStatus(HttpParam.RESPONSE_INTERNAL_SERVER_ERROR);

                String postfix = Param.DEFAULT_500_PAGE.substring(Param.DEFAULT_500_PAGE.lastIndexOf('.')+1);
                if(HttpParam.MIMEMAP.containsKey(postfix)){
                    resp.setHeaders("Content-Type",HttpParam.MIMEMAP.get(postfix));
                }
            }
        }
        else{
            //Return 404
            file = new File(Param.WEBROOT,Param.DEFAULT_404_PAGE);
            resp.setStatus(HttpParam.RESPONSE_NOT_FOUND);

            String postfix = Param.DEFAULT_404_PAGE.substring(Param.DEFAULT_404_PAGE.lastIndexOf('.')+1);
            if(HttpParam.MIMEMAP.containsKey(postfix)){
                resp.setHeaders("Content-Type",HttpParam.MIMEMAP.get(postfix));
            }
        }
        try {
            fis = new FileInputStream(file);
            size = fis.available();

            byte[] buffer = new byte[size];
            fis.read(buffer);

            return buffer;
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return new byte[0];

    }

    public Response handle(){
        switch(req.getMethod()){
            case HttpParam.METHOD_GET:
                doGet();
                break;
            case HttpParam.METHOD_HEAD:
                doHead();
                break;
            case HttpParam.METHOD_POST:
                doPost();
                break;
        }

        return resp;
    }
}
