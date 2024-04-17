package demo.entities;


import com.decentraldbcluster.dbclient.odm.annotations.Id;
import com.decentraldbcluster.dbclient.odm.annotations.Indexed;

public class Product {
    @Id
    @Indexed
    private String productId;

    @Indexed
    private int productPrice;
    @Indexed
    private String productDescription;

    public Product() {}

    public Product(int productPrice, String productDescription) {
        this.productPrice = productPrice;
        this.productDescription = productDescription;
    }


    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productPrice=" + productPrice +
                ", productDescription='" + productDescription + '\'' +
                '}';
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}
