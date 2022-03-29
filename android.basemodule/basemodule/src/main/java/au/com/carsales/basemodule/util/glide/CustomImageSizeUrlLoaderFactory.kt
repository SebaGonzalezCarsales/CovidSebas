package au.com.carsales.basemodule.util.glide

import au.com.carsales.basemodule.getAppContext
import au.com.carsales.basemodule.util.isTablet
import com.bumptech.glide.load.model.*
import java.io.InputStream

class CustomImageSizeUrlLoaderFactory : ModelLoaderFactory<CustomImageSizeModel, InputStream> {

    override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<CustomImageSizeModel, InputStream> {
        val modelLoader = multiFactory.build<GlideUrl, InputStream>(GlideUrl::class.java, InputStream::class.java)

        val modelCache = if (isTablet(getAppContext())) {
            ModelCache(600)
        } else {
            ModelCache<CustomImageSizeModel, GlideUrl>(300)
        }
        return CustomImageSizeUrlLoader(modelLoader, modelCache)
    }

    override fun teardown() {

    }
}