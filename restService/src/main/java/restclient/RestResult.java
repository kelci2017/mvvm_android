package restclient;

import java.util.HashMap;

/**
 * Created by Kelci on 6/11/2018.
 */

public class RestResult<T> {

    private int resultCode = 0;
    private String resultDesc;
    private T resultObject;
    private HashMap<String, T> resultMap;

    public RestResult(T result) {
        this.resultObject = result;
    }

    public RestResult() {}

    public RestResult(int resultCode, String resultDesc) {
        this.resultCode = resultCode;
        this.resultDesc = resultDesc;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public int getResultCode() {
        return this.resultCode;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public String getResultDesc() {
        return this.resultDesc;
    }

    public T getResultObject() {
        return this.resultObject;
    }

    public void setResultObject(T resultObject) {
        this.resultObject = resultObject;
    }

    public void addResult(String resultName,T result){
        if (resultName==null || result==null) return;
       resultMap.put(resultName,result);
    }
    public T getResult(String resultName){
        if (resultName==null) return null;
        return resultMap.get(resultName);
    }
    public boolean isSuccess(){
        if (resultCode == 0){
            return true;
        } else {
            return false;
        }
    }
}
