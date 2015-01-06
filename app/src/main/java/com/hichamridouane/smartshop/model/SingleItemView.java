package com.hichamridouane.smartshop.model;

/**
 * Created by Hicham Ridouane on 05/07/2014.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cengalabs.flatui.views.FlatButton;
import com.hichamridouane.smartshop.view.ImageLoader;
import com.hichamridouane.smartshop.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class SingleItemView extends Activity {

    String article_name;
    String vendor_name;
    String article_image;
    String article_date;
    String article_reduction;
    String article_favoris;
    String article_description;
    SimpleDateFormat sf = new SimpleDateFormat("dd/MM à KK:mm");

    DateFormat frenchFormat = DateFormat.getDateTimeInstance(
            DateFormat.MEDIUM,
            DateFormat.MEDIUM, new Locale("FR","fr")
    );

    ImageLoader imageLoader = new ImageLoader(this);
    Uri uri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.singleitemview);

        Intent i = getIntent();
        // Get the result of rank
        // rank = i.getStringExtra("rank");
        // Get the result of article_name
        article_name = i.getStringExtra("Nom_Article");
        // Get the result of vendor_name
        vendor_name = i.getStringExtra("Nom_Vendeur");
        // Get the result of article_image
        article_image = i.getStringExtra("Image_Article");
        article_date =i.getStringExtra("createdAt");
        article_reduction = i.getStringExtra("Reduction");
        article_favoris = i.getStringExtra("Favoris");
        article_description = i.getStringExtra("Description");

        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",Locale.ENGLISH);
        //String newDate = article_date;
        Date date = new Date();
        try {
            date = format.parse(article_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Locate the TextViews in singleitemview.xml
        //TextView txtrank = (TextView) findViewById(R.id.rank);
        TextView nm_article = (TextView) findViewById(R.id.article_single);
        TextView vd_article = (TextView) findViewById(R.id.vendor_single);
        //TextView dt_article = (TextView)    findViewById(R.id.date_single);
        FlatButton red_article = (FlatButton)findViewById(R.id.reduction_single);
        FlatButton fav_article = (FlatButton)   findViewById(R.id.favoris_single);
        TextView desc_article = (TextView)  findViewById(R.id.description);


        // Locate the ImageView in singleitemview.xml
        ImageView img_article = (ImageView) findViewById(R.id.image_single);

        // Set results to the TextViews
        // txtrank.setText(rank);
        nm_article.setText(article_name);
       // dt_article.setText((frenchFormat.format(article_date)).toString());
       // dt_article.setText(date.toString());
        red_article.setText(article_reduction);
        red_article.setTypeface(null, Typeface.BOLD);
        vd_article.setText("Magazin "+vendor_name);
        fav_article.setText(article_favoris+" favoris");
        fav_article.setTypeface(null, Typeface.BOLD);
        desc_article.setText(article_description);
        //fav_article.setTextColor(getResources().getColor(R.color.gray2));


        // Capture position and set results to the ImageView
        // Passes article_image images URL into ImageLoader.class
        imageLoader.DisplayImage(article_image, img_article);
        //uri = Uri.fromFile(imageLoader);



        FlatButton ajouter_favoris = (FlatButton)findViewById(R.id.ajouter_aux_favoris);
        ajouter_favoris.setTextColor(getResources().getColor(R.color.hot_orange));
        ajouter_favoris.setText(" Ajouter aux favoris");
        ajouter_favoris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SingleItemView.this, R.string.beta, Toast.LENGTH_LONG).show();
            }
        });
        FlatButton partager = (FlatButton)findViewById(R.id.partager);
        partager.setTextColor(getResources().getColor(R.color.gray2));

        FlatButton fermer = (FlatButton)    findViewById(R.id.fermer);
        fermer.setTextColor(getResources().getColor(R.color.gray2));
        fermer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        FlatButton share = (FlatButton) findViewById(R.id.partager);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("image/*");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Titre");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "Message de test");
                //sharingIntent.putExtra(Intent.EXTRA_STREAM, imageLoader);
                startActivity(Intent.createChooser(sharingIntent, "Partager via..."));
            }
        });
    }
}
