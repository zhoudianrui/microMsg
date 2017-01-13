package io.vicp.frlib.micromsg.web.handler;

import io.vicp.frlib.micromsg.exception.BusinessException;
import io.vicp.frlib.micromsg.util.DateUtil;
import io.vicp.frlib.micromsg.web.model.User;
import io.vicp.frlib.micromsg.web.model.UserInfo;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhoudr on 2016/12/27.
 */
public class BusinessExceptionHandler implements HandlerExceptionResolver{

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mv = new ModelAndView();
        UserInfo userInfo = new UserInfo();
        User user = new User();
        user.setName("张三");
        user.setId(1);
        userInfo.setUser(user);
        userInfo.setLoginTime(DateUtil.convertStringDateWithDefault(null, null));
        mv.addObject("userInfo", userInfo);
        if (ex instanceof BusinessException) {
            mv.setViewName("common/exception");
            mv.addObject("exceptionMsg", ((BusinessException) ex).getMsg());
        } else if (ex instanceof NullPointerException) {
            mv.setViewName("common/exception");
            mv.addObject("exceptionMsg", "请联系管理员");
        } else{
            mv.setViewName("redirect:index.do");
        }
        return mv;
    }
}
