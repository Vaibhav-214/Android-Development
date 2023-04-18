package com.example.week6.ui.screens.settingsscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.week6.R
import com.example.week6.utility.components.CustomBottomAppBar
import com.example.week6.utility.components.HorizontalSpacer
import com.example.week6.utility.components.ReusableIconButton
import com.example.week6.utility.components.ReusableTopBar
import com.example.week6.utility.components.VerticalSpacer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsScreenViewModel,
    navController: NavHostController
) {

    val language by remember {
        viewModel.language
    }.collectAsState()


    Scaffold(
        topBar = {
            ReusableTopBar(
                title = R.string.settings,
                iconId = R.drawable.settings
            )
        },
    ) { pad ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
                .background(
                    color = MaterialTheme.colorScheme.primary
                )
        ) {

            LanguageSelect(
                onEnglish = { viewModel.saveLanguage("en") },
                onHindi = { viewModel.saveLanguage("hi") },
                language = language
            )

            CustomBottomAppBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                containerColor = MaterialTheme.colorScheme.secondary

            ) {
                ReusableIconButton(iconId = R.drawable.user, onClick = {navController.popBackStack()})

                ReusableIconButton(iconId = R.drawable.settings)
            }
        }
    }
}

@Composable
fun LanguageSelect(
    onHindi: () -> Unit,
    onEnglish: () -> Unit,
    language: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(text = "App Language", style = MaterialTheme.typography.headlineMedium)

        VerticalSpacer(height = 20)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            Button(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(20.dp)
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (language == "en") MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.secondary
                ),
                onClick = {
                    onEnglish()
                }) {
                Text(
                    text = "English",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            HorizontalSpacer(width = 10)

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(20.dp),
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (language == "hi") MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.secondary,
                ),
                onClick = {
                    onHindi()
                }) {
                Text(
                    text = "Hindi",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )
            }

        }
    }
}

