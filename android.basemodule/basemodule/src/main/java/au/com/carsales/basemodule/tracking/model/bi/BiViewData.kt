package au.com.carsales.basemodule.tracking.model.bi


import java.io.Serializable
import java.util.HashMap

data class BiViewData(val trackingUrls: MutableList<String>? = null,
                      val items: String? = null,
                      var tags: HashMap<String, List<String>>? = null,
                      val properties: String? = null,
                      var gtsItems: String? = null,
                      var gtsProperties: String? = null,
                      var silo: String? = null) : Serializable