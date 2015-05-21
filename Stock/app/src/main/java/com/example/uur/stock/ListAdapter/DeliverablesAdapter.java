package com.example.uur.stock.ListAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.uur.stock.Model.DeliverablesModel;
import com.example.uur.stock.R;

import java.util.List;

/**
 * Created by Uğur on 18.5.2015.
 */
public class DeliverablesAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DeliverablesModel> modelDeliverables;

    public DeliverablesAdapter(Activity activity, List<DeliverablesModel> modelDeliverables) {
        this.activity = activity;
        this.modelDeliverables = modelDeliverables;
    }

    @Override
    public int getCount() {
        return modelDeliverables.size();
    }

    @Override
    public Object getItem(int position) {
        return modelDeliverables.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(convertView == null)
                convertView = inflater.inflate(R.layout.list_deliverables,null);

            TextView txtUrun = (TextView)convertView.findViewById(R.id.txtUrun);
            TextView txtMusteri = (TextView)convertView.findViewById(R.id.txtMusteri);
            TextView txtOdeme = (TextView)convertView.findViewById(R.id.txtOdeme);
            TextView txtSiparisDurum = (TextView)convertView.findViewById(R.id.txtSiparisDurum);
            TextView txtTeslimTarih = (TextView)convertView.findViewById(R.id.txtTeslimTarihi);
            TextView txtSevkiyatAdresi = (TextView)convertView.findViewById(R.id.txtSevkiyatAdresi);
            TextView txtID = (TextView)convertView.findViewById(R.id.txtid);



            DeliverablesModel d = modelDeliverables.get(position);

            txtUrun.setText(d.getUrunBilgileri());
            txtMusteri.setText(d.getMusteriBilgiler());
            txtOdeme.setText(d.getOdeme());
            txtSiparisDurum.setText(d.getSiparisDurumu());
            txtTeslimTarih.setText(d.getTeslimTarihi());
            txtSevkiyatAdresi.setText(d.getSevkiyatAdresi());
            txtID.setText(d.getID());

        return convertView;
    }
}
