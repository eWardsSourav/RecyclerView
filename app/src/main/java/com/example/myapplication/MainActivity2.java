package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity implements MainInterface {
    ProgressBar progressBar;
    TextView textView;
    RecyclerView recyclerView;
    LinearLayout cartlayout;
    TextView rstxt, totalprice, qtytxt, totalquantity, buynowbtn;
    ImageView deletecart;
    ImageView userprofile;
    SearchView searchView;
    ProgressDialog progressDialog;
    LottieAnimationView lottieAnimationView;
    int ttl = 0;
    MainInterface mainInterface;
    List<AllItems> mainItemList = new ArrayList<>();
    List<AllItems> itemsList = new ArrayList<>();
    List<AllItems> filterList = new ArrayList<>();
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        recyclerView = findViewById(R.id.recycler_view);
        rstxt = findViewById(R.id.rstxt);
        totalprice = findViewById(R.id.totalprice);
        qtytxt = findViewById(R.id.qtytxt);
        buynowbtn = findViewById(R.id.buynowbtn);
        totalquantity = findViewById(R.id.totalquantity);
        cartlayout = findViewById(R.id.cartlayour);
        deletecart = findViewById(R.id.deletecart);
        searchView = findViewById(R.id.search);
        userprofile = findViewById(R.id.userprofile);
        lottieAnimationView = findViewById(R.id.ltanime);

//        progressBar.setVisibility(View.VISIBLE);
//        textView.setVisibility(View.VISIBLE);
//        progressDialog = ProgressDialog.show(MainActivity2.this,"","Loading please wait");


        userprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth firebaseAuth =FirebaseAuth.getInstance();
                FirebaseUser user =firebaseAuth.getCurrentUser();
                Intent intent = new Intent(MainActivity2.this,ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
        new ApiManager("http://myewards.in").service.getitems("15657", "15", "24019", "", "", "", "", "1", "50", "", "", "", "name", "asc", "", "").enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
//                Toast.makeText(MainActivity2.this, ""+response.body().message, Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
//                    progressDialog.dismiss();
                    lottieAnimationView.setVisibility(View.GONE);
                    searchView.setVisibility(View.VISIBLE);
                    userprofile.setVisibility(View.VISIBLE);
                    if (!response.body().error) {
                        if (response.body().message.equals("Successful")) {
                            itemsList.addAll(response.body().data.item_list);
                            filterList.addAll(itemsList);
                            mainItemList.clear();
                            mainItemList.addAll(filterList);
                            setRecyclerView();
                            filterSearch();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                Toast.makeText(MainActivity2.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        List<AllItems> buyitems = new ArrayList<>();
        buynowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyitems.clear();
                for (int i = 0; i < adapter.allItemsList.size(); i++) {
                    if (adapter.allItemsList.get(i).qty > 0) {
                        buyitems.add(adapter.allItemsList.get(i));
                    }
                }
//                Toast.makeText(MainActivity2.this, "buyitems Size"+mainItemList.size(), Toast.LENGTH_SHORT).show();
                Gson gson = new Gson();
                String val = gson.toJson(buyitems);

                Intent intent = new Intent(MainActivity2.this, ConfirmationPage.class);
                intent.putExtra("data", val);
                startActivityForResult(intent,1);
            }
        });
    }
    public void setRecyclerView() {
        adapter = new Adapter(getApplicationContext(), filterList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void filterSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

//                adapter.notifyDataSetChanged();
//                Toast.makeText(MainActivity2.this, ""+filterList.get(0), Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList.clear();
                for (int i = 0; i < itemsList.size(); i++) {
                    if (itemsList.get(i).item_name.toLowerCase(Locale.ROOT).contains(s.toLowerCase(Locale.ROOT))) {
                        filterList.add(itemsList.get(i));

                    }
                }
                return false;
            }
        });
    }
//    public void buyNow() {
//
//        Toast.makeText(this, "bn"+buyitems.size(), Toast.LENGTH_SHORT).show();
//
//    }
    @Override
    public void onclick(int price, int quntity) {
//        Toast.makeText(this,"total quantity: "+quntity+" total price"+price, Toast.LENGTH_SHORT).show();
        totalprice.setText(String.valueOf(price));
        totalquantity.setText(String.valueOf(quntity));

        //cart bar hiding
        if (quntity > 0) {
            cartlayout.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
        if (quntity == 0) {
            cartlayout.setVisibility(View.GONE);
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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Toast.makeText(this, "rq"+resultCode, Toast.LENGTH_SHORT).show();

        if (requestCode == 1) {
            if(resultCode == 1) {
//                Toast.makeText(this, "bal", Toast.LENGTH_SHORT).show();
                String  strEditText = data.getStringExtra("MyData");
                Toast.makeText(this, ""+strEditText, Toast.LENGTH_SHORT).show();
                Log.e("dataaa",strEditText);

                Type listType = new TypeToken<ArrayList<AllItems>>(){}.getType();
                List<AllItems> yourClassList = new Gson().fromJson(strEditText, listType);

//                Log.e("LISSTTTTT",new Gson().toJson(yourClassList));
                for (int i=0;i<yourClassList.size();i++){
                    for (int j = 0; j < mainItemList.size(); j++) {
                        if(yourClassList.get(i).item_id==mainItemList.get(j).item_id){
//                            Toast.makeText(this, ""+yourClassList.get(j).qty, Toast.LENGTH_SHORT).show();
                            mainItemList.get(j).qty=yourClassList.get(i).qty;

                        }
                    }
                }

                adapter.notifyDataSetChanged();

//                filterList.clear();
//                filterList.addAll(mainItemList);
//                setRecyclerView();
//                adapter.notifyDataSetChanged();
//                adapter = new Adapter(getApplicationContext(), yourClassList, this);
//                recyclerView.setLayoutManager(new LinearLayoutManager(this));
//                recyclerView.setAdapter(adapter);
            }
        }





    }
}