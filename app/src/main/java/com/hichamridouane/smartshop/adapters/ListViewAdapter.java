package com.hichamridouane.smartshop.adapters;

/**
 * Created by Hicham Ridouane on 05/07/2014.
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cengalabs.flatui.views.FlatButton;
import com.hichamridouane.smartshop.presenter.Articles;
import com.hichamridouane.smartshop.view.ImageLoader;
import com.hichamridouane.smartshop.R;
import com.hichamridouane.smartshop.view.SingleItemView;

public class ListViewAdapter extends BaseAdapter{
    Context context;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    private List<Articles> list_of_articles = null;
    private ArrayList<Articles> arraylist;
    SimpleDateFormat sf = new SimpleDateFormat("d MMM yyyy",Locale.FRANCE);



    public ListViewAdapter(Context context,
                           List<Articles> list_of_articles) {
        this.context = context;
        this.list_of_articles = list_of_articles;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<Articles>();
        this.arraylist.addAll(list_of_articles);
        imageLoader = new ImageLoader(context);
    }

    public class ViewHolder {
        TextView article_name;
        TextView article_vendor;
        TextView article_category;
        ImageView article_image;
        FlatButton article_reduction;
        FlatButton article_favoris;
        TextView article_date;
        TextView article_description;

    }

    @Override
    public int getCount() {
        return list_of_articles.size();
    }

    @Override
    public Object getItem(int position) {
        return list_of_articles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item, null);
            // Locate the TextViews in listview_item.xml
            holder.article_name = (TextView) view.findViewById(R.id.article);
            holder.article_vendor = (TextView) view.findViewById(R.id.vendor);
            holder.article_category = (TextView) view.findViewById(R.id.category);
            holder.article_reduction = (FlatButton) view.findViewById(R.id.pourcentage);
            holder.article_favoris = (FlatButton)view.findViewById(R.id.likes);
            holder.article_date = (TextView)view.findViewById(R.id.date);
            // Locate the ImageView in listview_item.xml
            holder.article_image = (ImageView) view.findViewById(R.id.imagearticle2);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.article_name.setText(list_of_articles.get(position).getArticle_name());
        holder.article_vendor.setText(list_of_articles.get(position)
                .getArticle_vendor());
        holder.article_category.setText(list_of_articles.get(position).getArticle_category());
        holder.article_reduction.setText(list_of_articles.get(position).getArticle_reduction());
        holder.article_reduction.setTypeface(null, Typeface.BOLD);
        holder.article_favoris.setText(list_of_articles.get(position).getArticle_favoris() + " favoris");
        holder.article_favoris.setTextColor(view.getResources().getColor(R.color.gray2));



        holder.article_date.setText(sf.format(list_of_articles.get(position).getArticle_date()).toString());

        // Set the results into ImageView
        imageLoader.DisplayImage(list_of_articles.get(position).getArticle_image(),
                holder.article_image);

        // Listen for ListView Item Click
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                Intent intent = new Intent(context, SingleItemView.class);

                // Pass all data article_name
                intent.putExtra("Nom_Article",
                        (list_of_articles.get(position).getArticle_name()));
                // Pass all data singleVendorName
                intent.putExtra("Nom_Vendeur",
                        (list_of_articles.get(position).getArticle_vendor()));
                // Pass all data category :
                intent.putExtra("Categorie",
                        (list_of_articles.get(position).getArticle_category()));
                intent.putExtra("Reduction",
                        list_of_articles.get(position).getArticle_reduction());
                intent.putExtra("Favoris",
                        list_of_articles.get(position).getArticle_favoris());
                intent.putExtra("createdAt",
                        list_of_articles.get(position).getArticle_date().toString());
                // Pass all data article_image
                intent.putExtra("Image_Article",
                        (list_of_articles.get(position).getArticle_image()));

                intent.putExtra("Description",
                        (list_of_articles.get(position).getArticle_description()));
                // Start SingleItemView Class
                context.startActivity(intent);
            }
        });
        return view;
    }
}
