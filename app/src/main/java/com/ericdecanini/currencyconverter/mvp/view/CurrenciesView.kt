package com.ericdecanini.currencyconverter.mvp.view

import com.ericdecanini.currencyconverter.mvp.model.Currency

interface CurrenciesView {

    fun showCurrencies(currencies: ArrayList<Currency>)

    fun performConversion(newBaseValue: Double, currencies: ArrayList<Currency>)

    fun setCurrencyText(position: Int, value: Double)

    fun showProgressBar()

    fun hideProgressBar()

    fun showError()

}