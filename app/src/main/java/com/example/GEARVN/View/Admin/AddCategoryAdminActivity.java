package com.example.GEARVN.View.Admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.GEARVN.Model.CategoryModels;
import com.example.GEARVN.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddCategoryAdminActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<String> list;
    FirebaseFirestore db;
    RecyclerView rcvCategory;
    ImageView btnDelete;

    //Category

    List<CategoryModels> categoryList;
    CategoryAddAdapter categoryAdapter;
    CategoryModels categoryModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category_admin);
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        findViewById(R.id.image_add).setOnClickListener(view -> {
            startActivityForResult(new Intent(AddCategoryAdminActivity.this, AddCategoryActivity.class), 100);
        });

        Init();
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void Init(){
        categoryList = new ArrayList<>();
        categoryAdapter = new CategoryAddAdapter(this,categoryList);
        rcvCategory = findViewById(R.id.rcv_cate_admin);

        //Category
        db= FirebaseFirestore.getInstance();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcvCategory.setHasFixedSize(true);
        rcvCategory.setLayoutManager(layoutManager);

        //categoryList = new ArrayList<>();
        //categoryAdapter = new CategoryAdapter(getContext(), categoryList);
        rcvCategory.setAdapter(categoryAdapter);

        db.collection("LoaiSP")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                CategoryModels categoryModels = document.toObject(CategoryModels.class);
                                categoryList.add(categoryModels);

                            }
                            categoryAdapter.notifyDataSetChanged();
                        } else {
//                            Toast.makeText(get, "Error"+task.getException(),
//                                    Toast.LENGTH_SHORT).show();
                            Log.d("TAG", task.getException().getMessage());
                        }
                    }
                });

    }


    @Override
    @SuppressLint("NotifyDataSetChanged")
    public void onRefresh() {
        categoryAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }
}