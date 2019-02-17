package me.cbitler.raidbot.database.models.raid;

import me.cbitler.raidbot.database.Database;

import java.util.HashMap;

public class RaidLeaderRoleRepository {
    private static HashMap<String, String> raidLeaderRoleCache = new HashMap<>();

    public static HashMap<String, String> getRaidLeaderRoleCache(){
        if(raidLeaderRoleCache != null) return raidLeaderRoleCache;
        else {
            Database.getDatabase().
        }
    }
}
