package com.example.yeni;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.yeni.Interface.ItemClickListener;
import com.example.yeni.ViewHolder.ProductsViewHolder;
import com.example.yeni.model.Products;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ProductList extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference productList;
    String categoryId="";
    FirebaseRecyclerAdapter<Products, ProductsViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        //FireBase
        database=FirebaseDatabase.getInstance();
        productList=database.getReference("Product");

        recyclerView= findViewById(R.id.recycler_product);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Get Intent here
        if(getIntent() !=null)
            categoryId=getIntent().getStringExtra("CategoryId");
        if(!categoryId.isEmpty() && categoryId !=null)
        {
          loadListProduct(categoryId);
        }

}
    private void loadListProduct(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Products,ProductsViewHolder>(Products.class,
                R.layout.product_item,
                ProductsViewHolder.class,
                productList.orderByChild("MenuID").equalTo(categoryId) ){
            @Override
            protected void populateViewHolder(ProductsViewHolder viewHolder, Products model, int position) {
                viewHolder.product_name.setText(model.getName());
                Picasso.get().load(model.getImage())
                        .into(viewHolder.product_image);

                final Products local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLoginClick) {
                        //Start New Activity
                        Intent productDetail= new Intent(ProductList.this, ProductDetail.class);
                        productDetail.putExtra("ProductId", adapter.getRef(position).getKey());//Send product id to new activity
                        startActivity(productDetail);
                    }
                });
            }
        };
        //set Adapter
        recyclerView.setAdapter(adapter);
    }
}
