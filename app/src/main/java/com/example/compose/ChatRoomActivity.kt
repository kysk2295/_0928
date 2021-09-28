package com.example.compose

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class ChatRoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController= rememberNavController()
            val context= LocalContext.current
        abc(navController,context)




        }
    }
}

@Composable
fun abc(navController: NavHostController, context: Context)
{


    Surface(color = MaterialTheme.colors.background) {
        Scaffold(topBar = { ChatRoomAppBar(navController,context) }) {

        }
    }
}

@Composable
fun ChatRoomAppBar(navController: NavHostController, context: Context) {
    TopAppBar(elevation = 10.dp,
        backgroundColor = Color.White,
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)) {
            Image(painter = painterResource(id = R.drawable.back), contentDescription = null,
                contentScale = ContentScale.Fit, modifier = Modifier.clickable {
                    //navController.navigate(Screen.Chat.route)
                    val activity=context as Activity
                    activity.onBackPressed()

                })
            Text(text = "The Animal Farm",fontSize = 16.sp,fontWeight = FontWeight.Bold)
            Image(painter = painterResource(id = R.drawable.ic_menu), contentDescription = null,
                contentScale = ContentScale.Fit)


        }
    }

}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    ComposeTheme {
//    abc(navController)
//    }
//}