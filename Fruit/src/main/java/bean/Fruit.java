package bean;

public class Fruit {
    private int id;
    private String name;
    private int price;
    private int count;
    private String remark;

    public Fruit() {
    }

    public Fruit(int id, String name, int price, int count, String remark) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", remark='" + remark + '\'' +
                '}';
    }
}
