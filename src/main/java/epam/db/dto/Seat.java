package epam.db.dto;

public class Seat {

    private int seat_id;
    private int seat_number;
    private boolean isFree;
    private int carriage_id;

    public int getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(int seat_id) {
        this.seat_id = seat_id;
    }

    public int getCarriage_id() {
        return carriage_id;
    }

    public void setCarriage_id(int carriage_id) {
        this.carriage_id = carriage_id;
    }

    public int getSeat_number() {
        return seat_number;
    }

    public void setSeat_number(int seat_number) {
        this.seat_number = seat_number;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }
}
