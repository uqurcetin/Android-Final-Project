package com.example.uur.stock.Model;

/**
 * Created by Uğur on 18.5.2015.
 */
public class DeliverablesModel  {
    private String ID;
    private String MusteriBilgiler;
    private String TeslimTarihi;
    private String SiparisDurumu;
    private String UrunBilgileri;
    private String SevkiyatAdresi;
    private String Odeme;

    public DeliverablesModel(){

    }

    public DeliverablesModel(String id,String musteriBilgiler, String teslimTarihi, String siparisDurumu,
                             String urunBilgileri, String sevkiyatAdresi,String odeme) {
        ID=id;
        MusteriBilgiler = musteriBilgiler;
        TeslimTarihi = teslimTarihi;
        SiparisDurumu = siparisDurumu;
        UrunBilgileri = urunBilgileri;
        SevkiyatAdresi = sevkiyatAdresi;
        Odeme = odeme;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getOdeme() {
        return Odeme;
    }

    public void setOdeme(String odeme) {
        Odeme = odeme;
    }

    public String getMusteriBilgiler() {
        return MusteriBilgiler;
    }

    public void setMusteriBilgiler(String musteriBilgiler) {
        MusteriBilgiler = musteriBilgiler;
    }

    public String getTeslimTarihi() {
        return TeslimTarihi;
    }

    public void setTeslimTarihi(String teslimTarihi) {
        TeslimTarihi = teslimTarihi;
    }

    public String getSiparisDurumu() {
        return SiparisDurumu;
    }

    public void setSiparisDurumu(String siparisDurumu) {
        SiparisDurumu = siparisDurumu;
    }

    public String getUrunBilgileri() {
        return UrunBilgileri;
    }

    public void setUrunBilgileri(String urunBilgileri) {
        UrunBilgileri = urunBilgileri;
    }

    public String getSevkiyatAdresi() {
        return SevkiyatAdresi;
    }

    public void setSevkiyatAdresi(String sevkiyatAdresi) {
        SevkiyatAdresi = sevkiyatAdresi;
    }

    @Override
    public String toString() {
        return "DeliverablesModel{" +
                "MusteriBilgiler='" + MusteriBilgiler + '\'' +
                ", TeslimTarihi='" + TeslimTarihi + '\'' +
                ", SiparisDurumu='" + SiparisDurumu + '\'' +
                ", UrunBilgileri='" + UrunBilgileri + '\'' +
                ", SevkiyatAdresi='" + SevkiyatAdresi + '\'' +
                ", Odeme='" + Odeme + '\'' +
                '}';
    }
}
