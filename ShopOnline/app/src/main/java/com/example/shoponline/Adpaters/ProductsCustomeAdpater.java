package com.example.shoponline.Adpaters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoponline.Models.Products;
import com.example.shoponline.R;

import java.util.ArrayList;

public class ProductsCustomeAdpater extends BaseAdapter {

    ArrayList<Products> product;

    public ProductsCustomeAdpater(ArrayList<Products> product) {
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
        ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.products_layout, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        }
        else
        {
            viewHolder=(ViewHolder) convertView.getTag();
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
