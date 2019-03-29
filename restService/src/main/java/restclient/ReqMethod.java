package restclient;


import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.ImageLoader;

import restclient.objs.JsonStringRequest;

import java.util.Collections;
import java.util.Map;

/**
 * Created by Kelci on 6/11/2018.
 */

public class ReqMethod {



    private static int TIMEOUT = 20000;

    protected static void get(final VolleyService volleyService, String url, RestResponse restResponse) {
        JsonStringRequest stringRequest = new JsonStringRequest(Request.Method.GET, url, "", restResponse, restResponse) {
            @Override
            public String getBodyContentType() {
                String contentType = volleyService.getBodyContentType();
                if (contentType==null) return super.getBodyContentType();
                return contentType;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (volleyService.getHeaders()==null) return Collections.emptyMap();
                return volleyService.getHeaders();
            }
        };
        if (volleyService.getRestTag()!=null && volleyService.getRestTag().getTag()!=null) stringRequest.setTag(volleyService.getRestTag().getTag());
        addRequstQueue(stringRequest);
    }

    protected static void post(final VolleyService volleyService, String url,  RestResponse restResponse) {

        JsonStringRequest jsonStringRequest = new JsonStringRequest(Request.Method.POST, url, volleyService.generatePostBody() ,restResponse, restResponse){
            @Override
            public String getBodyContentType() {
                String contentType = volleyService.getBodyContentType();
                if (contentType==null) return super.getBodyContentType();
                return contentType;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (volleyService.getHeaders()==null) return Collections.emptyMap();
                return volleyService.getHeaders();
            }
        };
        if (volleyService.getRestTag().getTag()!=null) jsonStringRequest.setTag(volleyService.getRestTag().getTag());
        addRequstQueue(jsonStringRequest);
    }


    private synchronized static <T> void addRequstQueue(Request<T> request){
        request.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyService.getRequestQueue().add(request);
    }
    protected static void cancelRequesst(String reqTag) {
        if (reqTag == null) return;
        VolleyService.getRequestQueue().cancelAll(reqTag);
    }
    private static ImageLoader imageloader = createImageLoader();
    protected static ImageLoader getImageLoader () {
        return imageloader;
    }
    protected static ImageLoader createImageLoader(){

        ImageLoader mImageloader = new ImageLoader(VolleyService.getRequestQueue(),new ImageLoader.ImageCache(){
            LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(20);
            @Override
            public Bitmap getBitmap(String url) {
                return lruCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                lruCache.put(url,bitmap);
            }
        });
        return mImageloader;
    }
}
