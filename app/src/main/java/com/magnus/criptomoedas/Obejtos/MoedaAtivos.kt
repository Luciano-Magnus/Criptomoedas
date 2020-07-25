package com.magnus.criptomoedas

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MoedaAtivos(
    var high: String?,
    var low: String,
    var vol: String,
    var last: String,
    var buy: String,
    var sell: String,
    var opem: String,
    var data: String

) {
    override fun toString(): String {
        return data
    }

}