package epam.db.dto;

import java.util.Date;

public class Ticket {

    private int ticket_id;
    private String create_date;
    private String departure_date;
    private String arrival_date;
    private double ticketPrice;
    private int departure_station;
    private int arrival_station;
    private int user;
    private int train;
    private int carriage;
    private int seat;
    private String name;
    private String surname;

    public int getTicket_id() {
        return ticket_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getDeparture_date() {
        return departure_date;
    }

    public void setDeparture_date(String departure_date) {
        this.departure_date = departure_date;
    }

    public String getArrival_date() {
        return arrival_date;
    }

    public void setArrival_date(String arrival_date) {
        this.arrival_date = arrival_date;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public int getDeparture_station() {
        return departure_station;
    }

    public void setDeparture_station(int departure_station) {
        this.departure_station = departure_station;
    }

    public int getArrival_station() {
        return arrival_station;
    }

    public void setArrival_station(int arrival_station) {
        this.arrival_station = arrival_station;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getTrain() {
        return train;
    }

    public void setTrain(int train) {
        this.train = train;
    }

    public int getCarriage() {
        return carriage;
    }

    public void setCarriage(int carriage) {
        this.carriage = carriage;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }
}
