/*
 *
 *  Copyright (C) 2016 Suhan Lee
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.sundayblues.fbchat;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sundayblues.fbchat.server.RoomsResponse;

import java.util.List;


public class MessageRecyclerAdapter extends RecyclerView.Adapter<MessageRecyclerAdapter.CustomViewHolder> {
    private List<RoomsResponse> mFeedItemList;

    private Activity mActivity;


    public MessageRecyclerAdapter(FragmentActivity activity, List<RoomsResponse> feedItems) {
        this.mActivity = activity;
        this.mFeedItemList = feedItems;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.room_list_row, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        RoomsResponse feedItem = mFeedItemList.get(position);

        if (feedItem.name != null) {
            holder.roomName.setText(feedItem.name);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, ChatActivity.class);
                intent.putExtra("room_id", feedItem.id);
                mActivity.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mFeedItemList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setData(List<RoomsResponse> result) {
        mFeedItemList = result;
    }


    static class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView roomName;
        CardView cardView;

        CustomViewHolder(View view) {
            super(view);
            this.roomName = (TextView) view.findViewById(R.id.room_name);
            this.cardView = (CardView) view.findViewById(R.id.card_view);
        }
    }
}