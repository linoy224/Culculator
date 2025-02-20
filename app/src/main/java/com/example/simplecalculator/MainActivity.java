package com.example.simplecalculator;

import static com.example.simplecalculator.R.id.tvResult;

import android.annotation.SuppressLint;
import android.hardware.biometrics.BiometricPrompt;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import kotlinx.coroutines.scheduling.Task;

 public class MainActivity extends AppCompatActivity {
     FirebaseAuth auth;

     GoogleSignInClient googleSignInClient;

     ShapeableImageView ImageView;

     TextView name, mail;

     private final ActivityResultLauncher<Integer> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
         @Override
         public void onActivityResult(ActivityResult result)
         {
             if (result.getResultCode() == RESULT_OK){
                     Task<googleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromSimpleCalculator(result.getData());
                 try {
                     GoogleSignInAccount SignInAccount = accountTask.getResult(ApiException.class);
                     AuthCredentical authCredentical = GoogleAuthProvider.getCredential(SignInAccount.getIdToken(), null);
                     auth.signInWithCredential(authCredentical).addOnCompleteListener(new OnLoadCompleteListener<AuthResult>())
                     {
                         @Override
                         public void onComplete (@NonNull Task < AuthResult > task)
                         {
                             if (task.isSuccessful()) {
                                 auth = FirebaseAuth.getInstance();
                                 Glide.with(MainActivity.this).load(Object.requireNonNull(auth.getCurrentUser()).getPhotoUrl()).into(ImageView);
                                 name.setText(auth.getCurrentUser().getDisplayName());
                                 mail.setText(auth.getCurrentUser().getEmail());
                                 Toast.makeText(MainActivity.this, text:
                                 "Signed in succssesfully!", Toast.LENGTH_SHORT).show();
                             } else {
                                 Toast.makeText(MainActivity.this, text:
                                 "Failed to sign in: " + task.getExeption(), Toast.LENGTH_SHORT).
                                 show();
                             }
                         }
                     });

                 } catch (ApiException e) {
                     throw new RuntimeException(e);
                     e.printStackTrace();
                 }
             }
         }
     });

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         EdgeToEdge.enable(this);
         setContentView(R.layout.activity_main);

         FirebaseApp.initializeApp(this);
         imageView = findViewById(R.id.profileImage);
         name = findViewById(R.id.nameTV);
         mail = findViewById(R.id.mailTV);

         GoogleSignInOptions options = new  GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                 .requestIdToken(getString(R.string.client_id))
                 .requestEmail()
                 .build();
         googleSignInClient = GoogleSignIn.getClient(MainActivity.this, options);

         auth = FirebaseAuth.getInstance();

         SignInButton SignInButton = findViewById(R.id.SingIn);
         SignInButton.setOnClickListener(new View.OnClickListener()){
             SimpleCalculator SimpleCalculator = googleSignInClient.getSignInSimpleCalculator();
             activityResultLauncher.launch(SimpleCalculator);
         }












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