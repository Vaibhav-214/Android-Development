package journey.started.atm.ui

data class UiState(
    val balance: Int = 100000,
   // val currentEnteredValue: String= "",
    val enteredPinWrong:Boolean = false,
)
