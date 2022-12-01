import org.json.*;
public class FtxClientRest {
    public static FtxHttpRequest request = new FtxHttpRequest();

    public JSONObject deleteOrders(long id){
        return new JSONObject(request.deleteRequest("/orders/"+id));
    }//modifyOrder

    public JSONObject deleteAllOrders(){
        return new JSONObject(request.deleteRequest("/orders"));
    }//deleteAllOrders


    public JSONObject placeStopOrder(String market,String side, double triggerPrice, String type,double size,Boolean reduceOnly, Boolean retryUntilFilled, Double orderPrice){
        String s ="{\"market\": \""+market+"\", \"side\": \""+side+"\", \"triggerPrice\": "+triggerPrice+", \"size\": "+size+", \"type\": \""+type+"\", \"reduceOnly\": "+reduceOnly+", \"retryUntilFilled\": "+retryUntilFilled+", \"orderPrice\": "+orderPrice+"}";
        System.out.println(s);
        JSONObject t= new JSONObject(request.postRequest("/conditional_orders",s));
        System.out.println(t);
        return new JSONObject(t.get("result").toString());
    }//placeTriggerOrder
  
    public JSONObject placeOrder(String market,String side, double price, String type,double size,Boolean reduceOnly, Boolean ioc, boolean  postOnly, String clientId, boolean rejectOnPriceBand, String rejectAfterTs){
        String s ="{\"market\": \""+market+"\", \"side\": \""+side+"\", \"price\": "+price+", \"size\": "+size+", \"type\": \""+type+"\", \"reduceOnly\": "+reduceOnly+", \"ioc\": "+ioc+", \"postOnly\": "+postOnly+", \"clientId\": "+clientId+"}";
        JSONObject t= new JSONObject(request.postRequest("/orders",s));
        return new JSONObject(t.get("result").toString());
    }//placeOrder


    public JSONObject getOrderStatus(long id){
        JSONObject rr= new JSONObject(request.getRequest("/orders/"+id));
        return new JSONObject(rr.get("result").toString());
    }//getOrderStatus
 
    public JSONObject getOpenOrder(String market){
        JSONObject rr= new JSONObject(request.getRequest("/orders?market="+market));
        return new JSONObject(rr.get("result").toString());
    }//openOrder
    
    public JSONObject getAccount(){
        JSONObject rr= new JSONObject(request.getRequest("/account"));
        return new JSONObject(rr.get("result").toString());
    }//getAccount

    public JSONObject getBalances(){
        JSONObject rr= new JSONObject(request.getRequest("/wallet/balances"));
        return new JSONObject(rr.get("result").toString());
    }//getBalances

    public JSONObject getAllBalances(){
        JSONObject rr= new JSONObject(request.getRequest("/wallet/all_balances"));
        return new JSONObject(rr.get("result").toString());
    }//getAllBalances

    public JSONObject getPositions(){
        JSONObject rr= new JSONObject(request.getRequest("/positions"));
        return new JSONObject(rr.get("result").toString());
    }//getPositions

    public JSONObject getFills(String market){
        JSONObject rr= new JSONObject(request.getRequest("/fills?market="+market));
        return new JSONObject(rr.get("result").toString());
    }//getFills
    
    public JSONObject getMarkets(){
        JSONObject rr= new JSONObject(request.getRequest("/markets"));
        return new JSONObject(rr.get("result").toString());
    }//getMarkets
    
    public JSONObject getSingleMarket(String name){
        JSONObject rr= new JSONObject(request.getRequest("/markets/"+name));
        return new JSONObject(rr.get("result").toString());
    }//getMarkets
    
    public JSONObject getOrderbook(String name, String depth){
        JSONObject rr= new JSONObject(request.getRequest("/markets/"+name+"/orderbook?depth="+depth));
        return new JSONObject(rr.get("result").toString());
    }//getOrderbook

    public JSONObject getTrades(String name){
        JSONObject rr= new JSONObject(request.getRequest("/markets/"+name+"/trades"));
        return new JSONObject(rr.get("result").toString());
    }//getTrades

    public JSONObject getFuture(String name){
        JSONObject rr = new JSONObject(request.getRequest("/futures/"+name));
        return new JSONObject(rr.get("result").toString());
    }//getFuture

    public JSONObject getFuturestats(String name){
        JSONObject t=  new JSONObject(request.getRequest("/futures/"+name+"/stats"));
        return new JSONObject(t.get("result").toString());
    }//getFuturestats 

    public JSONArray getAllFutures(){
        JSONObject t = new JSONObject(request.getRequest("/futures"));
        return new JSONArray(t.get("result").toString());
    }//getFutures

}//FtxClientRest
