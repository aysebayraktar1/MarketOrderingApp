package com.example.yeni;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.yeni.Common.Common;
import com.example.yeni.database.Database;
import com.example.yeni.model.Order;
import com.example.yeni.model.Products;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductDetail extends AppCompatActivity {

    TextView product_Name, Product_Price;
    ImageView product_Image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    String productId="";

    FirebaseDatabase database;
    DatabaseReference product;
    Products currentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);


        //FireBase
        database=FirebaseDatabase.getInstance();
        product= database.getReference("Product");

        //Inıt View
        numberButton=findViewById(R.id.number_button);
        btnCart=findViewById(R.id.btnCart);


        //Get Product id from Intent
        if(getIntent() != null)
            productId=getIntent().getStringExtra("ProductId");
        if(!productId.isEmpty()){
            getDetailProduct(productId);

        }

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database database = new Database(ProductDetail.this);
                Order order = new Order(productId,
                        currentProduct.getName(),
                        numberButton.getNumber(),
                        currentProduct.getPrice(),
                        currentProduct.getDiscount(),
                        Common.currentUser.getPhoneNumber());
                database.addToCart(order);
                //database.cleanCart();
                Toast.makeText(ProductDetail.this, "Added to cart", Toast.LENGTH_SHORT).show();
            }
        });


        product_Name=findViewById(R.id.product_name);
        Product_Price=findViewById(R.id.product_price);
        product_Image=findViewById(R.id.img_food);

        collapsingToolbarLayout=findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);



    }

    private void getDetailProduct(String productId) {
        product.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentProduct=dataSnapshot.getValue(Products.class);

                //Set Image
                Picasso.get().load(currentProduct.getImage()).into(product_Image);

                collapsingToolbarLayout.setTitle(currentProduct.getName());

                Product_Price.setText(currentProduct.getPrice());

                product_Name.setText(currentProduct.getName());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
