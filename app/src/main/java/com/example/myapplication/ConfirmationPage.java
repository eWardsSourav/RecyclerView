package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ConfirmationPage extends AppCompatActivity implements MainInterface{

    TextView textView;
    RecyclerView recyclerView;
    NewAdapter adapter;
    LinearLayout cartlayout;
    TextView rstxt, totalprice, qtytxt, totalquantity, buynowbtn;
    ImageView deletecart;

   static List<AllItems> newList=new ArrayList<>();
    MainInterface mainInterface;
    List<AllItems> buylist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_page);
        textView=findViewById(R.id.textView2);
        recyclerView=findViewById(R.id.rcyview);
        rstxt = findViewById(R.id.rstxt);
        totalprice = findViewById(R.id.totalprice);
        qtytxt = findViewById(R.id.qtytxt);
        buynowbtn = findViewById(R.id.buynowbtn);
        totalquantity = findViewById(R.id.totalquantity);
        cartlayout = findViewById(R.id.cartlayour);
        deletecart = findViewById(R.id.deletecart);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        String vall=(String) b.get("data");

        buylist=new ArrayList<>();

        Type listType = new TypeToken<ArrayList<AllItems>>(){}.getType();
        List<AllItems> yourClassList = new Gson().fromJson(vall, listType);
//        buylist.clear();
        buylist.addAll(yourClassList);




//        List<AllItems> newlist=new ArrayList<>();
//        newlist.addAll(yourClassList);
        adapter= new NewAdapter(getApplicationContext(),buylist,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.totalPriceQuantityFinder();








        newList.clear();
        newList.addAll(buylist);
        adapter.notifyItemRangeInserted(0, newList.size());
//        adapter.notifyDataSetChanged();
//        adapter.notifyDataSetChanged();
//        newList.clear();
//        newList.addAll(buylist);

        Gson gson = new Gson();
        String valu = gson.toJson(newList);

        Log.e("valu",new Gson().toJson(valu));
        Toast.makeText(this, ""+valu, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent();
        intent.putExtra("MyData", valu);
        setResult(1, intent);

//      Intent intent = new Intent(ConfirmationPage.this,MainActivity2.class);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onclick(int price, int quantity) {
        totalprice.setText(String.valueOf(price));
        totalquantity.setText(String.valueOf(quantity));

        //cart bar hiding
        if (quantity == 0) {
            Toast.makeText(this, "all item removed", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ConfirmationPage.this,MainActivity2.class);
            startActivity(intent);
        }

        deletecart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//              Toast.makeText(MainActivity2.this,"click delete btn",Toast.LENGTH_SHORT).show();
               adapter.removeItems();
                Toast.makeText(getApplicationContext(), "all item removed", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }
        });

    }
}