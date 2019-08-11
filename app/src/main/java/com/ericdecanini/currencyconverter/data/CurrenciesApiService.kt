package com.ericdecanini.currencyconverter.data

import com.ericdecanini.currencyconverter.mvp.model.CurrencyList
import io.reactivex.Observable
import retrofit2.http.GET

interface CurrenciesApiService {

    @GET("latest?base=EUR")
    fun getCurrencies(): Observable<CurrencyList>

}