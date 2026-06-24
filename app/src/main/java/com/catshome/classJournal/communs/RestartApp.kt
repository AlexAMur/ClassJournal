package com.catshome.classJournal.communs

import android.content.Context
import android.content.Intent

import kotlin.system.exitProcess

fun restartApp(context: Context) {
    // 1. Создаем Intent для запуска главного экрана приложения
    val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
      // 2. Флаг NEW_TASK обязателен при запуске из Context
    intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    // 3. Очищаем весь предыдущий стек экранов
    intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
    // 3. Запускаем приложение заново
    context.startActivity(intent)
    // 4. Принудительно убиваем текущий процесс (0 означает "успешное завершение")
    exitProcess(0)
}