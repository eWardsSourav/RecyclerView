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

import java.util.List;

public class NewAdapter extends RecyclerView.Adapter<NewAdapter.MyView> {
    Context context;
    List<AllItems> alldata;

    public NewAdapter(Context context, List<AllItems> alldata) {
        this.context = context;
        this.alldata = alldata;
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.newlayout,parent,false);
        return new MyView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, @SuppressLint("RecyclerView") int position) {
        // Item Name
        holder.itemName.setText(alldata.get(position).item_name);
        //Item Description
        holder.itemDes.setText(alldata.get(position).description);
        //Add Button

        holder.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alldata.get(position).qty = alldata.get(position).qty + 1;

                notifyDataSetChanged();
            }

        });



        if (alldata.get(position).qty == 0) {
            holder.addbtn.setVisibility(View.VISIBLE);
            holder.cartitems.setVisibility(View.GONE);
        } else {
            holder.addbtn.setVisibility(View.GONE);
            holder.cartitems.setVisibility(View.VISIBLE);
            holder.num.setText(String.valueOf(alldata.get(position).qty));
//            holder.num.setText(String.valueOf(newAdapter.alldata.get(position).qty));

        }

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alldata.get(position).qty = alldata.get(position).qty + 1;
                notifyDataSetChanged();
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alldata.get(position).qty = alldata.get(position).qty - 1;

//                totalQuantityFinder();
//                mainInterface.onclick(allItemsList.get(position).qty);
                notifyDataSetChanged();


            }
        });


        if (alldata.get(position).item_marked_price == alldata.get(position).item_selling_price) {
            holder.sprice.setText(String.valueOf(alldata.get(position).item_selling_price));
            holder.mprice.setVisibility(View.GONE);

        } else {
            holder.mprice.setVisibility(View.VISIBLE);
            holder.mprice.setText(String.valueOf(alldata.get(position).item_marked_price));
            holder.sprice.setText(String.valueOf(alldata.get(position).item_selling_price));
            holder.mprice.setPaintFlags(holder.mprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }


    }

    @Override
    public int getItemCount() {
        return alldata.size();
    }

    public static class MyView extends RecyclerView.ViewHolder {
        TextView itemName, itemDes;
        RoundedImageView roundedImageView;
        TextView sprice, mprice;
        TextView addbtn;
        TextView minus, num, plus;
        LinearLayout cartitems;
        public MyView(@NonNull View itemView) {
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
        }
    }
}
