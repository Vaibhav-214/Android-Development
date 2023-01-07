package journey.started.atm.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import journey.started.atm.Destinations
import journey.started.atm.R
import journey.started.atm.ui.theme.ATMTheme

@Composable
fun OptionScreen(navController: NavHostController) {

    Column(modifier = Modifier
        .fillMaxSize()
        , horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = stringResource(id = R.string.choice), textAlign = TextAlign.Center,
            color = colorResource(id = R.color.white),fontWeight = FontWeight.Bold ,fontSize = 40.sp,
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.first))
                .padding(top = 99.dp, bottom = 98.dp)

        )
        Text(text = stringResource(id = R.string.balance),
            color = colorResource(id = R.color.white),
            textAlign = TextAlign.Center, fontSize = 30.sp,
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.second))
                .padding(bottom = 55.dp, top = 55.dp)
                .clickable {navController.navigate(Destinations.BALANCE.name) }
        )
        Text(text = stringResource(id = R.string.deposit),
            color = colorResource(id = R.color.white),
            textAlign = TextAlign.Center,fontSize = 30.sp,
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.third))
                .padding(bottom = 55.dp, top = 55.dp)
                .clickable { navController.navigate(Destinations.DEPOSIT.name)}
        )
        Text(text = stringResource(id = R.string.withdraw),
            color = colorResource(id = R.color.white),
            textAlign = TextAlign.Center,fontSize = 30.sp,
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.fourth))
                .padding(bottom = 55.dp, top = 55.dp)
                .clickable { navController.navigate(Destinations.WITHDRAW.name)}
        )
        Text(text = stringResource(id = R.string.exit),
            color = colorResource(id = R.color.white),
            textAlign = TextAlign.Center,fontSize = 30.sp,
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.fifth))
                .padding(bottom = 55.dp, top = 55.dp)
                .clickable { navController.navigate(Destinations.START.name)}
        )

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ATMTheme {
       // OptionScreen()
    }
}

