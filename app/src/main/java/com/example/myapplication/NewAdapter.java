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

public class NewAdapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    Context context;
    List<AllItems> alldata;
    QtySetterInterface qtySetterInterface;

    public NewAdapter(Context context, List<AllItems> alldata) {
        this.context = context;
        this.alldata = alldata;
    }


    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.newlayout, parent, false);
        return new Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.itemName.setText(alldata.get(position).item_name);
        //Item Description
        holder.itemDes.setText(alldata.get(position).description);
        holder.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alldata.get(position).qty = alldata.get(position).qty + 1;
//                Toast.makeText(context, ""+alldata.get(0).qty, Toast.LENGTH_SHORT).show();
//                qtySetterInterface.itemQty(alldata.get(position).qty);
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

        }

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alldata.get(position).qty = alldata.get(position).qty + 1;
//                Toast.makeText(context, ""+alldata.get(0).qty, Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alldata.get(position).qty = alldata.get(position).qty - 1;
//                Toast.makeText(context, ""+alldata.get(0).qty, Toast.LENGTH_SHORT).show();

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
        }
    }
}
