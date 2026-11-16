package com.example.btt7_todo_vtask.notification

import android.content.Context
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btt7_todo_vtask.NotificationManagerHelper
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NotificationViewModel : ViewModel() {
    private val sampleNotifications = listOf(
        NotificationItem(
            id = "sample_study_1",
            title = "Sắp đến hạn",
            message = "Ôn chương 3 - Cấu trúc dữ liệu lúc 14:00.",
            time = "13:45",
            type = NotificationType.UPCOMING,
            category = NotificationCategory.STUDY,
            isRead = false
        ),
        NotificationItem(
            id = "sample_study_2",
            title = "Đã quá hạn",
            message = "Bài đọc tham khảo môn Mạng máy tính chưa xong.",
            time = "10:10",
            type = NotificationType.OVERDUE,
            category = NotificationCategory.STUDY,
            isRead = false
        ),
        NotificationItem(
            id = "sample_server_1",
            title = "Thông báo máy chủ",
            message = "Server FCM vừa đồng bộ dữ liệu công việc vào hệ thống.",
            time = "11:00",
            type = NotificationType.SERVER,
            category = NotificationCategory.STUDY,
            isServer = true,
            isRead = false
        ),
        NotificationItem(
            id = "sample_life_1",
            title = "Làm tốt lắm!",
            message = "Bạn đã xong 2 việc trong sáng nay.",
            time = "11:30",
            type = NotificationType.COMPLETED,
            category = NotificationCategory.LIFE,
            isRead = false
        ),
        NotificationItem(
            id = "sample_study_3",
            title = "Nhắc việc",
            message = "Họp nhóm đồ án trong tuần này.",
            time = "Hôm qua",
            type = NotificationType.REMINDER,
            category = NotificationCategory.STUDY,
            isRead = false
        ),
        NotificationItem(
            id = "sample_life_2",
            title = "Nhắc việc",
            message = "Uống đủ nước và tập thể dục 30 phút chiều nay.",
            time = "15:00",
            type = NotificationType.REMINDER,
            category = NotificationCategory.LIFE,
            isRead = false
        )
    )

    private val _selectedTab = MutableStateFlow(NotificationCategory.ALL)
    val selectedTab: StateFlow<NotificationCategory> = _selectedTab.asStateFlow()

    private val _allNotifications = MutableStateFlow<List<NotificationItem>>(emptyList())
    private val _readIds = MutableStateFlow<Set<String>>(emptySet())

    val visibleNotifications: StateFlow<List<NotificationItem>> = combine(
        _allNotifications,
        _selectedTab,
        _readIds
    ) { list, tab, readIds ->
        list.filter { item ->
            !item.isRead && !readIds.contains(item.id) && when (tab) {
                NotificationCategory.ALL -> true // Tab Tất cả bao gồm toàn bộ (kể cả Server)
                NotificationCategory.STUDY -> item.category == NotificationCategory.STUDY
                NotificationCategory.LIFE -> item.category == NotificationCategory.LIFE
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val allUnreadCount: StateFlow<Int> = combine(_allNotifications, _readIds) { list, readIds ->
        list.count { !it.isRead && !readIds.contains(it.id) }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 0)

    val studyUnreadCount: StateFlow<Int> = combine(_allNotifications, _readIds) { list, readIds ->
        list.count { it.category == NotificationCategory.STUDY && !it.isRead && !readIds.contains(it.id) }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 0)

    val lifeUnreadCount: StateFlow<Int> = combine(_allNotifications, _readIds) { list, readIds ->
        list.count { it.category == NotificationCategory.LIFE && !it.isRead && !readIds.contains(it.id) }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 0)

    private var emptyTimerJob: Job? = null
    var isDemoRunning = false
        private set

    init {
        loadReadIdsAndInit()

        // Lắng nghe thay đổi từ NotificationManagerHelper
        viewModelScope.launch {
            snapshotFlow { NotificationManagerHelper.notifications.toList() }
                .collect { realNotifications ->
                    syncFromRealNotifications(realNotifications)
                }
        }
    }

    private fun loadReadIdsAndInit() {
        val persistentRead = NotificationManagerHelper.getPersistentReadIds().toMutableSet()
        for (notif in NotificationManagerHelper.notifications) {
            if (notif.isRead) {
                persistentRead.add(notif.id)
            }
        }
        _readIds.value = persistentRead

        val isCleared = NotificationManagerHelper.isInitialDemoCleared()
        val realNotifications = NotificationManagerHelper.notifications
        val unreadReal = realNotifications.filter { !it.isRead && !persistentRead.contains(it.id) }

        if (isCleared) //Đã đọc
         {
            _allNotifications.value = realNotifications.map {
                it.toNotificationItem().copy(isRead = it.isRead || persistentRead.contains(it.id))
            }
        } else {
            if (unreadReal.isNotEmpty()) {
                _allNotifications.value = realNotifications.map { it.toNotificationItem() }
            } else {
                startDemoTimer()
            }
        }
    }

    private fun syncFromRealNotifications(realList: List<com.example.btt7_todo_vtask.AppNotification>) {
        val persistentRead = NotificationManagerHelper.getPersistentReadIds().toMutableSet()
        val readSet = (_readIds.value + persistentRead).toMutableSet()

        val mappedList = mutableListOf<NotificationItem>()
        for (real in realList) {
            val item = real.toNotificationItem()
            if (real.isRead) {
                readSet.add(real.id)
            }
            mappedList.add(item.copy(isRead = real.isRead || readSet.contains(real.id)))
        }

        _readIds.value = readSet

        if (NotificationManagerHelper.isInitialDemoCleared() || realList.any { !it.isRead && !it.id.startsWith("sample_") }) {
            isDemoRunning = false
            emptyTimerJob?.cancel()
            _allNotifications.value = mappedList
        }
    }

    fun selectTab(tab: NotificationCategory) {
        _selectedTab.value = tab
    }

    fun startDemoTimer() {
        isDemoRunning = true
        emptyTimerJob?.cancel()

        val currentRead = _readIds.value
        _allNotifications.value = sampleNotifications.map { it.copy(isRead = currentRead.contains(it.id)) }

        emptyTimerJob = viewModelScope.launch {
            delay(10_000L) // 10 giây
            val newReadSet = _readIds.value.toMutableSet()
            val sampleIds = sampleNotifications.map { it.id }
            newReadSet.addAll(sampleIds)
            _readIds.value = newReadSet
            NotificationManagerHelper.setInitialDemoCleared(true)
            NotificationManagerHelper.addPersistentReadIds(sampleIds)

            _allNotifications.value = _allNotifications.value.map {
                if (it.id.startsWith("sample_")) it.copy(isRead = true) else it
            }
            isDemoRunning = false
        }
    }

    fun markAllAsRead(context: Context? = null) {
        emptyTimerJob?.cancel()
        isDemoRunning = false

        val currentVisible = visibleNotifications.value
        val idsToMark = currentVisible.map { it.id }.toMutableSet()
        val sampleIds = sampleNotifications.map { it.id }
        idsToMark.addAll(sampleIds)

        val updatedReadIds = _readIds.value.toMutableSet()
        updatedReadIds.addAll(idsToMark)
        _readIds.value = updatedReadIds

        _allNotifications.value = _allNotifications.value.map { item ->
            if (idsToMark.contains(item.id)) item.copy(isRead = true) else item
        }
    //Lưu vào db
        NotificationManagerHelper.setInitialDemoCleared(true)
        NotificationManagerHelper.addPersistentReadIds(idsToMark)
        NotificationManagerHelper.markAllAsRead(context)
    }

    fun markAsRead(id: String, context: Context? = null) {
        val updatedReadIds = _readIds.value.toMutableSet()
        updatedReadIds.add(id)
        _readIds.value = updatedReadIds

        _allNotifications.value = _allNotifications.value.map { item ->
            if (item.id == id) item.copy(isRead = true) else item
        }

        NotificationManagerHelper.addPersistentReadIds(listOf(id))
        NotificationManagerHelper.markAsRead(id, context)
        if (visibleNotifications.value.isEmpty()) {
            NotificationManagerHelper.setInitialDemoCleared(true)
        }
    }

    fun resetList() {
        emptyTimerJob?.cancel()
        isDemoRunning = false
        NotificationManagerHelper.clearPersistentReadIds()
        _readIds.value = emptySet()
        startDemoTimer()
    }
}
