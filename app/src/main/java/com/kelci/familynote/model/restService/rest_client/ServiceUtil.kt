package com.kelci.familynote.model.restService.rest_client

import restclient.RestHandler
import restclient.RestParms
import restclient.RestTag

class ServiceUtil {

    companion object {

        @Synchronized
        fun login(restTag: RestTag?, restParms: RestParms, restHandler: RestHandler<Any>?, useCache: Boolean) {
            RestLogin().call(restTag, restParms, restHandler, useCache)
        }

        @Synchronized
        fun register(restTag: RestTag?, restParms: RestParms, restHandler: RestHandler<Any>?, useCache: Boolean) {
            RestRegister().call(restTag, restParms, restHandler, useCache)
        }

        @Synchronized
        fun logout(restTag: RestTag?, restParms: RestParms?, restHandler: RestHandler<Any>?, useCache: Boolean) {
            RestLogout().call(restTag, restParms, restHandler, useCache)
        }

        @Synchronized
        fun getToken(restTag: RestTag?, restParms: RestParms?, restHandler: RestHandler<Any>?, useCache: Boolean) {
            RestGetToken().call(restTag, restParms, restHandler, useCache)
        }

        @Synchronized
        fun getFamilyMemberList(restTag: RestTag?, restParms: RestParms?, restHandler: RestHandler<Any>?, useCache: Boolean) {
            RestGetFamilyMemberList().call(restTag, restParms, restHandler, useCache)
        }

        @Synchronized
        fun submitNote(restTag: RestTag?, restParms: RestParms?, restHandler: RestHandler<Any>?, useCache: Boolean) {
            RestSubmitNote().call(restTag, restParms, restHandler, useCache)
        }

        @Synchronized
        fun filterNote(restTag: RestTag?, restParms: RestParms?, restHandler: RestHandler<Any>?, useCache: Boolean) {
            RestFilterNote().call(restTag, restParms, restHandler, useCache)
        }

        @Synchronized
        fun addFamilyMember(restTag: RestTag?, restParms: RestParms?, restHandler: RestHandler<Any>?, useCache: Boolean) {
            RestAddFamilyMember().call(restTag, restParms, restHandler, useCache)
        }

        @Synchronized
        fun searchNote(restTag: RestTag?, restParms: RestParms?, restHandler: RestHandler<Any>?, useCache: Boolean) {
            RestSearchNote().call(restTag, restParms, restHandler, useCache)
        }
    }

}