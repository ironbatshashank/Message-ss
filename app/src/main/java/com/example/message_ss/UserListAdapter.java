package com.example.message_ss;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserListAdapter implements RecyclerView.Adapter<UserListAdapter.UserListViewHolder> {

    ArrayList<UserObject> userList;

    public UserListAdapter(ArrayList<UserObject> userList) {
        this.userList = userList;
    }



    public class UserListViewHolder extends RecyclerView.ViewHolder{

        public UserListViewHolder(View view){
            super(view);
        }
    }

}
