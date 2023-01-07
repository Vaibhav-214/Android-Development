package journey.started.atm.ui.test

import journey.started.atm.ui.AppViewModel
import junit.framework.Assert.*
import org.junit.Test

class AppViewModelTest {
    val viewModel = AppViewModel()


    @Test
    fun appViewModel_ChangeDeposit_UpdateChangeDeposit() {
        var currentUiState = viewModel.uiState.value


        viewModel.changeDeposit("10000")
        val newDepo = viewModel.newDeposit
        assertEquals("10000", newDepo)

        viewModel.addDeposit()
        currentUiState = viewModel.uiState.value
        assertEquals(110000, currentUiState.balance)


    }
}