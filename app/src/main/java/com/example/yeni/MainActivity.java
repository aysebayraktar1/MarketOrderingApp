package com.example.yeni;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnSignIn , btnSignUp;
    TextView txtSlogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtSlogan = findViewById(R.id.txtSlogan);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View view){
                Intent redirectSignInPage= new Intent(MainActivity.this,signInPage.class);
                startActivity(redirectSignInPage);
                finish();
            }

        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redirectSignUpPage= new Intent(MainActivity.this,signUpPage.class);
                startActivity(redirectSignUpPage);
                finish();
            }
        });
        }
    @Override
    public void onBackPressed() {
        /*DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
    }
}
