package com.stock.enums;

public enum StockTypeEnum {
	
	STOCK_STATUS_HS("0", "沪深"),
	STOCK_STATUS_HK("1", "香港");

	private String code;
	
	private String msg;
	
	private StockTypeEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public static String getValueByCode(String code){
        for(StockTypeEnum stockTypeEnum:StockTypeEnum.values()){
            if(code.equals(stockTypeEnum.getCode())){
                return stockTypeEnum.getMsg();
            }
        }
        return  null;
    }
	
	public static String getCodeByValue(String msg) {
		for(StockTypeEnum stockTypeEnum:StockTypeEnum.values()) {
			if(msg.equals(stockTypeEnum.getMsg())) {
				return stockTypeEnum.getCode();
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