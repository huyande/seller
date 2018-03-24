package com.netease.controller;

import com.netease.model.Commodity;
import com.netease.model.CommodityForBuyer;
import com.netease.model.PerRequestUserHolder;
import com.netease.model.User;
import com.netease.service.CommodityService;
import com.netease.utils.CodeGeneUtils;
import com.netease.utils.StringAndFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @author zhanglbjames@163.com
 * @version Created on 18-3-20.
 */

@Controller
@RequestMapping(value = {"/commodity"})
public class CommodityController {
    private static final Logger logger = LoggerFactory.getLogger(CommodityController.class);

    @Autowired
    CommodityService commodityService;

    @Autowired
    PerRequestUserHolder localUserHolder;

    @Value(("${STORAGE_AMOUNT}"))
    private int STORAGE_AMOUNT;

    @Value(("${SOLD_QUANTITY}"))
    private int SOLD_QUANTITY;

    @Value(("${PUB_STATUS}"))
    private int PUB_STATUS;

    /**
     * 添加商品内容
     * <p>
     * 要点：
     * 1.TODO 对用户登录状态以及用户类型进行验证
     * 2.跳转到添加商品的详情页
     */
    @RequestMapping(value = {"/api/add"}, method = {RequestMethod.POST})
    public String addCommodity(@RequestParam("title") String title,
                               @RequestParam("summary") String comAbstract,
                               @RequestParam("pic") String picType,
                               @RequestParam("image") String imageUri,
                               @RequestParam("detail") String detail,
                               @RequestParam("price") String price,
                               @RequestParam(value = "storage", required = false) Integer storage,
                               Model model) {

        User user = localUserHolder.getLocalUser();

        Commodity commodity = new Commodity();

        // default value setting
        commodity.setSoldQuantity(SOLD_QUANTITY);
        commodity.setPubStatus(PUB_STATUS);
        // required value
        commodity.setTitle(title);
        commodity.setComAbstract(comAbstract);
        commodity.setPicUrl(imageUri);
        commodity.setDetail(detail);
        commodity.setPublisherId(user.getId());
        if (StringAndFileUtils.isDouble(price))
            commodity.setPerPrice(StringAndFileUtils.dataRruncation(Double.valueOf(price)));
        else {
            logger.error("price非数值类型数据，后端无法解析");
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
        commodity.setComCode(CodeGeneUtils.comAndOrdCodeGen(false, user.getId()));
        commodity.setPubTime(new Date());

        Map<String, Object> message = commodityService.addCommodity(commodity);
        // 范围异常
        if (message.containsKey("outOfRange")) {
            logger.error((String) message.get("outOfRange"));
            model.addAttribute("errorMessage", (String) message.get("outOfRange"));
            return "error";
        }
        Integer result = (Integer) message.get("addResult");

        // 正常插入跳转到查看页
        if (result > 0 && message.containsKey("addedCommodityId"))
            return "redirect:/commodity/page/show/" + message.get("addedCommodityId");
        else {// 可能发生了未知异常
            logger.error("后端无法完成插入数据或者已添加内容被立即删除");
            model.addAttribute("errorMessage",
                    "Inner Error:后端无法完成插入数据或者已添加内容被立即删除");
            return "error";
        }
    }


    /**
     * 保存商品修改内容
     * <p>
     * TODO 对用户登录状态以及用户类型进行验证
     * 要点：返回商品详情页
     */
    @RequestMapping(value = {"/api/save/{commodityId}"}, method = {RequestMethod.POST})
    public String saveCommodity(@PathVariable("commodityId") int commodityId,
                                @RequestParam("title") String title,
                                @RequestParam("summary") String comAbstract,
                                @RequestParam("pic") String picType,
                                @RequestParam("image") String imageUri,
                                @RequestParam("detail") String detail,
                                @RequestParam("price") String price,
                                Model model) {


        Commodity commodity = new Commodity();
        // required value
        commodity.setId(commodityId);
        commodity.setTitle(title);
        commodity.setComAbstract(comAbstract);
        commodity.setPicUrl(imageUri);
        commodity.setDetail(detail);
        commodity.setPicType(picType.equals("file") ? 0 : 1);
        if (StringAndFileUtils.isDouble(price))
            commodity.setPerPrice(Float.valueOf(price));
        else{
            logger.error("price非数值类型数据，后端无法解析");
            return "Inner Error:price非数值类型数据，后端无法解析";
        }

        Map<String, String> message = commodityService.updateCommodity(commodity);
        if (message.containsKey("outOfRange")) {
            logger.error((String) message.get("outOfRange"));

            model.addAttribute("errorMessage", message.get("outOfRange"));
            return "error";
        }
        return "redirect:/commodity/page/show/" + commodityId;
    }

    /**
     * 显示商品信息
     *
     * 对于购买者，已经购买的商品显示购买时的价格
     * TODO:任务疑惑-已经已经购买的商品是否可以重复购买R3.2与R4.3矛盾
     * 暂时是不可重复购买
     */
    @RequestMapping(value = {"/page/show/{commodityId}"}, method = RequestMethod.GET)
    public String showCommodityInfo(Model model, @PathVariable("commodityId") int commodityId) {

        Commodity commodity = commodityService.showCommodityInfo(commodityId);
        if (commodity != null) {
            if (commodity.getClass() == CommodityForBuyer.class) {
                model.addAttribute("commodity",(CommodityForBuyer) commodity);
            }else {
                model.addAttribute("commodity", commodity);
            }
        }
        return "commodityDetail";
    }

    /**
     * 显示编辑页面
     * <p>
     * TODO 对用户登录状态以及用户类型进行验证
     */
    @RequestMapping(value = {"/page/edit/{commodityId}"}, method = {RequestMethod.GET})
    public String showEditPage(Model model, @PathVariable("commodityId") int commodityId) {


        Commodity commodity = commodityService.showCommodityInfo(commodityId);

        if (commodity != null) {
            if (commodity.getClass() == CommodityForBuyer.class) {
                model.addAttribute("commodity", (CommodityForBuyer) commodity);
            } else {
                model.addAttribute("commodity", commodity);
            }
        }
        return "editCommodity";

    }

    /**
     * 显示添加商品页面
     * <p>
     * TODO 对用户登录状态以及用户类型进行验证
     */
    @RequestMapping(value = {"/page/create"}, method = {RequestMethod.GET})
    public String showCreatePage() {
        return "createCommodity";
    }


    /**
     * 图片上传
     * <p>
     * TODO 对用户登录状态以及用户类型进行验证
     */
    @ResponseBody
    @RequestMapping(value = {"/api/upload"}, method = {RequestMethod.POST})
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

    /**
     * 商家在展示页删除商品
     */
    @ResponseBody
    @RequestMapping(value = {"/api/delete"}, method = { RequestMethod.POST})
    public void deleteCommodity(@RequestParam("commodityId") int commodityId,
                                HttpServletResponse response) {
        commodityService.deleteCommodityById(commodityId);
    }

    //TODO 完成功能时，将错误引导页加上
    // 错误引导页
    @ExceptionHandler()
    public String error(Model model, Exception e) {
        model.addAttribute("errorMessage", "Inner Error: " + e.getMessage());
        return "error";
    }
}
