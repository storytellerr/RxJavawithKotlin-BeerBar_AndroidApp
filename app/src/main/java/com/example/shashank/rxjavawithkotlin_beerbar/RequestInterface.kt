package com.example.shashank.rxjavawithkotlin_beerbar

import io.reactivex.Observable
import retrofit2.http.GET

interface RequestInterface {

    @GET("beercraft")
    fun getData() : Observable<List<ApiModel>>
}