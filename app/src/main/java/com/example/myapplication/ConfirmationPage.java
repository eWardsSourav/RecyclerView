package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ConfirmationPage extends AppCompatActivity{

    TextView textView;
    RecyclerView recyclerView;
    NewAdapter adapter;

   static List<AllItems> newList=new ArrayList<>();
    MainInterface mainInterface;

    List<AllItems> buylist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_page);
        textView=findViewById(R.id.textView2);
        recyclerView=findViewById(R.id.rcyview);
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
        adapter= new NewAdapter(getApplicationContext(),buylist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);





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
}