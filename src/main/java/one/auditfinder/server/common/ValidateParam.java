package one.auditfinder.server.common;

import java.util.regex.Pattern;

public class ValidateParam {
	public static final int VALI_VALMINMAX = 0x00000001;
	public static final int VALI_STRMINMAX = 0x00000002;
	public static final int VALI_REGEXSTR = 0x00000004;
	public static final int VALI_PATTERN = 0x00000008;
	public static final int VALI_EMAIL = 0x00000010;
	public static final int VALI_NULLSKIP = 0x00000020;
//	public static final int VALI_VALMINMAX = 0x00000020;
//	public static final int VALI_VALMINMAX = 0x00000040;
//	public static final int VALI_VALMINMAX = 0x00000080;
	
	
	private int kind;
	
	private long min;
	private long max;
	private int exKind;
	private String field;
	private String regex;
	private String[] regexs;
	private Pattern pattern;
	private Pattern[] patterns;
	
	public ValidateParam() {
		kind = 0;
		min = 0;
		max = 0;
		exKind = 0;
		field = null;
		regex = null;
		regexs = null;
		pattern = null;
		patterns = null;
	}
	
	public boolean isValueMinMax() {
		return (( kind & VALI_VALMINMAX) > 0);
	}
	public boolean isStrMinMax() {
		return (( kind & VALI_STRMINMAX) > 0);
	}
	public boolean isRegExStr() {
		return (( kind & VALI_REGEXSTR) > 0);
	}
	public boolean isPattern() {
		return (( kind & VALI_PATTERN) > 0);
	}
	public boolean isEmail() {
		return (( kind & VALI_EMAIL) > 0);
	}
	public boolean isNullSkip() {
		return (( kind & VALI_NULLSKIP) > 0);
	}

	public final int getKind() {
		return kind;
	}

	public final void setKind(int kind) {
		this.kind = kind;
	}

	public final long getMin() {
		return min;
	}

	public final void setMin(long min) {
		this.min = min;
	}

	public final long getMax() {
		return max;
	}

	public final void setMax(long max) {
		this.max = max;
	}

	public final String getRegex() {
		return regex;
	}

	public final void setRegex(String regex) {
		this.regex = regex;
	}

	public final Pattern getPattern() {
		return pattern;
	}

	public final void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}

	public final String[] getRegexs() {
		return regexs;
	}

	public final void setRegexs(String[] regexs) {
		this.regexs = regexs;
	}

	public final Pattern[] getPatterns() {
		return patterns;
	}

	public final void setPatterns(Pattern[] patterns) {
		this.patterns = patterns;
	}

	public final int getExKind() {
		return exKind;
	}

	public final void setExKind(int exKind) {
		this.exKind = exKind;
	}

	public final String getField() {
		return field;
	}

	public final void setField(String field) {
		this.field = field;
	}
	
}
