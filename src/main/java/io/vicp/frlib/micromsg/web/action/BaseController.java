package io.vicp.frlib.micromsg.web.action;

import io.vicp.frlib.micromsg.exception.BusinessException;
import io.vicp.frlib.micromsg.util.*;
import io.vicp.frlib.micromsg.web.model.User;
import io.vicp.frlib.micromsg.web.model.UserInfo;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by zhoudr on 2016/12/12.
 */
public class BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected UserInfo checkLogin(HttpServletRequest httpServletRequest) throws BusinessException {
        Cookie[] cookies = httpServletRequest.getCookies();
        UserInfo userInfo = null;
        boolean found = false;
        if (!ArrayUtils.isEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if (StringUtils.isNotBlank(name) && name.equalsIgnoreCase(Constants.LOGIN_COOKIE_NAME)) {
                    found = true;
                    String value = cookie.getValue();
                    if (StringUtils.isNotBlank(value)) {
                        userInfo = parseLoginCookie(value);
                        if (userInfo != null) {
                            long hasLoginSeconds = DateUtil.getSecondsFromTimeToNow(userInfo.getLoginTime());
                            if (hasLoginSeconds > cookie.getMaxAge()) { // 登录时间超过cookie时长
                                String msg = "用户未登录[id={0}]";
                                String resultMsg = MessageFormat.format(msg, userInfo.getUser() == null ? -1 : userInfo.getUser().getId());
                                throw new BusinessException(UserStatus.USER_NOT_LOGIN_STATUS.getValue(), resultMsg);
                            } else {
                                // 后台查询用户状态
                                logger.info("check login user status");
                            }
                        }
                    }

                }
            }
        }
        if (!found) {
            throw new BusinessException(UserStatus.USER_NOT_REGISTER_STATUS.getValue(), "用户未注册");
        }
        return userInfo;
    }

    /**
     * 解析cookie值
     * @param value
     * @return
     */
    private UserInfo parseLoginCookie(String value) {
        UserInfo userInfo = null;
        String[] stringArray = value.split("_");
        if (!ArrayUtils.isEmpty(stringArray)) {
            long id = Long.valueOf(stringArray[0]);
            User user = new User();
            user.setId(id);
            userInfo = new UserInfo();
            userInfo.setUser(user);
            long loginTimeValue = Long.valueOf(stringArray[1]);
            Date loginTime = new Date(loginTimeValue);
            userInfo.setLoginTime(loginTime);
        }
        return userInfo;
    }

    /**
     * 假的登录信息
     * @param httpServletRequest
     */
    protected void makeFakeUserInfo (HttpServletRequest httpServletRequest) {
        UserInfo userInfo = new UserInfo();
        User user = new User();
        user.setName("张三");
        user.setId(1);
        userInfo.setUser(user);
        userInfo.setLoginTime(DateUtil.convertStringDateWithDefault(null, null));
        httpServletRequest.setAttribute("userInfo", userInfo);
        httpServletRequest.setAttribute("date", FasterJsonUtil.writeValueAsString(userInfo.getLoginTime()));
    }

    protected void upload(HttpServletRequest httpServletRequest) {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(httpServletRequest.getServletContext());
        if (commonsMultipartResolver.isMultipart(httpServletRequest)) {
            MultipartHttpServletRequest request = (MultipartHttpServletRequest)httpServletRequest;
            Map<String, MultipartFile> map = request.getFileMap();
            if (MapUtils.isNotEmpty(map)) {
                for (String inputName : map.keySet()) {
                    MultipartFile multipartFile = map.get(inputName);
                    try {
                        long fileSize = multipartFile.getSize();
                        String maxUploadSize = ResourceConfigUtil.getProperty("maxUploadSize");
                        if (StringUtils.isEmpty(maxUploadSize) || fileSize > Integer.valueOf(maxUploadSize)) {
                            throw new BusinessException(0, "上传文件太大");
                        }
                        String uploadFileSavePath = ResourceConfigUtil.getProperty("uploadFileSavePath");
                        String destFile = uploadFileSavePath + multipartFile.getOriginalFilename();
                        IOUtil.copy(multipartFile.getBytes(), destFile);
                    } catch (IOException e) {
                        logger.error("", e);
                    }
                }
            }
        }
    }
}
