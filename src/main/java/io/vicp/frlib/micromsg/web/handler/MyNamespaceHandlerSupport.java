package io.vicp.frlib.micromsg.web.handler;

/**
 * Created by zhoudr on 2016/12/24.
 */

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class MyNamespaceHandlerSupport extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("serviceBean", new MyBeanDefinitionParser());
    }
}
