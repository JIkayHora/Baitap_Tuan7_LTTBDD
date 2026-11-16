package com.example.btt7_todo_vtask.addtask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.btt7_todo_vtask.Category
import com.example.btt7_todo_vtask.globalSettings
import java.util.Calendar
import java.util.Locale

@Composable
fun AddTaskScreen(
    studyCategory: Category,
    lifeCategory: Category,
    onCancel: () -> Unit,
    onTaskCreated: () -> Unit,
    viewModel: AddTaskViewModel = viewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(studyCategory.tagName) {
        viewModel.initCategoryIfEmpty(studyCategory.tagName)
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is AddTaskUiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is AddTaskUiEvent.TaskSavedSuccessfully -> {
                    onTaskCreated()
                }
            }
        }
    }

    val isDark = when (globalSettings.themeMode) {
        "dark" -> true
        "light" -> false
        else -> isSystemInDarkTheme()
    }
    val accentColor = globalSettings.accentColor
    val bgColor = if (isDark) Color(0xFF121212) else Color(0xFFF9FAFB)
    val cardBgColor = if (isDark) Color(0xFF1E1E1E) else Color.White
    val textPrimary = if (isDark) Color(0xFFF3F4F6) else Color(0xFF111827)
    val textSecondary = if (isDark) Color(0xFF9CA3AF) else Color.Gray

    val showDatePicker = {
        val c = Calendar.getInstance()
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val isToday = (dayOfMonth == c.get(Calendar.DAY_OF_MONTH) &&
                        month == c.get(Calendar.MONTH) &&
                        year == c.get(Calendar.YEAR))
                val formatted = if (isToday) {
                    "Hôm nay"
                } else {
                    String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year)
                }
                viewModel.onDateSelected(formatted)
            },
            c.get(Calendar.YEAR),
            c.get(Calendar.MONTH),
            c.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    val showTimePicker = {
        val c = Calendar.getInstance()
        TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                val formatted = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute)
                viewModel.onTimeSelected(formatted)
            },
            c.get(Calendar.HOUR_OF_DAY),
            c.get(Calendar.MINUTE),
            true
        ).show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .systemBarsPadding()
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Hủy",
                color = accentColor,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable { onCancel() }
            )
            Text(
                text = "Việc mới",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = textPrimary
            )
            if (uiState.isSaving) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = accentColor
                )
            } else {
                Text(
                    text = "Lưu",
                    color = accentColor,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        viewModel.saveTask(context)
                    }
                )
            }
        }

        HorizontalDivider(color = if (isDark) Color(0xFF2E2E2E) else Color(0xFFE5E7EB))

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Card nhập Tên công việc & Ghi chú
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = cardBgColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Tên công việc", fontSize = 12.sp, color = textSecondary)
                    TextField(
                        value = uiState.title,
                        onValueChange = { viewModel.onTitleChanged(it) },
                        placeholder = {
                            Text(
                                "Ôn chương 4 – Giải thuật",
                                color = Color.Gray,
                                fontSize = 15.sp
                            )
                        },
                        singleLine = true,
                        isError = uiState.titleError != null,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            errorContainerColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (uiState.titleError != null) {
                        Text(
                            text = uiState.titleError ?: "",
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                        )
                    }

                    HorizontalDivider(color = if (isDark) Color(0xFF2E2E2E) else Color(0xFFE5E7EB))
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Ghi chú", fontSize = 12.sp, color = textSecondary)
                    TextField(
                        value = uiState.note,
                        onValueChange = { viewModel.onNoteChanged(it) },
                        placeholder = {
                            Text(
                                "Thêm ghi chú (không bắt buộc)",
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Column {
                Text(
                    "Nhóm",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = textPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val isStudy = uiState.selectedCategory == studyCategory.tagName
                    OutlinedButton(
                        onClick = { viewModel.onCategorySelected(studyCategory.tagName) },
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(
                            width = if (isStudy) 2.dp else 1.dp,
                            color = if (isStudy) accentColor else if (isDark) Color(0xFF333333) else Color(0xFFD1D5DB)
                        ),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (isStudy) accentColor.copy(alpha = 0.08f) else cardBgColor
                        )
                    ) {
                        Text(
                            studyCategory.tagName,
                            fontWeight = if (isStudy) FontWeight.Bold else FontWeight.Normal,
                            color = if (isStudy) accentColor else textPrimary
                        )
                    }

                    val isLife = uiState.selectedCategory == lifeCategory.tagName
                    OutlinedButton(
                        onClick = { viewModel.onCategorySelected(lifeCategory.tagName) },
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(
                            width = if (isLife) 2.dp else 1.dp,
                            color = if (isLife) Color(0xFFD97706) else if (isDark) Color(0xFF333333) else Color(0xFFD1D5DB)
                        ),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (isLife) Color(0xFFFEF3C7) else cardBgColor
                        )
                    ) {
                        Text(
                            lifeCategory.tagName,
                            fontWeight = if (isLife) FontWeight.Bold else FontWeight.Normal,
                            color = if (isLife) Color(0xFFD97706) else textPrimary
                        )
                    }
                }
            }

            // Card chọn Ngày và Giờ
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = cardBgColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                    // Hàng chọn ngày
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { showDatePicker() }
                        ) {
                            Icon(
                                Icons.Default.DateRange,
                                contentDescription = "Chọn ngày",
                                tint = accentColor,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("Ngày", fontSize = 14.sp, color = textPrimary)
                        }
                        OutlinedTextField(
                            value = uiState.date,
                            onValueChange = { viewModel.onDateSelected(it) },
                            singleLine = true,
                            modifier = Modifier.width(150.dp)
                        )
                    }

                    HorizontalDivider(color = if (isDark) Color(0xFF2E2E2E) else Color(0xFFE5E7EB))

                    // Hàng chọn giờ
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { showTimePicker() }
                        ) {
                            Icon(
                                Icons.Default.Schedule,
                                contentDescription = "Chọn giờ",
                                tint = accentColor,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("Giờ", fontSize = 14.sp, color = textPrimary)
                        }
                        OutlinedTextField(
                            value = uiState.time,
                            onValueChange = { viewModel.onTimeSelected(it) },
                            singleLine = true,
                            modifier = Modifier.width(120.dp)
                        )
                    }
                }
            }

            // Card Nhắc nhở
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = cardBgColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Outlined.Notifications,
                                contentDescription = null,
                                tint = accentColor,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    "Nhắc tôi trước hạn",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = textPrimary
                                )
                                Text(
                                    "Báo trước",
                                    fontSize = 11.sp,
                                    color = textSecondary
                                )
                            }
                        }
                        Switch(
                            checked = uiState.reminderEnabled,
                            onCheckedChange = { viewModel.onReminderToggled(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = accentColor
                            )
                        )
                    }

                    if (uiState.reminderEnabled) {
                        Spacer(modifier = Modifier.height(10.dp))
                        HorizontalDivider(color = if (isDark) Color(0xFF2E2E2E) else Color(0xFFE5E7EB))
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Thời gian nhắc nhở:", fontSize = 13.sp, color = textSecondary)
                            Box {
                                OutlinedButton(
                                    onClick = { viewModel.onReminderMenuExpanded(true) },
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        uiState.reminderTimeOption,
                                        fontSize = 12.sp,
                                        color = accentColor,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(
                                        Icons.Default.ArrowDropDown,
                                        contentDescription = null,
                                        tint = accentColor,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                DropdownMenu(
                                    expanded = uiState.isReminderMenuExpanded,
                                    onDismissRequest = { viewModel.onReminderMenuExpanded(false) }
                                ) {
                                    for (opt in AddTaskConstants.REMINDER_OPTIONS) {
                                        DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = if (opt == "Trước 15 phút") "$opt (Mặc định)" else opt,
                                                    fontWeight = if (opt == uiState.reminderTimeOption) FontWeight.Bold else FontWeight.Normal,
                                                    color = if (opt == uiState.reminderTimeOption) accentColor else textPrimary
                                                )
                                            },
                                            onClick = {
                                                viewModel.onReminderTimeSelected(opt)
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Mức ưu tiên
            Column {
                Text(
                    "Mức ưu tiên",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = textPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(cardBgColor)
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    for (p in AddTaskConstants.PRIORITY_OPTIONS) {
                        val isPSelected = uiState.selectedPriority == p
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isPSelected) accentColor else Color.Transparent)
                                .clickable { viewModel.onPrioritySelected(p) }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = p,
                                fontSize = 13.sp,
                                fontWeight = if (isPSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isPSelected) Color.White else textPrimary
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            // Nút Lưu công việc dưới cùng
            Button(
                onClick = {
                    viewModel.saveTask(context)
                },
                enabled = !uiState.isSaving,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = accentColor)
            ) {
                if (uiState.isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        "Lưu công việc",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

