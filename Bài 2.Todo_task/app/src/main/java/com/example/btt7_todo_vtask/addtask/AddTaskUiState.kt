package com.example.btt7_todo_vtask.addtask

data class AddTaskUiState(
    val title: String = "",
    val note: String = "",
    val selectedCategory: String = "Học tập",
    val date: String = "",
    val time: String = "",
    val reminderEnabled: Boolean = true,
    val reminderTimeOption: String = "Trước 15 phút",
    val selectedPriority: String = "Vừa",
    val isReminderMenuExpanded: Boolean = false,
    val titleError: String? = null,
    val isSaving: Boolean = false
)

sealed interface AddTaskUiEvent {
    data class ShowToast(val message: String) : AddTaskUiEvent
    object TaskSavedSuccessfully : AddTaskUiEvent
}

object AddTaskConstants {
    val REMINDER_OPTIONS = listOf(
        "Trước 15 phút",
        "Trước 30 phút",
        "Trước 5 phút",
        "Trước 1 giờ",
        "Đúng giờ"
    )

    val PRIORITY_OPTIONS = listOf("Thấp", "Vừa", "Cao")
}

