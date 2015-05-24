package com.example.uur.stock.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.uur.stock.Model.TeslimEdilenlerModel;
import com.example.uur.stock.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Uğur on 24.5.2015.
 */
public class TeslimEdilenlerAdapter extends BaseAdapter implements Filterable{
    LayoutInflater inflater;
    private List<TeslimEdilenlerModel> modelTeslimEdilenler;
    private List<TeslimEdilenlerModel> _modelTeslimEdilenler;
    private Context context;
    private ValueFilter valueFilter;


    public TeslimEdilenlerAdapter( Context context, List<TeslimEdilenlerModel> modelTeslimEdilenler) {
        super();

        this.modelTeslimEdilenler = modelTeslimEdilenler;
        this.context=context;
        _modelTeslimEdilenler=modelTeslimEdilenler;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getFilter();
    }

    @Override
    public int getCount() {
        return modelTeslimEdilenler.size();
    }

    @Override
    public Object getItem(int position) {
        return modelTeslimEdilenler.get(position);
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
        TeslimEdilenlerModel d = modelTeslimEdilenler.get(position);
        txtUrun.setText(d.getUrunBilgileri());
        txtMusteri.setText(d.getMusteriBilgiler());
        txtID.setText(d.getID());
        return convertView;
    }
    public void updateResults(List<TeslimEdilenlerModel> results){
        modelTeslimEdilenler=results;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        if(valueFilter==null){
            valueFilter=new ValueFilter();
        }
        return valueFilter;
    }


    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if(constraint!= null && constraint.length()>0){
                ArrayList<TeslimEdilenlerModel> filterList = new ArrayList<TeslimEdilenlerModel>();
                for(int i = 0;i<_modelTeslimEdilenler.size();i++){
                    if((_modelTeslimEdilenler.get(i).getMusteriBilgiler().toUpperCase())
                            .contains(constraint.toString().toUpperCase())){
                        TeslimEdilenlerModel model = new TeslimEdilenlerModel();
                        model.setID(_modelTeslimEdilenler.get(i).getID());
                        model.setMusteriBilgiler(_modelTeslimEdilenler.get(i).getMusteriBilgiler());
                        model.setUrunBilgileri(_modelTeslimEdilenler.get(i).getUrunBilgileri());
                        filterList.add(model);
                    }
                }
                results.count=filterList.size();
                results.values=filterList;
            }else{
                results.count=_modelTeslimEdilenler.size();
                results.values=_modelTeslimEdilenler;
            }
            return results;
        }
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            modelTeslimEdilenler=(ArrayList<TeslimEdilenlerModel>) results.values;
            notifyDataSetChanged();
        }
    }
}
