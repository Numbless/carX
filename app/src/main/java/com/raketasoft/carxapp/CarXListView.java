package com.raketasoft.carxapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
/*
Класс реализующий контент для Layout элемента управления
 */
class CarXListView extends BaseAdapter {
    protected Context context;
    protected LayoutInflater inflaterLayout;
    protected ArrayList<CarItem> items;
    protected InitViewHelper iv;
    protected int iconSize,spaceSize;
    protected float textSize;

    public CarXListView(Context context, InitViewHelper iv,ArrayList<CarItem> items,final float textSize,final int iconSize,final int spaceSize){
        this.context=context;
        this.items=items;
        this.iv=iv;
        this.textSize=textSize;
        this.iconSize=iconSize;
        this.spaceSize=spaceSize;
        inflaterLayout=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewItem=view;
        if (viewItem==null)
            viewItem=inflaterLayout.inflate(R.layout.carx_item_layout,viewGroup,false);

        LinearLayout itemLayout=viewItem.findViewById(R.id.itemLayout);
        try {
            itemLayout.setBackgroundColor(items.get(i).getType().equals("truck")?context.getResources().getColor(R.color.colorGreen):context.getResources().getColor(R.color.colorRed));
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        ImageView icon=viewItem.findViewById(R.id.item_icon);
        try{
            icon.setBackgroundResource(items.get(i).getType().equals("truck")?R.drawable.truck:R.drawable.tech);
            icon.setLayoutParams(iv.initParams(iconSize,iconSize,new int[]{spaceSize,0,0,0},0));
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        LinearLayout itemTextLayout=viewItem.findViewById(R.id.itemTextLayout);
        itemTextLayout.setLayoutParams(iv.initParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,new int[]{spaceSize,0,0,0},1));

        TextView itemID=viewItem.findViewById(R.id.item_id);
        itemID.setLayoutParams(iv.initParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,null,0));
        iv.initTextView(itemID,"#"+items.get(i).getID(),textSize);

        TextView itemText=viewItem.findViewById(R.id.item_text);
        itemText.setLayoutParams(iv.initParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,null,0));
        iv.initTextView(itemText,items.get(i).getWork(),textSize);

        return viewItem;
    }
}
