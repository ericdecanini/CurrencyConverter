package com.ericdecanini.currencyconverter

import com.ericdecanini.currencyconverter.mvp.presenter.CurrenciesPresenter
import com.ericdecanini.currencyconverter.mvp.view.CurrenciesView
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import org.mockito.*


@RunWith(MockitoJUnitRunner::class)
class CurrenciesActivityTests {

    // Simple Mock test with calculation of values
    // Probably the best thing I can do, test-wise
    // I only decided to approach TDD recently
    // So I do hope I'll improve in this area moving forward

    @Mock
    lateinit var currenciesView: CurrenciesView
    lateinit var currenciesPresenter: CurrenciesPresenter

    @Test
    fun whenTopValueIsChanged_thenOtherValuesAreCalculatedCorrectly() {
        val baseValue = 2.5
        currenciesPresenter = CurrenciesPresenter(currenciesView)
        currenciesPresenter.baseValue = baseValue

        // Start with Euro as the base currency
        val euroConversionRate = 1.0
        val poundConversionRate = 0.90163
        val audConversionRate = 1.6225
        val delta = 0.0001

        var expectedPoundValue = (baseValue / euroConversionRate) * poundConversionRate
        var actualPoundValue = currenciesPresenter.convertCurrencies(euroConversionRate, poundConversionRate)
        assertEquals(expectedPoundValue, actualPoundValue, delta)

        // Trying Australian Dollar as the base currency
        expectedPoundValue = (baseValue / audConversionRate) * poundConversionRate
        actualPoundValue = currenciesPresenter.convertCurrencies(audConversionRate, poundConversionRate)
        assertEquals(expectedPoundValue, actualPoundValue, delta)
    }

}
