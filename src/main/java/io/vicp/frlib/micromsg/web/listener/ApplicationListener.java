package io.vicp.frlib.micromsg.web.listener;

import io.vicp.frlib.micromsg.util.ResourceConfigUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by zhoudr on 2016/12/12.
 */
public class ApplicationListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        String env = ResourceConfigUtil.getProperty("env");
        System.out.println("application initialized, current system is " + env);
    }

    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("application destroyed");
    }
}
