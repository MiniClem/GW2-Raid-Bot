CREATE TABLE IF NOT EXISTS raids (
    raidId text PRIMARY KEY,
    serverId text NOT NULL,
    channelId text NOT NULL,
    leader text NOT NULL,
    `name` text NOT NULL,
    `description` text,
    `date` text NOT NULL,
    `time` text NOT NULL,
    roles text NOT NULL);

CREATE TABLE IF NOT EXISTS raidUsers (
     userId text,
     username text,
     spec text,
     role text,
     raidId text);

CREATE TABLE IF NOT EXISTS raidUsersFlexRoles (
    userId text,
    username text,
    spec text,
    role text,
    raidId text);

CREATE TABLE IF NOT EXISTS serverSettings (
    serverId text PRIMARY KEY,
    raid_leader_role text);