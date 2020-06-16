package com.example.ofood;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

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
        final TextView tvCarrot = findViewById(R.id.carrot0);
        final TextView tvBrocolli = findViewById(R.id.brocoli);
        final TextView tvPotato = findViewById(R.id.potato);
        final TextView tvEggplant = findViewById(R.id.eggplant);
        final TextView tvLettuce = findViewById(R.id.lettuce);
        final TextView tvTomato = findViewById(R.id.tomato);

        final TextView status = findViewById(R.id.status);
        Button home = findViewById(R.id.home);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        idOrder = sharedPreferences.getString(orderState, null);

        orderId.setText(idOrder.substring(0, 7));

        db.collection("UsersData").document(userID).collection("Orders").document(idOrder).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    long payment_sum = documentSnapshot.getLong("Price");
                    long carrot, potato, lettuce, tomato, eggplant, brocoli;
                    String status_id = documentSnapshot.getString("Status");

                    if(documentSnapshot.getLong("Carrot") != null){
                        carrot = documentSnapshot.getLong("Carrot");
                        tvCarrot.setText(carrot+"");
                    }

                    if(documentSnapshot.getLong("Brocoli") != null){
                        brocoli = documentSnapshot.getLong("Brocoli");
                        tvBrocolli.setText(brocoli+"");
                    }

                    if(documentSnapshot.getLong("Tomato") != null){
                        tomato = documentSnapshot.getLong("Tomato");
                        tvTomato.setText(tomato+"");
                    }

                    if(documentSnapshot.getLong("Potato") != null){
                        potato = documentSnapshot.getLong("Potato");
                        tvPotato.setText(potato+"");
                    }

                    if(documentSnapshot.getLong("Eggplant") != null){
                        eggplant = documentSnapshot.getLong("Eggplant");
                        tvEggplant.setText(eggplant+"");
                    }

                    if(documentSnapshot.getLong("Lettuce") != null){
                        lettuce = documentSnapshot.getLong("Lettuce");
                        tvLettuce.setText(lettuce+"");
                    }

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
