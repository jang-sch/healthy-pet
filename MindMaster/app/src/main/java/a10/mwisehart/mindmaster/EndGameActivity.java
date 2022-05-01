package a10.mwisehart.mindmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        Intent intent = getIntent();
        boolean win = intent.getBooleanExtra("win", false);
        TextView textView = findViewById(R.id.txtMessage);
        textView.setText(win? "You are a Mind Master" : "Game over");
        Button btn = findViewById(R.id.btnPlayAgain);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                finish();

            }


        });
    }
}