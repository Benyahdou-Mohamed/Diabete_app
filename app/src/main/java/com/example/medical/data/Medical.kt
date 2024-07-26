package com.example.medical.data

data class Medical(

    val id: Long, // Use Long for primary key
    val userId: String,
    val genre: String,
    val height: Int,
    val age: Int,
    val weight: Int,
    val insuline: String,
    val glycemie: Number,
    val Hba1c: Number,
    val dt2: Number,
    val Cholestrol: Number,
    val creatinine: Number,
    val uree: Number,
    val triglycerides: Number,
    val hdl: Number,
    val ldl: Number,
)
