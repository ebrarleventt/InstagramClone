package com.example.instaclone.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.instaclone.DividerItemDecoration;
import com.example.instaclone.R;
import com.example.instaclone.adapter.PostAdapter;
import com.example.instaclone.databinding.ActivityFeedBinding;
import com.example.instaclone.databinding.ActivityMainBinding;
import com.example.instaclone.model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private FirebaseAuth fAuth;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<Post> postArrayList;
    private ActivityFeedBinding binding;
    PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        postArrayList = new ArrayList<>();
        fAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        getData();

        binding.feedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(postArrayList);
        binding.feedRecyclerView.setAdapter(postAdapter);
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));

    }

    private void getData(){
        //Posts - isim aynı olmalı!
        firebaseFirestore.collection("Posts").orderBy("date", Query.Direction.DESCENDING ).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Toast.makeText(FeedActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                if(value!=null) {
                   for(DocumentSnapshot snapshot: value.getDocuments()){
                       Map<String, Object> data = snapshot.getData();
                        //Casting
                       String userEmail = (String) data.get("useremail");
                       String comment = (String) data.get("comment");
                       String downloadUrl = (String) data.get("downloadurl");

                       Post post = new Post(userEmail, comment, downloadUrl);
                       postArrayList.add(post);
                   }
                   postAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.addPost){
            Intent intentToUpload = new Intent(FeedActivity.this, LoadActivity.class);
            startActivity(intentToUpload);
        }
        else if(item.getItemId()==R.id.signOut){
            fAuth.signOut();
            Intent intentToMain = new Intent(FeedActivity.this, MainActivity.class);
            startActivity(intentToMain);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}