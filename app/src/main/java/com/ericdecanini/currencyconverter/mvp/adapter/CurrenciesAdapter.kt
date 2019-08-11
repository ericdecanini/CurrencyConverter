package com.ericdecanini.currencyconverter.mvp.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ericdecanini.currencyconverter.mvp.model.Currency
import com.ericdecanini.currencyconverter.mvp.view.CurrenciesView
import com.ericdecanini.currencyconverter.utility.Helper
import kotlinx.android.synthetic.main.list_item_currency.view.*


class CurrenciesAdapter(val currenciesView: CurrenciesView, private var currencies: ArrayList<Currency>, val mClickListener: OnItemClickListener) :
    RecyclerView.Adapter<CurrenciesAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(com.ericdecanini.currencyconverter.R.layout.list_item_currency, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = currencies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currency = currencies[position]
        val countryCode = currency.code

        // Update Views
        holder.view.textview_currencycode.text = countryCode
        holder.view.textview_currency.text = Helper.getCountryName(countryCode)
        holder.view.imageview_country.setImageResource(Helper.getCountryIcon(countryCode))

        // Update Values
        holder.view.edittext_currency.setText((String.format("%.2f",   currencies[position].conversionRate)))

        // Add EditText Listener
        if (position == 0) {
            holder.view.edittext_currency.isEnabled = true
            val textWatcher = object: TextWatcher {
                override fun afterTextChanged(text: Editable?) { updateCurrencies(text.toString()) }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            }
            holder.view.edittext_currency.addTextChangedListener(textWatcher)
            holder.view.edittext_currency.tag = textWatcher
        }

        // Add ItemClickListener
        holder.view.setOnClickListener { mClickListener.onItemClick(currencies, position) }

    }

    fun updateCurrencies(text: String) {
        val newBaseValue = if (text.isEmpty()) 0.0 else text.toDouble()
        currenciesView.performConversion(newBaseValue, currencies)
    }

    fun updateView(view: View, position: Int) {
        if (position == 0) {
            view.edittext_currency.isEnabled = true
            val textWatcher = object: TextWatcher {
                override fun afterTextChanged(text: Editable?) { updateCurrencies(text.toString()) }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            }
            view.edittext_currency.addTextChangedListener(textWatcher)
            view.edittext_currency.tag = textWatcher
        } else {
            if (view.edittext_currency.tag != null)
                view.edittext_currency.removeTextChangedListener(view.edittext_currency.tag!! as TextWatcher)
        }
        view.setOnClickListener { mClickListener.onItemClick(currencies, position) }
    }

    interface OnItemClickListener {
        fun onItemClick(currencies: ArrayList<Currency>, position: Int)
    }


}