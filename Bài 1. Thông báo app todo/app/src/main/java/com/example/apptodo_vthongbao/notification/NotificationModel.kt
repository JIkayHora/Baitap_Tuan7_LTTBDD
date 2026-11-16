package com.example.apptodo_vthongbao.notification

import com.example.apptodo_vthongbao.AppNotification

enum class NotificationType {
    UPCOMING,
    OVERDUE,
    COMPLETED,
    REMINDER,
    SERVER
}

enum class NotificationCategory(val title: String) {
    ALL("Tất cả"),
    STUDY("Học tập"),
    LIFE("Cuộc sống")
}

data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val time: String,
    val type: NotificationType,
    val isRead: Boolean = false,
    val category: NotificationCategory = NotificationCategory.STUDY,
    val isServer: Boolean = false,
    val deadlineMs: Long = 0L,
    val taskTime: String = ""
)

fun determineCategory(title: String, message: String, categoryHint: String = ""): NotificationCategory {
    val text = "$title $message $categoryHint".lowercase()
    return if (text.contains("cuộc sống") || text.contains("life") ||
        text.contains("siêu thị") || text.contains("mua sắm") ||
        text.contains("tập thể dục") || text.contains("uống nước") ||
        text.contains("sáng nay") || text.contains("gia đình") ||
        text.contains("bạn bè") || text.contains("dọn dẹp") ||
        text.contains("thực phẩm") || text.contains("ăn uống") ||
        text.contains("cá nhân")
    ) {
        NotificationCategory.LIFE
    } else {
        NotificationCategory.STUDY
    }
}

fun AppNotification.toNotificationItem(): NotificationItem {
    val lowerTitle = title.lowercase()
    val lowerMsg = message.lowercase()
    val isFromServer = type == "fcm" || type == "server_rtdb" || lowerTitle.contains("máy chủ") || lowerTitle.contains("server")

    val notifType = when {
        lowerTitle.contains("quá hạn") || lowerMsg.contains("quá hạn") ||
                (deadlineMs > 0L && deadlineMs < System.currentTimeMillis()) -> NotificationType.OVERDUE
        lowerTitle.contains("làm tốt") || lowerTitle.contains("xong") || lowerTitle.contains("hoàn thành") -> NotificationType.COMPLETED
        lowerTitle.contains("sắp đến hạn") || lowerTitle.contains("sắp") ||
                (deadlineMs > 0L && deadlineMs >= System.currentTimeMillis()) -> NotificationType.UPCOMING
        type == "reminder" || lowerTitle.contains("nhắc") -> NotificationType.REMINDER
        isFromServer -> NotificationType.SERVER
        else -> NotificationType.UPCOMING
    }

    val cat = determineCategory(title, message, type)

    return NotificationItem(
        id = id,
        title = title.ifBlank { "Thông báo" },
        message = message,
        time = time.ifBlank { "Vừa xong" },
        type = notifType,
        isRead = isRead,
        category = cat,
        isServer = isFromServer,
        deadlineMs = deadlineMs,
        taskTime = taskTime
    )
}

@Suppress("unused")
fun NotificationItem.toAppNotification(): AppNotification {
    return AppNotification(
        id = id,
        title = title,
        message = message,
        time = time,
        isRead = isRead,
        type = when (type) {
            NotificationType.UPCOMING -> "upcoming"
            NotificationType.OVERDUE -> "overdue"
            NotificationType.COMPLETED -> "completed"
            NotificationType.REMINDER -> "reminder"
            NotificationType.SERVER -> "fcm"
        },
        deadlineMs = deadlineMs,
        taskTime = taskTime
    )
}
