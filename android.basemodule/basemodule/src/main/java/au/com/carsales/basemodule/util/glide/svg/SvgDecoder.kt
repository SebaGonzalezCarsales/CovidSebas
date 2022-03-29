package au.com.carsales.basemodule.util.glide.svg

import com.bumptech.glide.load.Options
import com.caverock.androidsvg.SVGParseException
import com.caverock.androidsvg.SVG
import com.bumptech.glide.load.resource.SimpleResource
import com.bumptech.glide.load.ResourceDecoder
import com.bumptech.glide.load.engine.Resource
import java.io.IOException
import java.io.InputStream


/**
 * Created by anibalbastias on 2019-06-24.
 */

class SvgDecoder : ResourceDecoder<InputStream, SVG> {

    @Throws(IOException::class)
    override fun handles(source: InputStream, options: Options): Boolean {
        return true
    }

    @Throws(IOException::class)
    override fun decode(source: InputStream, width: Int, height: Int, options: Options): Resource<SVG> {
        try {
            val svg = SVG.getFromInputStream(source)
            return SimpleResource(svg)
        } catch (ex: SVGParseException) {
            //Crashlytics.logException(ex)
            throw IOException("Cannot load SVG from stream", ex)
        }
    }
}