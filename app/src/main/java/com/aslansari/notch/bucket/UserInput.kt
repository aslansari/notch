package com.aslansari.notch.bucket

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aslansari.notch.R
import com.aslansari.notch.ui.BackPressHandler
import com.aslansari.notch.ui.theme.NotchTheme

enum class InputSelector {
    NONE,
    MAP,
    DM,
    EMOJI,
    PHONE,
    PICTURE
}

@Preview
@Composable
fun UserInputPreview() {
    NotchTheme {
        UserInput(onMessageSent = {})
    }
}

@Composable
fun UserInput(
    onMessageSent: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var textState by remember { mutableStateOf(TextFieldValue()) }

    var currentInputSelector by rememberSaveable { mutableStateOf(InputSelector.NONE) }
    val dismissKeyboard = { currentInputSelector = InputSelector.NONE }

    // Intercept back navigation if there's a InputSelector visible
    if (currentInputSelector != InputSelector.NONE) {
        BackPressHandler(onBackPressed = dismissKeyboard)
    }

    // Used to decide if the keyboard should be shown
    var textFieldFocusState by remember { mutableStateOf(false) }

    Surface (tonalElevation = 2.dp) {
        Column (modifier = modifier) {
            UserInputText(
                textValue = textState,
                keyboardShown = currentInputSelector == InputSelector.NONE && textFieldFocusState,
                onTextChanged = {textState = it},
                onTextFieldFocused = { focused ->
                    if (focused) {
                        currentInputSelector = InputSelector.NONE
                    }
                    textFieldFocusState = focused
                },
                focusState = textFieldFocusState
            )
            UserInputSelector(
                onMessageSent = {
                    onMessageSent(textState.text)
                    // TODO: 27.11.2021 onMessageSent
                    // TODO: 27.11.2021 dismiss keyboard
                }
            )
        }
    }
}

val KeyboardShownKey = SemanticsPropertyKey<Boolean>("KeyboardShownKey")
var SemanticsPropertyReceiver.keyboardShownProperty by KeyboardShownKey

@Preview
@Composable
fun UserInputTextPreview() {
    NotchTheme {
        UserInputText(
            textValue = TextFieldValue("test"),
            keyboardShown = false,
            onTextChanged = {},
            onTextFieldFocused = {},
            focusState = false
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun UserInputTextPreviewDark() {
    NotchTheme {
        UserInputText(
            textValue = TextFieldValue("test"),
            keyboardShown = false,
            onTextChanged = {},
            onTextFieldFocused = {},
            focusState = false
        )
    }
}

@Composable
fun UserInputText(
    textValue: TextFieldValue,
    keyboardShown: Boolean,
    onTextChanged: (TextFieldValue) -> Unit,
    onTextFieldFocused: (Boolean) -> Unit,
    focusState: Boolean
) {
    val a11ylabel = stringResource(id = R.string.text_button_enter) // TODO: 28.11.2021 update desc
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .semantics {
                contentDescription = a11ylabel
                keyboardShownProperty = keyboardShown
            },
        horizontalArrangement = Arrangement.End
    ) {
        Surface {
            Box (
                modifier = Modifier
                    .height(64.dp)
                    .weight(1f)
                    .align(Alignment.Bottom)
            ) {
                var lastFocusState by remember { mutableStateOf(false)}
                BasicTextField(
                   value = textValue,
                   onValueChange = onTextChanged,
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(18.dp)
                       .align(Alignment.CenterStart)
                       .onFocusChanged { state ->
                           if (lastFocusState != state.isFocused) {
                               // TODO: 27.11.2021 trigger onTextFocusChange
                               onTextFieldFocused(state.isFocused)
                           }
                           lastFocusState = state.isFocused
                       },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    cursorBrush = SolidColor(LocalContentColor.current),
                    textStyle = LocalTextStyle.current.copy(color = LocalContentColor.current)
                )

                if (textValue.text.isEmpty() && !focusState) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(32.dp),
                        text = stringResource(id = R.string.text_field_hint)
                    )
                }
            }
        }
    }
}

@Composable
fun UserInputSelector(
    onMessageSent: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Button(
            modifier = Modifier
                .height(36.dp),
            onClick = onMessageSent
        ) {
            Text(
                text = stringResource(id = R.string.text_button_enter),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}