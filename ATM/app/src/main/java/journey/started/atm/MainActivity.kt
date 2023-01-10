package journey.started.atm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import journey.started.atm.ui.*
import journey.started.atm.ui.theme.ATMTheme

enum class Destinations() {
    START, OPTIONS, BALANCE, DEPOSIT, WITHDRAW
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ATMTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavigationHost(navController = navController, appViewModel = AppViewModel())

                }
            }
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController, appViewModel: AppViewModel = viewModel()) {
    NavHost(navController = navController, startDestination = Destinations.START.name) {
        // startDestination gives error until we add curly braces
        composable(Destinations.START.name) { StartScreen(navHostController = navController)}
        composable(Destinations.OPTIONS.name) { OptionScreen(navController = navController)}
        composable(Destinations.BALANCE.name) { BalanceScreen(appViewModel = appViewModel)}
        composable(Destinations.DEPOSIT.name) { Deposit(navHostController = navController, appViewModel = appViewModel)}
        composable(Destinations.WITHDRAW.name) { Withdraw(navHostController = navController) }
    }
    }
    








