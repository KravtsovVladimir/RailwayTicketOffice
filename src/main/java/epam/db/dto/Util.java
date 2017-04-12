package epam.db.dto;

import java.sql.Time;

public class Util {

    private int route_number;
    private String dep_city;
    private String arr_city;
    private int dep_st_id;
    private int arr_st_id;
    private int dep_seq;
    private int arr_seq;
    private Time dep_time;
    private Time arr_time;
    private Time travel_time;
    private int train_id;
    private String train_number;
    private int freeSeats_1st_class;
    private int freeSeats_2nd_class;
    private double price_1st_class;
    private double price_2nd_class;

    public int getDep_st_id() {
        return dep_st_id;
    }

    public void setDep_st_id(int dep_st_id) {
        this.dep_st_id = dep_st_id;
    }

    public String getDep_city() {
        return dep_city;
    }

    public void setDep_city(String dep_city) {
        this.dep_city = dep_city;
    }

    public Time getDep_time() {
        return dep_time;
    }

    public void setDep_time(Time dep_time) {
        this.dep_time = dep_time;
    }

    public int getDep_seq() {
        return dep_seq;
    }

    public void setDep_seq(int dep_seq) {
        this.dep_seq = dep_seq;
    }

    public int getArr_st_id() {
        return arr_st_id;
    }

    public void setArr_st_id(int arr_st_id) {
        this.arr_st_id = arr_st_id;
    }

    public String getArr_city() {
        return arr_city;
    }

    public void setArr_city(String arr_city) {
        this.arr_city = arr_city;
    }

    public Time getArr_time() {
        return arr_time;
    }

    public void setArr_time(Time arr_time) {
        this.arr_time = arr_time;
    }

    public int getArr_seq() {
        return arr_seq;
    }

    public void setArr_seq(int arr_seq) {
        this.arr_seq = arr_seq;
    }

    public int getRoute_number() {
        return route_number;
    }

    public void setRoute_number(int route_number) {
        this.route_number = route_number;
    }

    public int getTrain_id() {
        return train_id;
    }

    public void setTrain_id(int train_id) {
        this.train_id = train_id;
    }

    public String getTrain_number() {
        return train_number;
    }

    public void setTrain_number(String train_number) {
        this.train_number = train_number;
    }

    public int getFreeSeats_1st_class() {
        return freeSeats_1st_class;
    }

    public void setFreeSeats_1st_class(int freeSeats_1st_class) {
        this.freeSeats_1st_class = freeSeats_1st_class;
    }

    public int getFreeSeats_2nd_class() {
        return freeSeats_2nd_class;
    }

    public void setFreeSeats_2nd_class(int freeSeats_2nd_class) {
        this.freeSeats_2nd_class = freeSeats_2nd_class;
    }

    public Time getTravel_time() {
        return travel_time;
    }

    public void setTravel_time(Time travel_time) {
        this.travel_time = travel_time;
    }

    public double getPrice_1st_class() {
        return price_1st_class;
    }

    public void setPrice_1st_class(double price_1st_class) {
        this.price_1st_class = price_1st_class * 1.5;
    }

    public double getPrice_2nd_class() {
        return price_2nd_class;
    }

    public void setPrice_2nd_class(double price_2nd_class) {
        this.price_2nd_class = price_2nd_class;
    }

}
