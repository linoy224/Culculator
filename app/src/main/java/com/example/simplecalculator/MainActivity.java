package com.example.simplecalculator;

import static com.example.simplecalculator.R.id.tvResult;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
 Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btnMult), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
    }


    @SuppressLint("SetTextI18n")
    public void onBtnClicked(View view) {

        @SuppressLint("WrongViewCast") EditText et1 = findViewById(tvResult);
        String et1Text = et1.getText().toString();
        Integer num1 = Integer.valueOf(et1Text);

        @SuppressLint("WrongViewCast") EditText et2 = findViewById(R.id.btnMult);
        String et2Text = et2.getText().toString();
        Integer num2 = Integer.valueOf(et2Text);

        Integer result = null;
        if (view.getId() == R.id.btnPlus)
            result = num1 + num2;
        if (view.getId() == R.id.btnMinus)
            result = num1 - num2;
        if (view.getId() == R.id.btnMult)
            result = num1 * num2;
        if (view.getId() == R.id.btnDiv)
            result = num1 / num2;
        if (result != null) {
            TextView tvRes = findViewById(tvResult);
            tvRes.setText(result.toString());
        }



    }
}