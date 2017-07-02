package webserver.server;

import java.io.*;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by 4P on 2017/7/2.
 */
public class Main {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.println("******************************************");
        System.out.println("");
        System.out.println("Web Server Dashboard");
        System.out.println("");
        System.out.println("******************************************");
        System.out.println("");
        System.out.println("Input Webroot Directory:");
        Param.WEBROOT = scan.nextLine();
        if(Param.WEBROOT.equals(""))
            Param.WEBROOT = "D:/http";
        System.out.println("Input Server Listening Port:");
        try {
            Param.SERVER_PORT = Integer.parseInt(scan.next());
        }catch (Exception e){
            Param.SERVER_PORT = 8080;
        }
        System.out.println(Param.SERVER_PORT);
        Param.DEFAULT_404_PAGE = "error.html";
        Param.DEFAULT_PAGE = "index.html";
        Param.DEFAULT_500_PAGE = "baidu.html";
        Param.SOCKET_BUFFER = 1024;
        new Server().startServer();
    }
}
