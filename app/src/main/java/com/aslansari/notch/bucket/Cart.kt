package com.aslansari.notch.bucket

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aslansari.notch.Item
import com.aslansari.notch.ui.CartItemScreen
import com.google.accompanist.insets.*

@Preview
@Composable
@ExperimentalMaterial3Api
fun CartPreview() {
    Cart(
        modifier = Modifier,
        itemList = mockList(),
        onMessageSent = {},
        showUserInput = true
    )
}

@ExperimentalMaterial3Api
@Composable
fun Cart(
    modifier: Modifier,
    itemList :MutableList<Item>,
    onMessageSent: (String) -> Unit,
    showUserInput: Boolean
) {
    val scrollBehavior = remember { TopAppBarDefaults.pinnedScrollBehavior() }
    val scrollState = rememberLazyListState()

    Surface (modifier = modifier) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {
                ShoppingList(
                    itemList = itemList,
                    modifier = Modifier.weight(1f),
                    scrollState = scrollState
                )
                if (showUserInput) {
                    UserInput(
                        onMessageSent = {
                            onMessageSent(it)
                        },
                        modifier = Modifier.navigationBarsWithImePadding()
                    )
                }
            }
        }
    }
}

@Composable
fun FAB(onClick: () -> Unit) {
    FloatingActionButton(
        modifier = Modifier.navigationBarsPadding(),
        onClick = onClick,
        contentColor = Color.White,
        shape = RoundedCornerShape(20.dp)
    ){
        Icon(Icons.Filled.Add,"")
    }
}

@Composable
fun ShoppingList(
    itemList: MutableList<Item>,
    modifier: Modifier,
    scrollState: LazyListState
) {
    Box(modifier = modifier) {
        LazyColumn (
            state = scrollState,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = rememberInsetsPaddingValues(
                insets = LocalWindowInsets.current.statusBars,
                additionalTop = 24.dp,
                additionalStart = 5.dp,
                additionalEnd = 5.dp
            ),
            modifier = Modifier.fillMaxSize()
        ){
            items(items = itemList) {
                println("Item: name=${it.name}")
                CartItemScreen(name = it.name) { hasAdded ->
                    it.isAcquired = hasAdded
                }
            }
        }
    }
}

fun mockList() : MutableList<Item> {
    val list = mutableListOf<Item>()
    list.apply {
        add(Item("Cheese", false))
        add(Item("Milk", true))
        add(Item("Eggs", false))
        add(Item("Juice", false))
    }
    return list
}