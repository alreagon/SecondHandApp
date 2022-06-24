package com.example.finalprojectbinaracademy_secondhandapp.utils

import java.text.NumberFormat
import java.util.*

class ConvertNumberTo {

    fun rupiah(number: Double): String{
        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(number).toString()
    }
}