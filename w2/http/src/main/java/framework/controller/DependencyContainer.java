package framework.controller;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DependencyContainer {

    private static final Map<String, Class<?>> qualifierMap = new HashMap<>();
    private static List<Class<?>> output = new ArrayList<>();

    public static <T> void registerClass(String name, Class<T> cl) {
        if (qualifierMap.containsKey(name) && !qualifierMap.get(name).equals(cl)) {
            throw new IllegalArgumentException("Conflict: Two qualifiers with the same name: " + name);
        }
        qualifierMap.put(name, cl);
    }

    public static Object getClass(String qualifierName) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<?> cl = qualifierMap.get(qualifierName);
        if (cl == null) {
            throw new RuntimeException("No implementation found for qualifier: " + qualifierName);
        }
        return cl.getDeclaredConstructor().newInstance();
    }
    public static List<Class<?>> getAllClasses() {
        output.clear();
        String[] paths = System.getProperty("java.class.path").split(File.pathSeparator);

        for (String path : paths) {
            File file = new File(path);
            findClasses(file, "");
        }

        return output;
    }

    private static void findClasses(File directory, String packageName) {
        if (!directory.exists() || !directory.isDirectory()) return;

        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                findClasses(file, packageName + file.getName() + ".");
            } else if (file.getName().endsWith(".class")) {
                try {
                    String className = packageName + file.getName().replace(".class", "");
                    output.add(Class.forName(className));
                } catch (ClassNotFoundException e) {
                    System.out.println("Class not found: " + e.getMessage());
                }
            }
        }
    }
}

