package com.ericdecanini.currencyconverter.mvp.model

import com.google.gson.JsonObject

class CurrencyList {

    var base: String = ""

    var date: String = ""

    lateinit var rates: JsonObject

}