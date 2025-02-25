package com.example.mercadolibreapp.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.mercadolibreapp.R
import com.example.mercadolibreapp.helpers.ContentDescription

/**
 * @author Axel Sanchez
 */
@Composable
fun HomeScreen(
    navigateSearchScreen: (String) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.colorPrimary))
    ) {
        val (logo, editText) = createRefs()
        Logo(Modifier.constrainAs(logo) {
            top.linkTo(parent.top)
            bottom.linkTo(editText.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
        InputProduct(
            Modifier
                .constrainAs(editText) {
                    top.linkTo(logo.bottom)
                    bottom.linkTo(parent.bottom)
                }
                .padding(start = 60.dp, end = 60.dp), navigateSearchScreen)

        createVerticalChain(logo, editText, chainStyle = ChainStyle.Packed)
    }
}

@Composable
fun Logo(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.mercadolibre),
        contentDescription = ContentDescription.LOGO,
        alignment = Alignment.Center,
        modifier = modifier.size(200.dp, 200.dp)
    )
}

@Composable
fun InputProduct(modifier: Modifier, navigateSearchScreen: (String) -> Unit) {
    var query by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current

    TextField(
        value = query,
        onValueChange = { newText ->
            query = newText
        },
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = colorResource(id = R.color.black),
            backgroundColor = colorResource(id = R.color.white)
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                if(query.isEmpty()) {
                    Toast.makeText(context, context.resources.getString(R.string.debe_ingresar_una_busqueda), Toast.LENGTH_SHORT).show()
                } else{
                    navigateSearchScreen(query)
                }
            }
        ),
        placeholder = { Text(text = stringResource(R.string.busqueda)) },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = ContentDescription.START_ICON
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(25.dp))

    )
}