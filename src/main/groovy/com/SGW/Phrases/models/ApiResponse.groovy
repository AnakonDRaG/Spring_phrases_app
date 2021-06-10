package com.SGW.Phrases.models

import javax.validation.ConstraintViolation

class ApiResponse {

    static def ErrorMessage(def errors) {
        return [errors: errors]
    }


    static def SuccessMessage(def message, def resources, int status = 200) {
        return [success: message, resources: resources, status: status]
    }

    static def ConvertValidatorToJSON(Set<ConstraintViolation> data) {
        def json = [:]
        data.each { it -> json.put(it.propertyPath, it.message) }
        return json
    }
}
