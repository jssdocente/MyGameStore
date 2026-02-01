package com.pmdm.mygamestore.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pmdm.mygamestore.R
import com.pmdm.mygamestore.presentation.ui.componentes.LabeledTextFieldGS
import com.pmdm.mygamestore.presentation.ui.componentes.RoundedButton
import com.pmdm.mygamestore.presentation.ui.theme.dimens
import com.pmdm.mygamestore.presentation.viewmodel.LoginViewModel
import com.pmdm.mygamestore.presentation.viewmodel.LoginViewModelFactory

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(LocalContext.current)
    ),
    onLoginSuccess: () -> Unit = {}
) {
    // Observar el estado del ViewModel
    val uiState by viewModel.uiState.collectAsState()

    // Estado para mostrar mensajes con Snackbar
    val snackbarHostState = remember { SnackbarHostState() }

    // Efecto para mostrar errores
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { error ->
            snackbarHostState.showSnackbar(error)
            viewModel.clearError()
        }
    }

    // Efecto para navegar cuando el login es exitoso
    LaunchedEffect(uiState.isLoginSuccessful) {
        if (uiState.isLoginSuccessful) {
            onLoginSuccess()
            viewModel.resetLoginSuccess()
        }
    }

    Scaffold(
        topBar = {},
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier.padding(MaterialTheme.dimens.paddingMedium)
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.large))
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.extraLarge))

                // Campo Username con etiqueta
                LabeledTextFieldGS(
                    label = "Username",
                    value = uiState.username,
                    onValueChange = { newValue -> viewModel.onUsernameChange(newValue)},
                    placeholder = "Enter your username",
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isLoading
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.small))

                // Campo de Password con etiqueta
                LabeledTextFieldGS(
                    label = "Password",
                    value = uiState.password,
                    onValueChange = { newValue -> viewModel.onPasswordChange(newValue)},
                    placeholder = "Enter your password",
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    enabled = !uiState.isLoading
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.large))

                // Botón de Login
                RoundedButton(
                    texto = if (uiState.isLoading) "Loading..." else "Login",
                    colorFondo = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(MaterialTheme.dimens.buttonHeightMedium),
                    onClick = { viewModel.onLoginClick() },
                    enabled = !uiState.isLoading
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.large))

                // Botón Registro
                Text(
                    text = "Register",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.weight(1f))

                // Texto al final de la página
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(bottom = MaterialTheme.dimens.paddingMedium)
                )
            }

            // Loading indicator centrado
            if (uiState.isLoading) {

                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = 100.dp), // Desplaza 100dp hacia abajo
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}


