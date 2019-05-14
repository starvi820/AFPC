package one.auditfinder.server.common;

public class Triple<F, S, T> {
	
	private F first;
	private S second;
	private T third;
	
	public Triple() {
		
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

	public final T getThird() {
		return third;
	}

	public final void setThird(T third) {
		this.third = third;
	}

	
}
