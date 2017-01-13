package io.vicp.frlib.micromsg.web.action;

import io.vicp.frlib.micromsg.web.model.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhoudr on 2016/12/23.
 */
@Controller
public class LoginController extends BaseController {

    @RequestMapping(value = "getUserId")
    @ResponseBody
    public String getUserId (HttpServletRequest httpServletRequest) throws Exception{
        UserInfo userInfo = checkLogin(httpServletRequest);
        return String.valueOf(userInfo.getUser().getId());
    }

}
