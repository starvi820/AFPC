package one.auditfinder.server.common;

public class Pair<F,S> {
	private F first;
	private S second;
	
	public Pair() {
	}
	
	public Pair(F first, S second) {
		this.first = first;
		this.second = second;
	}

	public final F getFirst() {
		return first;
	}

	public final void setFirst(F first) {
		this.first = first;
	}

	public final S getSecond() {
		return second;
	}

	public final void setSecond(S second) {
		this.second = second;
	}
}
