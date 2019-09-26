package com.ning.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ning.o2o.dto.ShopExecution;
import com.ning.o2o.entity.Area;
import com.ning.o2o.entity.PersonInfo;
import com.ning.o2o.entity.Shop;
import com.ning.o2o.entity.ShopCategory;
import com.ning.o2o.enums.ShopStateEnum;
import com.ning.o2o.service.AreaService;
import com.ning.o2o.service.ShopCategoryService;
import com.ning.o2o.service.ShopService;
import com.ning.o2o.util.CodeUtil;
import com.ning.o2o.util.FileUtil;
import com.ning.o2o.util.HttpServletRequestUtil;
import com.ning.o2o.util.ImageUtil;
import com.sun.javafx.scene.shape.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequestMapping("shop")
public class ShopManagementController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;

    /**
     * 获取区域列表和商铺类别列表返回给前台
     *
     * @return
     */
    @RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopInitInfo() {
        Map<String, Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList = new ArrayList<>();
        List<Area> areaList = new ArrayList<>();
        try {
            shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();
            modelMap.put("shopCategoryList", shopCategoryList);
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errorMsg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/registershop", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> registerShop(HttpServletRequest request) {
        System.out.println("----------------------"+request.getParameter("shop"));
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (!CodeUtil.changeVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errorMsg", "验证码错误");
            return modelMap;
        }
        /**
         * 获取前端传来的店铺信息
         */
        String shopStr = HttpServletRequestUtil.getString(request,"shop");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errorMsg", e.getMessage());
            return modelMap;
        }
        /**
         * 获取前端传来的文件流
         */
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());//文件上传解析器，获取文件上传的上下文
        if (commonsMultipartResolver.isMultipart(request)) {  //判断这个请求中是否含有这个文件流
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        } else {
            modelMap.put("success", false);
            modelMap.put("errorMsg", "上传图片为空");
            return modelMap;
        }
        /**
         *
         */
        if (shop != null && shopImg != null) {
            PersonInfo owner = new PersonInfo();
            owner.setUserId(1L);
            shop.setOwner(owner);
//            File shopImgFile = new File(FileUtil.getImgBasePath() + FileUtil.getRandomFileName());
//            try {
//                shopImgFile.createNewFile();
//            } catch (IOException e) {
//                modelMap.put("success", false);
//                modelMap.put("errorMsg", e.getMessage());
//                return modelMap;
//            }
//            try {
//                inputStrameToFile(shopImg.getInputStream(), shopImgFile);
//            } catch (IOException e) {
//                modelMap.put("success", false);
//                modelMap.put("errorMsg", e.getMessage());
//                return modelMap;
//            }
            ShopExecution se = null;
            try {
                System.out.println("---------------"+shop);
                System.out.println("------------------------"+shopImg.getInputStream());
                System.out.println("------------------------------"+shopImg.getOriginalFilename());
                se = shopService.addShop(shop, shopImg.getInputStream(),shopImg.getOriginalFilename());
                if (se.getState() == ShopStateEnum.CHECK.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errorMsg", se.getStateInfo());
                }
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errorMsg", e.getMessage());
                return modelMap;
            }

            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errorMsg", "上传图片为空");
            return modelMap;
        }
    }

    /**
     * 将一个文件转化为文件流
     *
     * @param inputStream 前端传来的文件输出流
     * @param file        前端传来的文件
     */
//    private static void inputStrameToFile(InputStream inputStream, File file) {
//        FileOutputStream fos = null;
//        try {
//            new FileOutputStream(file);
//            int bytesRead = 0;
//            byte[] b = new byte[1024];
//            while ((bytesRead = inputStream.read(b)) != -1) {
//                fos.write(b, 0, bytesRead);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("调用inputStrameToFile时出错" + e.getMessage());
//        } finally {
//            try {
//                if (fos != null) {
//                    fos.close();
//                }
//                if (inputStream != null) {
//                    inputStream.close();
//                }
//            } catch (Exception e) {
//                throw new RuntimeException("关流时发生错误" + e.getMessage());
//            }
//        }
//    }
}
