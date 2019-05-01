package com.stock.enums;

public enum StockStatusEnum {
	
	STOCK_STATUS_INIT("0", "初始"),
	STOCK_STATUS_DELETE("1", "删除");

	private String code;
	
	private String msg;
	
	private StockStatusEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public static String getValueByCode(String code){
        for(StockStatusEnum stockStatusEnum:StockStatusEnum.values()){
            if(code.equals(stockStatusEnum.getCode())){
                return stockStatusEnum.getMsg();
            }
        }
        return  null;
    }
	
	public static String getCodeByValue(String msg) {
		for(StockStatusEnum stockStatusEnum:StockStatusEnum.values()) {
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