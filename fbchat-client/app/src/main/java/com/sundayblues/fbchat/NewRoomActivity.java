package com.sundayblues.fbchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sundayblues.fbchat.server.RoomServiceController;
import com.sundayblues.fbchat.server.RoomsResponse;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class NewRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_room);

        initUI();
    }

    private void initUI() {
        EditText editNewRoom = (EditText) findViewById(R.id.edit_new_room);

        Button btnCreateRoom = (Button) findViewById(R.id.btn_create_room);
        btnCreateRoom.setOnClickListener( view -> {
            String newRoom = editNewRoom.getText().toString();
            Observable<RoomsResponse> response = RoomServiceController.createRoom(newRoom);
            response.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe( result -> {
                Toast.makeText(getApplicationContext(), "creating room is completed", Toast.LENGTH_SHORT).show();
                finish();
            }, erros -> {
                Toast.makeText(getApplicationContext(), "create error" + erros.toString(), Toast.LENGTH_SHORT).show();

            });
        });

    }
}
