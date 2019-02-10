package me.cbitler.raidbot.database;

public interface DatabaseOpenHelper {

	/**
	 * Called when the Database is first created, making sure it is initialized with the very first tables from v1
	 */
	void onCreate();

	/**
	 * Called after creation of the Database, to upgrade with migration script from each new versions.
	 */
	void onUpgrade();
}
