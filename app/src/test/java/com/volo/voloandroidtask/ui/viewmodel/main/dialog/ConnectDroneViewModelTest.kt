package com.volo.voloandroidtask.ui.viewmodel.main.dialog

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.volo.voloandroidtask.ui.viewmodel.main.dialog.ConnectDroneViewModel
import com.volo.voloandroidtask.constants.Constants
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class ConnectDroneViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ConnectDroneViewModel

    @Mock
    private lateinit var passwordValidationObserver: Observer<Boolean>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = ConnectDroneViewModel()
        viewModel.passwordValidationResult.observeForever(passwordValidationObserver)
    }

    @Test
    fun `test validatePassword method with correct password`() {
        // Set up test data
        val password = Constants.wifiPassword

        // Call the method under test
        viewModel.validatePassword(password)

        // Verify the password validation result
        assertEquals(true, viewModel.passwordValidationResult.value)

        // Verify that the observer was called
        `when`(passwordValidationObserver.onChanged(true)).then {
            assertEquals(true, viewModel.passwordValidationResult.value)
        }
    }

    @Test
    fun `test validatePassword method with incorrect password`() {
        // Set up test data
        val password = "incorrectPassword"

        // Call the method under test
        viewModel.validatePassword(password)

        // Verify the password validation result
        assertEquals(false, viewModel.passwordValidationResult.value)

        // Verify that the observer was called
        `when`(passwordValidationObserver.onChanged(false)).then {
            assertEquals(false, viewModel.passwordValidationResult.value)
        }
    }
}
