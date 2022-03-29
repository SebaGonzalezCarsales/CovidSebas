package au.com.carsales.basemodule.api.volley

import android.content.Context
import com.android.volley.Network
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BaseHttpStack
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.ImageLoader
import java.io.File


class VolleyService(var context: Context) {
  private var diskCacheSize: Int = 0
  private var memoryCacheSize: Int = 0
  private val DEFAULT_CACHE_DIR = "volley"

  val requestQueue: RequestQueue by lazy { this.newRequestQueue(context, null as BaseHttpStack?, diskCacheSize) }
  val imageCache: LruBitmapCache by lazy { LruBitmapCache(memoryCacheSize) }

  val imageLoader: ImageLoader by lazy { ImageLoader(requestQueue, imageCache) }


  constructor(context: Context, memoryCacheSize: Int, diskCacheSize: Int) : this(context) {
    this.diskCacheSize = diskCacheSize
    this.memoryCacheSize = memoryCacheSize
  }

  private fun newRequestQueue(context: Context, stack: BaseHttpStack?, diskCacheSize: Int): RequestQueue {
    val network: BasicNetwork
    if (stack == null) {
      network = BasicNetwork(HurlStack())
    } else {
      network = BasicNetwork(stack)
    }

    return newRequestQueue(context, network, diskCacheSize)
  }

  private fun newRequestQueue(context: Context, network: Network, diskCacheSize: Int): RequestQueue {
    val cacheDir = File(context.cacheDir, DEFAULT_CACHE_DIR)
    val queue = RequestQueue(DiskBasedCache(cacheDir, diskCacheSize), network)
    queue.start()
    return queue
  }


}