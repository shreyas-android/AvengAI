package com.androidai.demo.avengerad.data

internal sealed class SuccessState<T>{
    
    data class Success<T>(val data: T): SuccessState<T>()
    
    data class Failure<T>(val adUnitId:String, val error: Throwable): SuccessState<T>()
    
}