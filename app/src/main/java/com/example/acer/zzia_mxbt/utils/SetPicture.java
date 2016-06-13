package com.example.acer.zzia_mxbt.utils;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;

/**
 * Created by C5-0 on 2016/5/26.
 */
public class SetPicture {
    public static GenericDraweeHierarchy hierarchy(String path,Context mContext) {
        Uri uri = Uri.parse(path);
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(mContext.getResources());
        GenericDraweeHierarchy hierarchy = builder
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                .setRoundingParams(new RoundingParams().setRoundAsCircle(true))
                .build();
        return hierarchy;
    }

    public static DraweeController controller(Uri uri) {
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .build();
        return controller;
    }

}
