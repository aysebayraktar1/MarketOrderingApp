package com.example.yeni;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yeni.Common.Common;
import com.example.yeni.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class signInPage extends AppCompatActivity {

    EditText phoneNumber, password;
    Button ButtonOK, ButtonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);

         phoneNumber= (EditText)findViewById(R.id.phoneNumberEditText);
         password= (EditText)findViewById(R.id.passwordEditText);
         ButtonOK=(Button)findViewById(R.id.signInButtonOK);
         ButtonCancel=(Button)findViewById(R.id.signInButtonCancel);

         //Inıt Database
         final FirebaseDatabase Database= FirebaseDatabase.getInstance();
         final DatabaseReference table_user= Database.getReference("User");

        ButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog= new ProgressDialog(signInPage.this);
                mDialog.setMessage("Please Waiting...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //Check If User not exist in Database
                        if (dataSnapshot.child(phoneNumber.getText().toString()).exists()) {
                            //Get User Information
                            mDialog.dismiss();
                            User user = dataSnapshot.child(phoneNumber.getText().toString()).getValue(User.class);
                            assert user != null;

                            if (user.getPassword().equals(password.getText().toString())) {
                                {
                                    table_user.removeEventListener(this);
                                    user.setPhoneNumber(phoneNumber.getText().toString());
                                    Toast.makeText(signInPage.this, "Sign in successfully !", Toast.LENGTH_SHORT).show();
                                    Intent homeIntent= new Intent(signInPage.this, Home.class);
                                    Common.currentUser= user;
                                    startActivity(homeIntent);
                                    finish();
                               }
                            }
                            else {
                                Toast.makeText(signInPage.this, "Sign in failed !", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(signInPage.this, "User Not Exist !", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent main = new Intent(signInPage.this , MainActivity.class);
        //main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(main);
    }
}
