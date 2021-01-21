package com.example.shoponline.Adpaters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoponline.Models.Products;
import com.example.shoponline.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class BagCustomAdpater extends BaseAdapter {

    ArrayList<Products> product;
    FirebaseFirestore database;

    public BagCustomAdpater(ArrayList<Products> product) {
        this.product=product;
    }
    @Override
    public int getCount() {
        return product.size();
    }

    @Override
    public Products getItem(int position) {
        return product.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BagCustomAdpater.ViewHolder viewHolder;
        if(convertView==null)
        {
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.bag_layout, null);
            viewHolder=new BagCustomAdpater.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder=(BagCustomAdpater.ViewHolder) convertView.getTag();
        }
        Products products=getItem(position);
        viewHolder.BrandName.setText(products.getBrand_name());
        viewHolder.ProductName.setText(products.getProduct_name());
        viewHolder.Price.setText(products.getProduct_price());
        viewHolder.ImageTV.setImageResource(products.getImage_file());

        return convertView;
    }

    class ViewHolder{

        TextView BrandName;
        TextView ProductName;
        TextView Price;
        ImageView ImageTV;
        public  ViewHolder(View view)
        {
            BrandName=view.findViewById(R.id.Brand);
            ProductName=view.findViewById(R.id.Product_name);
            Price=view.findViewById(R.id.Price);
            ImageTV=view.findViewById(R.id.image_view);
        }
    }
}