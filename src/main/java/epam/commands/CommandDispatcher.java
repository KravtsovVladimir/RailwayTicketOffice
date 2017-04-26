package epam.commands;

import epam.commands.exit.ExitCommand;
import epam.commands.route.SearchRoutesCommand;
import epam.commands.seat.ChoiceOfSeatCommand;
import epam.commands.seat.UpdateSeatsCommand;
import epam.commands.ticket.BuyTicketCommand;
import epam.commands.ticket.ShowTicket;
import epam.commands.user.*;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Мир on 11.04.2017.
 */
public class CommandDispatcher {

    private static final CommandDispatcher instance = new CommandDispatcher();
    private static final Logger logger = Logger.getLogger(CommandDispatcher.class);
    private Map<String, ICommand> commands = new HashMap<>();

    private CommandDispatcher() {
        Map<String, ICommand> map = new HashMap<>();

        map.put("login", new LoginCommand());
        map.put("registration", new RegistrationCommand());
        map.put("search", new SearchRoutesCommand());
        map.put("choose", new ChoiceOfSeatCommand());
        map.put("updateSeats", new UpdateSeatsCommand());
        map.put("buy", new BuyTicketCommand());
        map.put("showUsers", new ShowUsersCommand());
        map.put("block", new BlockUsersCommand());
        map.put("unblock", new UnblockUsersCommand());
        map.put("delete", new DeleteUsersCommand());
        map.put("exit", new ExitCommand());
        map.put("showTicket", new ShowTicket());

        commands = Collections.unmodifiableMap(map);
    }

    public static CommandDispatcher getInstance() {
        return instance;
    }

    public ICommand getCommand(String requestType) {
        return commands.get(requestType);
    }

}
