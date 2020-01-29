package container;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import annotation.ContentView;

public class InjectContainer {
    public static void inject(Object container) {
        injectContentView(container);
    }

    /**
     * 注入布局
     *
     * @param container 容器
     */
    private static void injectContentView(Object container) {
        Class<?> mMainActivityClass = container.getClass();
        /**
         * 拿到 Activity 中的注解
         */
        ContentView contentView = mMainActivityClass.getAnnotation(ContentView.class);
        if (contentView == null) {
            Log.d("TAG", "ContentView is null");
            return;
        }
        // 如果可以拿到这个注解
        int layoutId = contentView.value();
        try {
            Method method = mMainActivityClass.getMethod("setContentView", int.class);
            method.invoke(container, layoutId);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
