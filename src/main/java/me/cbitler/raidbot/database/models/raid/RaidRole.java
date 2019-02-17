package me.cbitler.raidbot.database.models.raid;

/**
 * Represents a role that is available in a raid
 * @author Christopher Bitler
 */
public class RaidRole {
    private int amount;
    private String name;

    /**
     * Create a new RaidRole object
     * @param amount The max amount of the role
     * @param name The name of the role
     */
    public RaidRole(int amount, String name) {
        this.amount = amount;
        this.name = name;
    }

    /**
     * Get the maximum number of people in this role
     * @return The maximum number of people in this role
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Get the name of the role
     * @return The name of the role
     */
    public String getName() {
        return name;
    }
}
