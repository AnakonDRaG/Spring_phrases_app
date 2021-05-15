package com.SGW.Phrases.models

class ApiResponse {

    static def ErrorMessage(def message, int status){
        return [error:message, status:status]
   }

    static def SuccessMessage(def message, def data, int status = 200){
        return [success:message,data:data, status:status]
    }
}
