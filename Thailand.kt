package com.diewland.thailand

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.alibaba.fastjson.JSON

class Thailand(
    context: Context,
    resId: Int,
    val tvDistrict: AutoCompleteTextView,
    val tvAmphoe: AutoCompleteTextView,
    val tvProvince: AutoCompleteTextView,
    val tvZipcode: AutoCompleteTextView
) {
    private var db: List<Address>
    private var itemsDistrict: ArrayList<String>
    private var itemsAmphoe: ArrayList<String>
    private var itemsProvince: ArrayList<String>
    private var itemsZipcode: ArrayList<String>
    private var adaptDistrict: ArrayAdapter<String>
    private var adaptAmphoe: ArrayAdapter<String>
    private var adaptProvince: ArrayAdapter<String>
    private var adaptZipcode: ArrayAdapter<String>

    init {
        db = loadAssets(context)
        itemsDistrict = ArrayList(db.map { it.district }.distinct().sorted())
        itemsAmphoe = ArrayList(db.map { it.amphoe }.distinct().sorted())
        itemsProvince = ArrayList(db.map { it.province }.distinct().sorted())
        itemsZipcode = ArrayList(db.map { it.zipcode.toString() }.distinct().sorted())
        adaptDistrict = ArrayAdapter(context, resId, itemsDistrict)
        adaptAmphoe = ArrayAdapter(context, resId, itemsAmphoe)
        adaptProvince = ArrayAdapter(context, resId, itemsProvince)
        adaptZipcode = ArrayAdapter(context, resId, itemsZipcode)

        listOf(
            Pack("district", tvDistrict, adaptDistrict, itemsDistrict),
            Pack("amphoe",   tvAmphoe,   adaptAmphoe,   itemsAmphoe),
            Pack("province", tvProvince, adaptProvince, itemsProvince),
            Pack("zipcode",  tvZipcode,  adaptZipcode,  itemsZipcode),
        ).forEach { (label, tv, adapt, adaptItems) ->
            tv.apply {
                threshold = 1
                setAdapter(adapt)
                setOnClickListener { if (text.isEmpty()) showDropDown() }
                setOnFocusChangeListener { v, hasFocus ->
                    if (hasFocus) {
                        val items = craftItems(label)
                        adaptItems.clear()
                        adaptItems.addAll(items.map { it.get(label) }.distinct().sorted())
                        adapt.notifyDataSetChanged()
                        if (text.isEmpty()) v.post { showDropDown() }
                    }
                }
            }
        }
    }

    private fun loadAssets(context: Context, filename: String="raw_database.json"): List<Address> {
        val raw = context.assets.open(filename).bufferedReader().use { it.readText() }
        return JSON.parseArray(raw, Address::class.javaObjectType) ?: listOf()
    }

    private fun craftItems(label: String): List<Address> {
        return when (label) {
            "district" -> craftItems(false, true, true, true)
            "amphoe"   -> craftItems(true, false, true, true)
            "province" -> craftItems(true, true, false, true)
            "zipcode"  -> craftItems(true, true, true, false)
            else       -> listOf() // fallback
        }
    }

    private fun craftItems(district: Boolean,
                           amphoe: Boolean,
                           province: Boolean,
                           zipcode: Boolean): List<Address> {
        var items = db.toList() // shallow copy, share ref
        if (district) {
            val t = tvDistrict.text.toString().trim()
            if (t.isNotEmpty()) items = items.filter { it.district == t }
        }
        if (amphoe) {
            val t = tvAmphoe.text.toString().trim()
            if (t.isNotEmpty()) items = items.filter { it.amphoe == t }
        }
        if (province) {
            val t = tvProvince.text.toString().trim()
            if (t.isNotEmpty()) items = items.filter { it.province == t }
        }
        if (zipcode) {
            val t = tvZipcode.text.toString().trim()
            if (t.isNotEmpty()) items = items.filter { it.zipcode.toString() == t }
        }
        return items
    }

}