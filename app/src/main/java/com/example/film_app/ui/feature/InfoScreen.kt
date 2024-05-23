package com.example.film_app.ui.feature

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun InfoScreen(navController: NavHostController) {

    val context = LocalContext.current

    Column(
        Modifier
            .fillMaxSize()
    ) {

        InfoToolbar { navController.popBackStack() }

        Info(
            {
                val recipientEmail = it // ایمیل شما
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:$recipientEmail")
                intent.putExtra(Intent.EXTRA_SUBJECT, "موضوع ایمیل")
                intent.putExtra(Intent.EXTRA_TEXT, "متن ایمیل")
                context.startActivity(intent)
            },
            {
                val linkedInProfileUrl = "https://www.linkedin.com/in/$it" // لینک حساب کاربری شما
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(linkedInProfileUrl)
                context.startActivity(intent)
            },
            {
                val instagramProfileUrl = "https://www.instagram.com/$it" // لینک حساب کاربری شما
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(instagramProfileUrl)
                context.startActivity(intent)
            }
        )

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoToolbar(onBackCLicked: () -> Unit) {

    TopAppBar(
        title = { Text(
            text = "Info",
            Modifier
                .fillMaxWidth()
                .padding(end = 42.dp),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        ) },
        navigationIcon = {
            IconButton(onClick = { onBackCLicked.invoke() }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = null)
            }
        },
        modifier = Modifier.fillMaxWidth()
    )

}

@Composable
fun Info(onEmailClicked: (String) -> Unit , onLinkedInClicked: (String) -> Unit , onInstagramClicked: (String) -> Unit) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Film Center",
                style = TextStyle(
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(start = 32.dp, top = 12.dp)
            )


        }

        Column {

            Text(
                text = "Description: ",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(top = 32.dp , start = 32.dp)
            )

            Text(
                text = "Search the application and find your favorite movie. You can see the movie trailers and decide on the movie selection" ,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light
                ),
                modifier = Modifier
                    .padding(start = 32.dp , top = 18.dp , end = 32.dp),
                lineHeight = 32.sp
            )

        }

        HorizontalDivider(
            modifier = Modifier.padding(top = 32.dp)
        )

        Row {

            Text(
                text = "Developer: ",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(top = 32.dp , start = 32.dp)
            )

            Text(
                text = " Mohammad Hosein Hajiakbari ",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light
                ),
                modifier = Modifier
                    .padding(top = 32.dp)
            )

        } // Dev

        Row {

            Text(
                text = "Email: ",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(top = 32.dp , start = 32.dp)
            )

            Text(
                text = " m.hosein.developer@gmail.com",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light
                ),
                modifier = Modifier
                    .padding(top = 32.dp)
                    .clickable { onEmailClicked.invoke("m.hosein.developer@gmail.com") }
            )

        } // Email

        Row {

            Text(
                text = "LinkedIn: ",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(top = 32.dp , start = 32.dp)
            )

            Text(
                text = " Mohammad Hosein Hajiakbari ",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light
                ),
                modifier = Modifier
                    .padding(top = 32.dp)
                    .clickable { onLinkedInClicked.invoke("mohammad-hosein-hajiakbari-662337246") }
            )

        } // LinkedIn

        Row {

            Text(
                text = "Instagram: ",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(top = 32.dp , start = 32.dp)
            )

            Text(
                text = " android.coder ",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light
                ),
                modifier = Modifier
                    .padding(top = 32.dp)
                    .clickable { onInstagramClicked.invoke("android.coder") }
            )

        } // Instagram

        HorizontalDivider(
            modifier = Modifier.padding(top = 32.dp)
        )

        Text(
            text = "Click on one of the IDs above to contact the developer of this application.",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Light
            ),
            modifier = Modifier
                .padding(start = 32.dp , top = 32.dp , end = 32.dp),
            lineHeight = 32.sp
        )

    }

}