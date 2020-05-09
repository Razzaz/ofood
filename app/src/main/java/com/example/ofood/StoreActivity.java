package com.example.ofood;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class StoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTransparentStatusBarOnly(StoreActivity.this);

        try
        {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        }
        catch (NullPointerException ignored){}

        setContentView(R.layout.activity_store);

        Button buttonBroccoli = findViewById(R.id.buttonBroccoli);
        Button buttonTomato = findViewById(R.id.buttonTomato);
        Button buttonPotato = findViewById(R.id.buttonPotato);
        Button buttonCarrot = findViewById(R.id.buttonCarrot);
        Button buttonLettuce = findViewById(R.id.buttonLettuce);
        Button buttonEggplant = findViewById(R.id.buttonEggplant);

        buttonBroccoli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StoreActivity.this, ItemActivity.class);
                intent.putExtra("foodName","Brocoli");
                intent.putExtra("itemPrice", 14000);
                intent.putExtra("itemDescription", "Herbaceous annual or biennial grown for its edible flower heads which are used as a vegetable.");
                startActivity(intent);
            }
        });

        buttonTomato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StoreActivity.this, ItemActivity.class);
                intent.putExtra("foodName","Tomato");
                intent.putExtra("itemPrice", 15000);
                intent.putExtra("itemDescription", "Fruits that are considered vegetables by nutritionists. Botanically, a fruit is a ripened flower ovary and contains seeds.");
                startActivity(intent);
            }
        });

        buttonPotato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StoreActivity.this, ItemActivity.class);
                intent.putExtra("foodName","Potato");
                intent.putExtra("itemPrice", 15000);
                intent.putExtra("itemDescription", "A root vegetable. It is a small plant with large leaves. The part of the potato that people eat is a tuber that grows under the ground.");
                startActivity(intent);
            }
        });

        buttonCarrot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StoreActivity.this, ItemActivity.class);
                intent.putExtra("foodName","Carrot");
                intent.putExtra("itemPrice", 15000);
                intent.putExtra("itemDescription", "A root vegetable, usually orange in colour, though purple, black, red, white, and yellow cultivars exist.");
                startActivity(intent);
            }
        });

        buttonLettuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StoreActivity.this, ItemActivity.class);
                intent.putExtra("foodName","Lettuce");
                intent.putExtra("itemPrice", 15000);
                intent.putExtra("itemDescription", "An annual plant of the daisy family, Asteraceae. It is most often grown as a leaf vegetable, but sometimes for its stem and seeds.");
                startActivity(intent);
            }
        });

        buttonEggplant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StoreActivity.this, ItemActivity.class);
                intent.putExtra("foodName","Eggplant");
                intent.putExtra("itemPrice", 15000);
                intent.putExtra("itemDescription", "A plant species in the nightshade family Solanaceae. Solanum melongena is grown worldwide for its edible fruit.");
                startActivity(intent);
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
