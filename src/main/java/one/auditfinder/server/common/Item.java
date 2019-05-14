package one.auditfinder.server.common;

import java.util.List;

public class Item<T, C> {
	
	private T item;
	private List<C> childs;
	
	public final T getItem() {
		return item;
	}
	
	public final void setItem(T item) {
		this.item = item;
	}
	
	public final List<C> getChilds() {
		return childs;
	}
	
	public final void setChilds(List<C> childs) {
		this.childs = childs;
	}
}
