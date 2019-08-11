package com.ericdecanini.currencyconverter.utility

class Helper {

    companion object {

        fun getCountryCodeConstant(countryCode: String): Int = when (countryCode) {
            "AUD" -> 0
            "BGN" -> 1
            "BRL" -> 2
            "CAD" -> 3
            "CHF" -> 4
            "CNY" -> 5
            "CZK" -> 6
            "DKK" -> 7
            "GBP" -> 8
            "HKD" -> 9
            "HRK" -> 10
            "HUF" -> 11
            "IDR" -> 12
            "ILS" -> 13
            "INR" -> 14
            "ISK" -> 15
            "JPY" -> 16
            "KRW" -> 17
            "MXN" -> 18
            "MYR" -> 19
            "NOK" -> 20
            "NZD" -> 21
            "PHP" -> 22
            "PLN" -> 23
            "RON" -> 24
            "RUB" -> 25
            "SEK" -> 26
            "SGD" -> 27
            "THB" -> 28
            "TRY" -> 29
            "USD" -> 30
            "ZAR" -> 31
            "EUR" -> 32
            else -> -1
        }

        fun getCountryIcon(countryCode: String): Int = Countries.countryIcons[getCountryCodeConstant(countryCode)]

        fun getCountryName(countryCode: String): String = Countries.countryNames[getCountryCodeConstant(countryCode)]

    }


}