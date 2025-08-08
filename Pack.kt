package com.diewland.thailand

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView

data class Pack(
    val label: String,
    val tv: AutoCompleteTextView,
    val adapt: ArrayAdapter<String>,
    val adaptItems: ArrayList<String>,
)
