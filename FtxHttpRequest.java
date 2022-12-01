import java.net.URI;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.*;
import java.io.*;
import java.time.Instant;

public class FtxHttpRequest{
    public final static String ENDPOINT = "https://ftx.com/api";
    public static HttpClient client = HttpClient.newHttpClient();
    private final static ApiCredentials key= new ApiCredentials();
    public static long tiempo= Instant.now().toEpochMilli();
    
    /**
     * @param path url of the request
     * @param body body of the request
     * @return the body of the request signed
     */
    private static String sign(String path, String body){
        String message = tiempo+"POST"+path+body;
        return key.getSign(message);   
    }//sign

    /**
     * @param path
     * @return
     */
    private static String sign(String path){
        String message = tiempo+"DELETE"+path;
        return key.getSign(message);   
    }//sign

    /**
     * @param path
     * @return response from the server
     */
    public String postRequest(String path,String body){
        HttpRequest request =  HttpRequest.newBuilder().uri(URI.create(ENDPOINT+path)).headers("FTX-KEY",key.getApiKey(),"FTX-SIGN",sign("/api"+path,body),"FTX-TS",""+tiempo).POST(BodyPublishers.ofString(body)).build();
        HttpResponse<String> t =null; //temp variable
        try {
            t = client.send(request, BodyHandlers.ofString());
        } catch (IOException e ) {
            System.out.print("I/O error");
        } catch (InterruptedException ex) {
            System.out.print("Interrupted exception error");
        }//try-catch
        return t.body();
    }//postRequest

    /**
     * @param path
     * @return
     */
    public String getRequest(String path){
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ENDPOINT+path)).build();
        HttpResponse<String> t =null; //temp variable
        try {
            t = client.send(request, BodyHandlers.ofString());
        } catch (IOException e ) {
            System.out.print("I/O error");
        } catch (InterruptedException ex) {
            System.out.print("Interrupted exception error");
        }//try-catch
        return t.body();
    }//getRequest

    public String deleteRequest(String path){
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ENDPOINT+path)).headers("FTX-KEY",key.getApiKey(),"FTX-SIGN",sign("/api"+path),"FTX-TS",""+tiempo).DELETE().build();
        HttpResponse<String> t =null; //temp variable
        try {
            t = client.send(request, BodyHandlers.ofString());
        } catch (IOException e ) {
            System.out.print("I/O error");
        } catch (InterruptedException ex) {
            System.out.print("Interrupted exception error");
        }//try-catch
        return t.body();
    }//deleteRequest
}//FtxHttpRequest