package au.com.carsales.basemodule.tracking.model.ga5

import java.io.Serializable

/**
 * Created by anibalbastias on 2019-07-30.
 */

class Ga5ViewData(
        var event: String? = null,
        var attributes: MutableMap<String?, String?>? = null,
        var detailsAttributes: MutableMap<String?, String?>? = null,
        var galleryAttributes: MutableMap<String?, String?>? = null,
        var callConnectAttributes: MutableMap<String?, String?>? = null,
        var callMeBackAttributes: MutableMap<String?, String?>? = null,
        var sellerNumberAttributes: MutableMap<String?, String?>? = null,
        var smsVerificationAttributes: MutableMap<String?, String?>? = null
): Serializable