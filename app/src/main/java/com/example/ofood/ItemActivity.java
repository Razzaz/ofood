package com.example.ofood;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class ItemActivity extends AppCompatActivity {

    int[] itemImages = {R.drawable.item_eggplant, R.drawable.item_carrot, R.drawable.item_tomato, R.drawable.item_potato, R.drawable.item_broccoli, R.drawable.item_lettuce};
    String[] foodNames = {"Eggplant", "Carrot", "Tomato", "Potato", "Brocoli", "Lettuce"};

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextView mItemPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTransparentStatusBarOnly(ItemActivity.this);

        try
        {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        }
        catch (NullPointerException ignored){}

        setContentView(R.layout.activity_item);

        String foodName = getIntent().getStringExtra("foodName");
        String itemDescription = getIntent().getStringExtra("itemDescription");

        TextView mFoodName = findViewById(R.id.foodName);
        TextView mItemName = findViewById(R.id.itemName);
        mItemPrice = findViewById(R.id.itemPrice);
        TextView mItemDescription = findViewById(R.id.itemDescription);
        ImageView mItemImage = findViewById(R.id.itemImage);

        mFoodName.setText(foodName);
        mItemName.setText(foodName);
        DocumentReference vegetable = db.collection("Vegetables").document(foodName);
        vegetable.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    String price = documentSnapshot.getString("Price");
                    mItemPrice.setText(String.format("Rp.%s/Kg", price));
                }
                else {
                    mItemPrice.setText("");
                }
            }
        });

        mItemDescription.setText(itemDescription);
        assert foodName != null;
        for(int i = 0; i < foodNames.length; i++){
            if(foodName.equals(foodNames[i])){
                mItemImage.setImageResource(itemImages[i]);
            }
        }
    }

    public void setTransparentStatusBarOnly(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
