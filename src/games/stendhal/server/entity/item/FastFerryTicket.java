package games.stendhal.server.entity.item;

import java.util.Map;

public class FastFerryTicket extends StackableItem {

//	public FastFerryTicket(String name, String clazz, String subclass, Map<String, String> attributes) {
//		super(name, clazz, subclass, attributes);
//		// TODO Auto-generated constructor stub
//	}
	public FastFerryTicket(final Map<String, String> attributes) {
		super("fast ferry ticket", "documents", "fast_ferry_ticket", attributes);
	}

	public FastFerryTicket(final int quantity) {
		super("fast ferry ticket", "documents", "fast_ferry_ticket", null);
		setQuantity(quantity);
	}

	/**
	 * Copy constructor.
	 *
	 * @param item
	 *            item to copy
	 */
	public FastFerryTicket(final FastFerryTicket item) {
		super(item);
	}

	@Override
	public String describe() {
		return "You see " + getQuantity() + " fast ferry tickets.";
	}

}
