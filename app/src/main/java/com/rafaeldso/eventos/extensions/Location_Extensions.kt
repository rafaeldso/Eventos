package com.rafaeldso.eventos.extensions

import android.content.Context
import android.location.Geocoder
import java.util.*


fun conversorDeCoordenadasEmEndereco(latitude : Double, longitude : Double, context: Context): String{
    val geocoder = Geocoder(context, Locale.getDefault())
    var endereco = "";
    val addresses = geocoder.getFromLocation(latitude, longitude, 1)
    if (addresses != null && addresses.size > 0) {
        val address: String = addresses.get(0).getAddressLine(0)
        endereco = address
    }
    return endereco

}