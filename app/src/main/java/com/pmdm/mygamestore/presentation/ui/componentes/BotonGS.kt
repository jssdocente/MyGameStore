package com.pmdm.mygamestore.presentation.ui.componentes

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pmdm.mygamestore.presentation.ui.theme.dimens

@Composable
fun BaseBoton(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colorFondo: Color = MaterialTheme.colorScheme.primary, // Color por defecto
    colorTexto: Color = MaterialTheme.colorScheme.onPrimary,
    shape: RoundedCornerShape
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorFondo,
            contentColor = colorTexto
        ),
        shape = shape
    ) {
        Text(text = texto,style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun RoundedButton(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(MaterialTheme.dimens.small),
    colorFondo: Color = MaterialTheme.colorScheme.primary, // Color por defecto
    colorTexto: Color = MaterialTheme.colorScheme.onPrimary,
) {
    BaseBoton(
        texto = texto,
        onClick = onClick,
        colorFondo = colorFondo,
        colorTexto = colorTexto,
        shape = shape,
        modifier = modifier
    )
}

