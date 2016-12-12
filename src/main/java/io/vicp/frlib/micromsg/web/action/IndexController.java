package io.vicp.frlib.micromsg.web.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhoudr on 2016/12/10.
 */
@Controller
public class IndexController extends BaseController{

    @RequestMapping(value = "index")
    public String index() {
        return "index";
    }
}
