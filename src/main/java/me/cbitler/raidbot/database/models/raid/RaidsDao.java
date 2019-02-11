package me.cbitler.raidbot.database.models.raid;

import me.cbitler.raidbot.raids.PendingRaid;

import java.sql.SQLException;

import static me.cbitler.raidbot.database.Database.getDatabase;
import static me.cbitler.raidbot.database.models.raid.RaidConverter.formatRolesForDatabase;

public class RaidsDao {

    /**
     * Insert a raid into the database
     * @param raid The raid to insert
     * @param messageId The embedded message / 'raidId'
     * @param serverId The serverId related to this raid
     * @param channelId The channelId for the announcement of this raid
     * @return True if inserted, false otherwise
     */
    private static boolean insertToDatabase(PendingRaid raid, String messageId, String serverId, String channelId) {
        String roles = formatRolesForDatabase(raid.getRolesWithNumbers());

        try {
            getDatabase().update("INSERT INTO `raids` (`raidId`, `serverId`, `channelId`, `leader`, `name`, `description`, `date`, `time`, `roles`) VALUES (?,?,?,?,?,?,?,?,?)", new String[] {
                    messageId,
                    serverId,
                    channelId,
                    raid.getLeaderName(),
                    raid.getName(),
                    raid.getDescription(),
                    raid.getDate(),
                    raid.getTime(),
                    roles
            });
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
