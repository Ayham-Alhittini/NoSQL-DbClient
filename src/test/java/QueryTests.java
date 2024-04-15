import com.decentraldbcluster.dbclient.Query;
import com.decentraldbcluster.dbclient.query.builders.CollectionQueryBuilder;
import com.decentraldbcluster.dbclient.query.builders.DocumentQueryBuilder;
import com.decentraldbcluster.dbclient.query.builders.IndexQueryBuilder;
import org.junit.Test;

public class QueryTests {
    @Test
    public void Main() {}

    //--------- Document Queries

    private Query select() {
        return new DocumentQueryBuilder()
                .collection("users")
                .findById("5b75aa85-69ae-4fd3-bf2f-797f5d4fde911")
                .build();
    }

    private Query addQuery() {
        return new DocumentQueryBuilder()
            .collection("users")
            .insert("""
                    {
                      "age" : 17,
                      "tail" : 156,
                      "siblings" : [ "ayham", "mulham", "alma" ],
                      "isAdult" : true,
                      "name" : "Menas alhettini",
                      "gender" : "Female",
                      "email" : "menas.hittini@example.com",
                      "bornDate" : "2006-12-29 10:00:00",
                      "address" : {
                        "street" : "alqsa",
                        "city" : "zarqa",
                        "state" : "zarqa",
                        "zipCode" : "13303",
                        "test" : {
                          "testFiled" : false
                        }
                      }
                    }
                    """)
                .build();
    }

    private Query updateQuery() {
        return new DocumentQueryBuilder()
                .collection("users")
                .update("6df199a3-1e63-4dce-9ded-3ced35db23511", """
                        {
                        "gender": "female"
                        }
                        """)
                .build();
    }

    private Query replaceQuery() {
        return new DocumentQueryBuilder()
                .collection("users")
                .replace("6df199a3-1e63-4dce-9ded-3ced35db23511",
                        """
                                {
                                    "age": 19,
                                    "tail": 156,
                                    "siblings": [
                                      "ayham",
                                      "mulham",
                                      "alma"
                                    ],
                                    "isAdult": false,
                                    "name": "ahmad",
                                    "gender": "Male",
                                    "email": "menas.hittini@example.com",
                                    "bornDate": "2006-12-29 10:00:00",
                                    "address": {
                                      "street": "alqsa",
                                      "city": "zarqa",
                                      "state": "zarqa",
                                      "zipCode": "13303",
                                      "test": {
                                        "testFiled": false
                                      }
                                    }
                                  }
                                """)
                .build();
    }

    private Query deleteQuery() {
        return new DocumentQueryBuilder()
                .collection("users")
                .delete("6df199a3-1e63-4dce-9ded-3ced35db23511")
                .build();
    }


    //--------- Collection Queries

    private Query showCollections() {
        return new CollectionQueryBuilder()
                .showCollections()
                .build();
    }

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