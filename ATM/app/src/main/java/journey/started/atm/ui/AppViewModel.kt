package journey.started.atm.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import journey.started.atm.data.DataSource.PIN
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel:ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()



    var pin by mutableStateOf("")
    fun changePin(enteredPin: String) {
        pin = enteredPin
    }
   fun checkPin():Boolean {
       if (pin.toInt() == PIN) {
           return true
       }else {
           _uiState.update { currentState ->
               currentState.copy(
                   enteredPinWrong = true
               )
           }
           return false
       }

   }
    var newDeposit by mutableStateOf("")


    fun changeDeposit(enteredValue: String) {
        newDeposit = enteredValue
    }
    fun addDeposit() {
        updateUiState(newDeposit,true)
        changeDeposit("")
    }
    var amount by mutableStateOf("")

    fun showWithdraw(enteredAmount: String) {
        amount = enteredAmount
    }
    fun withdraw() {
        updateUiState(amount,false)
        showWithdraw("")

    }

    private fun updateUiState(amount: String, increase: Boolean) {
        if (increase) {
            val increasedAmount = _uiState.value.balance.plus(amount.toInt())
            _uiState.update { currentState ->
                currentState.copy(
                    balance = increasedAmount

                )
            }
        }else {
            val decreasedAmount = _uiState.value.balance.minus(amount.toInt())
            _uiState.update { currentState ->
                currentState.copy(
                    balance = decreasedAmount
                )
            }
        }
    }
}