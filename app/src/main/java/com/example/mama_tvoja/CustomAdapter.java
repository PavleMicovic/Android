package com.example.mama_tvoja;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mama_tvoja.R;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter /*implements AdapterView.OnItemLongClickListener*/ {

    private Context mContext;
    private ArrayList<List_element> mElements;
    private RadioButton bla=null;
    private Button btn;

    public CustomAdapter(Context context)
    {
        mContext=context;
        mElements=new ArrayList<List_element>();
    }
    public void addElement(List_element element) {
        boolean contains = false;
        for (List_element c : mElements) {
            if (c.mName.equals(element.mName))
                contains = true;
            else if(c.mName.equals("")){}
        }
        if(contains==true) {
            Toast.makeText(mContext, "Item already exists", Toast.LENGTH_SHORT).show();
        }
        else {
            mElements.add(element);
            notifyDataSetChanged();
        }
    }
    public void removeElement(int position){
        mElements.remove(position);
       notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mElements.size();
    }

    @Override
    public Object getItem(int position) {
        Object rv = null;
        try {
            rv = mElements.get(position);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return rv;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_view, null);
            ViewHolder holder = new ViewHolder();
            holder.radio = (RadioButton) view.findViewById(R.id.list_btn);
            holder.name = (TextView) view.findViewById(R.id.list_text);
            holder.btn=(Button)view.findViewById(R.id.list_btn1);
            view.setTag(holder);
        }

        List_element element = (List_element) getItem(position);
        final ViewHolder holder = (ViewHolder) view.getTag();
        holder.name.setText(element.mName);
        holder.radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, Forecast.class);
                intent.putExtra("city_name", holder.name.getText().toString());
                mContext.startActivity(intent);
                bla=(RadioButton) v;
                bla.setChecked(false);
            }
        });

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, Forecast.class);
                intent.putExtra("city_name", holder.name.getText().toString());
                mContext.startActivity(intent);
                btn=(Button) v;
            }
        });


        return view;

    }

   /* public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        removeElement(position);
        return true;
    }*/

    private class ViewHolder {
        public RadioButton radio = null;
        public TextView name = null;
        public Button btn=null;

    }
}
