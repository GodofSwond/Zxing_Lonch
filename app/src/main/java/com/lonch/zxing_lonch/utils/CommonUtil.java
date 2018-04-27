package com.lonch.zxing_lonch.utils;

import android.hardware.Camera;

/**
 * Created by ${GodofSwond} on 2018/4/27.
 * 通用工具类
 */

public class CommonUtil {
    /**
     * @param
     * @return
     * @author miao
     * @createTime 2018/4/27
     * @lastModify 2018/4/27
     */
    public static boolean isCameraCanUse() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
        } catch (Exception e) {
            canUse = false;
        }
        if (canUse) {
            if (mCamera != null)
                mCamera.release();
            mCamera = null;
        }
        return canUse;
    }
}
