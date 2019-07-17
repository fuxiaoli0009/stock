package com.stock.enums;

public enum FlagEnum {
	
	INIT_CONCERNED("0", "初始化关注的数据");

	private String code;
	
	private String msg;
	
	private FlagEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public static String getValueByCode(String code){
        for(FlagEnum flagEnum:FlagEnum.values()){
            if(code.equals(flagEnum.getCode())){
                return flagEnum.getMsg();
            }
        }
        return  null;
    }
	
	public static String getCodeByValue(String msg) {
		for(FlagEnum flagEnum:FlagEnum.values()) {
			if(msg.equals(flagEnum.getMsg())) {
				return flagEnum.getCode();
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