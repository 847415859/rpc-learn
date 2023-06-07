package com.tk.rpcgrpcbootserver.config;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Description:
 * @Date : 2023/06/07 19:37
 * @Auther : tiankun
 */
public class EnableAutoConfigurationImportSelector implements DeferredImportSelector, BeanClassLoaderAware {
    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {

    }

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[0];
    }
}
