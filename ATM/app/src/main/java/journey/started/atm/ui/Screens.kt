package journey.started.atm.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import journey.started.atm.Destinations
import journey.started.atm.R
import journey.started.atm.ui.theme.ATMTheme
import java.text.NumberFormat


@Composable
    fun BalanceScreen(appViewModel: AppViewModel = viewModel()) {
        val appUiState by appViewModel.uiState.collectAsState()
        Column(modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()) {
            Spacer(modifier = Modifier.size(30.dp))
            Text(text = stringResource(id = R.string.check), fontSize = 35.sp)
            Spacer(modifier = Modifier.size(height = 50.dp, width = 50.dp))
            Text(text = stringResource(id = R.string.showBalance),
                modifier = Modifier.padding(), fontSize = 30.sp)
            Text(fontSize = 25.sp, text = NumberFormat.getCurrencyInstance().format(appUiState.balance) )

        }

    }

    @Composable
    fun Deposit(navHostController: NavHostController, appViewModel: AppViewModel = viewModel()) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = stringResource(id = R.string.account), fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(10.dp))
            OutlinedTextField(value = appViewModel.newDeposit ,
                onValueChange = {appViewModel.changeDeposit(it)},
                label = {
                    Text(text = stringResource(id = R.string.dep))
                },keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number ,imeAction = ImeAction.Go)
            )
            Spacer(modifier = Modifier.padding(20.dp))
            Button(onClick =
            {
                appViewModel.addDeposit()
                navHostController.navigate(Destinations.BALANCE.name)
            }
            ) {// always put parenthesis while calling any function
                Text(text = "PROCEED", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
        Text(text = "Deposit Money", fontSize = 35.sp, modifier = Modifier.padding(top = 50.dp, start = 20.dp))
    }

    @Composable
    fun Withdraw(appViewModel: AppViewModel = viewModel(),navHostController: NavHostController
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = stringResource(id = R.string.account), fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(10.dp))
            OutlinedTextField(value = appViewModel.amount ,
                onValueChange = {appViewModel.showWithdraw(it)},
                label = {
                    Text(text = stringResource(id = R.string.dep))
                },keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number ,imeAction = ImeAction.Go)
            )
            Spacer(modifier = Modifier.padding(20.dp))
            Button(onClick = { appViewModel.withdraw()}) {
                Text(text = "PROCEED", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
        Text(text = "Withdraw Money", fontSize = 35.sp, modifier = Modifier.padding(top = 50.dp, start = 20.dp))
    }

    @Composable
    fun StartScreen(modifier: Modifier = Modifier.fillMaxSize(), appViewModel: AppViewModel = viewModel(),
                    navHostController: NavHostController
                    ) {
        Column(modifier = modifier) {
            Text(text = stringResource(id =R.string.bankName),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(50.dp),
                fontSize = 44.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.size(140.dp))
            OutlinedTextField(value = appViewModel.pin, onValueChange ={appViewModel.changePin(it)},
                label = { Text(text = stringResource(id = R.string.enter)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp), shape = CircleShape,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number ,imeAction = ImeAction.Go)
                , keyboardActions = KeyboardActions(onGo = {
                    if(appViewModel.checkPin()) navHostController.navigate(Destinations.OPTIONS.name)
                })
            )
        }
    }






@Preview(showBackground = true)
@Composable
fun SecondPreview() {
    ATMTheme {
        //Deposit()
    }
}


