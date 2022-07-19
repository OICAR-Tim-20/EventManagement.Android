package hr.algebra.eventmanagement.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.algebra.eventmanagement.model.Comments
import hr.algebra.eventmanagement.repositories.CommentsRepository
import kotlinx.coroutines.launch

class CommentsViewModel(id: Int) : ViewModel() {
    private val _comments = MutableLiveData<List<Comments>>()
    private val errorMessage = MutableLiveData<String?>()
    private val commentsRepository = CommentsRepository()
    val comments: LiveData<List<Comments>> = _comments

    init {
        viewModelScope.launch {
            try {
                _comments.postValue(commentsRepository.getCommentsOfEvent(id))
                errorMessage.postValue(null)
            } catch (e: Throwable) {
                errorMessage.postValue("Could not get Events!")
                Log.e("Error", e.message.orEmpty(), e)
            }
        }
    }
}