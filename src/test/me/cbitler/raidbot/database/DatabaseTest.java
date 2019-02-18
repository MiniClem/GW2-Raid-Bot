package me.cbitler.raidbot.database;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    private String testdb = "test.db";

    @Test

    void connect() {
        Database database = new Database(testdb);
        database.connect();
    }

    @Test
    void query() {
    }

    @Test
    void update() {
    }

    @Test
    void tableInits() {
    }
}