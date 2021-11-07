package com.aslansari.notch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aslansari.notch.ui.CartItemScreen
import com.aslansari.notch.ui.theme.NotchTheme

class MainActivity : ComponentActivity() {

    private var itemList: MutableList<Item> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotchTheme {
                itemList = remember { mutableStateListOf() }
                Scaffold(
                    floatingActionButton = {
                        FAB(onClick = { itemList.add(Item("Cheese", false)) })
                    }
                ) {}
                ListContent(itemList = itemList)
            }
        }
    }
}

@Composable
fun ListContent(itemList: MutableList<Item>) {
    LazyColumn (
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp)
    ){
        items(items = itemList) {
            println("Item: name=${it.name}")
            CartItemScreen(name = it.name) { hasAdded ->
                it.isAcquired = hasAdded
            }
        }
    }
}

@Composable
fun FAB(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        backgroundColor = Color.Blue,
        contentColor = Color.White,
        shape = RoundedCornerShape(20.dp)
    ){
        Icon(Icons.Filled.Add,"")
    }
}