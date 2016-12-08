package com.sundayblues.fbchat.server;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface RoomService {

    @GET("/rooms.json")
    Observable<List<RoomsResponse>> getListRooms();

    @POST("/rooms.json")
    Observable<RoomsResponse> createRooms(@Query("room[name]") String rooms);
}
