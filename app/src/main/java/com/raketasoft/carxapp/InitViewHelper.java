package com.raketasoft.carxapp;

import android.graphics.Typeface;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

/*
Класс для тонкой настройки элементов управления, подходит для настройки большенства элементов управления в рамках данного проекта.
Позволяет настроить размеры элементов управлени, особенно это удобно в случае, если много однотипных эелементов управения на экране. Позволяет
настроить текстовый эелеиент управления
 */
class InitViewHelper{
    protected Typeface font;

    public InitViewHelper(Typeface font){
        this.font=font;
    }

    public LinearLayout.LayoutParams initParams(final int width, final int height, int magrins[], final int weight){
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(width,height,weight);
        try {
            params.setMargins(magrins[0],magrins[1],magrins[2],magrins[3]);
        }catch (NullPointerException e){
            e.printStackTrace();
        } catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return params;
    }

    public void initTextView(TextView tv, String text, final float textSize){
        tv.setText(Html.fromHtml(text));
        tv.setTypeface(font);
        tv.setTextSize(textSize);
    }
}