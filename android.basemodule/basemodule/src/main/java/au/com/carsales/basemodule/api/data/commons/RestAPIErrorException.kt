package au.com.carsales.basemodule.api.data.commons

class RestAPIErrorException(message: String?, errorCode: String?) : RuntimeException(message) {

    constructor(message: String?, errorInt: Int) : this(message, message)

}