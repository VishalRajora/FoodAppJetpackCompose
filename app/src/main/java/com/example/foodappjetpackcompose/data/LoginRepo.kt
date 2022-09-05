package com.example.foodappjetpackcompose.data

import com.example.foodappjetpackcompose.database.MyDao
import javax.inject.Inject

class LoginRepo @Inject constructor(private val dao: MyDao) {

    suspend fun saveData(
        email: String,
        password: String,
        name: String,
        address: String,
        phone: String,
    ): Long {
        val data =
            AuthModelClass(0,
                name = name,
                email = email,
                password = password,
                address = address,
                phoneNum = phone)
        return dao.InsertAuth(data)
    }

}