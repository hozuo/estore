package top.ericson.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author Ericson
 * @class ItemController
 * @date 2020/04/01 23:03
 * @version 1.0
 * @description 页面处理控制器
 */
@Controller
@RequestMapping("/instock")
public class InStockController {
	
    @RequestMapping("/{moduleUI}")
    public String doModuleUI(@PathVariable String moduleUI) {
        return "/"+moduleUI;
    }
}
