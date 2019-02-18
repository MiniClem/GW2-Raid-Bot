package me.cbitler.raidbot.raids;

import java.util.HashMap;

public class RaidLeaderRoleCache {
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
