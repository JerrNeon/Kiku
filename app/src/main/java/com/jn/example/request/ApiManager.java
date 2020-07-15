package com.jn.example.request;

import com.jn.example.BuildConfig;
import com.jn.kiku.net.retrofit.manager.IRetrofitManage;
import com.jn.kiku.net.retrofit.manager.RetrofitRequestManager;

import retrofit2.Retrofit;

/**
 * Author：Stevie.Chen Time：2019/7/9
 * Class Comment：ApiManager
 */
public class ApiManager {

    private Api mApi;

    private static class INSTANCE {
        private static final ApiManager apiManager = new ApiManager();
    }

    private ApiManager() {
    }

    public static ApiManager getInstance() {
        return INSTANCE.apiManager;
    }

    public Api getApiService() {
        if (mApi == null) {
            IRetrofitManage manage = new RetrofitRequestManager(BuildConfig.BASE_URL);
            Retrofit retrofit = manage.createRetrofit();
            if (retrofit != null)
                mApi = retrofit.create(Api.class);
        }
        return mApi;
    }
}
