package restClient;

import java.util.ArrayList;

/**
 * Created by Kelci on 6/14/2018.
 */

public class BatchService {

    private ArrayList<VolleyService> serviceList;
    private RestResult restResult = new RestResult();
    private int service_count = 0;
    private RestHandler restHandler;
    private RestHandler singleServiceResultHandler;

    public void addService (VolleyService volleyService){
        service_count ++;
        serviceList.add(volleyService);
    }

    public void call(RestTag restTag, final RestHandler restHandler){
        this.restHandler = restHandler;
        if (serviceList.size()>0){
            for (final VolleyService volleyService: serviceList){
                RestHandler restHandlerInner = new RestHandler(){
                    @Override
                    public void onReturn(RestResult restResult){
                        //pass each service result to singleservicehandler if user wants to use the single service result
                        singleServiceResultHandler.onReturnResult(restResult);
                        if (restResult.isSuccess()){
                            countDown(volleyService, restResult.getResultObject());
                        } else {
                            restHandler.onReturnResult(restResult);
                        }
                    }
                };
                volleyService.call(restTag,volleyService.getRestParms(),restHandlerInner,volleyService.isUseCache());
            }
        } else {
            restHandler.onReturnResult(restResult);
        }

    }
    public void setSingleServiceResultHandler(RestHandler singleServiceResultHandler){
        this.singleServiceResultHandler = singleServiceResultHandler;
    }
    private synchronized void countDown(VolleyService volleyService,Object resultObject){
        service_count--;
        if (resultObject != null) restResult.addResult(volleyService.getClass().getName(), resultObject);
        if (service_count == 0) restHandler.onReturnResult(restResult);
    }
}

