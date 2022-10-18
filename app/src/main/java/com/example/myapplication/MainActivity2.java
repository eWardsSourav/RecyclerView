package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity2 extends AppCompatActivity implements MainInterface {
    ProgressBar progressBar;
    TextView textView;
    RecyclerView recyclerView;
    LinearLayout cartlayout;
    TextView rstxt, totalprice, qtytxt, totalquantity, buynowbtn;
    ImageView deletecart;
    int ttl=0;

    MainInterface mainInterface;

    List<AllItems> itemsList = new ArrayList<>();
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        progressBar = findViewById(R.id.progressBar);

        textView = findViewById(R.id.textView);
        recyclerView = findViewById(R.id.recycler_view);

        rstxt = findViewById(R.id.rstxt);
        totalprice = findViewById(R.id.totalprice);
        qtytxt = findViewById(R.id.qtytxt);
        buynowbtn = findViewById(R.id.buynowbtn);
        totalquantity=findViewById(R.id.totalquantity);
        cartlayout=findViewById(R.id.cartlayour);
        deletecart=findViewById(R.id.deletecart);

        progressBar.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);


        new ApiManager("http://myewards.in").service.getitems("15657", "15", "24019", "", "", "", "", "1", "50", "", "", "", "name", "asc", "", "").enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
//                Toast.makeText(MainActivity2.this, ""+response.body().message, Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                    if (!response.body().error) {
                        if (response.body().message.equals("Successful")) {
                            itemsList.addAll(response.body().data.item_list);
                            setRecyclerView();

                        }
                    }


                }
                {

                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                Toast.makeText(MainActivity2.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void setRecyclerView() {
        adapter = new Adapter(getApplicationContext(), itemsList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onclick(int price,int quntity) {
//        Toast.makeText(this,"total quantity: "+quntity+" total price"+price, Toast.LENGTH_SHORT).show();
        totalprice.setText(String.valueOf(price));
        totalquantity.setText(String.valueOf(quntity));
        deletecart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(MainActivity2.this,"click delete btn",Toast.LENGTH_SHORT).show();

            adapter.removeItems();
            Toast.makeText(getApplicationContext(),"all item removed",Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();



            }
        });

    }
}