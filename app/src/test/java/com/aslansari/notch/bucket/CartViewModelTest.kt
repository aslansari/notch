package com.aslansari.notch.bucket

import com.aslansari.notch.bucket.persistence.Item
import com.aslansari.notch.bucket.persistence.ItemDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.*

@RunWith(JUnit4::class)
class CartViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: CartViewModel
    private lateinit var itemDAO: ItemDAO
    @Before
    fun setup() {

        itemDAO = mock {
            on { getItems() } doReturn flow {  }
        }

        val cartRepository = CartRepository(itemDAO)
        viewModel = CartViewModel(mainCoroutineRule.testDispatcher, cartRepository)
    }

    @Test
    fun `Testing adding item`(): Unit = mainCoroutineRule.testDispatcher.runBlockingTest {
        viewModel.addItem(getItem())
        viewModel.addItem(getItem())
        val testResults = mutableListOf<MutableList<Item>>()
        val job = launch {
            viewModel.itemListFlow.toList(testResults.toMutableList())
        }
        verify(itemDAO) {
            2 * { insertItem(any())}
        }
        job.cancel()
    }

    @Test
    fun `Testing updating item`(): Unit = mainCoroutineRule.testDispatcher.runBlockingTest {
        val item = getItem()
        viewModel.addItem(item)
        viewModel.updateItem(item.copy(name = "newName"))
        val testResults = mutableListOf<MutableList<Item>>()
        val job = launch {
            viewModel.itemListFlow.toList(testResults.toMutableList())
        }
        verify(itemDAO) {
            1 * { insertItem(any()) }
            1 * { updateItem(anyVararg()) }
        }
        job.cancel()
    }

    @Test
    fun `Testing deleting item`(): Unit = mainCoroutineRule.testDispatcher.runBlockingTest {
        val note = getItem()
        viewModel.addItem(note)
        viewModel.deleteItem(note.id)
        val testResults = mutableListOf<MutableList<Item>>()
        val job = launch {
            viewModel.itemListFlow.toList(testResults.toMutableList())
        }
        verify(itemDAO) {
            1 * { insertItem(any()) }
            1 * { deleteItem(any()) }
        }
        job.cancel()
    }

    fun getItem(): Item {
        return Item(
            name = "Cheese",
            isAcquired = false
        )
    }

    /**
     * Although it looks deprecated it
     */
    class MainCoroutineRule(
        val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    ) : TestWatcher() {

        override fun starting(description: Description?) {
            super.starting(description)
            Dispatchers.setMain(testDispatcher)
        }

        override fun finished(description: Description?) {
            super.finished(description)
            Dispatchers.resetMain()
            testDispatcher.cleanupTestCoroutines()
        }
    }
}