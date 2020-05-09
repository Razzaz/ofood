package com.example.ofood;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Objects;

public class StoreFragment extends Fragment {

    private FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_store, container, false);
        ImageView gotoStore = view.findViewById(R.id.gotoStore);
        gotoStore.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Objects.requireNonNull(getActivity()).getApplicationContext(), StoreActivity.class));
                //getActivity().finish();
            }
        });
        return view;
    }
}
