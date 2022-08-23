package com.pixabay.ui.base

sealed class Resource<T>(val data: T? = null, val message: String? = null) {

    class Success<T>(data: T?) : Resource<T>(data)

    class Loading<T>(data: T? = null) : Resource<T>(data)

    class Error<T>(message: String = "Undefined", data: T? = null, val cause: Throwable? = null) :
        Resource<T>(data, message)

    companion object {

        fun <T> loading(): Resource<T> {
            return Loading()
        }

        fun <T> showLoading(data: T? = null): Resource<T> {
            return Loading(data)
        }

        fun <T> success(): Resource<T> {
            return Success(null)
        }

        fun <T> success(data: T?): Resource<T> {
            return Success(data)
        }

        fun <T> error(message: String = "Undefined", data: T? = null, error: Throwable? = null): Resource<T> {
            return Error(message, data, error)
        }

    }
}