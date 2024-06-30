package com.example.moodup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.moodup.viewmodel.HomeViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations

class HomeViewModelTest {


    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel


    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = HomeViewModel()
    }

    @Test
    fun `md5 generates correct hash`() {
        val input = "test"
        val expected = "098f6bcd4621d373cade4e832627b4f6"
        val result = viewModel.md5(input)
        assert(result == expected)
    }
}
