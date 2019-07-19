package com.stock.enums;

public enum RemoteDataPrefixEnum {
	
	TENCENT_SH("s_sh", "沪"),
	TENCENT_SZ("s_sz", "深"),
	TENCENT_HK("hk", "港"),
	SINA_SH("sh", "沪"),
	SINA_SZ("sz", "深"),
	SINA_HK("hk", "港");

	private String code;
	
	private String msg;
	
	private RemoteDataPrefixEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public static String getValueByCode(String code){
        for(RemoteDataPrefixEnum remoteDataPrefixEnum:RemoteDataPrefixEnum.values()){
            if(code.equals(remoteDataPrefixEnum.getCode())){
                return remoteDataPrefixEnum.getMsg();
            }
        }
        return  null;
    }
	
	public static String getCodeByValue(String msg) {
		for(RemoteDataPrefixEnum remoteDataPrefixEnum:RemoteDataPrefixEnum.values()) {
			if(msg.equals(remoteDataPrefixEnum.getMsg())) {
				return remoteDataPrefixEnum.getCode();
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