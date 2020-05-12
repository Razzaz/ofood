package com.example.ofood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CheckOutActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private DocumentReference dbPrice = db.collection("Cart").document(userID);

    private int total;
    private TextView mTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTransparentStatusBarOnly(CheckOutActivity.this);

        try
        {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        }
        catch (NullPointerException ignored){}

        setContentView(R.layout.activity_check_out);

        mTotalPrice = findViewById(R.id.total_price);


        db.collection("Cart").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()){
                        final List<Integer> amount = new ArrayList<>();
                        final List<String> name = new ArrayList<>();

                        Map<String, Object> map = document.getData();
                        if (map != null){
                            for(Map.Entry<String, Object> entry : map.entrySet()){
                                amount.add(Integer.parseInt(entry.getValue().toString()));
                                name.add(entry.getKey());
                            }
                        }
                        db.collection("Price").document("Vegetable").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()){
                                        List<Integer> price = new ArrayList<>();
                                        List<String> vege = new ArrayList<>();

                                        Map<String, Object> map = document.getData();
                                        if (map != null){
                                            for(Map.Entry<String, Object> entry :map.entrySet()){
                                                price.add(Integer.parseInt(entry.getValue().toString()));
                                                vege.add(entry.getKey());
                                            }
                                        }
                                        String id = db.collection("UsersData").document(userID).collection("Orders").document().getId();
                                        Map<String, Object> invoice_stat = new HashMap<>();
                                        invoice_stat.put("Status", "Ongoing");
                                        db.collection("UsersData").document(userID).collection("Orders").document(id)
                                                .set(invoice_stat)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d(TAG, "Added to Orders.");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w(TAG, "Error adding to Orders! Try again.", e);
                                                    }
                                                });

                                        int count=0;
                                        int count2=0;
                                        int harga = 0;
                                        total = 0;
                                        for (String s: name){
                                            for (String t: vege){
                                                if(s.equals(t)){
                                                    harga= price.get(count2) * amount.get(count);
                                                    Map<String, Object> invoice_item = new HashMap<>();
                                                    invoice_item.put(s, amount.get(count));
                                                    db.collection("UsersData").document(userID).collection("Orders").document(id)
                                                            .update(invoice_item)
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    Log.d(TAG, "Added to chart.");
                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Log.w(TAG, "Error adding to chart! Try again.", e);
                                                                }
                                                            });
                                                }
                                                count2+=1;
                                            }
                                            count2=0;
                                            total+=harga;
                                            count+=1;
                                        }
                                        mTotalPrice.setText(String.format("Rp. %s", total));
                                        Log.d("TAG", String.valueOf(total));
                                        Map<String, Object> item = new HashMap<>();
                                        item.put("Price", total);
                                        Map<String, Object> invoice_item = new HashMap<>();
                                        invoice_item.put("Price", total);
                                        db.collection("UsersData").document(userID).collection("Orders").document(id)
                                                .update(invoice_item)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d(TAG, "Added to chart.");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w(TAG, "Error adding to chart! Try again.", e);
                                                    }
                                                });
                                        Map<String, Object> random = new HashMap<>();
                                        random.put("New", "0");

                                        db.collection("Cart").document(userID).delete();
                                        db.collection("Cart").document(userID)
                                                .set(random)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d(TAG, "Added to cart.");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w(TAG, "Error adding to cart! Try again.", e);
                                                    }
                                                });
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });

        final EditText mAddress = findViewById(R.id.address);
        TextView mPay = findViewById(R.id.pay);


        mPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String address = mAddress.getText().toString();

                if(address.length() > 0){
                    Map<String, Object> dataAddress = new HashMap<>();
                    dataAddress.put("Address", address);
                    db.collection("UsersData").document(userID)
                            .set(dataAddress, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "Address added.");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding address! Try again.", e);
                                }
                            });

                    startActivity(new Intent(getApplicationContext(), PaymentActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(CheckOutActivity.this, "Enter your address", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setTransparentStatusBarOnly(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
