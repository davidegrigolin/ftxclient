import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import org.json.JSONObject;
import java.util.concurrent.*;
public class Listener implements WebSocket.Listener {

    public void onOpen(WebSocket webSocket) {
        WebSocket.Listener.super.onOpen(webSocket);
    }//onOpen

    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        JSONObject r= new JSONObject(data.toString());
        
        if(r.get("type").equals("subscribed")){
            System.out.println("Subscribed!");
        }else if(r.get("type").equals("error")){
            if(r.get("msg").equals("Not logged in")){
                System.out.println("YOU HAVE TO LOGIN");
            }
            System.out.println(r.get("error"));
        }else if(r.get("channel").equals("orderbook")){
            JSONObject rr = new JSONObject(r.get("data").toString());
            FtxClientWebSocket.useOrderbook(rr);
        }else if(r.get("channel").equals("ticker")){
            JSONObject rr = new JSONObject(r.get("data").toString().replace("[", "").replace("]", ""));
            FtxClientWebSocket.useTicker(rr);
        }else if(r.get("channel").equals("trades")){
            JSONObject rr = new JSONObject(r.get("data").toString().replace("[", "").replace("]", ""));
            FtxClientWebSocket.useTrades(rr);
        }else if(r.get("channel").equals("fills")){
            JSONObject rr = new JSONObject(r.get("data").toString());
            FtxClientWebSocket.useFills(rr);
        }else if(r.get("channel").equals("orders")){
            JSONObject rr = new JSONObject(r.get("data").toString());
            FtxClientWebSocket.useOrders(rr);
        }else if(r.get("type").equals("unsubscribed")){
            System.out.println("Unsubscribed");
        }else{
            System.out.println("Something strange");
        }//if-else

        return WebSocket.Listener.super.onText(webSocket, data, last);
    }//onText

    public void onError(WebSocket webSocket, Throwable error) {
        System.out.println("error " + webSocket.toString());
        WebSocket.Listener.super.onError(webSocket, error);
    }//onError
    
    public CompletionStage<?> onPong(WebSocket webSocket, ByteBuffer message) {
        System.out.println("onPong: " + new String(message.array()));
        return WebSocket.Listener.super.onPong(webSocket, message);
    }//onPong

    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "ok");
        return WebSocket.Listener.super.onClose(webSocket, statusCode, reason);
    }//con close
}//WebSocketClient