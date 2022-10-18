package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    Context context;
    List<AllItems> allItemsList;
    List<Integer> val = new ArrayList<>();
    LinearLayout linearLayout;
    MainInterface mainInterface;

    public Adapter(Context context, List<AllItems> list,MainInterface mainInterface) {
        this.context = context;
        this.allItemsList = list;
        this.mainInterface=mainInterface;
    }

    //    public Adapter(List<AllItems> allItemsList, MainInterface mainInterface) {
//        this.allItemsList = allItemsList;
//        this.mainInterface = mainInterface;
//    }

    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.newlayout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
       // Item Name
        holder.itemName.setText(allItemsList.get(position).item_name);
//Item Description
        holder.itemDes.setText(allItemsList.get(position).description);
        //Add Button

        holder.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                allItemsList.get(position).qty = allItemsList.get(position).qty + 1;
//                ttl=allItemsList.get(position).qty + 1;
//                totalQuantityFinder();
//                mainInterface.onclick(allItemsList.get(position).qty, (int) allItemsList.get(position).item_selling_price);
                totalPriceQuantityFinder();
                notifyDataSetChanged();
            }

        });



        if (allItemsList.get(position).qty == 0) {
            holder.addbtn.setVisibility(View.VISIBLE);
            holder.cartitems.setVisibility(View.GONE);
        } else {
            holder.addbtn.setVisibility(View.GONE);
            holder.cartitems.setVisibility(View.VISIBLE);
            holder.num.setText(String.valueOf(allItemsList.get(position).qty));

        }

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allItemsList.get(position).qty = allItemsList.get(position).qty + 1;
//                totalQuantityFinder();
//                mainInterface.onclick(allItemsList.get(position).qty);
                totalPriceQuantityFinder();
                notifyDataSetChanged();
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allItemsList.get(position).qty = allItemsList.get(position).qty - 1;
//                totalQuantityFinder();
//                mainInterface.onclick(allItemsList.get(position).qty);
                totalPriceQuantityFinder();
                notifyDataSetChanged();


            }
        });


        if (allItemsList.get(position).item_marked_price == allItemsList.get(position).item_selling_price) {
            holder.sprice.setText(String.valueOf(allItemsList.get(position).item_selling_price));
            holder.mprice.setVisibility(View.GONE);

        } else {
            holder.mprice.setVisibility(View.VISIBLE);
            holder.mprice.setText(String.valueOf(allItemsList.get(position).item_marked_price));
            holder.sprice.setText(String.valueOf(allItemsList.get(position).item_selling_price));
            holder.mprice.setPaintFlags(holder.mprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }


    }

    @Override
    public int getItemCount() {
        return allItemsList.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemDes;
        RoundedImageView roundedImageView;
        TextView sprice, mprice;
        TextView addbtn;
        TextView minus, num, plus;
        LinearLayout cartitems;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            roundedImageView = itemView.findViewById(R.id.img);
            itemName = itemView.findViewById(R.id.nam);
            itemDes = itemView.findViewById(R.id.desss);
            sprice = itemView.findViewById(R.id.sprice);
            mprice = itemView.findViewById(R.id.mprice);
            addbtn = itemView.findViewById(R.id.addbtn);
            minus = itemView.findViewById(R.id.minus);
            num = itemView.findViewById(R.id.num);
            plus = itemView.findViewById(R.id.plus);
            cartitems = itemView.findViewById(R.id.cartitems);

//            addbtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mainInterface.onclick(getAdapterPosition());
//                }
//            });
//
//
//            plus.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mainInterface.onclick(getAdapterPosition());
//                }
//            });
//
//            minus.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mainInterface.onclick(getAdapterPosition());
//                }
//            });




        }
    }

//    public void totalQuantityFinder(){
//
////        mainActivity2.totalquantity.setText(String.valueOf(qty));
//        int ttl=0;
//        for (int i=0;i<allItemsList.size();i++){
//            ttl+=allItemsList.get(i).qty;
//        }
////        Toast.makeText(context.getApplicationContext(), "Total Quantity: "+ttl, Toast.LENGTH_SHORT).show();
//    }
    public  void totalPriceQuantityFinder(){
        int ttl=0;
        int ttlprice=0;
        for (int i=0;i<allItemsList.size();i++){
            ttl+=allItemsList.get(i).qty;
           ttlprice+=allItemsList.get(i).item_selling_price * allItemsList.get(i).qty;
        }
        mainInterface.onclick(ttlprice,ttl);
//        mainInterface.onclick(ttlprice);
//        Toast.makeText(context, ""+ttlprice, Toast.LENGTH_SHORT).show();

    }
    public void removeItems() {
        for (int i=0;i<allItemsList.size();i++){
            if (allItemsList.get(i).qty>0){
                allItemsList.get(i).qty=0;
            }
        }

        totalPriceQuantityFinder();


    }



}
