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

public class SettingsFragment extends Fragment {
    private FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //startActivity(new Intent(Objects.requireNonNull(getActivity()).getApplicationContext(), MoveActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);
//        ImageView buttonCheck = view.findViewById(R.id.button_check);
//        buttonCheck.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(Objects.requireNonNull(getActivity()).getApplicationContext(), CheckActivity.class));
//                getActivity().finish();
//            }
//        });
        return view;
    }
}
