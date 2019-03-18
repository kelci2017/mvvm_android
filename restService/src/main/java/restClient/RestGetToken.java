package restClient;

import android.util.Log;

import com.android.volley.AuthFailureError;

import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Created by Kelci on 6/13/2018.
 */

public class RestGetToken extends VolleyService {

    private String auth_key = "8DEEF4EE1B83715848D08FC5D5A5F8C284BEC6567085A6E315832005994AF049";

    @Override
    public RestResult<TokenObject> parseResult(String result){
        try{
            TokenObject tokenObject = fromJson(result, TokenObject.class);
            if (tokenObject.isSuccess()){
                VolleyService.JWT_TOKEN = tokenObject.getToken();
                Log.i(getClass().getName(),"Token is: " + VolleyService.JWT_TOKEN);
                return new RestResult(VolleyService.TOKEN_SCHEME + VolleyService.JWT_TOKEN);
            } else {
               return new RestResult(tokenObject.getResultCode(), VolleyService.COMMON_ERROR_DESC);
            }
        } catch (Exception e){
           return new RestResult(VolleyService.NETWORK_ERROR,e.getMessage());
        }
    }
    @Override
    public String getUrl(){
       return "http://192.168.1.177:8080/auth/getToken";
    }
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        final Map<String, String> headers = new HashMap<>();

        byte[] privateKeyBytes = null;
        try {
            privateKeyBytes = auth_key.getBytes("UTF-8");
        }catch (Exception e){
            e.printStackTrace();
            return headers;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("deviceid", "androidS72017");
        String compactJws = Jwts.builder()
                .setClaims(map)
                .setHeaderParam("typ","JWT")
                .signWith(SignatureAlgorithm.HS256, privateKeyBytes)
                .compact();
        headers.put("requestkey", compactJws);
        headers.put("applicationid", "123456");

        Log.i(getClass().getName(),"requestkey is: " + headers.get("requestkey"));

        return headers;
    }
    @Override
    public boolean needAuthorization(){
        return false;
    }
    @Override
    public boolean isUseCache(){
        if (VolleyService.JWT_TOKEN!=null){
            getRestHandler().onReturnResult(new RestResult(VolleyService.TOKEN_SCHEME + VolleyService.JWT_TOKEN));
            return true;
        } else {
            return false;
        }
    }
    private class TokenObject{
        private String token;
        private int resultCode;
        private void setToken(String token){
            this.token = token;
        }
        private String getToken(){
            return this.token;
        }
        private void setResultCode (int resultCode){
            this.resultCode = resultCode;
        }
        private int getResultCode(){
            return this.resultCode;
        }
        private boolean isSuccess(){
            if (this.resultCode == 0) {
                return true;
            } else {
                return false;
            }
        }
    }
}
