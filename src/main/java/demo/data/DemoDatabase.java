package demo.data;


import demo.entities.AppUser;
import demo.entities.Product;
import demo.entities.Ticket;
import com.decentraldbcluster.dbclient.odm.collection.Collection;
import com.decentraldbcluster.dbclient.odm.database.Database;

public class DemoDatabase extends Database {

    public Collection<Ticket> tickets;

    public Collection<Product> products;
    public Collection<AppUser> users;

}
