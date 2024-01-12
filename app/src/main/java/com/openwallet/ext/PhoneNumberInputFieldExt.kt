package com.openwallet.ext

import android.text.method.DigitsKeyListener
import android.widget.EditText

fun PhoneNumberInputField.setInputConstraint(accepted: String) {
    val et_phone: EditText = findViewById(R.id.phoneNumberInputFieldPhoneNumber)
    et_phone.keyListener = DigitsKeyListener.getInstance(accepted)
}