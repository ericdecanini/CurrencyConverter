package com.ericdecanini.currencyconverter.mvp.activitiy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ericdecanini.currencyconverter.R
import com.ericdecanini.currencyconverter.mvp.adapter.CurrenciesAdapter
import com.ericdecanini.currencyconverter.mvp.model.Currency
import com.ericdecanini.currencyconverter.mvp.presenter.CurrenciesPresenter
import com.ericdecanini.currencyconverter.mvp.view.CurrenciesView
import kotlinx.android.synthetic.main.activity_currencies.*
import kotlinx.android.synthetic.main.list_item_currency.view.*
import kotlinx.android.synthetic.main.splash_error.*
import kotlin.collections.ArrayList

class CurrenciesActivity : AppCompatActivity(), CurrenciesView, CurrenciesAdapter.OnItemClickListener {

    val mPresenter = CurrenciesPresenter(this)
    lateinit var mAdapter: CurrenciesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currencies)
        mPresenter.createCurrenciesObservable(this)

        button_retry.setOnClickListener { mPresenter.createCurrenciesObservable(this) }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.destroy()
    }

    override fun showCurrencies(currencies: ArrayList<Currency>) {
        mAdapter = CurrenciesAdapter(this, currencies, this)
        recyclerview_currencies.layoutManager = LinearLayoutManager(this)
        recyclerview_currencies.adapter = mAdapter
    }

    override fun performConversion(newBaseValue: Double, currencies: ArrayList<Currency>) {
        mPresenter.updateListValues(newBaseValue, currencies)
    }

    override fun setCurrencyText(position: Int, value: Double) {
            recyclerview_currencies.getChildAt(position).edittext_currency.setText((
                    String.format("%.2f", value)))
    }

    override fun onItemClick(currencies: ArrayList<Currency>, position: Int) {
        // Clear any focus from any currently selected edittext
        currentFocus?.clearFocus()

        // Move selected currency to top and shift everything else down
        mPresenter.moveCurrencyToTop(currencies, position)
        mAdapter.notifyItemMoved(position, 0)

        // Reestablish the listeners with updated positions
        for (index in 0 until mPresenter.currencies.size) {
            val view = recyclerview_currencies.findViewHolderForAdapterPosition(index)?.itemView
            view?.let { v -> mAdapter.updateView(v, index) }
        }
    }

    override fun showError() {
        recyclerview_currencies.visibility = View.GONE
        progress_bar.visibility = View.GONE
        splash_error.visibility = View.VISIBLE
    }

    override fun showProgressBar() {
        recyclerview_currencies.visibility = View.GONE
        splash_error.visibility = View.GONE
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progress_bar.visibility = View.GONE
        recyclerview_currencies.visibility = View.VISIBLE
    }


}
