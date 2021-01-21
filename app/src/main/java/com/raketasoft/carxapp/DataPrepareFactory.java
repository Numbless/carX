package com.raketasoft.carxapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
Класс обработки данных. Получает текст в формате JSON, если возникают ошибки разбора или иные непредвиденные ситуации, вернет
данные в виде структуры CarItem с кодом ошибки, которые можно узнать из метода CarItem->getStattus. Класс позволяеь получить обьект
с данными под указанным номером или вернуть готовый список таких обектов из всего доступного обьема данных представленного в виде текста в формате JSON
 */
public class DataPrepareFactory {
    protected JSONObject jobj;
    protected String json;

    public DataPrepareFactory(String json){
        this.json=json;
    }

    public CarItem prepareCar(final int carIndex){
        CarItem car=new CarItem();
        car.setStatus(0);
        try {
            jobj=new JSONObject(json);
        } catch (JSONException e) {
            car.setStatus(1);
            e.printStackTrace();
        }

        try {
            car.setID(Integer.parseInt(jobj.getJSONArray("cars").getJSONObject(carIndex).getString("id")));
            car.setNumber(jobj.getJSONArray("cars").getJSONObject(carIndex).getString("number"));
            car.setType(jobj.getJSONArray("cars").getJSONObject(carIndex).getString("type"));
            car.setDriver(jobj.getJSONArray("cars").getJSONObject(carIndex).getString("driver"));
            car.setWork(jobj.getJSONArray("cars").getJSONObject(carIndex).getString("work"));
        } catch (JSONException e) {
            car.setStatus(2);
            e.printStackTrace();
        }
        return car;
    }

    public ArrayList<CarItem> prepareAllData(){
        int all=0;
        try {
            jobj=new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        try {
            all=jobj.getJSONArray("cars").length();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        ArrayList<CarItem> carList=new ArrayList<>();
        for(int i=0;i<all;i++)
            carList.add(prepareCar(i));
        return carList;
    }
}
