package com.example.ofood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.ofood.CheckOutActivity.SHARED_PREFS;
import static com.example.ofood.CheckOutActivity.orderState;

public class OrderHistoryActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private String idOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTransparentStatusBarOnly(OrderHistoryActivity.this);

        try
        {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        }
        catch (NullPointerException ignored){}


        setContentView(R.layout.activity_order_history);

        TextView orderId = findViewById(R.id.order_id);
        final TextView payment = findViewById(R.id.payment);
        final TextView status = findViewById(R.id.status);
        Button home = findViewById(R.id.home);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        idOrder = sharedPreferences.getString(orderState, null);

        //String id = db.collection("UsersData").document(userID).collection("Orders").document().getId();
        orderId.setText(idOrder);

        db.collection("UsersData").document(userID).collection("Orders").document(idOrder).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    long payment_sum = documentSnapshot.getLong("Price");
                    String status_id = documentSnapshot.getString("Status");

                    payment.setText(payment_sum+"");
                    status.setText(status_id);
                }
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                finish();
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
