package com.example.uur.stock.Model;

/**
 * Created by Uğur on 18.5.2015.
 */
public class DeliverablesModel  {
    private String ID;
    private String MusteriBilgiler;
    private String UrunBilgileri;
    public DeliverablesModel(){
    }

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
