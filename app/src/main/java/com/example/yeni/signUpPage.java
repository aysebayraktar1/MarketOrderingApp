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

import com.example.yeni.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class signUpPage extends AppCompatActivity {
    EditText phoneNumber,name,password,email,address;
    Button buttonOK, buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);


        phoneNumber= findViewById(R.id.signInEditTextPhoneNumber);
        name= findViewById(R.id.signUpEditTextName);
        password= findViewById(R.id.signUpEditTextPassword);
        email= findViewById(R.id.signUpEditTextEmail);
        address= findViewById(R.id.signUpEditTextAddress);

        buttonOK= findViewById(R.id.signUpButtonOK);
        buttonCancel= findViewById(R.id.signUpButtonCancel);

        //Init Firebase
        final FirebaseDatabase Database= FirebaseDatabase.getInstance();
        final DatabaseReference table_user= Database.getReference("User");

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog= new ProgressDialog(signUpPage.this);
                mDialog.setMessage("Please Waiting...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //check if already user phone number
                        if(dataSnapshot.child(phoneNumber.getText().toString()).exists()) {
                            mDialog.dismiss();
                            Toast.makeText(signUpPage.this, "Phone Number Already Register !", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            table_user.removeEventListener(this);
                            mDialog.dismiss();
                            User user=new User(phoneNumber.getText().toString(), password.getText().toString(), name.getText().toString(),address.getText().toString(), email.getText().toString());
                            table_user.child(phoneNumber.getText().toString()).setValue(user);

                            Toast.makeText(signUpPage.this, "Sign Up Succesfully !", Toast.LENGTH_SHORT).show();
                            //finish();
                            //finishAffinity();
                            Intent main = new Intent(signUpPage.this , signInPage.class);
                            //main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            startActivity(main);


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
        Intent main = new Intent(signUpPage.this , MainActivity.class);
        //main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(main);
    }
}
