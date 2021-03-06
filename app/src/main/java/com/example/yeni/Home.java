package com.example.yeni;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.yeni.Common.Common;
import com.example.yeni.Interface.ItemClickListener;
import com.example.yeni.ViewHolder.MenuViewHolder;
import com.example.yeni.model.Category;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseDatabase database;
    DatabaseReference category;
    TextView txtFullName;
    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        //Init FireBase

        database= FirebaseDatabase.getInstance();
        category= database.getReference("Category");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent=new Intent(Home.this, Cart.class);
                startActivity(cartIntent);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //Set Name for User
        View headerView = navigationView.getHeaderView(0);
        txtFullName =headerView.findViewById(R.id.txtFullName);
        txtFullName.setText(Common.currentUser.getName());

        //Load menu
        recycler_menu =findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        loadMenu();

    }

    private void loadMenu() {
         adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.menu_item,MenuViewHolder.class,category) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {
                viewHolder.txtMenuName.setText(model.getName());
                Picasso.get().load(model.getImage()).into(viewHolder.imageView);
                final Category clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLoginClick) {
                        //Get Category Id and send to new activity
                        Intent productList=new Intent(Home.this, ProductList.class);
                        //Because Category Id is key
                        productList.putExtra("CategoryId", adapter.getRef(position).getKey());
                        startActivity(productList);
                    }
                });
            }
        };
        recycler_menu.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent main = new Intent(Home.this , MainActivity.class);
        //main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {



        } else if (id == R.id.nav_card) {
            Intent cartIntent =  new Intent(Home.this , Cart.class);
            startActivity(cartIntent);

        } else if (id == R.id.nav_orders) {
            Intent orderIntent = new Intent(Home.this , OrderStatus.class);
            startActivity(orderIntent);

        } else if (id == R.id.nav_signOut) {
            Intent main = new Intent(Home.this , MainActivity.class);
            //main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(main);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
