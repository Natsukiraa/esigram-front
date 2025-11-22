package com.example.esigram.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esigram.domains.models.FriendRequest
import com.example.esigram.domains.models.TmpUser
import com.example.esigram.domains.models.responses.PageModel
import com.example.esigram.domains.models.responses.PageModel.Companion.createEmptyPageModel
import com.example.esigram.domains.usecase.friend.FriendUseCases
import com.example.esigram.domains.usecase.user.UserUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class FriendViewModel(
    private val useCases: FriendUseCases, private val userUsesCases: UserUseCases
) : ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()


    private val _friends = MutableStateFlow(createEmptyPageModel<TmpUser>())
    val friends: StateFlow<PageModel<TmpUser>> = _friends.asStateFlow()

    private val _outboundFriendRequests = MutableStateFlow(createEmptyPageModel<FriendRequest>())
    val outboundFriendRequests: StateFlow<PageModel<FriendRequest>> =
        _outboundFriendRequests.asStateFlow()

    private val _inboundFriendRequests = MutableStateFlow(createEmptyPageModel<FriendRequest>())
    val inboundFriendRequests: StateFlow<PageModel<FriendRequest>> =
        _inboundFriendRequests.asStateFlow()

    private val _searchedUsers = MutableStateFlow<PageModel<TmpUser>>(createEmptyPageModel())
    val searchedUsers: StateFlow<PageModel<TmpUser>> = _searchedUsers.asStateFlow()

    private val _searchQuery = MutableStateFlow<String>("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        loadFriends()
        loadFriendRequests(outbound = true)
        loadFriendRequests(outbound = false)

        viewModelScope.launch {
            _searchQuery.filter { it.isNotBlank() }.collect { query ->
                Log.d("FriendViewModel", "Searching users with query: $query")
                searchUsers(query)
            }
        }
    }

    fun refreshAllData() {
        viewModelScope.launch {
            _isRefreshing.value = true

            try {
                val friendsList = useCases.getFriendsUseCase()
                _friends.value = friendsList

                val outbound = useCases.getFriendRequestsUseCase(true)
                _outboundFriendRequests.value = outbound

                val inbound = useCases.getFriendRequestsUseCase(false)
                _inboundFriendRequests.value = inbound
            } catch (e: Exception) {
                Log.e("FriendViewModel", "Error refreshing data: ${e.message}")
            } finally {
                _isRefreshing.value = false
            }
        }
    }


    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private suspend fun searchUsers(query: String) {
        try {
            val results = userUsesCases.getUsersUseCase(0, 20, query)
            _searchedUsers.value = _searchedUsers.value.copy(data = results.data)
        } catch (e: Exception) {
        }
    }

    private fun loadFriends() {
        viewModelScope.launch {
            try {
                val friendsList = useCases.getFriendsUseCase()
                _friends.value = friendsList
            } catch (e: Exception) {
            }
        }
    }

    private fun loadFriendRequests(outbound: Boolean) {
        viewModelScope.launch {
            try {
                val requestsList = useCases.getFriendRequestsUseCase(outbound)
                if (outbound) {
                    _outboundFriendRequests.value = requestsList
                } else {
                    _inboundFriendRequests.value = requestsList
                }
            } catch (e: Exception) {
                Log.e("FriendViewModel", "Error loading friend requests: ${e.message}")
            }
        }
    }

    fun askFriend(friendId: String, accept: Boolean = false) {
        viewModelScope.launch {
            try {
                Log.d("FriendViewModel", "Asking friend with ID: $friendId")
                useCases.askFriendUseCase(friendId)
                loadFriendRequests(true)
                if (accept) {
                    loadFriendRequests(false)
                    loadFriends()
                }
            } catch (e: Exception) {
                Log.e("FriendViewModel", "Error asking friend: ${e.message}")
            }
        }
    }

    fun rejectFriend(friendId: String) {
        viewModelScope.launch {
            try {
                useCases.rejectFriendUseCase(friendId)

                loadFriendRequests(false)
                loadFriendRequests(true)
            } catch (e: Exception) {
                Log.e("FriendViewModel", "Error rejecting friend: ${e.message}")
            }
        }
    }

    fun removeFriend(friendId: String) {
        viewModelScope.launch {
            try {
                useCases.removeFriendUseCase(friendId)

                loadFriends()
            } catch (e: Exception) {

            }
        }
    }
}
