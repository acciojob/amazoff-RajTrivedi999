package com.driver;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {
        this.id=id;
        int Time=convert(deliveryTime);
        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
    }

    public int convert(String time){
        int first=Integer.valueOf(time.substring(0,2))*60;
        int second=Integer.valueOf(time.substring(3,5));
        return first+second;
    }
    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
