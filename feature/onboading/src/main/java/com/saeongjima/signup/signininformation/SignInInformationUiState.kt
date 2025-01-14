package com.saeongjima.signup.signininformation

import com.saeongjima.model.DuplicateState
import com.saeongjima.model.account.Id
import com.saeongjima.model.account.Nickname
import com.saeongjima.model.account.Password

data class SignInInformationUiState(
    val isLoading: Boolean = false,
    val id: Id = Id(""),
    val isIdDuplication: DuplicateState = DuplicateState.NotChecked,
    val nickname: Nickname = Nickname(""),
    val isNicknameDuplication: DuplicateState = DuplicateState.NotChecked,
    val password: Password = Password(""),
    val passwordRepeat: Password = Password(""),
) {
    fun hasMetAllConditions(): Boolean {
        return id.isValid() &&
                isIdDuplication == DuplicateState.NotDuplicated &&
                nickname.isValid() &&
                isNicknameDuplication == DuplicateState.NotDuplicated &&
                password.isValid() &&
                password == passwordRepeat
    }
}

