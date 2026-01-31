package com.pmdm.mygamestore.presentation.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.pmdm.mygamestore.R
import com.pmdm.mygamestore.presentation.ui.componentes.LabeledTextFieldGS
import com.pmdm.mygamestore.presentation.ui.componentes.RoundedButton
import com.pmdm.mygamestore.presentation.ui.componentes.TextFieldGS
import com.pmdm.mygamestore.presentation.ui.theme.dimens

@Composable
fun LoginScreen() {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        topBar = {},
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

                //Campo Username ccon etiqueta

                LabeledTextFieldGS(
                    label = "Username",
                    value = username,
                    onValueChange = { username = it },
                    placeholder = "Enter your username",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.small))

                // Campo de Password con etiqueta

                LabeledTextFieldGS(
                    label = "Password",
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "Enter your password",
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.large))

                RoundedButton(
                    texto = "Login",
                    colorFondo = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxWidth().height(MaterialTheme.dimens.buttonHeightMedium),
                    onClick = {}
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
        }

    }
}
