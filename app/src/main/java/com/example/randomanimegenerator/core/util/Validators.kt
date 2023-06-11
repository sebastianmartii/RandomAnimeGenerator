package com.example.randomanimegenerator.core.util

import android.util.Patterns

fun CharSequence?.isEmailValid() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun CharSequence?.isPasswordValid() = !isNullOrEmpty() && length > 3 && contains("[0-9]".toRegex())