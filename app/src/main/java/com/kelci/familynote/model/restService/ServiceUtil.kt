package com.kelci.familynote.model.restService

import restClient.RestHandler
import restClient.RestParms
import restClient.RestTag
import restClientService.RestGetTasks

class ServiceUtil {

    companion object {

        @Synchronized
        fun login(restTag: RestTag, restParms: RestParms, restHandler: RestHandler<*>, useCache: Boolean) {
            RestLogin().call(restTag, restParms, restHandler, useCache)
        }
    }

}