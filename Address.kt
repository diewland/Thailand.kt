package com.diewland.thailand

data class Address (
    val district: String,
    val amphoe: String,
    val province: String,
    val zipcode: Int,
    val district_code: Int,
    val amphoe_code: Int,
    val province_code: Int,
) {
    fun get(key: String): String  {
        return when (key) {
            "district"      -> district
            "amphoe"        -> amphoe
            "province"      -> province
            "zipcode"       -> zipcode.toString()
            "district_code" -> district_code.toString()
            "amphoe_code"   -> amphoe_code.toString()
            "province_code" -> province_code.toString()
            else            -> "not support"
        }
    }
}