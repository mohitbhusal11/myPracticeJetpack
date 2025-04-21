package com.example.myjetpack

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.myjetpack.ui.theme.MyJetPackTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.GenericFontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.myjetpack.Utils.Constants
import com.example.myjetpack.viewModel.ImageViewModel
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MyJetPackTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = navBackStackEntry?.destination?.route
                        if (currentRoute != "practice/profile") {
                            BottomNavigationBar(navController = navController)
                        }
                    }, content = { padding ->
                        NavHostContainer(navController = navController, padding = padding)
                    }
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(containerColor = Color(0xFFD9EEF8)) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        Constants.BottomNavItems.forEach { navItem ->
            NavigationBarItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.findStartDestination().id)
                    }
                },
                icon = {
                    Icon(imageVector = navItem.icon, contentDescription = navItem.label)
                },
                label = {
                    Text(text = navItem.label)
                },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = Color.White,
                    indicatorColor = Color(0xFF195334)
                )
            )
        }

    }
}

@Composable
fun NavHostContainer(navController: NavHostController, padding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = Modifier.padding(padding)
    ) {
        composable("home") {
            PracticeUI(navController)
        }
        composable("search") {
            SearchScreen()
        }
        composable("profile") {
            CopilotProfileScreen()
        }
        composable("practice") {
            MyFistScreen()
        }
        composable("courseDetails/{courseName}") { backStackEntry ->
            val courseName = backStackEntry.arguments?.getString("courseName") ?: "Unknown Course"
            CourseDetailsScreen(courseName)
        }
        composable("practice/profile") {

            CopilotProfileScreen()

        }
    }

}

/*@Composable
fun PracticeUI() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0x0F000000))
        .padding(0.dp, 24.dp)
        .verticalScroll(rememberScrollState(), enabled = false)
        ) {
        PracticeTopBar()
        PracticeLazyRow(clips = listOf("Data Structures", "Algorithms", "Competitive Programming", "Python"))
        PracticeSuggestionSection()
        Text(text = "Courses", fontWeight = FontWeight.W500, fontFamily = FontFamily(Font(R.font.poppins_medium)), fontSize = 24.sp, color = Color.Black, modifier = Modifier.padding(24.dp, 0.dp))
        PracticeCourseSection(courses = listOf(Course("Geek of the year", R.drawable.ic_launcher_foreground, Color(0x1C673AB7)), Course("Geek of the year", R.drawable.ic_launcher_foreground, Color(0x1CF44336)), Course("Geek of the year", R.drawable.ic_launcher_foreground, Color(0x1CCDDC39)), Course("Geek of the year", R.drawable.ic_launcher_foreground, Color(0x1D03A9F4)), Course("Geek of the year", R.drawable.ic_launcher_foreground, Color(0x1D03A9F4))))
    }
}*/

@Composable
fun PracticeUI(navController: NavHostController) {
    val clips = listOf("Data Structures", "Algorithms", "Competitive Programming", "Python")
    var coroutine = rememberCoroutineScope()
    coroutine.launch {
        val token = Firebase.messaging.token
        Log.d("FCM token:", token.await().toString())
    }

    val courses = listOf(
        Course("Basic C++", R.drawable.ic_launcher_foreground, Color(0x1C673AB7)),
        Course("Algorithm", R.drawable.ic_launcher_foreground, Color(0x1CF44336)),
        Course("Advance C++", R.drawable.ic_launcher_foreground, Color(0x1CCDDC39)),
        Course("Advance Java", R.drawable.ic_launcher_foreground, Color(0x1D03A9F4)),
        Course("Communication", R.drawable.ic_launcher_foreground, Color(0x1C673AB7)),
        Course("Web Development", R.drawable.ic_launcher_foreground, Color(0x1CFF9800)),
        Course("Backend", R.drawable.ic_launcher_foreground, Color(0x1C388E3C)),
        Course("Android", R.drawable.ic_launcher_foreground, Color(0x1CC2185B)),
        Course("IOS", R.drawable.ic_launcher_foreground, Color(0x1C0097A7))
    )

    // Single scrolling container with proper constraints
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x0F000000))
            .padding(24.dp, 24.dp, 24.dp, 0.dp),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Full-width header items
        item(span = { GridItemSpan(maxLineSpan) }) {
            Column {
                PracticeTopBar(navController)
                PracticeLazyRow(clips = clips)
                PracticeSuggestionSection()
                Text(
                    text = "Courses",
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp),
                    fontWeight = FontWeight.W500,
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    fontSize = 24.sp,
                    color = Color.Black
                )
            }
        }

        // Grid items
        items(courses.size) { course ->
            PracticeCourseItem(navController, course = courses[course])
        }
    }
}

@Composable
fun PracticeCourseItem(navController: NavHostController, course: Course) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable {
                navController.navigate("courseDetails/${course.title}")
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = course.bgColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                text = course.title,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                color = Color.Black,
                modifier = Modifier.align(Alignment.TopStart)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    Icons.Default.PlayArrow,
                    contentDescription = "Course icon",
                    modifier = Modifier.size(25.dp)
                )
                Text(
                    text = "Start",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    color = Color.White,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF4CAF50))
                        .padding(8.dp, 4.dp)
                )
            }
        }
    }
}

@Composable
fun PracticeSuggestionSection() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(0.dp, 12.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0x5C4CAF50))
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Daily Coding",
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                color = Color.White
            )
            Text(
                text = "Do at least • 3-10 problems / day",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                color = Color.White
            )
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(150.dp))
                .background(Color(0xBA4CAF50))
        ) {
            Icon(
                Icons.Default.PlayArrow,
                contentDescription = "Play icon",
                modifier = Modifier.padding(16.dp)
            )
        }

    }
}

@Composable
fun PracticeLazyRow(clips: List<String>) {
    var selectedClipIndex by remember {
        mutableStateOf(0)
    }
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(clips.size) {
            Box(modifier = Modifier
                .padding(0.dp, 12.dp)
                .clickable { selectedClipIndex = it }
                .clip(
                    RoundedCornerShape(16.dp)
                )
                .background(
                    if (selectedClipIndex == it) Color(0x0F000000) else Color(0x258F2828)
                )) {
                Text(
                    text = clips[it],
                    modifier = Modifier.padding(24.dp, 16.dp),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_medium))
                )
            }
        }
    }
}

@Composable
fun CourseDetailsScreen(courseName: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = courseName,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "This is a dummy description for the course. Start learning to explore more about $courseName.",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Button(
            onClick = { /* Start learning action */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Start Learning")
        }
        Button(
            onClick = { /* Save for later action */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Save for Later")
        }
    }
}

@Composable
fun CopilotProfileScreen() {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Image
        Card(
            shape = CircleShape,
            modifier = Modifier
                .size(120.dp)
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        Toast
                            .makeText(context, "Profile Image Clicked", Toast.LENGTH_SHORT)
                            .show()
                    },
                contentScale = ContentScale.Crop
            )
        }

        // Name Field
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Email Field
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Phone Number Field
        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Save Button
        Button(
            onClick = {
                Toast.makeText(context, "Profile Saved", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text("Save")
        }
    }
}

@Composable
fun PracticeTopBar(navController: NavHostController) {
    Row(
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 12.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            Text(
                text = "Good morning,Joe",
                color = Color.DarkGray,
                fontWeight = FontWeight(700),
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.poppins_medium))
            )
            AnimationText(
                text = "We Wish you have a good day!",
                color = Color.Gray,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight(500),
                fontSize = 16.sp,
                modifier = Modifier
            )
        }
        Icon(
            Icons.Default.AccountCircle,
            contentDescription = "Profile icon",
            Modifier
                .size(50.dp)
                .clickable {
                    navController.navigate("practice/profile") {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.findStartDestination().id)
                    }
                },
            colorResource(id = R.color.black)
        )
    }
}

@Composable
fun AnimationText(text: String, color: Color, fontFamily: GenericFontFamily, fontWeight: FontWeight, fontSize: TextUnit, modifier: Modifier) {

    var isVisible by remember { mutableStateOf(true) }
    var coroutine = rememberCoroutineScope()

    LaunchedEffect(Unit){
        coroutine.launch {
            while (true){
                isVisible = !isVisible
                delay(1000)
            }
        }
    }

    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 1100)
    )
    Text(modifier = Modifier.alpha(alpha), text = text, color = color, fontFamily = fontFamily, fontWeight = fontWeight, fontSize = fontSize)
    /*AnimatedVisibility(visible = isVisible, enter = fadeIn(animationSpec = tween(durationMillis = 1100)), exit = fadeOut(animationSpec = tween(durationMillis = 1100))) {
    }*/


}

@Composable
fun MyFistScreen() {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var isAnimating by remember { mutableStateOf(false) }
    var color = remember { Animatable(Color.Red) }

    LaunchedEffect(isAnimating) {

        while (isAnimating){
            color.animateTo(Color.Red, animationSpec = tween(100))
            color.animateTo(Color.Gray, animationSpec = tween(100))
            color.animateTo(Color.Black, animationSpec = tween(100))
            color.animateTo(Color.Blue, animationSpec = tween(100))
            color.animateTo(Color.Cyan, animationSpec = tween(100))
            color.animateTo(Color.Green, animationSpec = tween(100))
            color.animateTo(Color.Magenta, animationSpec = tween(100))
            color.animateTo(Color.Unspecified, animationSpec = tween(100))
        }

    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.systemBars,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(color = color.value)
        ) {
            MyButton(isAnimating){isAnimating = !isAnimating}
            MyInputField()
            MySwitched()
            MyRadioButtonComponent()
            MyCheckBoxComponent()
            MyJeppackCard()
            MySnackBar()
            MyButtonToShowSnackBar(scope, snackbarHostState)
            MyAlertDialog()
            MyCircularImage()
            MyButtonWithIcon()
        }
    }
}

@Composable
fun MyButtonWithIcon() {
    val context = LocalContext.current
    Column(
        Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(
            onClick = {
                Toast.makeText(
                    context,
                    "Clicked myButtonWithIcon",
                    Toast.LENGTH_SHORT
                ).show()
            },
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color.Gray),
            contentPadding = PaddingValues(24.dp, 8.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
        ) {
            Icon(Icons.Default.Add, contentDescription = "content description", tint = Color.Gray)
            Text(text = "Add new post", color = Color.White)
        }
    }
}

@Composable
fun MyCircularImage() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val painter = painterResource(id = R.drawable.ic_launcher_foreground)
        Card(
            Modifier.size(150.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(Color.Gray),
            elevation = CardDefaults.cardElevation(18.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = "Sample Image",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .padding(8.dp),
                alignment = Alignment.Center,
                contentScale = ContentScale.Fit,
                alpha = DefaultAlpha
            )
        }
    }
}

@Composable
fun MyAlertDialog() {
    var showDialog by remember { mutableStateOf(false) }
    Column(
        Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { showDialog = true }) {
            Text(text = "Show alert dialog")
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    Button(onClick = {
                        showDialog = false
                    }) { Text(text = "Confirm") }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDialog = false
                    }) { Text(text = "Dismiss") }
                },
                icon = { Icon(Icons.Filled.Warning, contentDescription = "Warning Icon") },
                title = { Text(text = "Alert dialog title") },
                text = { Text(text = "This is description of alert dialog", color = Color.Gray) },
                modifier = Modifier.padding(8.dp),
                shape = RoundedCornerShape(16.dp),
                containerColor = Color.White,
                iconContentColor = Color.Red,
                titleContentColor = Color.Black,
                textContentColor = Color.DarkGray,
                tonalElevation = 8.dp,
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = false
                )
            )
        }
    }
}

@Composable
fun MyButtonToShowSnackBar(scope: CoroutineScope, snackbarHostState: SnackbarHostState) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = "This is snack-bar/toast message",
                    actionLabel = "Close",
                    duration = SnackbarDuration.Short
                )
            }
        }) {
            Text(text = "Show snack-bar")
        }
    }
}

@Composable
fun MySnackBar() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Snack Bar's",
            fontSize = 24.sp,
            fontWeight = FontWeight.W500,
            fontFamily = FontFamily.Cursive,
        )

        Snackbar(Modifier.padding(8.dp), dismissAction = {
            IconButton(onClick = {}) {
                Icon(Icons.Filled.Close, "Close")
            }
        }) {
            Text(text = "This is normal snack-bar with close icon only")
        }

        Snackbar(Modifier.padding(8.dp),
            action = { TextButton(onClick = {}) { Text(text = "Action") } },
            dismissAction = {
                IconButton(onClick = {}) {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = "Close"
                    )
                }
            }) {

            Text(text = "This is basic snack-bar with action item")
        }

        Snackbar(
            Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            actionOnNewLine = true,
            action = { TextButton(onClick = {}) { Text(text = "Action") } },
            dismissAction = {
                IconButton(onClick = {}) {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = "Close"
                    )
                }
            }
        ) {

            Text(text = "Snack-bar with action item below text")

        }
    }
}

@Composable
fun MyJeppackCard() {
    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(Modifier.padding(24.dp)) {
                Text(text = "This is first line of card", fontWeight = FontWeight.W700)
                Text(
                    text = "This is second line of card",
                    fontStyle = FontStyle.Italic,
                    fontSize = 24.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MaterialTheme {
//        MyButton()
//        MyFistScreen()
        SearchScreen()
//        ProfileScreen()
//        PracticeUI()
//        CopilotProfileScreen()
//        MyTextView("Hey its jetpack")
    }
}

@Composable
fun MyCheckBoxComponent() {
    val listOfCheck = listOf("Term And Condition 1", "Agreement 2", "Share data 3")
    val mContext = LocalContext.current
    val isTermsAndCondition1 = remember { mutableStateOf(false) }
    val isTermsAndCondition2 = remember { mutableStateOf(false) }
    val isTermsAndCondition3 = remember { mutableStateOf(false) }
    val listOfCheckDataType =
        listOf(isTermsAndCondition1, isTermsAndCondition2, isTermsAndCondition3)
    Column(
        modifier = Modifier.padding(16.dp, 8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {

        listOfCheck.forEachIndexed { index, myText ->
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Checkbox(
                    checked = listOfCheckDataType[index].value,
                    onCheckedChange = {
                        listOfCheckDataType[index].value = it
                    },
                    modifier = Modifier,
                    enabled = true,
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Green,
                        uncheckedColor = Color.Gray,
                        checkmarkColor = Color.White
                    ),
                    interactionSource = remember { MutableInteractionSource() }
                )

                Text(
                    text = myText + if (listOfCheckDataType[index].value) " Checked" else " UnChecked",
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@Composable
fun MySwitched() {
    val mContext = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 16.dp)
    ) {
        val mchecked = remember { mutableStateOf(false) }
        Switch(checked = mchecked.value, onCheckedChange = { mchecked.value = it })

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            onClick = {
                Toast.makeText(mContext, mchecked.value.toString(), Toast.LENGTH_SHORT).show()
            },
            shape = RoundedCornerShape(15.dp)
        ) {
            Text("Show switch state", color = Color.White)
        }
    }
}

@Composable
fun MyRadioButtonComponent() {
    val radioOptions = listOf("DSA", "Java", "C++")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        radioOptions.forEach { text ->
            Row(
                Modifier.selectable(
                    selected = (text == selectedOption),
                    onClick = { onOptionSelected(text) }),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val context = LocalContext.current
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = {
                        onOptionSelected(text)
                        Toast.makeText(context, "selected text option is $text", Toast.LENGTH_SHORT)
                            .show()
                    },
                    modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 0.dp),
                    enabled = true,
                    colors = RadioButtonDefaults.colors(
                        Color.Green,
                        Color.Gray
                    ),
                    interactionSource = remember { MutableInteractionSource() }
                )

                Text(
                    text = text,
                    modifier = Modifier,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun MyInputField() {
    var text: String by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Enter your text here") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp),
        maxLines = 4,
        shape = RoundedCornerShape(15.dp),
        colors = TextFieldDefaults.colors().copy(
            focusedLabelColor = Color.Gray,
            cursorColor = Color.Gray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        )
    )
    Text(text = "You entered: $text", modifier = Modifier.padding(start = 24.dp, end = 24.dp))
}

@Composable
fun MyButton(isAnimation : Boolean, function: () -> Unit) : Unit {

    val context = LocalContext.current
    Button(
        onClick = { function.invoke()
            Toast.makeText(context, "Clicked my button isAnimation: $isAnimation", Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 16.dp),
        enabled = true,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = Color.Blue
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp),
        border = BorderStroke(width = 2.dp, brush = SolidColor(Color.Black)),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 12.dp,
            end = 12.dp,
            bottom = 12.dp,
        ),
        interactionSource = remember { MutableInteractionSource() }
    ) {
        Text(
            text = "My Button",
            fontSize = 24.sp,
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SearchScreen(viewModel: ImageViewModel = viewModel()) {

    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x0B000000)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()) {
            TextField(
                value = text,
                onValueChange = {
                    text = it
                    if(text.length > 3) {
                        viewModel.clearImages()
                        viewModel.fetchRandlomImages(20)
                    }
                                },
                modifier = Modifier
                    .weight(1f)
                    .padding(24.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0x4B000000),
                    unfocusedContainerColor = Color(0x4B000000),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedLabelColor = Color.White,
                    cursorColor = Color.White
                ),
                placeholder = { Text(text = "Search", color = Color.White) },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search icon",
                        tint = Color.White
                    )
                },
                shape = RoundedCornerShape(8.dp)
            )

        }
//        MyConstraintLayout()
        MyStaggeredLayout(viewModel)
    }
}

@Composable
fun MyStaggeredLayout(viewModel: ImageViewModel = viewModel()) {
    val images = viewModel.images

    

    LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2), modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 24.dp), verticalItemSpacing = 8.dp, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(images.size){ index ->
            AsyncImage(model = images[index], contentDescription = "Image $index", contentScale = ContentScale.Crop, modifier = Modifier
                .fillMaxWidth()
                .height((75..300).random().dp)
                .clip(RoundedCornerShape(16.dp)))
        }
    }

}

@Composable
fun MyConstraintLayout() {
    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {
        val (image, text) = createRefs()
        Image(painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Image description",
            Modifier
                .background(Color.Blue)
                .size(70.dp)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                })

        Text(text = "This is textview image name",
            Modifier
                .background(Color.Magenta)
                .constrainAs(text) {
                    top.linkTo(image.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Using scaffold") },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.secondary)
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                Text(text = "This is content body", modifier = Modifier.align(Alignment.Center))
            }
        },
        bottomBar = {
            BottomAppBar {
                Text(
                    text = "Bottom bar",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                Toast.makeText(
                    context,
                    "Clicked floating button",
                    Toast.LENGTH_SHORT
                ).show()
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add icon")
            }
        }
    )

}

data class Course(
    val title: String,
    @DrawableRes val iconId: Int,
    val bgColor: Color
)
