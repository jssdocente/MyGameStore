package com.pmdm.mygamestore.presentation.ui.screens
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pmdm.mygamestore.presentation.ui.theme.dimens

@Composable
fun HomeScreen() {
    Text(
        text = "Home Screen",
        modifier = Modifier.padding(MaterialTheme.dimens.paddingMedium)
    )
}
