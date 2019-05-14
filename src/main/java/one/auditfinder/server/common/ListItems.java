package one.auditfinder.server.common;

import java.util.List;

public class ListItems<A, B> {
	private List<Item<A,B>> items;
	
	public ListItems() {	
	}

	public final List<Item<A, B>> getItems() {
		return items;
	}

	public final void setItems(List<Item<A, B>> items) {
		this.items = items;
	}
}
