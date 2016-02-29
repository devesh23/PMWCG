package com.devesh.pmw;


import android.util.SparseBooleanArray;

public class DataProvider {

    private int car_poster_resource;
    private String CarName;
    private String Price;
    private String Company;
    public boolean check_state;
    public DataProvider(int Car_poster_resource,String CarName)
    {
        this.setCar_poster_resource(Car_poster_resource);
        this.setCarName(CarName);

    }
    public DataProvider(String company,String carName,String price)
    {
        this.setPrice(price);
        this.setCarName(carName);
        this.setCompany(company);

    }
    public DataProvider(String CarName,String Price)
    {
        this.setCarName(CarName);
        this.setPrice(Price);
    }
    public DataProvider(String CarName,boolean check_state)
    {
        this.setCarName(CarName);
        this.setcheck_state(check_state);

    }
    public void setcheck_state(Boolean check_state)
    {
        this.check_state =check_state;
    }
    public Boolean getcheck_state() {
        return check_state;
    }
    public int getCar_poster_resource() {
        return car_poster_resource;
    }

    public void setCar_poster_resource(int car_poster_resource) {
        this.car_poster_resource = car_poster_resource;
    }

    public String getCarName() {
        return CarName;
    }
    public String getCompany() {
        return Company;
    }
    public void setCompany(String company) {
        Company = company;
    }
    public void setCarName(String carName) {
        CarName = carName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
    public boolean isSelected() {
        return check_state;
    }

    public void setSelected(boolean selected) {
        this.check_state = selected;
    }
}
