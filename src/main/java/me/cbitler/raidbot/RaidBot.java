package me.cbitler.raidbot;

import me.cbitler.raidbot.commands.CommandRegistry;
import me.cbitler.raidbot.commands.EndRaidCommand;
import me.cbitler.raidbot.commands.HelpCommand;
import me.cbitler.raidbot.commands.InfoCommand;
import me.cbitler.raidbot.creation.CreationStep;
import me.cbitler.raidbot.database.Database;
import me.cbitler.raidbot.database.QueryResult;
import me.cbitler.raidbot.handlers.ChannelMessageHandler;
import me.cbitler.raidbot.handlers.DMHandler;
import me.cbitler.raidbot.handlers.ReactionHandler;
import me.cbitler.raidbot.raids.PendingRaid;
import me.cbitler.raidbot.raids.RaidManager;
import me.cbitler.raidbot.selection.SelectionStep;
import me.cbitler.raidbot.utility.GuildCountUtil;
import me.cbitler.raidbot.utility.Variables;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import static me.cbitler.raidbot.raids.RaidLeaderRoleCache.addToRaidLeaderRole;
import static me.cbitler.raidbot.raids.RaidLeaderRoleCache.getRaidLeaderRoleByServerId;
import static me.cbitler.raidbot.utility.Variables.RaidBotProperty.DATABASE;

/**
 * Class representing the raid bot itself.
 * This stores the creation/roleSelection map data and also the list of pendingRaids
 * Additionally, it also stores the database in use by the bot and serves as a way
 * for other classes to access it.
 *
 * @author Christopher Bitler
 */
public class RaidBot {
    private static RaidBot instance;
    private JDA jda;

    HashMap<String, CreationStep> creation = new HashMap<String, CreationStep>();
    HashMap<String, PendingRaid> pendingRaids = new HashMap<String, PendingRaid>();
    HashMap<String, SelectionStep> roleSelection = new HashMap<String, SelectionStep>();

    //TODO: This should be moved to it's own settings thing

    private static Database db;

    /**
     * Create a new instance of the raid bot with the specified JDA api
     * @param jda The API for the bot to use
     */
    public RaidBot(JDA jda) {
        instance = this;

        this.jda = jda;
        jda.addEventListener(new DMHandler(this), new ChannelMessageHandler(), new ReactionHandler());
        try {
            db = new Database(Variables.getINSTANCE().getStringProperty(DATABASE.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.connect();
        RaidManager.loadRaids();

        CommandRegistry.addCommand("help", new HelpCommand());
        CommandRegistry.addCommand("info", new InfoCommand());
        CommandRegistry.addCommand("endRaid", new EndRaidCommand());

        new Thread(() -> {
            while (true) {
                try {
                    GuildCountUtil.sendGuilds(jda);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000*60*5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Map of UserId -> creation step for people in the creation process
     * @return The map of UserId -> creation step for people in the creation process
     */
    public HashMap<String, CreationStep> getCreationMap() {
        return creation;
    }

    /**
     * Map of the UserId -> roleSelection step for people in the role selection process
     * @return The map of the UserId -> roleSelection step for people in the role selection process
     */
    public HashMap<String, SelectionStep> getRoleSelectionMap() {
        return roleSelection;
    }

    /**
     * Map of the UserId -> pendingRaid step for raids in the setup process
     * @return The map of UserId -> pendingRaid
     */
    public HashMap<String, PendingRaid> getPendingRaids() {
        return pendingRaids;
    }

    /**
     * Get the JDA server object related to the server ID
     * @param id The server ID
     * @return The server related to that that ID
     */
    public Guild getServer(String id) {
        return jda.getGuildById(id);
    }

    /**
     * Exposes the underlying library. This is mainly necessary for getting Emojis
     * @return The JDA library object
     */
    public JDA getJda() {
        return jda;
    }

    /**
     * Get the database that the bot is using
     * @return The database that the bot is using
     */
    public Database getDatabase() {
        return db;
    }

    /**
     * Get the raid leader role for a specific server.
     * This works by caching the role once it's retrieved once, and returning the default if a server hasn't set one.
     * @param serverId the ID of the server
     * @return The name of the role that is considered the raid leader for that server
     */
    public String getRaidLeaderRole(String serverId) {
        if (getRaidLeaderRoleByServerId(serverId) != null) {
            return getRaidLeaderRoleByServerId(serverId);
        } else {
            try {
                QueryResult results = getDatabase().query("SELECT `raid_leader_role` FROM `serverSettings` WHERE `serverId` = ?",
                        new String[]{serverId});
                if (results.getResults().next()) {
                    addToRaidLeaderRole(serverId, results.getResults().getString("raid_leader_role"));
                    return getRaidLeaderRoleByServerId(serverId);
                } else {
                    return "Raid Leader";
                }
            } catch (Exception e) {
                return "Raid Leader";
            }
        }
    }

    /**
     * Set the raid leader role for a server. This also updates it in SQLite
     * @param serverId The server ID
     * @param role The role name
     */
    public void setRaidLeaderRole(String serverId, String role) {
        addToRaidLeaderRole(serverId, role);
        try {
            db.update("INSERT INTO `serverSettings` (`serverId`,`raid_leader_role`) VALUES (?,?)",
                    new String[] { serverId, role});
        } catch (SQLException e) {
            //TODO: There is probably a much better way of doing this
            try {
                db.update("UPDATE `serverSettings` SET `raid_leader_role` = ? WHERE `serverId` = ?",
                        new String[] { role, serverId });
            } catch (SQLException e1) {
                // Not much we can do if there is also an insert error
            }
        }
    }

    /**
     * Get the current instance of the bot
     * @return The current instance of the bot.
     */
    public static RaidBot getInstance() {
        return instance;
    }

}
