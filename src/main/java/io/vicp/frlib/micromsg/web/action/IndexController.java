package io.vicp.frlib.micromsg.web.action;

import com.fasterxml.jackson.annotation.JacksonInject;
import io.vicp.frlib.micromsg.exception.BusinessException;
import io.vicp.frlib.micromsg.service.IndexService;
import io.vicp.frlib.micromsg.util.FasterJsonUtil;
import io.vicp.frlib.micromsg.web.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

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

    @RequestMapping(value = "index")
    public String index() throws Exception{
        logger.info(indexService.getName());
        UserInfo userInfo = checkLogin();
        logger.info(FasterJsonUtil.writeValueAsString(userInfo));
        return "index";
    }

}
