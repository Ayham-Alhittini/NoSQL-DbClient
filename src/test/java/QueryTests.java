import com.decentraldbcluster.dbclient.Query;
import com.decentraldbcluster.dbclient.query.builders.CollectionQueryBuilder;
import com.decentraldbcluster.dbclient.query.builders.IndexQueryBuilder;
import org.junit.Test;

public class QueryTests {
    @Test
    public void Main() {}

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