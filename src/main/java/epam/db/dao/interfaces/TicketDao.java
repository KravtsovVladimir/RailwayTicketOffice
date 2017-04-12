package epam.db.dao.interfaces;

public interface TicketDao {

    void buyTicket(String train_number, String dep_date, String dep_st, String arr_st, double price,
                   int carriage_number, int seat_number, String name, String surname, int dep_st_seq,
                   int arr_st_seq, int user_id);

}
