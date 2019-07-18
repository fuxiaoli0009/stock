package com.stock.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stock.dataobject.RemoteDataInfo;
import com.stock.service.TencentApiService;

@RestController
@RequestMapping(value="test")
public class TestController {
	
	@Autowired
	private TencentApiService tencentApiService;

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public void test() {
		String codes = "s_sz000559,s_sz000913,s_sh601918";
		String response = tencentApiService.getRealTimeInfoFromRemote(codes);
		if(response!=null && response.contains(";")) {
			String[] responseArray = response.split(";");
			Map<String, RemoteDataInfo> remoteDataInfoMap = new HashMap<String, RemoteDataInfo>();
			for(int i=0; i< responseArray.length-1; i++) {
				RemoteDataInfo remote = new RemoteDataInfo();
				String[] strs = responseArray[i].split("~");
				remote.setCode(strs[2]);
				remote.setName(strs[1]);
				remote.setRealTimePrice(Double.parseDouble(strs[3]));
				remote.setRatePercent(strs[5]+"%");
				remoteDataInfoMap.put(remote.getCode(), remote);
			}
			System.out.println(1);
		}
		
	}
	
}
