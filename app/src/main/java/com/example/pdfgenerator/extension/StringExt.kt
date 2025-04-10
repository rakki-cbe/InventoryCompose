package com.example.pdfgenerator.extension

fun String?.filterNull():String{
   return this ?: ""
}