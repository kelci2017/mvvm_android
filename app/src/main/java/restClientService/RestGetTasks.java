package restClientService;

import android.util.Log;

import restClient.RestResult;
import restClient.VolleyService;

/**
 * Created by Kelci on 6/19/2018.
 */

public class RestGetTasks extends VolleyService {
     @Override
    public RestResult parseResult(String result){
//        Log.i(getClass().getName(),"aaaaaaaaaaaaaa" + result);
//        Task task = fromJson(result, Task.class);
//        if (task.getResultCode() == 12) return new RestResult(task.getResultCode(),task.getResultDesc());
//        return new RestResult(task);
        return new RestResult<>();
    }
    @Override
    public String getUrl(){
        return "http://192.168.1.177:3000/tasks/:5b2940d1a8608c3a0ce67ede";
    }
    @Override
    public RestResult initialize(){
        return new RestResult();
    }
}
