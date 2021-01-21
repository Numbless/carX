package com.raketasoft.carxapp;

/*
Класс опеределяющий структуру данных одного эелемента
 */
public class CarItem {
    private int id;
    private String number,type,driver,work;
    private int status;

    public void setID(final int id){
        this.id=id;
    }

    public int getID(){
        return id;
    }

    public void setNumber(String number){
        this.number=number;
    }

    public String getNumber(){
        return number;
    }

    public void setType(String type){
        this.type=type;
    }

    public String getType(){
        return type;
    }

    public void setDriver(String driver){
        this.driver=driver;
    }

    public String getDriver(){
        return driver;
    }

    public void setWork(String work){
        this.work=work;
    }

    public String getWork(){
        return work;
    }

    public void setStatus(final int status){
        this.status=status;
    }

    public int getStatus(){
        return status;
    }
}
