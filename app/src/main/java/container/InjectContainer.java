package container;

import android.os.Binder;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import annotation.BindView;
import annotation.ContentView;
import annotation.OnClick;

public class InjectContainer {
    public static void inject(Object container) {
        injectContentView(container);
        injectBindView(container);
        injectEvent(container);
    }

    private static void injectEvent(final Object container) {
        Class<?> mainActivity = container.getClass();
        Method[] methods = mainActivity.getDeclaredMethods();
        for (final Method method : methods) {
            method.setAccessible(true);
            final OnClick onClickAnnotation = method.getAnnotation(OnClick.class);
            if (null == onClickAnnotation) {
                continue;
            }
            int value = onClickAnnotation.value();
            try {
                Method findViewById = mainActivity.getMethod("findViewById", int.class);
                Object view = findViewById.invoke(container, value);
                View eventView = (View) view;
                eventView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            // 当点击这个 View 的时候，执行使用了注解的方法
                            method.invoke(container);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
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
