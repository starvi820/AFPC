package one.auditfinder.server.common;

public class ValidateResult {
	
	private int result;
	private int objectIndex;
	private String name;
	private String value;
	private int propertyIndex;
	private int exCode;
	private String exValue;	
	
	public ValidateResult() {
		result = 0;
		objectIndex= 0;
		name = null;
		value = null;
		propertyIndex = 0;
		exCode = 0;
		exValue=null;
	}


	public final int getResult() {
		return result;
	}


	public final void setResult(int result) {
		this.result = result;
	}


	public final int getObjectIndex() {
		return objectIndex;
	}


	public final void setObjectIndex(int objectIndex) {
		this.objectIndex = objectIndex;
	}


	public final String getName() {
		return name;
	}


	public final void setName(String name) {
		this.name = name;
	}


	public final String getValue() {
		return value;
	}


	public final void setValue(String value) {
		this.value = value;
	}


	public final int getPropertyIndex() {
		return propertyIndex;
	}


	public final void setPropertyIndex(int propertyIndex) {
		this.propertyIndex = propertyIndex;
	}


	public final int getExCode() {
		return exCode;
	}


	public final void setExCode(int exCode) {
		this.exCode = exCode;
	}


	public final String getExValue() {
		return exValue;
	}


	public final void setExValue(String exValue) {
		this.exValue = exValue;
	}
}
