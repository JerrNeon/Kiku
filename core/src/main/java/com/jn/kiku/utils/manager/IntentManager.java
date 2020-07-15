package com.jn.kiku.utils.manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (界面跳转管理)
 * @create by: chenwei
 * @date 2018/5/10 17:34
 */
public class IntentManager {

    /**
     * @param activity activity
     * @param cls      需要跳转的类
     * @param param    需要传递的参数，为空不传递参数
     */
    public static void startActivity(@NonNull Activity activity, @NonNull Class<?> cls, @Nullable Object param) {
        Intent intent = getIntent(cls.getSimpleName(), param);
        intent.setClass(activity, cls);
        activity.startActivity(intent);
    }

    /**
     * @param fragment fragment
     * @param cls      需要跳转的类
     * @param param    需要传递的参数，为空不传递参数
     */
    public static void startActivity(@NonNull Fragment fragment, @NonNull Class<?> cls, @Nullable Object param) {
        Intent intent = getIntent(cls.getSimpleName(), param);
        intent.setClass(Objects.requireNonNull(fragment.getActivity()), cls);
        fragment.startActivity(intent);
    }

    /**
     * @param activity    activity
     * @param cls         需要跳转的类
     * @param param       需要传递的参数，为空不传递参数
     * @param requestCode 请求码，用于目标界面返回回来的参数区分
     */
    public static void startActivity(@NonNull Activity activity, @NonNull Class<?> cls, @Nullable Object param, @Nullable Object requestCode) {
        if (requestCode != null) {
            if (requestCode instanceof Integer) {
                Intent intent = getIntent(cls.getSimpleName(), param);
                intent.setClass(activity, cls);
                activity.startActivityForResult(intent, (int) requestCode);
            } else
                throw new IllegalArgumentException("requestCode no support other type,only Integer");
        } else {
            startActivity(activity, cls, param);
        }
    }

    /**
     * @param fragment    fragment
     * @param cls         需要跳转的类
     * @param param       需要传递的参数，为空不传递参数
     * @param requestCode 请求码，用于目标界面返回回来的参数区分
     */
    public static void startActivity(@NonNull Fragment fragment, @NonNull Class<?> cls, @Nullable Object param, @Nullable Object requestCode) {
        if (requestCode != null) {
            if (requestCode instanceof Integer) {
                Intent intent = getIntent(cls.getSimpleName(), param);
                intent.setClass(Objects.requireNonNull(fragment.getActivity()), cls);
                fragment.startActivityForResult(intent, (int) requestCode);
            } else
                throw new IllegalArgumentException("requestCode no support other type,only Integer");
        } else {
            startActivity(fragment, cls, param);
        }
    }

    /**
     * 通过类所在包名启动Activity，并且含有Bundle数据和返回数据
     *
     * @param activity          activity
     * @param targetPackageName 需要跳转的类所在完整包名
     * @param bundle            数据
     */
    public static void startActivity(@NonNull Activity activity, @NonNull String targetPackageName, @Nullable Bundle bundle) throws ClassNotFoundException {
        Intent intent = getIntent(targetPackageName, bundle);
        intent.setClass(Objects.requireNonNull(activity), Class.forName(targetPackageName));
        activity.startActivity(intent);
    }

    /**
     * 通过类所在包名启动Activity，并且含有Bundle数据和返回数据
     *
     * @param fragment          fragment
     * @param targetPackageName 需要跳转的类所在完整包名
     * @param bundle            数据
     */
    public static void startActivity(@NonNull Fragment fragment, @NonNull String targetPackageName, @Nullable Bundle bundle) throws ClassNotFoundException {
        Intent intent = getIntent(targetPackageName, bundle);
        intent.setClass(Objects.requireNonNull(fragment.getActivity()), Class.forName(targetPackageName));
        fragment.startActivity(intent);
    }

    /**
     * 获得Fragment对象并传递参数
     *
     * @param params 要传递的参数
     * @param tClass 传递的目的Fragment的Class对象
     * @param <T>    传递的目的Fragment
     * @return
     */
    public static <T extends Fragment> T newInstance(@NonNull Class<T> tClass, @Nullable Object params) {
        T fragment = null;
        try {
            fragment = tClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        Bundle bundle = getBundle(tClass.getSimpleName(), params);
        assert fragment != null;
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * 获取Intent对象
     *
     * @param key   需要传递的参数Key
     * @param param 需要传递的参数，为空不传递参数
     * @return
     */
    private static Intent getIntent(@NonNull String key, @Nullable Object param) {
        Intent intent = new Intent();
        if (param != null) {
            if (param instanceof Integer) {
                intent.putExtra(key, (int) param);
            } else if (param instanceof Long) {
                intent.putExtra(key, (long) param);
            } else if (param instanceof Float) {
                intent.putExtra(key, (float) param);
            } else if (param instanceof Double) {
                intent.putExtra(key, (double) param);
            } else if (param instanceof String) {
                intent.putExtra(key, (String) param);
            } else if (param instanceof Bundle) {
                intent.putExtras((Bundle) param);
            } else if (param instanceof List) {
                List list = (List) param;
                intent.putExtra(key, (Serializable) list);
            } else if (param instanceof Map) {
                Map map = (Map) param;
                intent.putExtra(key, (Serializable) map);
            } else if (param instanceof Serializable) {
                intent.putExtra(key, (Serializable) param);
            } else if (param instanceof Parcelable) {
                intent.putExtra(key, (Parcelable) param);
            }
        }
        return intent;
    }

    /**
     * 获取Bundle对象
     *
     * @param key   需要传递的参数Key
     * @param param 需要传递的参数，为空不传递参数
     * @return
     */
    private static Bundle getBundle(@NonNull String key, @Nullable Object param) {
        Bundle bundle = new Bundle();
        if (param != null) {
            if (param instanceof Integer) {
                bundle.putInt(key, (int) param);
            } else if (param instanceof Long) {
                bundle.putLong(key, (long) param);
            } else if (param instanceof Float) {
                bundle.putFloat(key, (float) param);
            } else if (param instanceof Double) {
                bundle.putDouble(key, (double) param);
            } else if (param instanceof String) {
                bundle.putString(key, (String) param);
            } else if (param instanceof Bundle) {
                bundle = (Bundle) param;
            } else if (param instanceof List) {
                List list = (List) param;
                bundle.putSerializable(key, (Serializable) list);
            } else if (param instanceof Map) {
                Map map = (Map) param;
                bundle.putSerializable(key, (Serializable) map);
            } else if (param instanceof Serializable) {
                bundle.putSerializable(key, (Serializable) param);
            } else if (param instanceof Parcelable) {
                bundle.putParcelable(key, (Parcelable) param);
            }
        }
        return bundle;
    }

    /**
     * 获取上一个界面传递过来的参数
     *
     * @param activity      activity
     * @param cls           当前界面Class
     * @param defaultObject 默认值
     * @return
     */
    public static Object getParam(@NonNull Activity activity, @NonNull Class<?> cls, @Nullable Object defaultObject) {
        if (defaultObject instanceof Bundle)
            return activity.getIntent().getExtras();
        else {
            return getParam(activity.getIntent().getExtras(), cls.getSimpleName(), defaultObject);
        }
    }

    /**
     * 获取上一个界面传递过来的参数
     *
     * @param fragment      fragment
     * @param cls           当前界面Class
     * @param defaultObject 默认值
     * @return
     */
    public static Object getParam(@NonNull Fragment fragment, @NonNull Class<?> cls, @Nullable Object defaultObject) {
        if (defaultObject instanceof Bundle)
            return fragment.getArguments();
        else {
            return getParam(fragment.getArguments(), cls.getSimpleName(), defaultObject);
        }
    }

    /**
     * 获取参数
     *
     * @param bundle        bundle
     * @param key           对应的key
     * @param defaultObject 默认值
     * @return
     * @throws Exception
     */
    public static Object getParam(@Nullable Bundle bundle, @NonNull String key, @Nullable Object defaultObject) {
        try {
            if (bundle != null) {
                if (defaultObject == null)
                    return null;
                else {
                    if (defaultObject instanceof Integer) {
                        return bundle.getInt(key, (int) defaultObject);
                    } else if (defaultObject instanceof Long) {
                        return bundle.getLong(key, (long) defaultObject);
                    } else if (defaultObject instanceof Float) {
                        return bundle.getFloat(key, (float) defaultObject);
                    } else if (defaultObject instanceof Double) {
                        return bundle.getDouble(key, (double) defaultObject);
                    } else if (defaultObject instanceof String) {
                        return bundle.getString(key) != null ? bundle.getString(key) : (String) defaultObject;
                    } else if (defaultObject instanceof List) {
                        List list = (List) bundle.getSerializable(key);
                        return list != null ? list : (List) defaultObject;
                    } else if (defaultObject instanceof Map) {
                        Map map = (Map) bundle.getSerializable(key);
                        return map != null ? map : (Map) defaultObject;
                    } else if (defaultObject instanceof Serializable) {
                        return bundle.getSerializable(key);
                    } else if (defaultObject instanceof Parcelable) {
                        return bundle.getParcelable(key);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
