package com.ericdecanini.currencyconverter.mvp.presenter

import android.content.Context
import com.ericdecanini.currencyconverter.data.CurrenciesApiService
import com.ericdecanini.currencyconverter.mvp.model.Currency
import com.ericdecanini.currencyconverter.mvp.view.CurrenciesView
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class CurrenciesPresenter(val mView: CurrenciesView) {

    var baseValue: Double = 1.0
    var loaded = false

    var currencies = ArrayList<Currency>()
    var positions = ArrayList<Int>()

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://revolut.duckdns.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    private val disposable = CompositeDisposable()

    // TODO: This method needs to be revised due to a noticeable drop in performance every time the interval is passed
    fun createCurrenciesObservable(context: Context) {
        // Update the UI
        mView.showProgressBar()

        // Check for network connection
        val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting

        if (!isConnected) {
            mView.showError()
            return
        }

        // Create the Observable
        val currenciesApi = retrofit.create(CurrenciesApiService::class.java)

        val currenciesObservable = Observable.interval(1, TimeUnit.SECONDS)
            .switchMap{ currenciesApi.getCurrencies() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { mView.showError() }
            .map { result ->
                val countryCodes = ArrayList<Currency>()
                // Add Base Currency (Euro)
                val base = result.base
                countryCodes.add(Currency(base, 1.0, 1.0))

                // Add List of CurrencyList
                val keys = result.rates.entrySet().iterator()
                while (keys.hasNext()) {
                    val key = keys.next()
                    countryCodes.add(Currency(key.component1(), key.component2().asDouble, key.component2().asDouble))
                }

            return@map countryCodes
            }
            .subscribe { currencies ->
                mView.hideProgressBar()
                if (!loaded) {
                    for (i in 0 until currencies.size) {
                        positions.add(i)
                    }

                    this.currencies = currencies
                    mView.showCurrencies(currencies)
                    loaded = true
                } else {
                    // Find out the positions of each of the currencies
                    // then recalculate their values according to the top value
                    this.currencies[0].value = baseValue
                    for (index in 0 until positions.size) {
                        this.currencies[index] = currencies[positions[index]]
                        mView.performConversion(baseValue, this.currencies)
                    }
                }

            }

        disposable.add(currenciesObservable)
    }

    fun updateListValues(newBaseValue: Double, currencies: ArrayList<Currency>) {
        baseValue = newBaseValue
        currencies[0].value = newBaseValue

        for (position in 1 until currencies.size) {
            currencies[position].value = convertCurrencies(currencies[0].conversionRate, currencies[position].conversionRate)
            mView.setCurrencyText(position, currencies[position].value)
        }

        this.currencies = currencies
    }

    fun convertCurrencies(cRate1: Double, cRate2: Double): Double {
        return (baseValue / cRate1) * cRate2
    }


    fun moveCurrencyToTop(currencies: ArrayList<Currency>, position: Int) {
        val temp = currencies[position]
        val tempPosition = positions[position]

        for (index in position downTo 1) {
            currencies[index] = currencies[index - 1]
            positions[index] = positions[index - 1]
        }
        currencies[0] = temp
        positions[0] = tempPosition

        baseValue = currencies[0].value
        this.currencies = currencies
    }

    fun destroy() {
        disposable.clear()
    }


}