package webserver.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by 4P on 2017/7/2.
 */
public class Response {
    private String httpVersion;
    private int status;
    private String status_string;
    private Map<String, String> headers;
    private byte[] data;
    private int data_length;

    public Response(){
        headers = new HashMap<String, String>();
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
        status_string = HttpParam.STATUSMAP.get(status+"");
    }

    public String getStatus_string() {
        return status_string;
    }

    public void setStatus_string(String status_string) {
        this.status_string = status_string;
    }

    public String getHeaders(String header) {
        return headers.get(header);
    }

    public void setHeaders(String header, String value) {
        headers.put(header,value);
    }

    public Iterator<Map.Entry<String, String>> iterator(){
        return headers.entrySet().iterator();
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getData_length() {
        return data_length;
    }

    public void setData_length(int data_length) {
        this.data_length = data_length;
    }

    public byte[] getBytes(int cho){
        String head = "";
//        status_string=;
        head+=HttpParam.HTTP_VERSION+" "+status+" "+status_string+"\r\n";
        Iterator<Map.Entry<String, String>> map = iterator();
        while(map.hasNext()){
            Map.Entry<String, String> m = map.next();
            head+=m.getKey()+": "+m.getValue()+"\r\n";
        }

        head+="\r\n";

        byte[] head_byte = head.getBytes();
        ByteArrayOutputStream baos=null;
        if(cho==1)
            baos = new ByteArrayOutputStream(head_byte.length+data_length);
        else if(cho==2){
            baos = new ByteArrayOutputStream(head_byte.length);
        }
        try {
            baos.write(head_byte);
            if(cho==1)baos.write(data);
            baos.flush();
            baos.close();

            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }
}
