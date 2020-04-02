package suyan.domain;

/**
 * Author:liang;
 * Date:2020/4/1;
 * Time:9:06;
 * Project Name:Spring_Practice;
 * Package Name:DAL.domain;
 * description:
 */
public class Account {
    private String name;
    private double money;

    public Account(String name, double money) {
        this.name = name;
        this.money = money;
    }

    public Account() {
        super();
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", money=" + money +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
