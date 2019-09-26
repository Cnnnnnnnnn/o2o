package com.ning.o2o.web.superadmin;

import com.ning.o2o.entity.Area;
import com.ning.o2o.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/superadmin")
public class AreaController {

    Logger logger = LoggerFactory.getLogger(AreaController.class);

    @Autowired
    private AreaService areaService;
//    AreaService areaService = new AreaServiceImpl();

    @RequestMapping(value = "/listarea",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> listArea(){

        logger.info("-----------start------------");

        long startTime = System.currentTimeMillis();

        Map<String,Object> modelMap = new HashMap<String, Object>();
        List<Area> list = new ArrayList<Area>();
        try {
            list = areaService.getAreaList();
            modelMap.put("rows",list);
            modelMap.put("size",list.size());
        }catch (Exception e){
            e.printStackTrace();
            //当发生错误时，像页面返回一个success为false
            modelMap.put("success",false);
            //向页面返回错误信息
            modelMap.put("errMsg",e.toString());
        }
        long endTime = System.currentTimeMillis();
        logger.error("测试成功");
        logger.debug("costTime:[{}ms]",endTime-startTime);
        logger.info("--------------end---------------");
        return modelMap;
    }
}
