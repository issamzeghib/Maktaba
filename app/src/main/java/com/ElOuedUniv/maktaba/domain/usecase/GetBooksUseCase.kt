package com.ElOuedUniv.maktaba.domain.usecase

import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.data.repository.BookRepositoryImpl


class GetBooksUseCase(
    private val bookRepository: BookRepositoryImpl
) {
    operator fun invoke(): List<Book> {
        return bookRepository.getAllBooks()
    }
}
