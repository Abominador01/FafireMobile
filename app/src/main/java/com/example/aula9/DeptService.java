package com.example.aula9;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DeptService {

    @POST("departments")
    Call<DeptResponse> createRequestPostDepts(@Body DeptPost deptPost);

    @PUT("departments/{department_id}")
    Call<DeptResponse> createRequestPutDepts(@Body DeptPost deptPost, @Path("department_id") int id);

    @DELETE("departments/{department_id}")
    Call<Object> createRequestDeleteDepts(@Path("department_id") int id);

    @GET("departments")
    Call<List<DeptResponse>> createRequestGetAllDepts();
}
