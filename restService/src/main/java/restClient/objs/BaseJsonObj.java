package restClient.objs;

/**
 * Created by Kelci on 6/14/2018.
 */

public class BaseJsonObj {

    private int resultCode;
    private String resultDesc;


    public BaseJsonObj(int resultCode, String resultDesc){
        this.resultCode = resultCode;
        this.resultDesc = resultDesc;
    }

    public void setResultCode(int resultCode){
        this.resultCode = resultCode;
    }
    public int getResultCode(){
        return this.resultCode;
    }
    public void setResultDesc (String resultDesc){
        this.resultDesc = resultDesc;
    }
    public String getResultDesc () {
        return this.resultDesc;
    }
    public boolean isSuccess(){
        if (resultCode == 0) {
            return true;
        } else {
            return false;
        }
    }
}
