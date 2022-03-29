package au.com.carsales.basemodule.util.glide.svg

import android.graphics.drawable.PictureDrawable
import com.bumptech.glide.load.resource.SimpleResource
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.engine.Resource
import com.caverock.androidsvg.SVG
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder


/**
 * Created by anibalbastias on 2019-06-24.
 */

class SvgDrawableTranscoder : ResourceTranscoder<SVG, PictureDrawable> {

    override fun transcode(toTranscode: Resource<SVG>, options: Options): Resource<PictureDrawable> {
        val svg = toTranscode.get()
        val picture = svg.renderToPicture()
        val drawable = PictureDrawable(picture)
        return SimpleResource(drawable)
    }
}