package demo.entities;

import com.decentraldbcluster.dbclient.odm.annotations.Id;
import com.decentraldbcluster.dbclient.odm.annotations.Indexed;

public class Ticket {
    @Id
    private String tickedId;

    private String movieName;
    @Indexed
    private int ticketNumber;

    public Ticket(String movieName, int ticketNumber) {
        this.movieName = movieName;
        this.ticketNumber = ticketNumber;
    }

    public Ticket() {}

    public String getTickedId() {
        return tickedId;
    }

    public String getMovieName() {
        return movieName;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }
}
