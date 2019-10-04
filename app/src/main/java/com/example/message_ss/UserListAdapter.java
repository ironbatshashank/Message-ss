package com.example.message_ss;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserListAdapter implements RecyclerView.Adapter<UserListAdapter.UserListRecyclerView> {

    ArrayList<UserObject> userList;

    public UserListAdapter(ArrayList<UserObject> userList) {
        this.userList = userList;
    }
}
