package com.gonzalezcs.covidnewmodule.ui.utils


sealed class StateView<T> {

    class Loading<T>(val visibility: Int) : StateView<T>()
    data class Success<T>(val data: T): StateView<T>()
    data class Error<T>(val message:String): StateView<T>()

    companion object {

        fun <T> loading(visibility: Int) : StateView<T>{
            return Loading(visibility)
        }

        fun <T> success(data:T): StateView<T>{
            return Success(data)
        }

        fun <T> error(message: String): StateView<T>{
            return Error(message)
        }
    }
}