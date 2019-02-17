package me.cbitler.raidbot.database.models.raid;

import java.util.List;

public class RaidConverter {
    /**
     * Formats the roles associated with a raid in a form that can be inserted into a database row.
     * This combines them as [number]:[name];[number]:[name];...
     * @param rolesWithNumbers The roles and their amounts
     * @return The formatted string
     */
    static String formatRolesForDatabase(List<RaidRole> rolesWithNumbers) {
        String data = "";

        for (int i = 0; i < rolesWithNumbers.size(); i++) {
            RaidRole role = rolesWithNumbers.get(i);
            if(i == rolesWithNumbers.size() - 1) {
                data += (role.getAmount() + ":" + role.getName());
            } else {
                data += (role.getAmount() + ":" + role.getName() + ";");
            }
        }

        return data;
    }
}
