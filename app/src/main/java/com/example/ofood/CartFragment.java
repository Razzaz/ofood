package com.example.ofood;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class CartFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String userID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    private DocumentReference cart = db.collection("Cart").document(userID);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view =  inflater.inflate(R.layout.fragment_cart, container, false);

        final TextView buttonCheckOut = view.findViewById(R.id.check_out);

        buttonCheckOut.setEnabled(false);

        buttonCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Objects.requireNonNull(getActivity()).getApplicationContext(), CheckOutActivity.class));
            }
        });

        final TextView mBrocoli = view.findViewById(R.id.brocoli);
        final TextView mCarrot = view.findViewById(R.id.carrot);
        final TextView mEggplant = view.findViewById(R.id.eggplant);
        final TextView mLettuce = view.findViewById(R.id.lettuce);
        final TextView mPotato = view.findViewById(R.id.potato);
        final TextView mTomato = view.findViewById(R.id.tomato);

        cart.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    String brocoli = documentSnapshot.getString("Brocoli");
                    String potato = documentSnapshot.getString("Potato");
                    String carrot = documentSnapshot.getString("Carrot");
                    String tomato = documentSnapshot.getString("Tomato");
                    String eggplant = documentSnapshot.getString("Eggplant");
                    String lettuce = documentSnapshot.getString("Lettuce");

                    if(brocoli == null){
                        mBrocoli.setText("0 Kg");
                    }
                    else{
                        mBrocoli.setText(String.format("%s Kg", brocoli));
                        buttonCheckOut.setEnabled(true);
                    }

                    if(carrot == null){
                        mCarrot.setText("0 Kg");
                    }
                    else{
                        mCarrot.setText(String.format("%s Kg", carrot));
                        buttonCheckOut.setEnabled(true);
                    }

                    if(eggplant == null){
                        mEggplant.setText("0 Kg");
                    }
                    else{
                        mEggplant.setText(String.format("%s Kg", eggplant));
                        buttonCheckOut.setEnabled(true);
                    }

                    if(lettuce == null){
                        mLettuce.setText("0 Kg");
                    }
                    else{
                        mLettuce.setText(String.format("%s Kg", lettuce));
                        buttonCheckOut.setEnabled(true);
                    }

                    if(potato == null){
                        mPotato.setText("0 Kg");
                    }
                    else{
                        mPotato.setText(String.format("%s Kg", potato));
                        buttonCheckOut.setEnabled(true);
                    }

                    if(tomato == null){
                        mTomato.setText("0 Kg");
                    }
                    else{
                        mTomato.setText(String.format("%s Kg", tomato));
                        buttonCheckOut.setEnabled(true);
                    }
                }
            }
        });

        return view;
    }
}
