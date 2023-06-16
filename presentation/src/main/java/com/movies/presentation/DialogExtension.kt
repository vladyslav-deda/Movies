package com.movies.presentation

import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.RadioButton
import android.widget.RadioGroup
import com.google.android.material.slider.RangeSlider
import com.google.android.material.textview.MaterialTextView

fun Context.showFilteringAndSortingDialog(
    minPriceValue: Float,
    maxPriceValue: Float
) = Dialog(this).apply {
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    setContentView(R.layout.filtering_and_sorting_dialog_layout)
    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    val priceFromTextView = findViewById<MaterialTextView>(R.id.price_from_value)
    val priceToTextView = findViewById<MaterialTextView>(R.id.price_to_value)
    priceFromTextView.text = this@showFilteringAndSortingDialog.getString(
        R.string.price_with_dollar_currency,
        minPriceValue.toInt()
    )
    priceToTextView.text = this@showFilteringAndSortingDialog.getString(
        R.string.price_with_dollar_currency,
        maxPriceValue.toInt()
    )
    val rangeSlider = findViewById<RangeSlider>(R.id.price_slider)
    setRangeSliderValues(rangeSlider, minPriceValue, maxPriceValue)
    rangeSlider.addOnChangeListener { slider, _, _ ->
        priceFromTextView.text = this@showFilteringAndSortingDialog.getString(
            R.string.price_with_dollar_currency,
            slider.values[0].toInt()
        )
        priceToTextView.text = this@showFilteringAndSortingDialog.getString(
            R.string.price_with_dollar_currency,
            slider.values[1].toInt()
        )
    }
    val sortCheckBoxGroup = findViewById<RadioGroup>(R.id.sort_by_radio_group)
    SortByValues.values().forEach {
        sortCheckBoxGroup.addView(this@showFilteringAndSortingDialog.createStatusCheckBox(it))
    }
}.show()


private fun setRangeSliderValues(rangeSlider: RangeSlider, minValue: Float, maxValue: Float) {
    rangeSlider.valueFrom = minValue
    rangeSlider.valueTo = maxValue
    rangeSlider.setValues(minValue, maxValue)
}

private fun Context.createStatusCheckBox(value: SortByValues): RadioButton {
    return RadioButton(this).apply {
        text = value.text
        buttonTintList =
            ColorStateList.valueOf(this@createStatusCheckBox.getColor(R.color.iris_blue))
        setTextColor(this@createStatusCheckBox.getColor(R.color.white))
    }
}