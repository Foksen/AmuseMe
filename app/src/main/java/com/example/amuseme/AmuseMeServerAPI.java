package com.example.amuseme;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AmuseMeServerAPI {
    String BASE_URL = "http://h304301261.nichost.ru/";

    @GET("getRandAmusement")
    Observable<AmusementItemResponse> getRandAmusement(@Query("themes") String themes);

    @FormUrlEncoded
    @POST("updateParam")
    Completable updateParam(@Field("amusementId") int amusementId, @Field("param") String param, @Field("delta") int delta);
}
