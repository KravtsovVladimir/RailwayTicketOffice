package epam.db.dao.interfaces;

public interface RouteDao {

    double calculateRoutePrice(String train_number, int dep_st_seq, int arr_st_seq);

}
