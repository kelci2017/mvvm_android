package restclient.objs;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by Kelci on 6/13/2018.
 */

public class JsonStringRequest extends JsonRequest<JSONObject> {

    Gson gson;

    public JsonStringRequest(int method, String url, String requestBody, Response.Listener<JSONObject> listener,
                       Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);}

    public Response<JSONObject> parseNetworkResponse(NetworkResponse response){
//        if (response.statusCode == 200) {
//            return Response.success(response.data.toString(), HttpHeaderParser.parseCacheHeaders(response));
//        } else {
//            return Response.error(new VolleyError());
//        }
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
}
