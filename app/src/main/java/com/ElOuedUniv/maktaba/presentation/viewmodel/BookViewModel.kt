package com.ElOuedUniv.maktaba.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.domain.usecase.GetBooksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookViewModel(
    private val getBooksUseCase: GetBooksUseCase
) : ViewModel() {

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // متغير داخلي لحفظ القائمة الكاملة (حتى لا نضطر لتحميلها من جديد عند إلغاء الفلترة)
    private var allBooks: List<Book> = emptyList()

    init {
        loadBooks()
    }

    private fun loadBooks() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val bookList = getBooksUseCase()
                allBooks = bookList // حفظ النسخة الأصلية هنا
                _books.value = bookList
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * دالة لفلترة الكتب التي تتجاوز 400 صفحة
     */
    fun filterLongBooks() {
        _books.value = allBooks.filter { it.nbPages > 400 }
    }

    /**
     * دالة لإعادة عرض جميع الكتب وإلغاء الفلترة
     */
    fun resetFilter() {
        _books.value = allBooks
    }

    fun refreshBooks() {
        loadBooks()
    }
}
