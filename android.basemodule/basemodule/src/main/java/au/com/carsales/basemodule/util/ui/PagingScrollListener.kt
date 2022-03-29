package au.com.carsales.basemodule.util.ui

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.functions.Action


class PagingScrollListener(private val LIMIT: Int? = 20) : RecyclerView.OnScrollListener() {

    private var emitter: ObservableEmitter<Int?>? = null

    fun setEmitter(emitter: ObservableEmitter<Int?>?) {
        this.emitter = emitter
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy == 0) return
        if (emitter?.isDisposed == false) {
            val position = getLastVisibleItemPosition(recyclerView)
            val updatePosition = recyclerView.adapter?.itemCount ?: 0 - 1 - (LIMIT?.div(2) ?: 0)
            if (position >= updatePosition - 1) {
                emitter?.onNext((recyclerView.adapter?.itemCount ?: 0) - 1)
            }
        }
    }

    private fun getLastVisibleItemPosition(recyclerView: RecyclerView?): Int {
        val manager = recyclerView?.layoutManager as LinearLayoutManager?
        return manager?.findLastVisibleItemPosition() ?: 0
    }
}

fun RecyclerView.listOffsetAsObservable(limit: Int? = null): Observable<Int?>? {
    val listener = PagingScrollListener(limit)
    return Observable
            .create(ObservableOnSubscribe<Int?> { emmiter ->
                listener.setEmitter(emmiter)
                addOnScrollListener(listener)
            })
            .doOnTerminate { removeOnScrollListener(listener) }
            .distinctUntilChanged()
}
