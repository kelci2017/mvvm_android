package com.kelci.familynote.utilities;

import android.util.Log;

public class ServerResponseChecker {
    public static String onCheck(String response)
    {

        //Check if it is a connection error
        if ( null == response || response.equals(CommonCodes.NO_CONNECTION)){
            Log.i("ServerResponseChecker", "Error Code is: " + CommonCodes.NO_CONNECTION);
            return CommonCodes.NO_CONNECTION;
        }

        //Check if it is a Http Error
        else if (response.equals((CommonCodes.HTTP_FAIL))) {
            Log.i("ServerResponseChecker", "Error Code is: " + CommonCodes.HTTP_FAIL);

            return CommonCodes.HTTP_FAIL;
        }

        //Check if it is a Empty return
        else if (response.equals(CommonCodes.RESPONSE_EMPTY)) {
            Log.i("ServerResponseChecker", "Error Code is: " + CommonCodes.RESPONSE_EMPTY);


            return CommonCodes.RESPONSE_EMPTY;
        }

        return CommonCodes.NO_ERROR;
    }
}
