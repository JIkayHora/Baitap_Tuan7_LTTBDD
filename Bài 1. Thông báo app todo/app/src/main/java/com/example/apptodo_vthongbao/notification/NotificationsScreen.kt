package com.example.apptodo_vthongbao.notification

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apptodo_vthongbao.AppBottomNavigation
import com.example.apptodo_vthongbao.globalSettings

@Composable
fun NotificationsScreen(
    onNavigate: (String) -> Unit,
    @Suppress("UNUSED_PARAMETER") onBack: () -> Unit = {},
    viewModel: NotificationViewModel = viewModel()
) {
    val context = LocalContext.current
    val isDark = when (globalSettings.themeMode) {
        "dark" -> true
        "light" -> false
        else -> isSystemInDarkTheme()
    }

    val accentColor = Color(0xFF2563EB)
    val bgColor = if (isDark) Color(0xFF121212) else Color(0xFFF9FAFB)
    val cardBgColor = if (isDark) Color(0xFF1E1E1E) else Color.White
    val textPrimary = if (isDark) Color(0xFFF3F4F6) else Color(0xFF111827)
    val textSecondary = if (isDark) Color(0xFF9CA3AF) else Color(0xFF6B7280)
    val timeColor = if (isDark) Color(0xFF9CA3AF) else Color(0xFF6B7280)
    val borderColor = if (isDark) Color(0xFF2E2E2E) else Color(0xFFE5E7EB)
    val dividerColor = if (isDark) Color(0xFF2A2A2A) else Color(0xFFF3F4F6)

    val selectedTab by viewModel.selectedTab.collectAsState()
    val notifications by viewModel.visibleNotifications.collectAsState()
    val allCount by viewModel.allUnreadCount.collectAsState()
    val studyCount by viewModel.studyUnreadCount.collectAsState()
    val lifeCount by viewModel.lifeUnreadCount.collectAsState()

    Scaffold(
        bottomBar = {
            AppBottomNavigation(
                currentRoute = "notifications",
                accentColor = accentColor,
                isDark = isDark,
                onNavigate = onNavigate,
                onOpenAdd = { onNavigate("add_task") },
                onOpenNotifications = { }
            )
        },
        containerColor = bgColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(bgColor)
        ) {
            // Header: "Thông báo" & "Đánh dấu đã đọc"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Thông báo",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = textPrimary
                )

                if (notifications.isNotEmpty()) {
                    Text(
                        text = "Đánh dấu đã đọc",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = accentColor,
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .clickable { viewModel.markAllAsRead(context) }
                            .padding(horizontal = 6.dp, vertical = 3.dp)
                    )
                }
            }

            // Thanh 3 Tab: "Tất cả", "Học tập", "Cuộc sống"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 4.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (isDark) Color(0xFF1E1E1E) else Color(0xFFF1F5F9))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                NotificationTabButton(
                    title = NotificationCategory.ALL.title,
                    count = allCount,
                    isSelected = selectedTab == NotificationCategory.ALL,
                    isDark = isDark,
                    accentColor = accentColor,
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.selectTab(NotificationCategory.ALL) }
                )

                NotificationTabButton(
                    title = NotificationCategory.STUDY.title,
                    count = studyCount,
                    isSelected = selectedTab == NotificationCategory.STUDY,
                    isDark = isDark,
                    accentColor = accentColor,
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.selectTab(NotificationCategory.STUDY) }
                )

                NotificationTabButton(
                    title = NotificationCategory.LIFE.title,
                    count = lifeCount,
                    isSelected = selectedTab == NotificationCategory.LIFE,
                    isDark = isDark,
                    accentColor = accentColor,
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.selectTab(NotificationCategory.LIFE) }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (notifications.isEmpty()) {
                EmptyNotificationView(
                    selectedTab = selectedTab,
                    isDark = isDark,
                    accentColor = accentColor,
                    textPrimary = textPrimary,
                    textSecondary = textSecondary,
                    onNavigate = onNavigate,
                    onReloadSample = { viewModel.resetList() }
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp, vertical = 6.dp)
                ) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = cardBgColor),
                            border = BorderStroke(1.dp, borderColor),
                            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                        ) {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                notifications.forEachIndexed { index, item ->
                                    NotificationRowItem(
                                        item = item,
                                        isDark = isDark,
                                        accentColor = accentColor,
                                        textPrimary = textPrimary,
                                        textSecondary = textSecondary,
                                        timeColor = timeColor,
                                        onItemClick = { viewModel.markAsRead(item.id, context) }
                                    )
                                    if (index < notifications.size - 1) {
                                        HorizontalDivider(
                                            modifier = Modifier.padding(start = 68.dp, end = 16.dp),
                                            color = dividerColor,
                                            thickness = 1.dp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun NotificationTabButton(
    title: String,
    count: Int,
    isSelected: Boolean,
    isDark: Boolean,
    accentColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val tabBg = if (isSelected) {
        if (isDark) Color(0xFF2A2A38) else Color.White
    } else {
        Color.Transparent
    }

    val contentColor = if (isSelected) {
        accentColor
    } else {
        if (isDark) Color(0xFF9CA3AF) else Color(0xFF64748B)
    }

    Box(
        modifier = modifier
            .height(38.dp)
            .then(
                if (isSelected) Modifier.shadow(2.dp, RoundedCornerShape(10.dp)) else Modifier
            )
            .clip(RoundedCornerShape(10.dp))
            .background(tabBg)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = contentColor
            )

            if (count > 0) {
                Spacer(modifier = Modifier.width(5.dp))
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) accentColor else Color(0xFF94A3B8)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$count",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun NotificationRowItem(
    item: NotificationItem,
    isDark: Boolean,
    accentColor: Color,
    textPrimary: Color,
    textSecondary: Color,
    timeColor: Color,
    onItemClick: () -> Unit
) {
    val (iconBgColor, iconTint, iconVector) = when (item.type) {
        NotificationType.UPCOMING,
        NotificationType.REMINDER -> Triple(
            if (isDark) Color(0xFF1E293B) else Color(0xFFEBF5FF),
            Color(0xFF2563EB),
            Icons.Default.AccessTime
        )
        NotificationType.OVERDUE -> Triple(
            if (isDark) Color(0xFF371B1B) else Color(0xFFFEE2E2),
            Color(0xFFEF4444),
            Icons.Default.Warning
        )
        NotificationType.COMPLETED -> Triple(
            if (isDark) Color(0xFF143322) else Color(0xFFDCFCE7),
            Color(0xFF22C55E),
            Icons.Default.Check
        )
        NotificationType.SERVER -> Triple(
            if (isDark) Color(0xFF162A45) else Color(0xFFE0F2FE),
            Color(0xFF0284C7),
            Icons.Default.CloudUpload
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Icon tròn bo góc
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(iconBgColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = iconVector,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Nội dung tiêu đề và mô tả
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = textPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = item.message,
                fontSize = 13.sp,
                color = textSecondary,
                lineHeight = 18.sp
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Thời gian & chấm xanh chưa đọc
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 2.dp)
        ) {
            Text(
                text = item.time,
                fontSize = 12.sp,
                color = timeColor
            )

            if (!item.isRead) {
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .clip(CircleShape)
                        .background(accentColor)
                )
            }
        }
    }
}

@Composable
private fun EmptyNotificationView(
    selectedTab: NotificationCategory,
    isDark: Boolean,
    accentColor: Color,
    textPrimary: Color,
    textSecondary: Color,
    onNavigate: (String) -> Unit,
    onReloadSample: () -> Unit
) {
    val tabDesc = when (selectedTab) {
        NotificationCategory.ALL -> "Khi có việc sắp đến hạn, quá hạn hoặc lời nhắc, bạn sẽ thấy chúng ở đây."
        NotificationCategory.STUDY -> "Khi có bài tập, môn học hoặc đồ án sắp đến hạn, bạn sẽ thấy chúng ở đây."
        NotificationCategory.LIFE -> "Khi có lời nhắc sinh hoạt, việc cá nhân hoặc thói quen, bạn sẽ thấy chúng ở đây."
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            // Icon chuông lớn với huy hiệu tích xanh ở góc dưới phải
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .clickable { onReloadSample() },
                contentAlignment = Alignment.Center
            ) {

                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .background(if (isDark) Color(0xFF1E293B) else Color(0xFFE8F1FD)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Chưa có thông báo",
                        tint = accentColor,
                        modifier = Modifier.size(46.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .align(Alignment.BottomEnd)
                        .clip(CircleShape)
                        .background(Color(0xFF22C55E))
                        .border(2.dp, if (isDark) Color(0xFF121212) else Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Tiêu đề
            Text(
                text = "Chưa có thông báo nào",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = textPrimary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Mô tả
            Text(
                text = tabDesc,
                fontSize = 13.sp,
                color = textSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onNavigate("add_task") },
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                modifier = Modifier
                    .height(44.dp)
                    .padding(horizontal = 8.dp),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 0.dp)
            ) {
                Text(
                    text = "+ Thêm việc cần làm",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Cài đặt nhắc việc",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = accentColor,
                modifier = Modifier
                    .clickable { onNavigate("settings") }
                    .padding(8.dp)
            )
        }
    }
}
