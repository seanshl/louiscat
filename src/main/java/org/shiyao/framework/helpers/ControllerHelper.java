package org.shiyao.framework.helpers;

import org.shiyao.framework.annotation.Action;
import org.shiyao.framework.beans.Handler;
import org.shiyao.framework.beans.Request;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ControllerHelper {
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<>();

    static {
        Set<Class<?>> controllerSet = ClassHelper.getControllerClassSet();

        for (Class<?> controllerClass : controllerSet) {
            Method[] methods = controllerClass.getDeclaredMethods();

            for (Method method : methods) {
                if (method.isAnnotationPresent(Action.class)) {
                    Action action = method.getAnnotation(Action.class);

                    String mapping = action.value();

                    //Verify the mapping rule
                    if (mapping.matches("\\w+:/\\w*")) {
                        String[] array = mapping.split(":");
                        if (array.length == 2) {
                            String requestMethod = array[0];
                            String requestPath = array[1];

                            Request request = new Request(requestMethod, requestPath);
                            Handler handler = new Handler(controllerClass, method);

                            ACTION_MAP.put(request, handler);
                        }
                    }
                }
            }
        }
    }

    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }
}
