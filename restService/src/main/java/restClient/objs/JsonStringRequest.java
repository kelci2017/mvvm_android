package restClient.objs;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

/**
 * Created by Kelci on 6/13/2018.
 */

public class JsonStringRequest extends JsonRequest<String> {

    public JsonStringRequest(int method, String url, String requestBody, Response.Listener<String> listener,
                       Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);}

    public Response<String> parseNetworkResponse(NetworkResponse response){
        if (response.statusCode == 200) {
            return Response.success(response.data.toString(), HttpHeaderParser.parseCacheHeaders(response));
        } else {
            return Response.error(new VolleyError());
        }
    }
}
