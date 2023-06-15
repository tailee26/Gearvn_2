package com.example.GEARVN.View.Admin;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.GEARVN.Model.CategoryModels;
import com.example.GEARVN.Presenter.SetOnItemClick;
import com.example.GEARVN.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAddAdapter extends RecyclerView.Adapter<CategoryAddAdapter.CategoryViewHolder> {

    Context context;
    private Activity activity;
    List<CategoryModels> categoryList;
    FirebaseFirestore db;

    public CategoryAddAdapter(Context context, List<CategoryModels> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_category_admin, parent, false);

        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryModels categoryModels = categoryList.get(position);

        holder.categoryName.setText(categoryModels.getTenloai());
        Picasso.get().load(categoryModels.getHinhanh()).into(holder.categoryImage);

        //Glide.with(context).load(categoryModels.getHinhanh()).placeholder(R.drawable.smartphone).into(holder.categoryImage);
        //String name = categoryList.get(position).getTenloai();
        //Glide.with(context).load(categoryList.get(position).getHinhanh()).into(holder.categoryImage);

        //holder.setCategory(name,position);


        db= FirebaseFirestore.getInstance();
        holder.btndelete.setOnClickListener(view -> {
            db.collection("LoaiSP").document(categoryModels.getId()).delete().addOnSuccessListener(unused -> {
                Log.d("TAG", "Thanh cong");
                Toast.makeText(context, "Xoá sản phẩm thành công!!!", Toast.LENGTH_SHORT).show();
                    categoryList.remove(position);
                    notifyItemRemoved(position);
            }).addOnFailureListener(e -> {
                Log.d("TAG", "Fail");
                Toast.makeText(context, "Xoá sản phẩm thất bại!!!", Toast.LENGTH_SHORT).show();
            });

        });
        holder.SetOnItem(new SetOnItemClick() {
            @Override
            //chi tiet san phẩm
            public void SetItemClick(View view, int pos) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog_update_category);


                EditText tenloai = dialog.findViewById(R.id.editdanhmuc);
                ImageView imageadd = dialog.findViewById(R.id.image_add);
                ImageView cancel = dialog.findViewById(R.id.cancel);

                tenloai.setText(categoryModels.getTenloai());
                Picasso.get().load(categoryModels.getHinhanh()).into(imageadd);

                Button update = dialog.findViewById(R.id.btnxacnhan);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CategoryModels sp = new CategoryModels();
                        sp.setTenloai(tenloai.getText().toString());
                        sp.setHinhanh(imageadd.toString());
                        db.collection("LoaiSP").document(categoryModels.getId()).set(sp).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context, "Thành công!!!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Thất bại!!!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                dialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public  static class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView categoryImage;
        TextView categoryName;
        ImageView btndelete;
        CardView cardView;
        SetOnItemClick itemClick;


        private CategoryAddAdapter adapter;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryImage = (ImageView) itemView.findViewById(R.id.category_image);
            categoryName = (TextView) itemView.findViewById(R.id.category_title);
            btndelete = (ImageView) itemView.findViewById(R.id.button_delete);
            cardView = (CardView) itemView.findViewById(R.id.card_view);

            itemView.setOnClickListener(this);

        }
        public void SetOnItem(SetOnItemClick itemClick) {
            this.itemClick = itemClick;
        }

        @Override
        public void onClick(View v) {
            itemClick.SetItemClick(v, getAdapterPosition());
        }

    }



}

// lets import all the category images