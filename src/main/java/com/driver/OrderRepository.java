package com.driver;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
@Component
public class OrderRepository {

    HashMap<String,Order> orderHashMap=new HashMap<>();
    HashMap<String,DeliveryPartner> deliveryPartnerHashMap=new HashMap<>();
    HashMap<String, ArrayList<String>> pairListMap=new HashMap<>();

    public void addOrder(Order order){
        orderHashMap.put(order.getId(),order);
    }

    public void addPartner(String partnerId){
        DeliveryPartner deliveryPartner=new DeliveryPartner(partnerId);
        deliveryPartnerHashMap.put(deliveryPartner.getId(),deliveryPartner);
    }

    public void addOrderPartnerPair(String orderId, String partnerId){
            ArrayList<String> orderList = new ArrayList<>();
            orderList.add(orderId);
            pairListMap.put(partnerId, orderList);
    }

    public Order getOrderById(String orderId){
        return orderHashMap.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId){
        return deliveryPartnerHashMap.get(partnerId);
    }

    public int getOrderCountByPartnerId(String partnerId){
        int ans=0;
        try {
            ans=pairListMap.get(partnerId).size();
        }catch (Exception e){
            System.out.print(e);
        }
        return ans;
    }

    public List<String> getOrdersByPartnerId(String partnerId){
        return pairListMap.get(partnerId);
    }

    public List<String> getAllOrders(){
        ArrayList<String> allOrders=new ArrayList<>();
        for(String orders : orderHashMap.keySet()){
            allOrders.add(orders);
        }
        return allOrders;
    }

    public Integer getCountOfUnassignedOrders(){
        int assigned=0;
        for(String name : deliveryPartnerHashMap.keySet()){
            assigned+=pairListMap.get(name).size();
        }
        return orderHashMap.size()-assigned;
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){
        int pending=0;
        int deliveryTime=convert(time);
        for(int i=0;i<pairListMap.get(partnerId).size();i++){
            String temp=pairListMap.get(partnerId).get(i);
            int timeTake=orderHashMap.get(temp).getDeliveryTime();
            if(deliveryTime<timeTake) pending++;
        }
        return pending;
    }
    public int convert(String time){
        int first=Integer.valueOf(time.substring(0,2))*60;
        int second=Integer.valueOf(time.substring(3,5));
        return first+second;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId){
        int timeTake=0;
        for(int i=0;i<pairListMap.get(partnerId).size();i++){
            String temp=pairListMap.get(partnerId).get(i);
            timeTake+=orderHashMap.get(temp).getDeliveryTime();
        }
        String ans=convert(timeTake);
        return ans;
    }
    public String convert(int timeTake){
        String ans="";
        int first=timeTake/60;
        int second=timeTake-first*60;
        if(first<10){
            ans+='0';
            ans+=first;
            ans+=':';
            if(second<10){
                ans+='0';
                ans+=second;
            }else {
                ans+=second;
            }
        }else{
            ans+=first;
            ans+=':';
            if(second<10){
                ans+='0';
                ans+=second;
            }else {
                ans+=second;
            }
        }
        return ans;
    }

    public void deletePartnerById(String partnerId){
        deliveryPartnerHashMap.remove(partnerId);
    }

    public void deleteOrderById(String orderId){
        for(String name : pairListMap.keySet()){
            for(int i=0;i<pairListMap.get(name).size();i++) {
                if (pairListMap.get(name).get(i) == orderId) {
                    pairListMap.get(name).remove(i);
                    return;
                }
            }
        }
        orderHashMap.remove(orderId);
    }
}
