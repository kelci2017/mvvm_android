package restclient;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;


/**
 * Created by Kelci on 6/11/2018.
 */

public class RestResponse implements Response.Listener<JSONObject>, Response.ErrorListener{

    private VolleyService volleyService;
    private static int MAXIMUM_RETRY_TIMES = 3;

    public RestResponse(VolleyService volleyService) {
        this.volleyService = volleyService;
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.i("RestResponse", "Called service successfully. ResponseObject:" + response.toString());
//        try{
//            BaseJsonObj baseJsonObj =volleyService.fromJson(response,BaseJsonObj.class);
//            if (volleyService.getRetry_times() < MAXIMUM_RETRY_TIMES && baseJsonObj.isSuccess()){
//               volleyService.callService();
//               return;
//            } else {
//                Log.i(getClass().getName(), "the retry" + volleyService.getUrl() +  "has reached the maximum times");
//            }
//        } catch(Exception e){
//           e.printStackTrace();
//        }


      RestResult restResult = volleyService.parseResult(response);
      if (restResult == null) {
          restResult.setResultCode(VolleyService.CODE_COMMON_ERROR);
          restResult.setResultDesc(VolleyService.COMMON_ERROR_DESC);
          return;
      }
      volleyService.getRestHandler().onReturnResult(restResult);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
       if (isTokenExpired(error)){
           if (volleyService.getRetry_times() < MAXIMUM_RETRY_TIMES){

           } else {
               Log.i(getClass().getName(),"token has expired but reached the maximum retry times");
           }
           volleyService.clearToken();
           volleyService.callService();
           return;
       } else {
           error.printStackTrace();
       }
        volleyService.getRestHandler().onReturnResult(new RestResult());
    }

    private boolean isTokenExpired(VolleyError error){
        NetworkResponse networkResponse = error.networkResponse;
        if (networkResponse != null) {
           int statusCode = networkResponse.statusCode;
           if (statusCode == 401) {
               return true;
           }
        }
        return false;
    }
}
