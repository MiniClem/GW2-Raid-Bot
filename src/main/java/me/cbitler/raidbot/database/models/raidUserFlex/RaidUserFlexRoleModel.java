package me.cbitler.raidbot.database.models.raidUserFlex;

public class RaidUserFlexRoleModel {
	private String userId;
	private String username;
	private String spec;
	private String role;
	private String raidId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRaidId() {
		return raidId;
	}

	public void setRaidId(String raidId) {
		this.raidId = raidId;
	}
}
