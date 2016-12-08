package com.sundayblues.fbchat.server;

import com.sundayblues.fbchat.common.ServiceGenerator;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RoomServiceController {

    public static Observable<List<RoomsResponse>> getListRooms() {
        RoomService service = ServiceGenerator.createService(RoomService.class);
        Observable<List<RoomsResponse>> response = service.getListRooms();
        return response;
    }

    public static Observable<RoomsResponse> createRoom(String name) {
        RoomService service = ServiceGenerator.createService(RoomService.class);
        Observable<RoomsResponse> response = service.createRooms(name);
        return response;
    }

}
