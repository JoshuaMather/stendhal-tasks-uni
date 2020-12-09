/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.client.actions;

import static games.stendhal.common.constants.Actions.ALTERKILL;
import static games.stendhal.common.constants.Actions.INSPECTKILL;
import static games.stendhal.common.constants.Actions.INSPECTQUEST;
import static games.stendhal.common.constants.Actions.REMOVEDETAIL;
import static games.stendhal.common.constants.General.COMBAT_KARMA;

import java.util.HashMap;
import java.util.Locale;
import java.util.Set;
import java.util.Map;

/**
 * Manages Slash Action Objects.
 */
public class XMLSlashActionRepository {

	/** Set of client supported Actions. */
	private static HashMap<String, SlashAction> actions = new HashMap<String, SlashAction>();

	/**
	 * Registers the available Action.
	 */
	public static void register() {
		final SlashAction msg = new MessageAction();
		final SlashAction supporta = new SupportAnswerAction();
		final SlashAction who = new WhoAction();
		final SlashAction help = new HelpAction();
		final GroupMessageAction groupMessage = new GroupMessageAction();
		
		DefaultActionManager manager=new DefaultActionManager();
		
		Map<String, DefaultAction> map=manager.getActions();

		
		actions.put("/", new RemessageAction());
		actions.put("add", new XMLAction("add",map.get("add")));
		actions.put("adminlevel", new AdminLevelAction());
		actions.put("adminnote", new AdminNoteAction());
		actions.put("alter", new XMLAction("alter",map.get("alter")));
		actions.put("altercreature", new XMLAction("altercreature",map.get("altercreature")));
		actions.put(ALTERKILL, new AlterKillAction());
		actions.put("alterquest", new AlterQuestAction());
		actions.put("answer", new AnswerAction());
		actions.put("atlas", new AtlasBrowserLaunchCommand());
		actions.put("away", new AwayAction());

		actions.put("ban", new XMLAction("ban",map.get("ban")));

		actions.put("clear", new ClearChatLogAction());
		actions.put("clickmode", new ClickModeAction());
		actions.put("clientinfo", new ClientInfoAction());
		actions.put("commands", help);
		actions.put("config", new ConfigAction());

		actions.put("drop", new DropAction());

		actions.put("cast", new CastSpellAction());

		actions.put("gag", new XMLAction("gag",map.get("gag")));
		actions.put("gmhelp", new GMHelpAction());
		actions.put("group", new GroupManagementAction(groupMessage));
		actions.put("groupmessage", new XMLAction("group message",map.get("group message")));
		actions.put("grumpy", new GrumpyAction());

		actions.put("help", help);

		actions.put("ignore", new IgnoreAction());
		actions.put("inspect", new XMLAction("inspect",map.get("inspect")));
		actions.put(INSPECTKILL, new InspectKillAction());
		actions.put(INSPECTQUEST, new XMLAction(INSPECTQUEST,map.get(INSPECTQUEST)));
		actions.put("invisible", new XMLAction("invisible",map.get("invisible")));

		actions.put("jail", new XMLAction("jail",map.get("jail")));

		actions.put("listproducers", new XMLAction("listproducers",map.get("listproducers")));

		actions.put("me", new XMLAction("me",map.get("me")));
		actions.put("msg", msg);
		actions.put("mute", new MuteAction());

		actions.put("names", who);

		actions.put("p", groupMessage);
		actions.put("profile", new ProfileAction());
		actions.put("travellog", new XMLAction("travel log",map.get("travel log")));

		actions.put("quit", new QuitAction());

		actions.put("remove", new XMLAction("removebuddy",map.get("removebuddy")));

		actions.put("sentence", new XMLAction("sentence",map.get("sentence")));
		actions.put("status", new SentenceAction()); // Alias for /sentence
		actions.put("settings", new SettingsAction());

		actions.put("sound", new SoundAction());
		actions.put("volume", new VolumeAction());
		actions.put("vol", new VolumeAction());

		actions.put("storemessage", new XMLAction("store message",map.get("store message")));
		actions.put("postmessage", new StoreMessageAction());

		actions.put("summonat", new SummonAtAction());
		actions.put("summon", new SummonAction());
		actions.put("supportanswer", new XMLAction("supportanswer",map.get("supportanswer")));
		actions.put("supporta", supporta);
		actions.put("support", new XMLAction("support",map.get("support")));

		actions.put("takescreenshot", new ScreenshotAction());
		actions.put("teleport", new XMLAction("teleport",map.get("teleport")));
		actions.put("teleportto", new TeleportToAction());
		actions.put("tellall", new XMLAction("tellall",map.get("tellall")));
		actions.put("tell", msg);

		actions.put("where", new WhereAction());
		actions.put("who", new XMLAction("who",map.get("who")));
		actions.putAll(BareBonesBrowserLaunchCommandsFactory.createBrowserCommands());

		/* Movement */
		actions.put("walk", new AutoWalkAction());
		actions.put("stopwalk", new AutoWalkStopAction());
		actions.put("movecont", new MoveContinuousAction());

		// PvP challenge actions
		actions.put("challenge", new XMLAction("create challenge",map.get("create challenge")));
		actions.put("accept", new XMLAction("accept challenge",map.get("accept challenge")));

		actions.put(COMBAT_KARMA, new SetCombatKarmaAction());

		// allows players to remove the detail layer manually
		actions.put(REMOVEDETAIL, new XMLAction(REMOVEDETAIL,map.get(REMOVEDETAIL)));
	}

	/**
	 * Gets the Action object for the specified Action name.
	 *
	 * @param name
	 *            name of Action
	 * @return Action object
	 */
	public static SlashAction get(String name) {
		String temp = name.toLowerCase(Locale.ENGLISH);
		return actions.get(temp);
	}

	/**
	 * Get all known command names.
	 *
	 * @return set of commands
	 */
	public static Set<String> getCommandNames() {
		return actions.keySet();
	}
}
