package com.example.freyds.timar3;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

class ListItem {
    String name;
    boolean selected = false;

    public ListItem(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}


public class ListAdapter extends ArrayAdapter<ListItem> {

    private List<ListItem> job_list;
    private Context context;

    public ListAdapter(List<ListItem> job_list, Context context) {
        super(context, R.layout.activity_list_adapter, job_list);
        this.job_list = job_list;
        this.context = context;
    }

    private static class ListHolder {
        public TextView name;
        public CheckBox checkbox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ListHolder holder = new ListHolder();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.activity_list_adapter, null);

            holder.name = (TextView) v.findViewById(R.id.name);
            holder.checkbox = (CheckBox) v.findViewById(R.id.chk_box);

            holder.checkbox.setOnCheckedChangeListener((yfirlit) context);

        }
        else {
            holder = (ListHolder) v.getTag();
        }

        ListItem l = job_list.get(position);
        holder.name.setText(l.getName());
        holder.checkbox.setChecked(l.isSelected());
        holder.checkbox.setTag(l);
        return v;
    }
}
