import com.decentraldbcluster.dbclient.DbClient;
import com.decentraldbcluster.dbclient.DbConnection;
import com.decentraldbcluster.dbclient.Query;
import com.decentraldbcluster.dbclient.builders.CollectionQueryBuilder;
import com.decentraldbcluster.dbclient.builders.DocumentQueryBuilder;
import com.decentraldbcluster.dbclient.builders.IndexQueryBuilder;
import org.junit.jupiter.api.Test;

public class ApplicationTests {
    @Test
    public void Main() {
        DbConnection connection = new DbConnection("CSgeORGvezm+uirSZFqlul9msPNm+/ytCF78wY3o/YeBxifDQq0dvOZ2eW4qGd94Q2XEP+DCfgFrCd+SS6PK6on4eRhl5kW7q8iOx2boLK8=",
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1OGMyZGRlNS0yNDZhLTQ3NGYtYjEwZC1iY2M1ZDYyZDIyNGIiLCJpYXQiOjE3MTEwNTEyNDgsImVtYWlsIjoiYXloYW0uaGl0dGluaTI2OEBnbWFpbC5jb20iLCJ1c2VybmFtZSI6IkF5aGFtIn0.P0OGl50k5QaFm3XmKYfMSltIi_eOZUlW1Nwr_KVbzWU");

        DbClient client = new DbClient(connection);
        Query query = showCollections();
        System.out.println(client.executeQuery(query));
    }

    //--------- Document Queries
    private Query select() {
        return new DocumentQueryBuilder()
                .collection("users").select().build();
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