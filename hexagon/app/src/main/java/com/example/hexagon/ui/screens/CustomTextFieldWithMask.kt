package com.example.hexagon.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.hexagon.utils.applyDateMask

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextFieldWithMask(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(text = value)) }

    TextField(
        value = textFieldValue,
        onValueChange = { newValue ->
            val maskedText = applyDateMask(newValue.text)
            val newCursorPosition = maskedText.length.coerceAtMost(maskedText.length)


            textFieldValue = TextFieldValue(
                text = maskedText,
                selection = androidx.compose.ui.text.TextRange(newCursorPosition)
            )

            onValueChange(maskedText)
        },
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}
