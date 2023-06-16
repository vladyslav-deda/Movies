package com.movies.presentation

import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.RadioButton
import android.widget.RadioGroup
import com.google.android.material.button.MaterialButton
import com.google.android.material.slider.RangeSlider
import com.google.android.material.textview.MaterialTextView

object DialogExtension {

    private var sortType: SortType? = null
    private var minPriceRange: Int? = null
    private var maxPriceRange: Int? = null

    fun Context.showFilteringAndSortingDialog(
        currentMinPriceValue: Float,
        currentMaxPriceValue: Float,
        generalMinPriceValue: Float,
        generalMaxPriceValue: Float,
        currentSortType: SortType?,
        onApplyClicked: (newSortType: SortType?, newPriceRange: Pair<Int, Int>?) -> Unit
    ) = Dialog(this).apply {
        sortType = currentSortType
        minPriceRange = currentMinPriceValue.toInt()
        maxPriceRange = currentMaxPriceValue.toInt()
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.filtering_and_sorting_dialog_layout)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val priceFromTextView = findViewById<MaterialTextView>(R.id.price_from_value)
        val priceToTextView = findViewById<MaterialTextView>(R.id.price_to_value)

        this@showFilteringAndSortingDialog.setPriceInTextView(
            currentMinPriceValue.toInt(),
            priceFromTextView
        )
        this@showFilteringAndSortingDialog.setPriceInTextView(
            currentMaxPriceValue.toInt(),
            priceToTextView
        )

        findViewById<RangeSlider>(R.id.price_slider).apply {
            valueFrom = generalMinPriceValue
            valueTo = generalMaxPriceValue
            setValues(currentMinPriceValue, currentMaxPriceValue)
            addOnChangeListener { slider, _, _ ->
                val priceFrom = slider.values[0].toInt()
                val priceTo = slider.values[1].toInt()
                this@showFilteringAndSortingDialog.setPriceInTextView(
                    priceFrom,
                    priceFromTextView
                )
                this@showFilteringAndSortingDialog.setPriceInTextView(
                    priceTo,
                    priceToTextView
                )
                minPriceRange = priceFrom
                maxPriceRange = priceTo
            }
        }
        val sortCheckBoxGroup = findViewById<RadioGroup>(R.id.sort_by_radio_group)
        SortType.values().forEach {
            sortCheckBoxGroup.addView(this@showFilteringAndSortingDialog.createStatusCheckBox(it))
        }
        findViewById<MaterialButton>(R.id.apply_button).setOnClickListener {
            val newSortType = if (sortType == currentSortType) null else sortType
            val newPriceRange =
                if (minPriceRange != currentMinPriceValue.toInt() || maxPriceRange != currentMaxPriceValue.toInt()) {
                    Pair(minPriceRange!!, maxPriceRange!!)
                } else {
                    null
                }
            onApplyClicked(newSortType, newPriceRange)
            dismiss()
        }
    }.show()

    private fun Context.setPriceInTextView(value: Int, textView: MaterialTextView) {
        textView.text = this.getString(R.string.price_with_dollar_currency, value)
    }

    private fun Context.createStatusCheckBox(value: SortType): RadioButton {
        return RadioButton(this).apply {
            id = View.generateViewId()
            text = value.text
            buttonTintList =
                ColorStateList.valueOf(this@createStatusCheckBox.getColor(R.color.iris_blue))
            setTextColor(this@createStatusCheckBox.getColor(R.color.white))
            isChecked = value == sortType
            setOnCheckedChangeListener { buttonView, _ ->
                if (buttonView.isPressed) {
                    sortType = value
                }
            }
        }
    }
}