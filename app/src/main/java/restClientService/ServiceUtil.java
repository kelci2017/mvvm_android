package restClientService;

import restClient.RestHandler;
import restClient.RestParms;
import restClient.RestTag;


/**
 * Created by Kelci on 6/18/2018.
 */

public class ServiceUtil {

    public static synchronized void getTasks(RestTag restTag, RestParms restParms, RestHandler restHandler, boolean useCache) {

        new RestGetTasks().call(restTag, restParms, restHandler, useCache);
    }
}