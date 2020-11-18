package games.stendhal.server.entity.item;

import java.util.Map;

public class FastFerryTicket extends StackableItem {
	
	/**
	 * Creates a new fast ferry ticket with specified attributes
	 *
	 * @param attributes
	 */
	public FastFerryTicket(final Map<String, String> attributes) {
		super("fast ferry ticket", "documents", "fast_ferry_ticket", attributes);
	}

	/**
	 * Creates a specified amount fast ferry ticket
	 *
	 * @param quantity
	 */
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
