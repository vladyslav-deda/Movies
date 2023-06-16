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

    private var sortType: SortByValues? = null

    fun Context.showFilteringAndSortingDialog(
        minPriceValue: Float,
        maxPriceValue: Float,
        currentSortType: SortByValues?,
        onApplyClicked: (newSortType: SortByValues?, newPriceRange: Pair<Int, Int>?) -> Unit
    ) = Dialog(this).apply {
        sortType = currentSortType
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.filtering_and_sorting_dialog_layout)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val priceFromTextView = findViewById<MaterialTextView>(R.id.price_from_value)
        val priceToTextView = findViewById<MaterialTextView>(R.id.price_to_value)

        this@showFilteringAndSortingDialog.setPriceInTextView(
            minPriceValue.toInt(),
            priceFromTextView
        )
        this@showFilteringAndSortingDialog.setPriceInTextView(
            maxPriceValue.toInt(),
            priceToTextView
        )

        findViewById<RangeSlider>(R.id.price_slider).apply {
            valueFrom = minPriceValue
            valueTo = maxPriceValue
            setValues(minPriceValue, maxPriceValue)
            addOnChangeListener { slider, _, _ ->
                this@showFilteringAndSortingDialog.setPriceInTextView(
                    slider.values[0].toInt(),
                    priceFromTextView
                )
                this@showFilteringAndSortingDialog.setPriceInTextView(
                    slider.values[1].toInt(),
                    priceToTextView
                )
            }
        }
        val sortCheckBoxGroup = findViewById<RadioGroup>(R.id.sort_by_radio_group)
        SortByValues.values().forEach {
            sortCheckBoxGroup.addView(this@showFilteringAndSortingDialog.createStatusCheckBox(it))
        }
        findViewById<MaterialButton>(R.id.apply_button).setOnClickListener {
            val newSortType = if (sortType == currentSortType) null else sortType
            onApplyClicked(newSortType, null)
            dismiss()
        }
    }.show()

    private fun Context.setPriceInTextView(value: Int, textView: MaterialTextView) {
        textView.text = this.getString(R.string.price_with_dollar_currency, value)
    }

    private fun Context.createStatusCheckBox(value: SortByValues): RadioButton {
        return RadioButton(this).apply {
            id = View.generateViewId()
            text = value.text
            buttonTintList =
                ColorStateList.valueOf(this@createStatusCheckBox.getColor(R.color.iris_blue))
            setTextColor(this@createStatusCheckBox.getColor(R.color.white))
            isChecked = value == sortType
            setOnCheckedChangeListener { buttonView, isChecked ->
                if (buttonView.isPressed) {
                    sortType = value
                }
            }
        }
    }
}