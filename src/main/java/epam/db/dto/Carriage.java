package epam.db.dto;

public class Carriage {

    private int carriage_id;
    private int carriage_number;
    private int carriage_class;
    private int total_num_of_seats;
    private int train_id;

    public int getCarriage_id() {
        return carriage_id;
    }

    public void setCarriage_id(int carriage_id) {
        this.carriage_id = carriage_id;
    }

    public int getTrain_id() {
        return train_id;
    }

    public void setTrain_id(int train_id) {
        this.train_id = train_id;
    }

    public int getCarriage_number() {
        return carriage_number;
    }

    public void setCarriage_number(int carriage_number) {
        this.carriage_number = carriage_number;
    }

    public int getCarriage_class() {
        return carriage_class;
    }

    public void setCarriage_class(int carriage_class) {
        this.carriage_class = carriage_class;
    }

    public int getTotal_num_of_seats() {
        return total_num_of_seats;
    }

    public void setTotal_num_of_seats(int total_num_of_seats) {
        this.total_num_of_seats = total_num_of_seats;
    }

}
