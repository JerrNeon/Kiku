package com.jn.kiku.ttp.map.callback;

import com.baidu.mapapi.search.route.BikingRouteLine;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteLine;

import java.util.List;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (路线搜索结果监听)
 * @create by: chenwei
 * @date 2017/3/13 14:30
 */
public interface RouteSearchResultListener {

    enum Type {
        DRIVING, TRANSIT, BIKING, WALKING
    }

    void onDrivingSuccess(List<DrivingRouteLine> rLines);

    void onTransitSuccess(List<TransitRouteLine> rLines);

    void onBikingSuccess(List<BikingRouteLine> rLines);

    void onWalkingSuccess(List<WalkingRouteLine> rLines);

    void onFailure(Type type);
}
