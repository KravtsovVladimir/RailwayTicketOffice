package epam.db.dto;

import java.util.Date;

public class Ticket {

    private int ticket_id;
    private Date create_date;
    private Date departure_date;
    private Date arrival_date;
    private double ticketPrice;
    private Station departure_station;
    private Station arrival_station;
    private User user;
    private Train train;
    private Carriage carriage;
    private Seat seat;

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Date getDeparture_date() {
        return departure_date;
    }

    public void setDeparture_date(Date departure_date) {
        this.departure_date = departure_date;
    }

    public Date getArrival_date() {
        return arrival_date;
    }

    public void setArrival_date(Date arrival_date) {
        this.arrival_date = arrival_date;
    }

    public Station getDeparture_station() {
        return departure_station;
    }

    public void setDeparture_station(Station departure_station) {
        this.departure_station = departure_station;
    }

    public Station getArrival_station() {
        return arrival_station;
    }

    public void setArrival_station(Station arrival_station) {
        this.arrival_station = arrival_station;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Carriage getCarriage() {
        return carriage;
    }

    public void setCarriage(Carriage carriage) {
        this.carriage = carriage;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

}
