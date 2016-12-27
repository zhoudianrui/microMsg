package io.vicp.frlib.micromsg.web.handler;

import io.vicp.frlib.micromsg.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created by zhoudr on 2016/12/27.
 */
public class BusinessExceptionHandler implements HandlerExceptionResolver{

    private static final Logger logger = LoggerFactory.getLogger(BusinessExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (handler == null) {
            logger.error("no method expose exception");
            return null;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler; // 抛出异常的方法
        Method method = handlerMethod.getMethod();
        ModelAndView mv = new ModelAndView();
        if (ex instanceof BusinessException) {
            mv.setViewName("common/exception");
            mv.addObject("exceptionMsg", ((BusinessException) ex).getMsg());
        } else if (ex instanceof NullPointerException) {
            mv.setViewName("common/exception");
            mv.addObject("exceptionMsg", "请联系管理员");
        } else {
            mv.setViewName("index");
        }
        return mv;
    }
}
