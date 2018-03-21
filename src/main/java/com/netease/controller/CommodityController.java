package com.netease.controller;

import com.netease.model.Commodity;
import com.netease.model.PerRequestUserHolder;
import com.netease.model.User;
import com.netease.service.CommodityService;
import com.netease.utils.StringAndFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author zhanglbjames@163.com
 * @version Created on 18-3-20.
 */

@Controller
@RequestMapping(value = {"/commodity"})
public class CommodityController {
    @Autowired
    CommodityService commodityService;

    @Autowired
    PerRequestUserHolder localUserHoler;

    @Value(("${STORAGE_AMOUNT}"))
    private int STORAGE_AMOUNT;

    @Value(("${SOLD_QUANTITY}"))
    private int SOLD_QUANTITY;

    @Value(("${PUB_STATUS}"))
    private int PUB_STATUS;


    // 添加商品内容
    @RequestMapping(value = {"/add"}, method = {RequestMethod.POST})
    public String addCommodity(@RequestParam("title") String title,
                               @RequestParam("summary") String comAbstract,
                               @RequestParam("pic") String picType,
                               @RequestParam("image") String imageUri,
                               @RequestParam("detail") String detail,
                               @RequestParam("price") String price,
                               @RequestParam(value = "storage", required = false) Integer storage,
                               Model model) {

        User user = localUserHoler.getLocalUser();

        Commodity commodity = new Commodity();

        // default value setting
        commodity.setSoldQuantity(SOLD_QUANTITY);
        commodity.setPubStatus(PUB_STATUS);
        // required value
        commodity.setTitle(title);
        commodity.setComAbstract(comAbstract);
        commodity.setPicURI(imageUri);
        commodity.setDetail(detail);
        if (StringAndFileUtils.isFloat(price))
            commodity.setPerPrice(Float.valueOf(price));
        else {
            model.addAttribute("errorMessage",
                    "Inner Error:price非数值类型数据，后端无法解析");
            return "error";
        }

        commodity.setPicType(picType.equals("file") ? 0 : 1);
        // not required value
        if (storage != null) {
            commodity.setStorageAmount(storage);
        } else
            commodity.setStorageAmount(STORAGE_AMOUNT);

        Map<String, Object> message = commodityService.addCommodity(commodity);
        // 范围异常
        if (message.containsKey("outOfRange")) {
            model.addAttribute("errorMessage", (String) message.get("outOfRange"));
            return "error";
        }
        Integer result = (Integer) message.get("addResult");

        // 正常插入跳转到查看页
        if (result > 0 && message.containsKey("addedCommodityId"))
            return "redirect:/commodity/show/" + message.get("addedCommodityId");
        else {// 可能发生了未知异常
            model.addAttribute("errorMessage",
                    "Inner Error:后端无法完成插入数据或者已添加内容被立即删除");
            return "error";
        }
    }


    // 保存商品修改内容
    @RequestMapping(value = {"/save/{commodityId}"}, method = {RequestMethod.POST})
    public String saveCommodity(@PathVariable("commodityId") int commodityId,
                                @RequestParam("title") String title,
                                @RequestParam("summary") String comAbstract,
                                @RequestParam("pic") String picType,
                                @RequestParam("image") String imageUri,
                                @RequestParam("detail") String detail,
                                @RequestParam("price") String price) {


        Commodity commodity = new Commodity();
        // required value
        commodity.setId(commodityId);
        commodity.setTitle(title);
        commodity.setComAbstract(comAbstract);
        commodity.setPicURI(imageUri);
        commodity.setDetail(detail);
        commodity.setPicType(picType.equals("file") ? 0 : 1);
        if (StringAndFileUtils.isFloat(price))
            commodity.setPerPrice(Float.valueOf(price));
        else
            return "Inner Error:price非数值类型数据，后端无法解析";

        commodityService.updateCommodity(commodity);
        return "redirect:/commodity/show/" + commodityId;
    }

    // 显示商品信息
    @RequestMapping(value = {"/show/{commodityId}"}, method = RequestMethod.GET)
    public String showCommodityInfo(Model model, @PathVariable("commodityId") int commodityId) {

        Commodity commodity = commodityService.showCommodityInfo(commodityId);
        if (commodity != null) {
            model.addAttribute("commodity", commodity);
            return "show";
        } else {
            model.addAttribute("errorMessage", "无法获取商品(id=" + commodityId + ")的信息");
            return "error";
        }

    }

    // 显示编辑页面
    @RequestMapping(value = {"/edit{commodityId}"}, method = {RequestMethod.GET})
    public String showEditPage(Model model, @PathVariable("commodityId") int commodityId) {

        Commodity commodity = commodityService.showCommodityInfo(commodityId);
        if (commodity != null) {
            model.addAttribute("commodity", commodity);
            return "editCommodity";
        } else {
            model.addAttribute("errorMessage", "无法获取商品(id=" + commodityId + ")的信息");
            return "error";
        }
    }

    // 显示添加商品页面
    @RequestMapping(value = {"/create"}, method = {RequestMethod.GET})
    public String showCreatePage() {
        return "createCommodity";
    }


    // 图片上传
    @ResponseBody
    @RequestMapping(value = {"/upload"}, method = {RequestMethod.POST})
    public String uploadFile(@RequestParam(value = "file", required = false) MultipartFile file) {
        Map<String, String> message = commodityService.saveFile(file);
        if (message.containsKey("error")) {
            return message.get("error");
        }
        String path = null;
        if (message.containsKey("path")) {
            path = message.get("path");
        }
        return path;
    }
}
