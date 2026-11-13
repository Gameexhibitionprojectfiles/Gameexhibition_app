package com.example.gameexhibition

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

private object Route {
    const val Welcome = "welcome"
    const val SignIn = "sign_in"
    const val Student = "student_dashboard"
    const val Organiser = "organiser_dashboard"
}

private val Navy = Color(0xFF17264B)
private val Blue = Color(0xFF3457D5)
private val Teal = Color(0xFF009E9A)
private val Purple = Color(0xFF7357D8)
private val Ink = Color(0xFF1C2945)
private val Muted = Color(0xFF68748D)
private val Canvas = Color(0xFFF4F6FB)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AppTheme { ExhibitionApp() } }
    }
}

@Composable
private fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = lightColorScheme(primary = Blue, secondary = Teal, background = Canvas, surface = Color.White), content = content)
}

@Composable
private fun ExhibitionApp() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = Route.Welcome) {
        composable(Route.Welcome) { Welcome { nav.navigate(Route.SignIn) } }
        composable(Route.SignIn) {
            SignIn(
                back = { nav.popBackStack() },
                student = { nav.navigate(Route.Student) { popUpTo(Route.Welcome) } },
                organiser = { nav.navigate(Route.Organiser) { popUpTo(Route.Welcome) } }
            )
        }
        composable(Route.Student) { StudentDashboard { nav.navigate(Route.SignIn) { popUpTo(Route.Welcome) } } }
        composable(Route.Organiser) { OrganiserDashboard { nav.navigate(Route.SignIn) { popUpTo(Route.Welcome) } } }
    }
}

@Composable
private fun Welcome(start: () -> Unit) {
    Box(Modifier.fillMaxSize().background(Brush.linearGradient(listOf(Navy, Blue, Teal))).padding(26.dp), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(shape = RoundedCornerShape(30.dp), color = Color.White.copy(alpha = .15f), modifier = Modifier.size(112.dp)) {
                Box(contentAlignment = Alignment.Center) { Icon(Icons.Default.SportsEsports, null, tint = Color.White, modifier = Modifier.size(64.dp)) }
            }
            Spacer(Modifier.height(28.dp))
            Text("GAME EXHIBITION", color = Color.White, fontSize = 29.sp, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center)
            Text("REGISTRATION & ATTENDANCE", color = Color(0xFFBDF8EF), fontSize = 14.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Spacer(Modifier.height(18.dp))
            Text("Register. Explore. Play. Get rewarded.", color = Color.White.copy(alpha = .9f), fontSize = 16.sp, textAlign = TextAlign.Center)
            Spacer(Modifier.height(42.dp))
            Button(onClick = start, modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(17.dp), colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Navy)) {
                Text("GET STARTED", fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp)
                Spacer(Modifier.width(9.dp)); Icon(Icons.Default.ArrowForward, null)
            }
        }
    }
}

@Composable
private fun SignIn(back: () -> Unit, student: () -> Unit, organiser: () -> Unit) {
    Column(Modifier.fillMaxSize().background(Canvas).padding(24.dp), verticalArrangement = Arrangement.Center) {
        IconButton(onClick = back) { Icon(Icons.Default.ArrowBack, "Back", tint = Ink) }
        Spacer(Modifier.height(16.dp))
        Text("Welcome back", color = Ink, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
        Text("Choose how you want to enter the app.", color = Muted, modifier = Modifier.padding(top = 7.dp))
        Spacer(Modifier.height(30.dp))
        Text("CONTINUE AS", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.3.sp)
        Spacer(Modifier.height(12.dp))
        RoleCard("Student", "Explore exhibitions and track your participation", Icons.Default.School, student, Blue, Color(0xFFEAF0FF))
        Spacer(Modifier.height(14.dp))
        RoleCard("Organiser", "Oversee exhibitions, attendance and engagement", Icons.Default.AdminPanelSettings, organiser, Purple, Color(0xFFF0EDFF))
        Spacer(Modifier.height(24.dp))
        Text("Demo navigation only — authentication and trusted role verification are not connected.", color = Muted, fontSize = 11.sp, textAlign = TextAlign.Center)
    }
}

@Composable
private fun RoleCard(title: String, detail: String, icon: ImageVector, onClick: () -> Unit, accent: Color, tint: Color) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp)) {
        Row(Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(52.dp).background(tint, RoundedCornerShape(15.dp)), contentAlignment = Alignment.Center) { Icon(icon, null, tint = accent, modifier = Modifier.size(28.dp)) }
            Column(Modifier.weight(1f).padding(horizontal = 14.dp)) {
                Text(title, color = Ink, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                Text(detail, color = Muted, fontSize = 12.sp, lineHeight = 17.sp)
            }
            Icon(Icons.Default.ArrowForward, null, tint = accent)
        }
    }
}

@Composable
private fun StudentDashboard(onSignOut: () -> Unit) {
    Column(
        Modifier.fillMaxSize().background(Pale).verticalScroll(rememberScrollState())
    ) {
        Box(
            Modifier.fillMaxWidth()
                .background(Brush.linearGradient(listOf(Navy, Color(0xFF23546A), Teal)))
                .padding(horizontal = 24.dp, vertical = 34.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("STUDENT SPACE", color = Color(0xFFB8FFF4), fontSize = 11.sp,
                            fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp)
                        Text("Hello, Alex 👋", color = Color.White, fontSize = 28.sp,
                            fontWeight = FontWeight.ExtraBold)
                    }
                    TextButton(onClick = onSignOut) { Text("Sign out", color = Color.White) }
                }
                Spacer(Modifier.height(20.dp))
                Text(
                    "Welcome to the Game Exhibition",
                    color = Color.White,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "A space to discover student creativity, explore games and enjoy exhibition experiences.",
                    color = Color.White.copy(alpha = .84f),
                    fontSize = 14.sp,
                    lineHeight = 21.sp
                )
            }
        }
        Column(Modifier.fillMaxSize().padding(22.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(Modifier.padding(22.dp)) {
                    Box(
                        Modifier.size(54.dp).background(Color(0xFFE5F8F5), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.EmojiEvents, null, tint = Teal, modifier = Modifier.size(29.dp))
                    }
                    Spacer(Modifier.height(18.dp))
                    Text("Explore. Experience. Create.", color = Ink, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Discover the ideas and games being shared at your exhibition. Enjoy the experience and connect with the student community.",
                        color = Muted, fontSize = 13.sp, lineHeight = 20.sp
                    )
                }
            }
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEAF7F5))
            ) {
                Column(Modifier.padding(22.dp)) {
                    Text("Your exhibition space", color = Ink, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(7.dp))
                    Text(
                        "You are signed in to the student area. This dashboard is intentionally kept simple, with no points, levels, event panels or achievement summaries.",
                        color = Muted, fontSize = 12.sp, lineHeight = 19.sp
                    )
                }
            }
        }
    }
}
@Composable
private fun OrganiserDashboard(onSignOut: () -> Unit) {
    Column(
        Modifier.fillMaxSize().background(Pale).verticalScroll(rememberScrollState())
    ) {
        Box(
            Modifier.fillMaxWidth()
                .background(Brush.linearGradient(listOf(Color(0xFF201B4B), Color(0xFF5742A6), Color(0xFF7866C8))))
                .padding(horizontal = 24.dp, vertical = 34.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("ORGANISER SPACE", color = Color(0xFFE0D9FF), fontSize = 11.sp,
                            fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp)
                        Text("Welcome, organiser", color = Color.White, fontSize = 27.sp,
                            fontWeight = FontWeight.ExtraBold)
                    }
                    TextButton(onClick = onSignOut) { Text("Sign out", color = Color.White) }
                }
                Spacer(Modifier.height(20.dp))
                Text(
                    "Game Exhibition",
                    color = Color.White,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "Your dedicated space for supporting a great exhibition experience.",
                    color = Color.White.copy(alpha = .84f),
                    fontSize = 14.sp,
                    lineHeight = 21.sp
                )
            }
        }
        Column(Modifier.fillMaxSize().padding(22.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(Modifier.padding(22.dp)) {
                    Box(
                        Modifier.size(54.dp).background(Color(0xFFF0ECFF), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Event, null, tint = Purple, modifier = Modifier.size(29.dp))
                    }
                    Spacer(Modifier.height(18.dp))
                    Text("Welcome to your organiser area", color = Ink, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "This is the organiser landing screen for the Game Exhibition Registration and Attendance App.",
                        color = Muted, fontSize = 13.sp, lineHeight = 20.sp
                    )
                }
            }
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0EDFA))
            ) {
                Column(Modifier.padding(22.dp)) {
                    Text("Organiser workspace", color = Ink, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(7.dp))
                    Text(
                        "The dashboard is intentionally minimal. It does not display managed exhibition lists, attendance figures or engagement summaries.",
                        color = Muted, fontSize = 12.sp, lineHeight = 19.sp
                    )
                }
            }
        }
    }
}
@Composable
private fun StatCard(value: String, label: String, icon: ImageVector, accent: Color, modifier: Modifier) {
    Card(modifier, shape = RoundedCornerShape(17.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(Modifier.padding(15.dp)) {
            Icon(icon, null, tint = accent, modifier = Modifier.size(23.dp))
            Spacer(Modifier.height(10.dp))
            Text(value, color = Ink, fontSize = 25.sp, fontWeight = FontWeight.ExtraBold)
            Text(label, color = Muted, fontSize = 11.sp, lineHeight = 15.sp)
        }
    }
}

@Composable
private fun OrganiserStat(value: String, label: String, modifier: Modifier) {
    Card(modifier, shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = .14f))) {
        Column(Modifier.padding(11.dp)) {
            Text(value, color = Color.White, fontSize = 23.sp, fontWeight = FontWeight.ExtraBold)
            Text(label, color = Color.White.copy(alpha = .85f), fontSize = 10.sp)
        }
    }
}

@Composable
private fun Achievement(title: String, detail: String, icon: ImageVector) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = Teal, modifier = Modifier.size(23.dp))
        Column(Modifier.padding(start = 11.dp)) {
            Text(title, color = Ink, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            Text(detail, color = Muted, fontSize = 10.sp)
        }
    }
}

@Composable
private fun EventSummary(name: String, status: String, count: String, accent: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(9.dp).background(accent, CircleShape))
        Column(Modifier.weight(1f).padding(start = 11.dp)) {
            Text(name, color = Ink, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(status, color = Muted, fontSize = 10.sp)
        }
        Text(count, color = Ink, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
    }
}
