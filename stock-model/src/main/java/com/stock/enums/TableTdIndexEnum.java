package com.stock.enums;

public enum TableTdIndexEnum {
	
	TABLE_TD_BUYPRICE("4", "买入价格"),
	TABLE_TD_DESC("7", "备注");

	private String code;
	
	private String msg;
	
	private TableTdIndexEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public static String getValueByCode(String code){
        for(TableTdIndexEnum stockStatusEnum:TableTdIndexEnum.values()){
            if(code.equals(stockStatusEnum.getCode())){
                return stockStatusEnum.getMsg();
            }
        }
        return  null;
    }
	
	public static String getCodeByValue(String msg) {
		for(TableTdIndexEnum stockStatusEnum:TableTdIndexEnum.values()) {
			if(msg.equals(stockStatusEnum.getMsg())) {
				return stockStatusEnum.getCode();
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