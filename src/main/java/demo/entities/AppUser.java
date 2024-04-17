package demo.entities;


import com.decentraldbcluster.dbclient.odm.annotations.Id;


public class AppUser {
    @Id
    private String usedId;

//    @Indexed
    private String name;
//    @Indexed
    private int age;


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public AppUser() {
    }

    public AppUser(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getUsedId() {
        return usedId;
    }

    public void setUsedId(String usedId) {
        this.usedId = usedId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "Id='" + usedId + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}