package epam.db.dto;

public class Train {

    private int train_id;
    private String train_number;
    private int num_of_carriages;
    private int route_num;

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

    public int getNum_of_carriages() {
        return num_of_carriages;
    }

    public void setNum_of_carriages(int num_of_carriages) {
        this.num_of_carriages = num_of_carriages;
    }

    public int getRoute_num() {
        return route_num;
    }

    public void setRoute_num(int route_num) {
        this.route_num = route_num;
    }

}
