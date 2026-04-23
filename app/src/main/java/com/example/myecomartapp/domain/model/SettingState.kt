package com.example.myecomartapp.domain.model

data class SettingState(
    val userProfile : SettingProfileState = SettingProfileState(),
    val isLoading : Boolean = false,
    val error : String? = null,
    val isSaving : Boolean = false,
    val saveSuccess : Boolean = false,
    val profileUrl : String? = null

)


