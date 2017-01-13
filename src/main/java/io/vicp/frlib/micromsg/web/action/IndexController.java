package io.vicp.frlib.micromsg.web.action;

import io.vicp.frlib.micromsg.service.IndexService;
import io.vicp.frlib.micromsg.util.DateUtil;
import io.vicp.frlib.micromsg.util.FasterJsonUtil;
import io.vicp.frlib.micromsg.web.model.User;
import io.vicp.frlib.micromsg.web.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhoudr on 2016/12/10.
 */
@Controller
public class IndexController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    // @Resource(name="indexService")
    // 采用autowired使用bean的id与变量名进行匹配
    @Autowired
    private IndexService indexService;

    @RequestMapping(value = "/index")
    public String index(HttpServletRequest httpServletRequest) throws Exception{
        //logger.info(indexService.getName());
        //UserInfo userInfo = checkLogin();
        //logger.info(FasterJsonUtil.writeValueAsString(userInfo));
        makeFakeUserInfo(httpServletRequest);
        return "index";
    }



    @RequestMapping("/upload")
    public String uploadFiles(HttpServletRequest httpServletRequest) throws Exception{
        upload(httpServletRequest);
        makeFakeUserInfo(httpServletRequest);
        return "redirect:index.do";
    }

}
