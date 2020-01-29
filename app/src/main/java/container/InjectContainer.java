package container;

import android.os.Binder;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import annotation.BindView;
import annotation.ContentView;

public class InjectContainer {
    public static void inject(Object container) {
        injectContentView(container);
        injectBindView(container);
    }

    private static void injectBindView(Object container) {
        Class<?> mainActivity = container.getClass();
        Field[] fields = mainActivity.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            BindView bindView = field.getAnnotation(BindView.class);
            if (null == bindView) {
                continue;
            }
            int viewId = bindView.value();
            try {
                Method method = mainActivity.getMethod("findViewById", int.class);
                Object findViewById = method.invoke(container, viewId);
                // 将找到的值给参数赋值
                field.set(container, findViewById);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
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
