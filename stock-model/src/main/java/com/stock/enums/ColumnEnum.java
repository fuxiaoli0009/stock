package com.stock.enums;

public enum ColumnEnum {
	
	DESCRIPTION("description", "description列"),
	BUYPRICE("buyprice", "buyprice列");

	private String code;
	
	private String msg;
	
	private ColumnEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public static String getValueByCode(String code){
        for(ColumnEnum columnEnum:ColumnEnum.values()){
            if(code.equals(columnEnum.getCode())){
                return columnEnum.getMsg();
            }
        }
        return  null;
    }
	
	public static String getCodeByValue(String msg) {
		for(ColumnEnum columnEnum:ColumnEnum.values()) {
			if(msg.equals(columnEnum.getMsg())) {
				return columnEnum.getCode();
			}
		}
		return null;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}