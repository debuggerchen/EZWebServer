package webserver.server;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by 4P on 2017/7/2.
 */
public class Request {
    private int method;
    private String uri;
    private String httpVersion;
    private Map<String, String> headers;
    private String data;
    private int data_length;

    public Request(){
        headers = new HashMap<String, String>();
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public void setMethod(String method){
        switch (method.toUpperCase()){
            case "GET":
                this.method=HttpParam.METHOD_GET;
                break;
            case "HEAD":
                this.method=HttpParam.METHOD_HEAD;
                break;
            case "POST":
                this.method=HttpParam.METHOD_POST;
                break;
            default:
                System.out.println("Unsupported method");
                break;
        }
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getData_length() {
        return data_length;
    }

    public void setData_length(int data_length) {
        this.data_length = data_length;
    }

    public String getUri() {

        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public void setHeader(String header, String value){
        headers.put(header,value);
    }

    public String getHeader(String header){
        return headers.get(header);
    }

    public Iterator<Map.Entry<String, String>> iterator(){
        return headers.entrySet().iterator();
    }
}
