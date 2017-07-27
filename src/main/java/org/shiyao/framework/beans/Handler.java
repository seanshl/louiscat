package org.shiyao.framework.beans;

import java.lang.reflect.Method;

public class Handler {
    private Class<?> controllerClass;

    private Method actionMethod;

    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return this.controllerClass;
    }

    public Method getActionMethod() {
        return this.actionMethod;
    }
}
