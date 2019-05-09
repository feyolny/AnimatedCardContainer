package com.animatedcardcontainer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.feyolny.view.AnimatedCardContainer;

public class MainActivity extends AppCompatActivity {

    private AnimatedCardContainer animatedCard;
    private AnimationCardAdapter cardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animatedCard = findViewById(R.id.animatedCard);
        cardAdapter = new AnimationCardAdapter();
        animatedCard.setContainerAdapter(cardAdapter);

    }
}
