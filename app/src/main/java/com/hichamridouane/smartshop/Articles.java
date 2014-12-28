package com.hichamridouane.smartshop;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Hicham Ridouane on 05/07/2014.
 */
public class Articles {
    private String Article_category;
    private String Article_name;
    private String Article_vendor;
    private String Article_image;
    private String reduction;
    private String favoris;
    private String description;
    private Date date;

    public String getArticle_category() {
        return Article_category;
    }

    public void setArticle_category(String Article_category) {
        this.Article_category = Article_category;
    }

    public String getArticle_name() {
        return Article_name;
    }

    public void setArticle_name(String Article_name) {
        this.Article_name = Article_name;
    }

    public String getArticle_vendor() {
        return Article_vendor;
    }

    public void setArticle_vendor(String Article_vendor) {
        this.Article_vendor = Article_vendor;
    }

    public String getArticle_image() {
        return Article_image;
    }

    public void setArticle_image(String Article_image) {
        this.Article_image = Article_image;
    }

    public String getArticle_reduction() {
        return reduction;
    }

    public void setArticle_reduction(String reduction) {
        this.reduction = reduction;
    }

    public String getArticle_favoris() {
        return favoris;
    }

    public void setArticle_favoris(String favoris) {
        this.favoris = favoris;
    }

    public Date getArticle_date() {
        DateFormat frenchFormat = DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM,
                DateFormat.MEDIUM, new Locale("FR","fr")
        );
        return date;
    }

    public void setArticle_date(Date date) {

        this.date = date;
    }

    public String getArticle_description() {
        return description;
    }

    public void setArticle_description(String description) {
        this.description = description;
    }

}
