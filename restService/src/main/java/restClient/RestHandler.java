package restClient;

/**
 * Created by Kelci on 6/11/2018.
 */

public abstract class RestHandler<T> {

      public void onReturnResult (RestResult<T> restResult){
          onReturn(restResult);
      }
      public abstract void onReturn(RestResult<T> result);
}
