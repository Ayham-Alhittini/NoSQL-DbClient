import com.decentraldbcluster.dbclient.Query;
import com.decentraldbcluster.dbclient.query.builders.CollectionQueryBuilder;
import com.decentraldbcluster.dbclient.query.builders.IndexQueryBuilder;
import org.junit.Test;

public class QueryTests {
    @Test
    public void Main() {}

    //--------- Collection Queries


    private Query createCollection() {
        return new CollectionQueryBuilder()
                .createCollection("users2")
                .build();
    }

    private Query dropCollection() {
        return new CollectionQueryBuilder()
                .createCollection("users2")
                .build();
    }


    //--------- Index Queries

    private Query createIndex() {
        return new IndexQueryBuilder()
                .collection("users")
                .createIndex("gender")
                .build();
    }

    private Query dropIndex() {
        return new IndexQueryBuilder()
                .collection("users")
                .dropIndex("gender")
                .build();
    }

}