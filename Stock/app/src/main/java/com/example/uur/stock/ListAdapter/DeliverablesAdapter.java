package com.example.uur.stock.ListAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.uur.stock.Model.DeliverablesModel;
import com.example.uur.stock.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Uğur on 18.5.2015.
 */
public class DeliverablesAdapter extends BaseAdapter implements Filterable{
    private LayoutInflater inflater;
    private List<DeliverablesModel> modelDeliverables;
    private List<DeliverablesModel> _modelDeliverables;
    private Context context;
    private ValueFilter valueFilter;

    public DeliverablesAdapter(Context context, List<DeliverablesModel> modelDeliverables) {
        super();
        this.modelDeliverables = modelDeliverables;
        this.context = context;
        _modelDeliverables = modelDeliverables;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getFilter();
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

            if(convertView == null)
                convertView = inflater.inflate(R.layout.list_row,null);

            TextView txtUrun = (TextView)convertView.findViewById(R.id.title);
            TextView txtMusteri = (TextView)convertView.findViewById(R.id.artist);
            TextView txtID = (TextView)convertView.findViewById(R.id.duration);

            DeliverablesModel d = modelDeliverables.get(position);

            txtUrun.setText(d.getUrunBilgileri());
            txtMusteri.setText(d.getMusteriBilgiler());
            txtID.setText(d.getID());

        return convertView;
    }
    public void updateResults(List<DeliverablesModel> results){
        modelDeliverables=results;
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
                ArrayList<DeliverablesModel> filterList = new ArrayList<DeliverablesModel>();
                for(int i = 0;i<_modelDeliverables.size();i++){
                    if((_modelDeliverables.get(i).getMusteriBilgiler().toUpperCase())
                            .contains(constraint.toString().toUpperCase())){
                        DeliverablesModel model = new DeliverablesModel();
                        model.setID(_modelDeliverables.get(i).getID());
                        model.setMusteriBilgiler(_modelDeliverables.get(i).getMusteriBilgiler());
                        model.setUrunBilgileri(_modelDeliverables.get(i).getUrunBilgileri());
                        filterList.add(model);
                    }
                }
                results.count=filterList.size();
                results.values=filterList;
            }else{
                results.count=_modelDeliverables.size();
                results.values=_modelDeliverables;
            }
            return results;
        }
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            modelDeliverables=(ArrayList<DeliverablesModel>) results.values;
            notifyDataSetChanged();
        }
    }
}
