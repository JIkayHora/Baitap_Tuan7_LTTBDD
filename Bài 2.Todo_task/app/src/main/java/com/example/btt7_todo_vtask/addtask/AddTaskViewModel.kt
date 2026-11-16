package com.example.btt7_todo_vtask.addtask

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btt7_todo_vtask.FirebaseServiceHelper
import com.example.btt7_todo_vtask.MockTeachApiService
import com.example.btt7_todo_vtask.TodoTask
import com.example.btt7_todo_vtask.appData
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

class AddTaskViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(createInitialState())
    val uiState: StateFlow<AddTaskUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<AddTaskUiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        initDefaultDateTime()
    }

    private fun createInitialState(): AddTaskUiState {
        val (defaultDate, defaultTime) = getDefaultDateTime()
        return AddTaskUiState(
            date = defaultDate,
            time = defaultTime
        )
    }

    private fun initDefaultDateTime() {
        if (_uiState.value.date.isBlank() || _uiState.value.time.isBlank()) {
            val (d, t) = getDefaultDateTime()
            _uiState.update { it.copy(date = d, time = t) }
        }
    }

    fun initCategoryIfEmpty(defaultCategory: String) {
        if (_uiState.value.selectedCategory.isBlank()) {
            _uiState.update { it.copy(selectedCategory = defaultCategory) }
        }
    }

    fun onTitleChanged(newTitle: String) {
        _uiState.update {
            it.copy(
                title = newTitle,
                titleError = if (newTitle.isNotBlank()) null else it.titleError
            )
        }
    }

    fun onNoteChanged(newNote: String) {
        _uiState.update { it.copy(note = newNote) }
    }

    fun onCategorySelected(categoryName: String) {
        _uiState.update { it.copy(selectedCategory = categoryName) }
    }

    fun onDateSelected(newDate: String) {
        _uiState.update { it.copy(date = newDate) }
    }

    fun onTimeSelected(newTime: String) {
        _uiState.update { it.copy(time = newTime) }
    }

    fun onReminderToggled(enabled: Boolean) {
        _uiState.update { it.copy(reminderEnabled = enabled) }
    }

    fun onReminderMenuExpanded(expanded: Boolean) {
        _uiState.update { it.copy(isReminderMenuExpanded = expanded) }
    }

    fun onReminderTimeSelected(option: String) {
        _uiState.update {
            it.copy(
                reminderTimeOption = option,
                isReminderMenuExpanded = false
            )
        }
    }

    fun onPrioritySelected(priority: String) {
        _uiState.update { it.copy(selectedPriority = priority) }
    }

    fun saveTask(context: Context) {
        val currentState = _uiState.value
        val trimmedTitle = currentState.title.trim()

        if (trimmedTitle.isBlank()) {
            _uiState.update { it.copy(titleError = "Vui lòng nhập tên công việc") }
            viewModelScope.launch {
                _uiEvent.send(AddTaskUiEvent.ShowToast("Vui lòng nhập tên công việc"))
            }
            return
        }

        _uiState.update { it.copy(isSaving = true, titleError = null) }

        viewModelScope.launch {
            try {
                val newId = System.currentTimeMillis().toString()
                val targetCategory = appData.getCategoryByName(currentState.selectedCategory)
                val effectiveDate = currentState.date.trim().ifBlank { "Hôm nay" }

                val newTask = TodoTask(
                    id = newId,
                    title = trimmedTitle,
                    category = targetCategory,
                    time = currentState.time.trim(),
                    date = effectiveDate,
                    note = currentState.note.trim(),
                    priority = currentState.selectedPriority,
                    reminder = currentState.reminderEnabled,
                    reminderTime = currentState.reminderTimeOption,
                    initialCompleted = false
                )

                // Gọi Mock API
                try {
                    MockTeachApiService.createTask(newTask)
                } catch (_: Exception) {
                    // Tiếp tục lưu Firebase nếu mock API lỗi
                }

                // Lưu lên Firebase Realtime Database & kích hoạt AlarmManager nếu có hẹn giờ
                FirebaseServiceHelper.addNewTask(
                    context = context,
                    title = trimmedTitle,
                    categoryName = currentState.selectedCategory,
                    time = currentState.time.trim(),
                    date = effectiveDate,
                    note = currentState.note.trim(),
                    priority = currentState.selectedPriority,
                    reminder = currentState.reminderEnabled,
                    reminderTime = currentState.reminderTimeOption,
                    taskId = newId
                )

                appData.tasks.add(0, newTask)

                _uiState.update { it.copy(isSaving = false) }
                _uiEvent.send(AddTaskUiEvent.ShowToast("Đã lưu công việc thành công!"))
                _uiEvent.send(AddTaskUiEvent.TaskSavedSuccessfully)
            } catch (e: Exception) {
                _uiState.update { it.copy(isSaving = false) }
                _uiEvent.send(AddTaskUiEvent.ShowToast("Lỗi khi lưu công việc: ${e.message}"))
            }
        }
    }

    fun resetForm() {
        val (defaultDate, defaultTime) = getDefaultDateTime()
        _uiState.value = AddTaskUiState(
            date = defaultDate,
            time = defaultTime
        )
    }

    private fun getDefaultDateTime(): Pair<String, String> {
        val todayCal = Calendar.getInstance()
        val defaultDateStr = "Hôm nay"
        val defaultTimeStr = String.format(
            Locale.getDefault(), "%02d:%02d",
            (todayCal.get(Calendar.HOUR_OF_DAY) + 1) % 24,
            0
        )
        return Pair(defaultDateStr, defaultTimeStr)
    }
}

