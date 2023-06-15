package com.example.GEARVN.View.Admin;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.GEARVN.Model.CategoryModels;
import com.example.GEARVN.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public class AddCategoryActivity extends AppCompatActivity {
    private static final int LIBRARY_PICKER = 12312;
    EditText edtTenLoai;
    ImageView imageView , btnBack;
    Button btnAdd ;
    private Spinner spinerthongke;
    private List<String> list;
    FirebaseFirestore db;
    private String image = "";
    ProgressDialog dialog;
    private CategoryModels categoryModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        db = FirebaseFirestore.getInstance();
        Init();
        if (categoryModels != null) {

            edtTenLoai.setText(categoryModels.getTenloai());

            if (!TextUtils.isEmpty(categoryModels.getHinhanh())) {
                Picasso.get().load(categoryModels.getHinhanh()).into(imageView);
                image = categoryModels.getHinhanh();
            }

        }


    }

    private void Init(){
        dialog = new ProgressDialog(this); // this = YourActivity
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Upload image");
        dialog.setMessage("Uploading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        edtTenLoai = findViewById(R.id.edt_tenloaisp);
        imageView = findViewById(R.id.image_add);
        btnAdd = findViewById(R.id.btn_Add);
        btnBack = findViewById(R.id.btn_add_back);

        imageView.setOnClickListener(view -> {
            pickImage();
        });
        findViewById(R.id.btn_add_back).setOnClickListener(view -> {
            finish();
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validate()) {
                    return;
                }
                try {
                    CategoryModels sp = new CategoryModels();
                    String id = UUID.randomUUID().toString();
                    sp.setTenloai(edtTenLoai.getText().toString());
                    sp.setHinhanh(image);
                    sp.setId(id);
                    db.collection("LoaiSP").document(id).set(sp).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(AddCategoryActivity.this, "Thành công!!!", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddCategoryActivity.this, "Thất bại!!!", Toast.LENGTH_SHORT).show();
                        }
                    });
//                    db.collection("LoaiSP").add(sp).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                        @Override
//                        public void onSuccess(@NonNull DocumentReference documentReference) {
//                            Toast.makeText(AddCategoryActivity.this, "Thành công!!!", Toast.LENGTH_SHORT).show();
//                            setResult(RESULT_OK);
//                            finish();
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(AddCategoryActivity.this, "Thất bại!!!", Toast.LENGTH_SHORT).show();
//                        }
//                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private boolean validate() {
        if (TextUtils.isEmpty(image)) {
            Toast.makeText(this, "Vui lòng chọn hình ảnh", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(edtTenLoai.getText().toString())) {
            Toast.makeText(this, "Vui lòng nhập tên sản phẩm", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }
    private void pickImage() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    LIBRARY_PICKER);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(Intent.createChooser(intent, "Select Image"), LIBRARY_PICKER);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // pick image after request permission success
            pickImage();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LIBRARY_PICKER && resultCode == RESULT_OK && null != data) {
            try {

                dialog.show();
                Uri uri = data.getData();
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] datas = baos.toByteArray();
                String filename = System.currentTimeMillis() + "";
                StorageReference storageReference;
                storageReference = FirebaseStorage.getInstance("gs://pixelshop-app.appspot.com/").getReference();
                storageReference.child("Category").child(filename + ".jpg").putBytes(datas).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        if (taskSnapshot.getTask().isSuccessful()) {
                            storageReference.child("Category").child(filename + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(@NonNull Uri uri) {
                                    imageView.setImageBitmap(bitmap);
                                    image = uri.toString();
                                }
                            });
                            Toast.makeText(AddCategoryActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
            } catch (FileNotFoundException e) {
                Log.d("CHECKED", e.getMessage());
                dialog.dismiss();
            }

        }


    }
}