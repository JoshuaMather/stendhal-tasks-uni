/***************************************************************************
 *                   (C) Copyright 2003-2019 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/

package games.stendhal.server.maps.deniran.river;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import games.stendhal.common.Direction;
import games.stendhal.common.parser.Sentence;
import games.stendhal.server.core.config.ZoneConfigurator;
//import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.pathfinder.FixedPath;
import games.stendhal.server.core.pathfinder.Node;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.player.Player;

/**
 * A port manager
 */
public class PortManagerNPC implements ZoneConfigurator {

	@Override
	public void configureZone(StendhalRPZone zone,
			Map<String, String> attributes) {
		buildNPC(zone);
	}

	private void buildNPC(final StendhalRPZone zone) {
		final SpeakerNPC npc = new SpeakerNPC("Fiete") {
			@Override
			public void createDialog() {
				addGreeting("Moin! How can I #help you?");
				addJob("Me port manager. Busy job. Very important!");
				addReply(Arrays.asList("delayed", "break"),
						"Me works hard. Very hard. So break fine, when no ships.");
				addHelp("The capital city of Deniran is to the north.");
				addQuest("Lots of work, when ships come. But now, no ships, no work. Return later.");
				addGoodbye("Return later. Lot's of work, when ships come.");
				
				add(ConversationStates.ATTENDING,
						"ados",
						null,
						ConversationStates.ATTENDING,
						null,
						new ChatAction() {
							@Override
							public void fire(final Player player, final Sentence sentence, final EventRaiser npc) {
								if (player.isEquipped("fast ferry ticket")) {
									npc.say("Thank you! Enjoy your trip!");
									
									player.teleport("0_ados_coast_s_w2", 99, 99, Direction.UP, null);

								} else {
									npc.say("You must have a ticket to travel to Ados.");
								}
							}
						});
			}

			@Override
			protected void createPath() {
				final List<Node> nodes = new LinkedList<Node>();
				nodes.add(new Node(82, 52));
				nodes.add(new Node(100, 52));
				setPath(new FixedPath(nodes, true));
			}

		};
		npc.setPosition(82, 52);
		npc.setEntityClass("beardmannpc");
		npc.setDescription("You see a strong man, lazily pacing.");
		zone.add(npc);
	}
}
