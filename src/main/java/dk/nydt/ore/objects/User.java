package dk.nydt.ore.objects;

public class User {
    private String name;
    private double balance;
    private int level;
    private int xp;
    private int prestige;

    public User() {
    }

    public User(String name, double balance, int level, int xp, int prestige) {
        this.name = name;
        this.balance = balance;
        this.level = level;
        this.xp = xp;
        this.prestige = prestige;
    }
}
