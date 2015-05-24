package com.example.uur.stock.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.uur.stock.Model.TeslimEdilemeyenlerModel;
import com.example.uur.stock.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Uğur on 24.5.2015.
 */
public class TeslimEdilemeyenlerAdapter extends BaseAdapter implements Filterable {
    LayoutInflater inflater;
    private List<TeslimEdilemeyenlerModel> modelTeslimEdilemeyenler ;
    private List<TeslimEdilemeyenlerModel> _modelTeslimEdilemeyenler ;
    private Context context;
    private ValueFilter valueFilter;


    public TeslimEdilemeyenlerAdapter(Context context,List<TeslimEdilemeyenlerModel> modelTeslimEdilemeyenler){
        super();
        this.modelTeslimEdilemeyenler=modelTeslimEdilemeyenler;
        this.context = context;
        _modelTeslimEdilemeyenler=modelTeslimEdilemeyenler;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getFilter();
    }
    @Override
    public int getCount() {
        return modelTeslimEdilemeyenler.size();
    }

    @Override
    public Object getItem(int position) {
        return modelTeslimEdilemeyenler.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
            convertView = inflater.inflate(R.layout.list_row,null);
        TextView txtUrun = (TextView)convertView.findViewById(R.id.title);
        TextView txtMusteri = (TextView)convertView.findViewById(R.id.artist);
        TextView txtID = (TextView)convertView.findViewById(R.id.duration);

        TeslimEdilemeyenlerModel d = modelTeslimEdilemeyenler.get(position);
        txtUrun.setText(d.getUrunBilgileri());
        txtMusteri.setText(d.getMusteriBilgiler());
        txtID.setText(d.getID());
        return convertView;

    }
    public void updateResults(List<TeslimEdilemeyenlerModel> results){
        modelTeslimEdilemeyenler=results;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        if(valueFilter==null){
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }
    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if(constraint!= null && constraint.length()>0){
                ArrayList<TeslimEdilemeyenlerModel> filterList = new ArrayList<TeslimEdilemeyenlerModel>();
                for(int i = 0;i<_modelTeslimEdilemeyenler.size();i++){
                    if((_modelTeslimEdilemeyenler.get(i).getMusteriBilgiler().toUpperCase())
                            .contains(constraint.toString().toUpperCase())){
                        TeslimEdilemeyenlerModel model = new TeslimEdilemeyenlerModel();
                        model.setID(_modelTeslimEdilemeyenler.get(i).getID());
                        model.setMusteriBilgiler(_modelTeslimEdilemeyenler.get(i).getMusteriBilgiler());
                        model.setUrunBilgileri(_modelTeslimEdilemeyenler.get(i).getUrunBilgileri());
                        filterList.add(model);
                    }
                }
                results.count=filterList.size();
                results.values=filterList;
            }else{
                results.count=_modelTeslimEdilemeyenler.size();
                results.values=_modelTeslimEdilemeyenler;
            }
            return results;
        }
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            modelTeslimEdilemeyenler=(ArrayList<TeslimEdilemeyenlerModel>) results.values;
            notifyDataSetChanged();
        }
    }
}
