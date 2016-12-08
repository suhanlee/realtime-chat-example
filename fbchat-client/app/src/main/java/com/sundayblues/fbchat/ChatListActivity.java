package com.sundayblues.fbchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.sundayblues.fbchat.server.RoomServiceController;
import com.sundayblues.fbchat.server.RoomsResponse;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChatListActivity extends AppCompatActivity {

    private SwipeRefreshLayout mSwipeLayout;
    private RecyclerView mRecyclerView;
    private MessageRecyclerAdapter mFeedServerAdapter;
    private List<RoomsResponse> mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatListActivity.this, NewRoomActivity.class);
                startActivity(intent);
            }
        });

        initUI();
    }

    private void initUI() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setHasFixedSize(true);

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(() -> updateFromServer() );

        mResult = new ArrayList<>();
        mFeedServerAdapter = new MessageRecyclerAdapter(ChatListActivity.this, mResult);
        mRecyclerView.setAdapter(mFeedServerAdapter);

        updateFromServer();
    }

    private void updateFromServer() {
        Observable<List<RoomsResponse>> response = RoomServiceController.getListRooms();
        response.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( result -> {
                    mFeedServerAdapter.setData(result);
                    mFeedServerAdapter.notifyDataSetChanged();
                    mSwipeLayout.setRefreshing(false);
                }, erros -> {

                    Toast.makeText(getApplicationContext(), "erros:" + erros.toString(), Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateFromServer();
    }
}
