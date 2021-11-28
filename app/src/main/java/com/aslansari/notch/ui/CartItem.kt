package com.aslansari.notch.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aslansari.notch.R
import com.aslansari.notch.ui.theme.NotchTheme

data class ItemState(val hasAdded: Boolean)

@Composable
fun CartItemScreen(name: String, onCardClicked: (Boolean) -> Unit) {
    var state by remember { mutableStateOf(ItemState(false)) }
    CartItem(name = name, onCardClicked = {
        state = ItemState(!state.hasAdded)
        onCardClicked.invoke(state.hasAdded)
    }, state)
}

@Composable
fun CartItem(name: String, onCardClicked: () -> Unit, itemState: ItemState) {
    Surface (
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 80.dp)
            .clickable { onCardClicked() },
        tonalElevation = 2.dp,
        shape = RoundedCornerShape(4.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .padding(start = 10.dp),
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(5.dp)
                    .weight(4f),
            )
            val resId = if (itemState.hasAdded) {
                R.drawable.ic_check_circle_24
            } else {
                R.drawable.ic_shopping_basket_24
            }
            Icon(
                painter = painterResource(id = resId),
                contentDescription = "",
                modifier = Modifier
                    .weight(1f)
                    .size(36.dp)
            )
        }
    }
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .heightIn(min = 80.dp)
//            .clickable { onCardClicked() },
//        backgroundColor = MaterialTheme.colors.surface,
//        elevation = 1.dp,
//        shape = RoundedCornerShape(4.dp)
//    ) {
//
//    }
}

@Preview
@Composable
fun CardPreview() {
    NotchTheme {
        CartItem("Cheese", {}, ItemState(false))
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun CardPreviewNight() {
    NotchTheme {
        CartItem("Cheese", {}, ItemState(false))
    }
}