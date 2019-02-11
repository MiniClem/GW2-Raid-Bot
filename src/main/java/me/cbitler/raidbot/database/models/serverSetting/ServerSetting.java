package me.cbitler.raidbot.database.models.serverSetting;

public class ServerSetting {
	private String serverId;
	private String raidLeaderRole;

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getRaidLeaderRole() {
		return raidLeaderRole;
	}

	public void setRaidLeaderRole(String raidLeaderRole) {
		this.raidLeaderRole = raidLeaderRole;
	}
}
