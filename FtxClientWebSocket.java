
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import org.json.JSONObject;
import java.time.Instant;

public class FtxClientWebSocket {
    private final static ApiCredentials key= new ApiCredentials();
    public static long tiempo= Instant.now().toEpochMilli();
    public static Listener listener = new Listener();
    public static WebSocket ws = HttpClient.newHttpClient().newWebSocketBuilder().buildAsync(URI.create("wss://ftx.com/ws/"), listener).join();
    public static void main(String[] args)throws Exception{
        //getTicker("BTC-PERP");
        while(true){}//
    }//main
    

    public static void useOrders(JSONObject t){
        System.out.println(t);
    }//useMarkets
    public static void useFills(JSONObject t){
        System.out.println(t);
    }//useMarkets
    public static void useMarkets(JSONObject t){
        System.out.println(t);
    }//useMarkets
    public static void useTicker(JSONObject t){
        //System.out.println(t);
    }//useTicker
    public static void useTrades(JSONObject t){
        System.out.println(t);
    }//useTrades
    public static void useOrderbook(JSONObject t){
        System.out.println(t);
    }//useOrderbook


    public static void getFills(){
        login();
        ws.sendText(getText("fills"),true);
    }//fills
    public static void getOrders(){
        login();
        ws.sendText(getText("orders"),true);
    }//orders
    public static void getTicker(String ticker)throws Exception{
        ws.sendText(getText("ticker",ticker),true);
    }//ticker
    public static void getMarkets(String ticker){
        ws.sendText(getText("markets",ticker),true);
    }//markets
    public static void getTrades(String ticker){
        ws.sendText(getText("trades",ticker),true);
    }//trades
    public static void getOrderbook(String ticker){
        ws.sendText(getText("orderbook",ticker),true);
    }//orderbook

    /**
     * @param channel type of channel to subscribe 
     * @param ticker 
     * @return
     */
    public static String getText(String channel, String ticker){
        return "{\"op\":\"subscribe\",\"channel\":\""+channel+"\",\"market\":\""+ticker+"\"}";
    }//getText
    
    public static String getText(String channel){
        return "{\"op\":\"subscribe\",\"channel\":\""+channel+"\"}";
    }//getText

    public static void unsubscribe(String channel, String ticker){
        ws.sendText("{\"op\": \"unsubscribe\", \"channel\": \""+channel+"\", \"market\": \""+ticker+"\"}", true);
    }//unsubscribe
    
    private static void login(){
        String s="{\"args\": {\"key\": \""+key.getApiKey()+"\", \"sign\": \""+key.getSign(tiempo+"websocket_login")+"\", \"time\": "+ tiempo +"},\"op\":\"login\"}";
        ws.sendText(s, true);
    }//login
}//FtxClientWebSocket








































        
        // WebSocket.Listener listener = new WebSocket.Listener() {    
        //     public void onOpen(WebSocket webSocket) {
        //         WebSocket.Listener.super.onOpen(webSocket);
        //     }//onOpen

        //     public CompletionStage<?> onText(WebSocket webSocket,CharSequence message,boolean last) {
        //         JSONObject r= new JSONObject(message.toString());
        //         if(r.get("type").equals("subscribed")){
        //             System.out.println("Subscribed!");
        //         }else if(r.get("type").equals("error")){
        //             System.out.println(r.get("error"));   
        //         }else if(r.get("type").equals("update")){
        //             JSONObject rr = new JSONObject(r.get("data").toString().replace("[", "").replace("]", ""));
                    
        //             // System.out.println(rr);
        //         }else{
        //             System.out.println("Something strange");
        //         }
        //         return WebSocket.Listener.super.onText(webSocket, message, last);
        //     }//onText
        // };//listener
        // WebSocket ws = HttpClient.newHttpClient().newWebSocketBuilder().buildAsync(URI.create("wss://ftx.com/ws/"), listener).join();
        // ws.sendText(getText("trades","BTC-PERP"),true);
        
        // while(true){}