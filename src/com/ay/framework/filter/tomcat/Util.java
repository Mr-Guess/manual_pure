package com.ay.framework.filter.tomcat;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/** 
 * @Description 
 * @date 2012-10-30
 * @author WangXin
 */
public class Util {
	/**
     * 从所有的线程类加载器中加载类
      * @param classname
     * @return
     */
    public static Class getClass(String classname) {
        Thread[] threads = getAllThread();
        Class clazz = null;
        for (int i = 0; i < threads.length; i++) {
            try {
                // 获得线程上下文的类加载器
                  ClassLoader loader = threads[i].getContextClassLoader();
                if (loader == null) {
                    continue;
                }
                // 加载类
                  clazz = loader.loadClass(classname);
                // 加载成功返回
                  return clazz;
            } catch (ClassNotFoundException e) {
            }
        }
        return null;
    }

    /**
     * 反射调用一个对象的某个无参方法
      * @param obj
     * @param methodName
     * @return
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Object invokeMethod(Object obj, String methodName)
            throws SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        Method method = obj.getClass().getMethod(methodName, new Class[0]);
        return method.invoke(obj, new Object[0]);
    }
    
    /**
     * 反射调用一个类的某个无参静态方法
      * @param clazz
     * @param methodName
     * @return
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @SuppressWarnings("unchecked")
    public static Object invokeStaticMethod(Class clazz, String methodName)
            throws SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Method method = clazz.getMethod(methodName, new Class[0]);
        return method.invoke(null, new Object[0]);
    }

    /**
     * 获得所有线程
      * 
     * @return
     */
    private static Thread[] getAllThread() {
        ThreadGroup root = Thread.currentThread().getThreadGroup();
        ThreadGroup ttg = root;
        while ((ttg = ttg.getParent()) != null) {
            root = ttg;
        }
        Thread[] tlist = new Thread[(int) (root.activeCount() * 1.2)];
        // return java.util.Arrays.copyOf(tlist, root.enumerate(tlist, true));
        return copyOf(tlist, root.enumerate(tlist, true));
    }

    private static Thread[] copyOf(Thread[] tlist, int length) {
        Thread[] threads = new Thread[length];
        System.arraycopy(tlist, 0, threads, 0, length);
        return threads;
    }
    
}

