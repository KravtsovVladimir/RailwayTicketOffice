package epam.db.service;

import epam.db.dao.DaoFactory;
import epam.db.dao.interfaces.SeatDao;
import epam.db.dao.interfaces.TicketDao;
import epam.db.dto.Ticket;
import epam.regexp.RegExp;

import java.util.List;

public class TicketService {
    private final TicketDao ticketDao;
    private final SeatDao seatDao;

    public TicketService() {
        this.ticketDao = DaoFactory.getInstance().getTicketDao();
        this.seatDao = DaoFactory.getInstance().getSeatDao();
    }

    public synchronized boolean buyTickets(String train_number, String dep_date, String dep_st, String arr_st, double price,
                                           int dep_st_seq, int arr_st_seq, int user_id, List<Integer> carriages,
                                           List<Integer> seats, List<String> names, List<String> surnames) {

        for (int i = 0; i < seats.size(); i++) {
            if (!seatDao.isFree(train_number, dep_date, dep_st_seq, arr_st_seq, carriages.get(i), seats.get(i))) {
                return false;
            }
        }

        for (int i = 0; i < seats.size(); i++) {
            if (!(RegExp.validateForLettersAndDash(names.get(i)) && RegExp.validateForLettersAndDash(surnames.get(i)))){
                throw new IllegalArgumentException("name or surname invalid");
            }
            ticketDao.buyTicket(train_number, dep_date, dep_st, arr_st, price, carriages.get(i), seats.get(i),
                    names.get(i), surnames.get(i), dep_st_seq, arr_st_seq, user_id);
        }

        return true;
    }

    public List<Ticket> getTickets(int user_id){
        return ticketDao.getTickets(user_id);
    }
}
