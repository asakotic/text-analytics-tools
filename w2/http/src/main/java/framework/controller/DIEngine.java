package framework.controller;

import framework.annotations.*;
import framework.request.enums.HttpMethod;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DIEngine {
    private static Map<Class<?>, Object> singletonClasses = new HashMap<>();
    private static Map<Class<?>, Object> controllers = new HashMap<>();
    private static Map<HttpRequest, Method> httpMap = new HashMap<>();

    static {
        try {
            init();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static void init() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        List<Class<?>> allClasses = DependencyContainer.getAllClasses();
        for (Class<?> cl : allClasses) {
            if (cl.isAnnotationPresent(Controller.class)) {
                for (Method method : cl.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(Path.class)) {
                        Path path = method.getAnnotation(Path.class);
                        if(method.isAnnotationPresent(Get.class) || method.isAnnotationPresent(Post.class)){
                            HttpRequest request = new HttpRequest(path.path(), method.isAnnotationPresent(Get.class) ? HttpMethod.GET : HttpMethod.POST);
                            if (httpMap.putIfAbsent(request, method) != null)
                                throw new RuntimeException("Only one method can be on that path: " + request.getPath());
                        } else
                            throw new RuntimeException("Not declared the request type");
                    }
                }
                createInstance(cl);
            }
            if (cl.isAnnotationPresent(Qualifier.class)) {
                Qualifier qualifier = cl.getAnnotation(Qualifier.class);
                DependencyContainer.registerClass(qualifier.value(), cl);
            }

        }
    }
    public static Object createInstance(Class<?> cl) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (singletonClasses.containsKey(cl))
            return singletonClasses.get(cl);

        Constructor<?> constructor = cl.getDeclaredConstructor();
        Object instance = constructor.newInstance();
        for (Field field : cl.getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                Object o;
                Class<?> type = field.getType();
                if (!type.isInterface()) {
                    o =  createInstance(field.getType());
                } else if (field.isAnnotationPresent(Qualifier.class)) {
                    String qualifierName = field.getAnnotation(Qualifier.class).value();
                    o = DependencyContainer.getClass(qualifierName);
                } else
                    throw new RuntimeException("Autowired field needs Qualifier annotation too!");

                field.setAccessible(true);
                field.set(instance, o);

                if (field.getAnnotation(Autowired.class).verbose()) {
                    System.out.println("Initialized " + field.getType().getName() + " in " + cl.getSimpleName() + " on " + LocalDateTime.now() + " with " + o.hashCode());
                }
            }
        }

        if (cl.isAnnotationPresent(Controller.class)) {
            controllers.put(cl, instance);
        } else if (cl.isAnnotationPresent(Service.class)
                || cl.isAnnotationPresent(Component.class)
                || cl.isAnnotationPresent(Bean.class) && cl.getAnnotation(Bean.class).scope().equals("singleton")) {
            singletonClasses.put(cl, instance);
        }
        return instance;
    }


    public static Map<Class<?>, Object> getControllers() {
        return controllers;
    }

    public static Map<HttpRequest, Method> getHttpMap() {
        return httpMap;
    }
}