package restClient;

import java.util.HashMap;

/**
 * Created by Kelci on 6/11/2018.
 */

public class RestParms {

    private HashMap<String, Object> parameterMap = new HashMap<>();
    private Object[] params;

    public void setParams (String... params) {
       this.params = params;
    }

    public RestResult checkParams (){
        RestResult restResult = new RestResult();
        if (params!=null){
          if (params.length%2!=0){
              restResult.setResultCode(VolleyService.INPUTERROR_CODE);
              restResult.setResultDesc(VolleyService.INPUTERROR_DESC);
              return restResult;
          }
          for (int i=0;i<params.length;i+=2){
              Object paramName = params[i];
              Object paramVaule = params[i+1];
              if (paramName instanceof  String) {
                  parameterMap.put((String)paramName,paramVaule);
              } else {
                  restResult.setResultCode(VolleyService.INPUTERROR_CODE);
                  restResult.setResultDesc(VolleyService.INPUTERROR_DESC);
                  return restResult;
              }

          }
        }

        return restResult;
    }

    public Object getParam(String key) {
        return parameterMap.get(key);
    }
}
