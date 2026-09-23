package com.suarez.semana05_navegacion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.suarez.semana05_navegacion.StudentRepository
import com.suarez.semana05_navegacion.ui.theme.Background
import com.suarez.semana05_navegacion.ui.theme.Primary
import com.suarez.semana05_navegacion.ui.theme.TextPrimary
import com.suarez.semana05_navegacion.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AcademicRecordScreen(navController: NavController, studentId: Int) {
    val student = StudentRepository.getStudentById(studentId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Expediente Académico", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = TextPrimary,
                    navigationIconContentColor = TextPrimary
                )
            )
        },
        containerColor = Background
    ) { paddingValues ->
        if (student != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Primary,
                            shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                        )
                        .padding(bottom = 32.dp, top = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        StudentAvatar(studentId = student.id, size = 96.dp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = student.name,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = student.career,
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 14.sp
                        )
                    }
                }

                // Info Card
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        InfoRow(icon = Icons.Default.Badge, label = "ID Estudiante", value = student.studentCode)
                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Background, thickness = 1.dp)
                        InfoRow(icon = Icons.Default.Email, label = "Correo Electrónico", value = student.email)
                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Background, thickness = 1.dp)
                        InfoRow(icon = Icons.Default.School, label = "Facultad", value = student.faculty)
                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Background, thickness = 1.dp)
                        InfoRow(icon = Icons.Default.Description, label = "Biografía", value = student.biography)
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = label, color = TextSecondary, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = value, color = TextPrimary, fontSize = 14.sp)
        }
    }
}
