package com.aslansari.notch.bucket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.aslansari.notch.MainViewModel
import com.aslansari.notch.NotchApp
import com.aslansari.notch.bucket.persistence.Item
import com.aslansari.notch.ui.LocalBackPressedDispatcher
import com.aslansari.notch.ui.theme.NotchTheme
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ViewWindowInsetObserver
import com.google.accompanist.insets.navigationBarsPadding

class CartFragment : Fragment() {

    private val activityViewModel: MainViewModel by activityViewModels()
    private val cartViewModel: CartViewModel by activityViewModels(factoryProducer = {
        CartViewModelFactory((activity?.application as NotchApp).repository)
    })

    @ExperimentalMaterial3Api
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        // Create a ViewWindowInsetObserver using this view, and call start() to
        // start listening now. The WindowInsets instance is returned, allowing us to
        // provide it to AmbientWindowInsets in our content below.
        val windowInsets = ViewWindowInsetObserver(this)
            // We use the `windowInsetsAnimationsEnabled` parameter to enable animated
            // insets support. This allows our `ConversationContent` to animate with the
            // on-screen keyboard (IME) as it enters/exits the screen.
            .start(windowInsetsAnimationsEnabled = true)

        setContent {
            CompositionLocalProvider(
                LocalBackPressedDispatcher provides requireActivity().onBackPressedDispatcher,
                LocalWindowInsets provides windowInsets,
            ) {
                NotchTheme {
                    val inputShown by activityViewModel.inputFieldShouldShown.collectAsState()
                    val itemList :List<Item> by cartViewModel.itemListFlow.collectAsState()
                    Cart(
                        modifier = Modifier.navigationBarsPadding(bottom = false),
                        itemList = itemList.toMutableList(),
                        onMessageSent = {
                            cartViewModel.addItem(Item(it, false))
                            activityViewModel.itemAdded()
                        },
                        showUserInput = inputShown
                    )
                }
            }
        }
    }
}