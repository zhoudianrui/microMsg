package io.vicp.frlib.micromsg.web.handler;

import io.vicp.frlib.micromsg.service.IndexService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by zhoudr on 2016/12/24.
 */
public class MyBeanDefinitionParser implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext){
        RootBeanDefinition def = new RootBeanDefinition(IndexService.class);
        String id = element.getAttribute("id");
        BeanDefinitionHolder idHolder = new BeanDefinitionHolder(def, id);
        BeanDefinitionReaderUtils.registerBeanDefinition(idHolder, parserContext.getRegistry());

        String name = element.getAttribute("name");
        BeanDefinitionHolder nameBeanDefinitionHolder = new BeanDefinitionHolder(def, name);
        BeanDefinitionReaderUtils.registerBeanDefinition(nameBeanDefinitionHolder, parserContext.getRegistry());
        def.getPropertyValues().addPropertyValue("name", name);
       /* BeanDefinition beanDefinition = parserContext.getReaderContext().getRegistry().getBeanDefinition("org.springframework.web.servlet.mvc" +
                ".method.annotation.RequestMappingHandlerAdapter");
        def.getPropertyValues().addPropertyValue("requestMappingHandlerAdapter", beanDefinition);*/

        return def;
    }

}
