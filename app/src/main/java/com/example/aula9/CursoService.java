package com.example.aula9;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CursoService {

    @POST("courses")
    Call<CursoResponse> createRequestPost(@Body CursoPost cursoPost);

    @PUT("courses/{curso_id}")
    Call<CursoResponse> createRequestPut(@Body CursoPost cursoPost, @Path("curso_id") int id);

    @DELETE("courses/{curso_id}")
    Call<Object> createRequestDelete(@Path("curso_id") int id);

    @GET("courses")
    Call<List<CursoResponse>> createRequestGetAll();

}