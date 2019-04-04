package restclient;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kelci on 6/12/2018.
 */

public abstract class VolleyService {

    public static final String REQUESTTYPE_GET = "GET";
    public static String JWT_TOKEN = null;
    public static final int INPUTERROR_CODE = 51;
    public static final String INPUTERROR_DESC = "input error";
    public static final String TOKEN_SCHEME = "Bearer ";
    public static final int CODE_COMMON_ERROR = 61;
    public static final String COMMON_ERROR_DESC = "unknown error";
    public static final int NETWORK_ERROR = 50;
    public static final String NETWORK_ERROR_DESC = "network error";

    private RestHandler restHandler;
    private RestTag restTag;
    private RestParms restParms;
    private boolean useCache;
    private int retry_times = 0;
    private static RequestQueue requestQueue;

    Gson gson = new Gson();

    private RestResponse restResponse = new RestResponse(VolleyService.this);

    public abstract RestResult parseResult(JSONObject result);

    public abstract String getUrl();

    public RestResult initialize(){
        return new RestResult();
    }

    public void call(RestTag restTag, RestParms restParms, RestHandler restHandler, boolean useCache){

        this.restHandler = restHandler;
        this.restTag = restTag;
        this.restParms = restParms;
        this.useCache = useCache;

        RestResult restResult = restParms==null?null:restParms.checkParams();
        if (restResult!=null&&!restResult.isSuccess()) {
            restHandler.onReturnResult(restResult);
            return;
        }

        RestResult initialRestResult = initialize();
        if (initialRestResult!=null && !initialRestResult.isSuccess()){
            restHandler.onReturnResult(initialRestResult);
        }
        if (useCache && isUseCache()) {
            Log.i(getClass().getName(), "get result from cache");
            return;
        }
        callService();
    }

    public void callService(){
        retry_times++;
//       if(needAuthorization()){
//          new RestGetToken().call(null, null, new RestHandler() {
//              @Override
//              public void onReturn(RestResult result) {
//                  callVolley();
//              }
//          }, true);
//       } else {
//           callVolley();
//       }
        callVolley();
    }
    private void callVolley(){
        if (getRequestType().equals(REQUESTTYPE_GET)) {
            ReqMethod.get(VolleyService.this, getUrl(), restResponse);
        } else {
            ReqMethod.post(VolleyService.this, getUrl(), restResponse);
        }
    }
    protected String getRequestType(){
        return REQUESTTYPE_GET;
    }
    protected String getBodyContentType() {
        return "application/json; charset=utf-8";
    }

    public Map<String, String> getHeaders() throws AuthFailureError {
        if (!needAuthorization()) return null;
        final Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", TOKEN_SCHEME + getToken());

        return headers;
    }

    protected RestHandler getRestHandler() {
        return this.restHandler;
    }
    protected String generatePostBody (){
        return null;
    }
    protected RestTag getRestTag() {
        return this.restTag;
    }
    protected RestParms getRestParms(){
        return this.restParms;
    }
    public boolean needAuthorization(){
        return true;
    }
    public boolean isUseCache(){
        return false;
    }
    public <T> T fromJson(String result, Class<T> classType) throws JsonSyntaxException {

        try {
            return gson.fromJson(result, classType);
        } catch (JsonSyntaxException e){
            e.printStackTrace();
            return null;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    protected final String toJson(Object src) {
        return gson.toJson(src);
    }
    protected void clearToken(){
        JWT_TOKEN = null;
    }
    public String getToken() { return JWT_TOKEN;}
    protected Object getParameter(String key){
        return getRestParms().getParam(key);
    }
    protected int getRetry_times(){
        return retry_times;
    }
    public synchronized static RequestQueue getRequestQueue(){

        return requestQueue;
    }
    public static void setRequestQueue (Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }
}
