package com.demo.dailynews.network

import com.demo.dailynews.model.ApiResponse
import retrofit2.Call
import retrofit2.http.*

interface EndPoints {
    @GET
    fun getApiOneNews(@Url url: String): Call<ApiResponse>
    }
