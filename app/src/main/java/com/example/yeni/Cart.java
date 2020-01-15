package com.example.yeni;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.yeni.Common.Common;
import com.example.yeni.ViewHolder.CartAdapter;
import com.example.yeni.database.Database;
import com.example.yeni.model.Order;
import com.example.yeni.model.Request;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTotalPrice;
    Button btnPlace;

    List<Order> cart= new ArrayList<>();
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        //Firebase
        database=FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");
        //Init
        recyclerView=findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice=findViewById(R.id.total);
        btnPlace=findViewById(R.id.btnPlaceOrder);
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showAlertDiaolog();
            }
        });

        loadListProduct();

    }
    private void showAlertDiaolog() {
        AlertDialog.Builder alertdialog=new AlertDialog.Builder(Cart.this);
        alertdialog.setTitle("One more step!");
        alertdialog.setMessage("Enter your address: ");
        final EditText edtAddress= new EditText(Cart.this);
        LinearLayout.LayoutParams lp= new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtAddress.setLayoutParams(lp);
        alertdialog.setView(edtAddress);// Add edit text to alert
        alertdialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Request request= new Request(
                        Common.currentUser.getPhoneNumber(),
                        Common.currentUser.getName(),
                        edtAddress.getText().toString(),
                        txtTotalPrice.getText().toString(),
                        cart
                );
                //Submit to Firebase, we will using System.CurrentMilli to key
                requests.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(request);
                //Delete cart
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this, "Thank you, Order Place", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
        alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        })   ;
            alertdialog.show();
        }

    private void loadListProduct() {
        cart= new Database(this).getCarts();
        adapter=new CartAdapter(cart,this);
        recyclerView.setAdapter(adapter);
                //Calculate total price
        int total=0;
        for(Order order:cart)
        {
            total += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));
        }
        Locale locale= new Locale("en", "US");
        NumberFormat fmt= NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(fmt.format(total));
    }
}
