package com.example.hexagon.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

object DateInputMask {

    fun applyDateMask(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            private var isUpdating: Boolean = false
            private var oldText = ""

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) {
                    isUpdating = false
                    return
                }

                val str = s.toString().replace(Regex("[^\\d]"), "")
                var formattedStr = oldText

                if (str.length > 8) {
                    formattedStr = oldText
                } else if (str.length > 4) {
                    formattedStr = "${str.substring(0, 2)}/${str.substring(2, 4)}/${str.substring(4)}"
                } else if (str.length > 2) {
                    formattedStr = "${str.substring(0, 2)}/${str.substring(2)}"
                } else {
                    formattedStr = str
                }

                isUpdating = true
                oldText = formattedStr
                editText.setText(formattedStr)
                editText.setSelection(formattedStr.length)
            }
        })
    }
}
