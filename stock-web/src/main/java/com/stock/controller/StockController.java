package com.stock.controller;

import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.stock.model.TbStock;
import com.stock.service.StockService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "api")
public class StockController {

	private final Logger logger = LoggerFactory.getLogger(StockController.class);
	
    @Autowired
    private StockService stockService;
    
    @ApiOperation(value = "更新", httpMethod = "GET")
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public void update(@RequestParam("column") String column, @RequestParam("code") String code, @RequestParam("value") String value) {
    	logger.info("column:{}, code:{}, value:{}", column, code, value);
    	stockService.update(column, code, value);
    }
    
    @ApiOperation(value = "删除", httpMethod = "GET")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public void delete(@RequestParam("code") String code) {
    	stockService.delete(code);
    }
    
    @RequestMapping(value = "/addStock", method = RequestMethod.GET)
    public void addStock(@RequestParam("code") String code, @RequestParam("name") String name, 
    		@RequestParam("maxPrice") String maxPrice, @RequestParam("buyPrice") String buyPrice) {
    	TbStock tbStock = new TbStock();
    	tbStock.setCode(code);
    	tbStock.setName(name);
    	tbStock.setBuyPrice(new BigDecimal(buyPrice));
    	tbStock.setMaxValue(new BigDecimal(maxPrice));
    	stockService.addStock(tbStock);
    }
    
    @ApiOperation(value = "文件上传", httpMethod = "GET")
    @RequestMapping(value = "/uploadExcel", method = RequestMethod.GET)
    public ModelAndView uploadExcel() {
    	return new ModelAndView("/uploadExcel");
    }
	  
}
