package com.example.autotutoria20;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class b_main_2_lesson_freeuse extends Fragment {

    private static View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.b_main_2_lesson_freeuse, container, false);

        // Set click listeners for the cards
        setCardClickListeners();

        return view;
    }

    // Method to set click listeners for the cards
    private void setCardClickListeners() {
        // Card 1 click listener
        view.findViewById(R.id.card1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchLessonActivity(1);
            }
        });

        // Card 2 click listener
        view.findViewById(R.id.card2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchLessonActivity(2);
            }
        });

        // Card 3 click listener
        view.findViewById(R.id.card3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchLessonActivity(3);
            }
        });

        // Card 4 click listener
        view.findViewById(R.id.card4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchLessonActivity(4);
            }
        });
    }

    // Method to launch activity related to the clicked card
    private void launchLessonActivity(int cardId) {
        Intent intent;
        switch (cardId) {
            case 1:
                intent = new Intent(getActivity(), c_Lesson_freeuse_1.class);
                break;
            case 2:
                intent = new Intent(getActivity(), c_Lesson_freeuse_2.class);
                break;
            case 3:
                intent = new Intent(getActivity(), c_Lesson_freeuse_3.class);
                break;
            case 4:
                intent = new Intent(getActivity(), c_Lesson_freeuse_4.class);
                break;
            default:
                return;
        }
        startActivity(intent);
    }

    // Method to display a toast message
    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
