package me.cbitler.raidbot.utility;

import me.cbitler.raidbot.RaidBot;
import net.dv8tion.jda.core.entities.Emote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.cbitler.raidbot.utility.Variables.RaidBotProperty.DISCORD_EMOTE;
import static me.cbitler.raidbot.utility.Variables.getINSTANCE;

public class Reactions {
    /*
        <:X_:547197405334994945>
		<:Weaver:547197405058301952>
		<:Tempest:547197404655517697>
		<:Spellbreaker:547197404655386646>
		<:Soulbeast:547197405087662110>
		<:Scrapper:547197404957376552>
		<:Scourge:547197404764700674>
		<:Renegade:547197404961832970>
		<:Reaper:547197404940730368>
		<:Mirage:547197404982542351>
		<:Holosmith:547197404948987905>
		<:Herald:547197404961832960>
		<:Firebrand:547197404852649984>
		<:Druid:547197404609380383>
		<:Dragonhunter:547197404605317133>
		<:Deadeye:547197404831547411>
		<:Daredevil:547197404630482946>
		<:Chronomancer:547197404747792415>
		<:Berserker:547197404839936000>
     */


	/**
	 * List of reactions representing classes
	 */
	static String[] specs = {
			"Dragonhunter", //387295988282556417
			"Firebrand", //387296167958151169
			"Herald", //387296053659172869
			"Renegade", //387296192381321226
			"Berserker", //387296013947502592
			"Spellbreaker", //387296212421836800
			"Scrapper", //387296081823662081
			"Holosmith", //387296176770121738
			"Druid", // 387296044716916738
			"Soulbeast", //387296205488521216
			"Daredevil", //387296029533274113
			"Deadeye", //387296159716081664
			"Weaver", //387296219988361218
			"Tempest", //387296089340117002
			"Chronomancer", //387296021710897152
			"Mirage", //387296184114610185
			"Reaper", //387296061997318146
			"Scourge" //387296198928891905
	};

	static Emote[] reactions = {
			getEmoji("387295988282556417"), // Dragonhunter
			getEmoji("387296167958151169"), // Firebrand
			getEmoji("387296053659172869"), // Herald
			getEmoji("387296192381321226"), // Renegade
			getEmoji("387296013947502592"), // Berserker
			getEmoji("387296212421836800"), // Spellbreaker
			getEmoji("387296081823662081"), // Scrapper
			getEmoji("387296176770121738"), // Holosmith
			getEmoji("387296044716916738"), // Druid
			getEmoji("387296205488521216"), // Soulbeast
			getEmoji("387296029533274113"), // Daredevil
			getEmoji("387296159716081664"), // Deadeye
			getEmoji("387296219988361218"), // Weaver
			getEmoji("387296089340117002"), // Tempest
			getEmoji("387296021710897152"), // Chronomancer
			getEmoji("387296184114610185"), // Mirage
			getEmoji("387296061997318146"), // Reaper
			getEmoji("387296198928891905"), // Scourge
			getEmoji("387346852867211274") // X_
	};

	static Emote[] customReactions = {
		getExternalEmoji("547197405334994945"),     // <:X_:>
		getExternalEmoji("547197405058301952"),     // <:Weaver:>
		getExternalEmoji("547197404655517697"),     // <:Tempest:>
		getExternalEmoji("547197404655386646"),     // <:Spellbreaker:>
		getExternalEmoji("547197405087662110"),     // <:Soulbeast:>
		getExternalEmoji("547197404957376552"),     // <:Scrapper:>
		getExternalEmoji("547197404764700674"),     // <:Scourge:>
		getExternalEmoji("547197404961832970"),     // <:Renegade:>
		getExternalEmoji("547197404940730368"),     // <:Reaper:>
		getExternalEmoji("547197404982542351"),     // <:Mirage:>
		getExternalEmoji("547197404948987905"),     // <:Holosmith:>
		getExternalEmoji("547197404961832960"),     // <:Herald:>
		getExternalEmoji("547197404852649984"),     // <:Firebrand:>
		getExternalEmoji("547197404609380383"),     // <:Druid:>
		getExternalEmoji("547197404605317133"),     // <:Dragonhunter:>
		getExternalEmoji("547197404831547411"),     // <:Deadeye:>
		getExternalEmoji("547197404630482946"),     // <:Daredevil:>
		getExternalEmoji("547197404747792415"),     // <:Chronomancer:>
		getExternalEmoji("547197404839936000")      // <:Berserker:>
	};

	/**
	 * Get an emoji from it's emote ID via JDA
	 *
	 * @param id The ID of the emoji
	 * @return The emote object representing that emoji
	 */
	private static Emote getEmoji(String id) {
		return RaidBot.getInstance().getJda().getEmoteById(id);
	}

	/**
	 * Get an Emoji from an external Guild used as a repository for this puprose.
	 * @param id
	 * @return
	 */
	private static Emote getExternalEmoji(String id) {
		try {
			return RaidBot.getInstance().getJda().getGuildById(getINSTANCE().getStringProperty(DISCORD_EMOTE.toString())).getEmoteById(id);
		} catch (IOException e) {
			e.printStackTrace();
			return getEmoji(id);
		}
	}

	/**
	 * Get the list of reaction names as a list
	 *
	 * @return The list of reactions as a list
	 */
	public static List<String> getSpecs() {
		return new ArrayList<>(Arrays.asList(specs));
	}

	/**
	 * Get the list of emote objects
	 *
	 * @return The emotes
	 */
	public static List<Emote> getEmotes() {
		return new ArrayList<>(Arrays.asList(customReactions));
	}


}
