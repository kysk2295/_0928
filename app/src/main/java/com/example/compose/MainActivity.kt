package com.example.compose
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import com.example.compose.ui.theme.ComposeTheme
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                // A surface container using the 'background' color from the theme
                ContentView {
                    startActivity(Intent(this,ChatRoomActivity::class.java))
                }

            }
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun ContentView(navigateToDetail: (RandomUser) -> Unit){
    Surface(color = MaterialTheme.colors.background) {
        val navControll= rememberNavController()
        Scaffold(
            bottomBar = {MyBottomBar(navControll)}) {
            BottomBarMain(navControll,navigateToDetail = navigateToDetail)
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun BottomBarMain(navControll: NavHostController, navigateToDetail: (RandomUser) -> Unit) {
    NavHost(navControll, startDestination = Screen.Home.route ){
        composable(Screen.Chat.route){
            ChatScreen(navigateToDetail = navigateToDetail)
        }
        composable(Screen.Home.route){
            HomeScreen()
        }
        composable(Screen.Bookcase.route){
            BookcaseScreen()
        }
        composable(Screen.Profile.route){
            ProfileScreen()
        }
    }
}

@Composable
fun ChatScreen(navigateToDetail: (RandomUser) -> Unit){
    Scaffold(topBar = { MyAppBar()}) {
        RandomUserListView(randomUsers = DataProvider.userList,navigateToDetail=navigateToDetail)
    }

}

@ExperimentalFoundationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun HomeScreen(){
    Scaffold {

        val textState= remember{ mutableStateOf(TextFieldValue(""))}
        MySearhView(textState)
        val tabs= listOf(TabItem.Recent, TabItem.Philosophy,
            TabItem.Literature,TabItem.Fiction, TabItem.Computer,
            TabItem.Toeic)

        val pagerState = rememberPagerState(pageCount = tabs.size)
        TextTabs(tabs,pagerState)
        TabsContent(tabs = tabs, pagerState = pagerState)

    }

}

@ExperimentalFoundationApi
@Composable
fun RecentScreen(){

    Column(modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Top) {
        GridItemView()
    }





}

@ExperimentalFoundationApi
@Composable
fun GridItemView() {
    val list =(1..20).map{  }

    LazyVerticalGrid(cells = GridCells.Fixed(3) ,
        content = {
                  items(list.size){ index: Int ->

                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Card(modifier = Modifier.size(72.dp,91.dp),
                        backgroundColor = Color.Gray, elevation = 8.dp) {

                        }
                        Text(text = "오징어게임", fontSize = 14.sp, fontStyle = FontStyle.Normal, color = Color.Black)
                        Text(text ="곽건" , fontSize = 12.sp, color = Color.LightGray)

                    }
                      
                  }
        }, contentPadding = PaddingValues(start = 0.dp, top = 0.dp),)
}

@Composable
fun LiteratureScreen(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .wrapContentSize(Alignment.Center)
            .offset(0.dp, 160.dp)
    ) {
        Text(text = "Literature View")
    }

}


@Composable
fun FictionScreen(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(text = "Fiction View")
    }

}


@Composable
fun PhilosophyScreen(){
    Column(

        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(text = "Philosophy View", modifier = Modifier.align(Alignment.CenterHorizontally))
    }

}


@Composable
fun ComputerScreen(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(text = "Computer View")
    }

}

@Composable
fun ToeicScreen(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Toeic View")
    }

}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun TabsContent(tabs: List<TabItem>, pagerState: PagerState){
    HorizontalPager(state = pagerState, modifier = Modifier.offset(0.dp,240.dp)) { page ->
        tabs[page].screen()


    }
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun TextTabs(tabs: List<TabItem>, pagerState: PagerState){
    val scope = rememberCoroutineScope()
//    var tabIndex by remember { mutableStateOf(0)}
//    var tabdata = listOf("최근", "문학","소설","철학","컴퓨터","토익")

    ScrollableTabRow(selectedTabIndex =
        pagerState.currentPage,modifier =Modifier.offset(0.dp,156.dp),
    backgroundColor = Color.White,
        edgePadding=10.dp,
    divider = {
        TabRowDefaults.Divider(
            thickness = 0.dp
        )
    }, indicator = {tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.customTabIndicatorOffset(tabPositions[pagerState.currentPage]),
                height = 3.dp,
                color = Color.Red
            )
        }) {

        val interactionSource = remember { MutableInteractionSource() }

        tabs.forEachIndexed{ index, tab ->
            Tab(selected = pagerState.currentPage == index,
                onClick = {
                scope.launch{
                    pagerState.animateScrollToPage(index)
                }


            }, text={
                Text(text = tab.title, color = Color.Black,  )
            }, modifier = Modifier
                    .padding(0.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        enabled = true

                    ) {})
                

        }
    }

}

@Composable
fun Modifier.customTabIndicatorOffset(currentTabPosition: TabPosition) : Modifier = composed(
inspectorInfo = debugInspectorInfo {
    name="tabIndicatorOffset"
    value=currentTabPosition
}
) {
    val indicatorWidth = 32.dp
    val currenttabWidth = currentTabPosition.width
    val indicatorOffset by animateDpAsState(
        targetValue = currentTabPosition.left + currenttabWidth/2 - indicatorWidth/2,
    animationSpec = tween(durationMillis = 100,easing = LinearEasing))
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = indicatorOffset)
        .width(indicatorWidth)
}

@Composable
fun MySearhView(state: MutableState<TextFieldValue>){

    Column(modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center) {

        Card(elevation = 20.dp,
            modifier = Modifier
                .size(295.dp, 50.dp)
                .offset(0.dp, 70.dp),
        shape = RoundedCornerShape(5.dp)) {

    TextField(value = state.value, onValueChange = { value->
        state.value=value
    },
    modifier = Modifier.fillMaxSize(),

    textStyle = TextStyle(color = Color.Gray,fontSize = 13.sp),

        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription ="",
            modifier = Modifier
                .size(20.dp))
        },
        trailingIcon = {
            if (state.value != TextFieldValue("")) {
                IconButton(onClick = {
                    state.value = TextFieldValue("")
                })
                {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .size(20.dp)

                    )

                }
            }
        },
        singleLine = true,
        shape = RectangleShape,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            leadingIconColor = Color.LightGray,
            trailingIconColor = Color.LightGray,
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )


    )
        }
    }



}

@Composable
fun BookcaseScreen(){
    Text(
        text = Screen.Bookcase.title,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.primary,
        fontSize = 20.sp
    )
}
@Composable
fun ProfileAppBar(){
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
            Image(painter = painterResource(id = R.drawable.ic_menu), contentDescription = null,
                contentScale = ContentScale.Fit)
            Text(text = "프로필",fontSize = 16.sp,fontWeight = FontWeight.Bold)
            Image(painter = painterResource(id = R.drawable.ic_edit), contentDescription = null,
                contentScale = ContentScale.Fit)


        }
    }

}
@Composable
fun ProfileScreen(){
    Scaffold(topBar ={ ProfileAppBar()} ) {
        Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,) {

            Card(modifier = Modifier
                .size(156.dp)
                .offset(0.dp, 120.dp),
            shape = CircleShape,
            elevation = 16.dp, backgroundColor = Color.LightGray) {
            }


        }
    }
}
@Composable
fun RandomUserListView(randomUsers:List<RandomUser>, navigateToDetail: (RandomUser) -> Unit){
    LazyColumn(){
        items(randomUsers){
            //for문을 돌아 randomuser 클래스 하나를 넘겨준다
            RandomUserView(it,navigateToDetail=navigateToDetail)
        }
    }
}

@Composable
fun RandomUserView(randomuser:RandomUser, navigateToDetail:(RandomUser) -> Unit){
    val typography:Typography= MaterialTheme.typography

    Column() {
        Row(modifier = Modifier
            .height(74.dp)
            .padding(20.dp, 10.dp)
            .fillMaxWidth()
            .clickable { navigateToDetail(randomuser) },
            verticalAlignment = Alignment.CenterVertically){
            ProfileImg(imgUrl = randomuser.ProfileImg)

            Column {
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp)){
                    Text(text = randomuser.name, style = typography.body2)
                    Text(text = randomuser.timestamp, style = typography.h2)
                }

                Text(text = randomuser.recentmsg, style = typography.h1
                    ,modifier = Modifier.padding(10.dp,2.dp))
            }
        }

        Divider(color = Color.LightGray, thickness = 0.5.dp,
        modifier = Modifier.padding(20.dp,0.dp))

    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun ProfileImg(imgUrl:String, modifier: Modifier=Modifier){
    val bitmap : MutableState<Bitmap?> = mutableStateOf(null)
    val imagemodifier=
        modifier
            .size(47.dp, 47.dp)
            .clip(RoundedCornerShape(10.dp))

    Glide.with(LocalContext.current)
        .asBitmap()
        .load(imgUrl)
        .into(object : CustomTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                bitmap.value=resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {

            }

        })

        //비트맵이 있다면
//        if(bitmap != null)
//        {
//            Image(bitmap = bitmap!!.asImageBitmap(),
//                contentDescription = null,
//                contentScale = ContentScale.Fit,
//                modifier = imagemodifier)
//        }else{
//            Image(painter = painterResource(id = R.drawable.ic_baseline_account_circle_24),
//                contentDescription = null,
//                contentScale = ContentScale.Fit,
//                modifier = imagemodifier)
//
//        }
        bitmap.value?.asImageBitmap()?.let { fetchedBitmap ->
            Image(bitmap = fetchedBitmap, contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = imagemodifier)
        } ?: Image(painter = painterResource(id = R.drawable.ic_baseline_account_circle_24),
            contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = imagemodifier)







}
@Composable
fun MyBottomBar(navControll: NavController) {
    val items = listOf(
        Screen.Chat,
        Screen.Home,
        Screen.Bookcase,
        Screen.Profile
    )

    BottomNavigation(elevation = 10.dp) {
        val navBackStackEntry by navControll.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.map {
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = it.icon),
                        contentDescription = it.title
                    )
                },
                selected = currentRoute == it.route,
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(alpha = 0.4f),
                onClick = {
                    navControll.navigate(it.route)
                }

            )

        }



    }



//    val selectedState = remember {
//        mutableStateOf("home")
//    }
//    BottomAppBar(
//        elevation = 10.dp
//    ) {
//        BottomNavigationItem(
//            icon = {
//                Icon(Icons.Filled.Home, "Home")
//            },
//            selected = selectedState.value=="home",
//            onClick = {
//                selectedState.value="home"
//            },
//            selectedContentColor = Color.White,
//            unselectedContentColor = Color.White.copy(alpha = 0.4f)
//        )
//
//        BottomNavigationItem(
//            icon = {
//                Icon(Icons.Filled.Menu, "Menu")
//            },
//            selected = selectedState.value=="menu",
//            onClick = {
//                selectedState.value="menu"
//            },
//            selectedContentColor = Color.White,
//            unselectedContentColor = Color.White.copy(alpha = 0.4f)
//        )
//
//
//    }
}

@Composable
fun MyAppBar() {
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
            Text(text = "채팅",fontSize = 16.sp,fontWeight = FontWeight.Bold)
            Image(painter = painterResource(id = R.drawable.ic_search), contentDescription = null,
                contentScale = ContentScale.Fit)

        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    ComposeTheme {
//        ContentView()
//    }
//}