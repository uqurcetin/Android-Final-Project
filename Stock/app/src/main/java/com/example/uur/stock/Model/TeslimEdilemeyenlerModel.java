package com.example.uur.stock.Model;

/**
 * Created by Uğur on 24.5.2015.
 */
public class TeslimEdilemeyenlerModel {
    private String ID;
    private String MusteriBilgiler;
    private String UrunBilgileri;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMusteriBilgiler() {
        return MusteriBilgiler;
    }

    public void setMusteriBilgiler(String musteriBilgiler) {
        MusteriBilgiler = musteriBilgiler;
    }

    public String getUrunBilgileri() {
        return UrunBilgileri;
    }

    public void setUrunBilgileri(String urunBilgileri) {
        UrunBilgileri = urunBilgileri;
    }


}
