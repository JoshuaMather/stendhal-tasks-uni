package games.stendhal.server.entity.creature;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import games.stendhal.server.core.engine.SingletonRepository;

public class DwarfTest {

	
	/** 
	 * Test that dwarf speed (for under level 30 dwarfs) is correctly 0.8
	 */
	@Test
	public void testDwarfSpeed() {
		assertEquals(0.8, SingletonRepository.getEntityManager().getCreature("dwarf").getBaseSpeed(), 0.001);
		assertEquals(0.8, SingletonRepository.getEntityManager().getCreature("dwarf guardian").getBaseSpeed(), 0.001);
		assertEquals(0.8, SingletonRepository.getEntityManager().getCreature("elder dwarf").getBaseSpeed(), 0.001);
		assertEquals(0.8, SingletonRepository.getEntityManager().getCreature("hero dwarf").getBaseSpeed(), 0.001);
		assertEquals(0.8, SingletonRepository.getEntityManager().getCreature("duergar").getBaseSpeed(), 0.001);
	}

}
