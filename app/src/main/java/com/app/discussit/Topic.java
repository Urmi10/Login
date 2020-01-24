package com.app.discussit;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashSet;

public class Topic extends AppCompatActivity {
    String UserName;
    ArrayAdapter arrayAdpt;
    Button back;
    private DatabaseReference dbr = FirebaseDatabase.getInstance().getReference().getRoot();
    ArrayList<String> listOfDiscussion = new ArrayList<>();
    ListView lvDiscussionTopics;

    @SuppressLint({"ResourceType"})
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_topic);
        this.lvDiscussionTopics = (ListView) findViewById(R.id.list);
        this.arrayAdpt = new ArrayAdapter(this, 17367043, this.listOfDiscussion);
        this.lvDiscussionTopics.setAdapter(this.arrayAdpt);
        getUserName();
        this.dbr.addValueEventListener(new ValueEventListener() {
            public void onCancelled(DatabaseError databaseError) {
            }

            public void onDataChange(DataSnapshot dataSnapshot) {
                HashSet hashSet = new HashSet();
                for (DataSnapshot key : dataSnapshot.getChildren()) {
                    hashSet.add(key.getKey());
                }
                Topic.this.arrayAdpt.clear();
                Topic.this.arrayAdpt.addAll(hashSet);
                Topic.this.arrayAdpt.notifyDataSetChanged();
            }
        });
        this.lvDiscussionTopics.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Intent intent = new Intent(Topic.this.getApplicationContext(), Chat.class);
                intent.putExtra("selected_topic", ((TextView) view).getText().toString());
                intent.putExtra("user_name", Topic.this.UserName);
                Topic.this.startActivity(intent);
            }
        });
    }

    public void getUserName() {
        Builder builder = new Builder(this);
        builder.setPositiveButton("Enter Into TOPIC...!!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Bundle extras = Topic.this.getIntent().getExtras();
                Topic.this.UserName = extras.getString("userName");
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Topic.this.getUserName();
            }
        });
        builder.show();
    }
}
