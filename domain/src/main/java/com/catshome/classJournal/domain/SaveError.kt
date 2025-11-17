package com.catshome.classJournal.domain

data class SaveError
    (
    val code: CodeError,
  //  val errorMessage: String
)

enum class CodeError {
    Sucessfful,
    primaryKeyEmpty,
    invalidArgument
}

