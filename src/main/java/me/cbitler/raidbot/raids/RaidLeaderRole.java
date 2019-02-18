package me.cbitler.raidbot.raids;

import java.util.HashMap;

public class RaidLeaderRole {
    private static final HashMap<String, String> raidLeaderRoleCache = new HashMap<>();

    public static HashMap<String, String> getRaidLeaderRoleCache(){
        return raidLeaderRoleCache;
    }

    public synchronized static void addToRaidLeaderRole(String serverId, String role){
        raidLeaderRoleCache.put(serverId, role);
    }

    public static String getRaidLeaderRoleByServerId(String serverId){
        return raidLeaderRoleCache.get(serverId);
    }
}
