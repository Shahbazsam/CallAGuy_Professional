package com.example.callaguy_professional.core.domain




sealed interface Result<out D , out E: Error> {
    data class Success<out D >(val data : D) :Result< D , Nothing>
    data class Error<out E : com.example.callaguy_professional.core.domain.Error>(val error : E):Result<Nothing , E>
}



inline fun <T, E: Error, R> Result<T,E>.map( map : (T) -> R ): Result<R, E> {

    return when(this){
        is Result.Error -> this
        is Result.Success -> Result.Success(map(data))
    }
}

inline fun <T, E: Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T ,E> {
    return when(this){
        is Result.Success -> {
            action(data)
            this
        }
        is Result.Error -> this
    }
}

inline fun <T, E: Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E>{
    return when(this){
        is Result.Success -> this
        is Result.Error -> {
            action(error)
            this
        }
    }
}

fun <T, E: Error> Result<T, E>.asEmptyResult() : EmptyResult<E> {
    return map {  }
}

typealias EmptyResult<E> = Result<Unit, E>