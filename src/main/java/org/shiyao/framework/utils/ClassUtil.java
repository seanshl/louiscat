package org.shiyao.framework.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Load all classed under the base package, eg, classes using some annotation, or implementing some interface.
 * It is a bottom layer for ClassHelper
 */
public final class ClassUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    private static final String PACKAGE_DELIMIER = ".";
    private static final String PATH_DELIMIBER = "/";
    private static final String SPEC_DELIMITER = "%20";
    private static final String CLASS_SUFFIX = ".class";

    public enum Protocols {
        JAR("jar"),
        FILE("file");

        String protocol;

        private Protocols(String protocol) {
            this.protocol = protocol;
        }
    }

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * Load class for a particular class name.
     * @param className
     * @param isInitialized
     * @return
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> cls;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("load class failure", e);
            throw new RuntimeException(e);
        }

        return cls;
    }

    /**
     * Load all classes, including .class or .java into classSet under a specific package name.
     * @param packageName
     * @return
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        if (StringUtils.isBlank(packageName)) {
            return classSet;
        }

        try {
            String packageBasePath = packageName.replace(PACKAGE_DELIMIER, PATH_DELIMIBER);
            Enumeration<URL> urls = getClassLoader().getResources(packageBasePath);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();

                    if (Protocols.FILE.protocol.equals(protocol)) {
                        String packagePath = url.getPath().replace(SPEC_DELIMITER, " ");
                        addClass(classSet, packagePath, packageName);
                    } else if (Protocols.JAR.protocol.equals(protocol)) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        url.openConnection();

                        if (jarURLConnection != null) {
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if (jarFile != null) {
                                Enumeration<JarEntry> jarEntries = jarFile.entries();

                                while (jarEntries.hasMoreElements()) {
                                    JarEntry jarEntry = jarEntries.nextElement();
                                    String jarEntryName = jarEntry.getName();

                                    if (jarEntryName.endsWith(CLASS_SUFFIX)) {
                                        String className = jarEntryName.substring(0,
                                                jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                                        doAddClass(classSet, className);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("failed get class set. ", e.getMessage());
            throw new RuntimeException(e);
        }

        return classSet;
    }

    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles((File f) -> {
            return (f.isFile() && f.getName().endsWith(".class")) || f.isDirectory();
        });

        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));

                if (StringUtils.isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                }
                doAddClass(classSet, className);
            } else {
                String subPackagePath = fileName;
                if (StringUtils.isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }

                String subPackageName = fileName;
                if (StringUtils.isNotEmpty(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }

                addClass(classSet, subPackagePath, subPackageName);
            }
        }
    }

    private static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> cls = loadClass(className, false);
        classSet.add(cls);
    }
}
