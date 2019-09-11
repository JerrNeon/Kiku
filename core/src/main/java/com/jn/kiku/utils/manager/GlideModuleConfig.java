package com.jn.kiku.utils.manager;

import android.app.ActivityManager;
import android.content.Context;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;
import com.jn.kiku.utils.AppUtils;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (Glide配置)
 * @create by: chenwei
 * @date 2016/10/9 10:14
 */
@GlideModule
public class GlideModuleConfig extends AppGlideModule {

    private int diskSize = 1024 * 1024 * 100;//100M
    private int memorySize = (int) (Runtime.getRuntime().maxMemory()) / 8;  // 取1/8最大内存作为最大缓存

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // 定义缓存大小和位置
        //builder.setDiskCache(new InternalCacheDiskCacheFactory(context, diskSize));  //内存中
        //builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "cache", diskSize)); //sd卡中

        // 默认内存和图片池大小
        //MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        //int defaultMemoryCacheSize = calculator.getMemoryCacheSize(); // 默认内存大小
        //int defaultBitmapPoolSize = calculator.getBitmapPoolSize(); // 默认图片池大小
        //builder.setMemoryCache(new LruResourceCache(defaultMemoryCacheSize)); // 该两句无需设置，是默认的
        //builder.setBitmapPool(new LruBitmapPool(defaultBitmapPoolSize));

        // 自定义内存和图片池大小
        //builder.setMemoryCache(new LruResourceCache(memorySize));
        //builder.setBitmapPool(new LruBitmapPool(memorySize));

        // 定义图片格式
        //builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);//设置ARGB_8888是为了防止小屏手机过渡压缩导致图片底色出现浅绿色的问题
        //builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565); // 默认
        ActivityManager.MemoryInfo memoryInfo = AppUtils.getMemoryInfo(context);
        if (null != memoryInfo) {
            //builder.setDecodeFormat(memoryInfo.lowMemory ? DecodeFormat.PREFER_RGB_565 : DecodeFormat.PREFER_ARGB_8888);
        }
        //默认大小是250M,缓存文件放在APP的缓存文件夹下。
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, diskSize));
        builder.setMemoryCache(new LruResourceCache(memorySize));
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
    }

}
