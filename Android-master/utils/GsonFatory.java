package com.megain.nfctemp.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.math.BigDecimal;

/**
 * 创建特定要求的Gson对象
 */
public class GsonFatory {

    public static Gson getUploadGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Float.class,
                        (JsonSerializer<Float>) (value, theType, context) -> {
                            if (value.isNaN()) {
                                return new JsonPrimitive(0); // Convert NaN to zero
                            } else if (value.isInfinite() || value < 0.01) {
                                return new JsonPrimitive(value); // Leave small numbers and infinite alone
                            } else {
                                // Keep 1 decimal digits only
                                return new JsonPrimitive((new BigDecimal(value)).setScale(1, BigDecimal.ROUND_HALF_UP));
                            }
                        })
                .registerTypeAdapter(Double.class,
                        (JsonSerializer<Double>) (value, theType, context) -> {
                            if (value.isNaN()) {
                                return new JsonPrimitive(0); // Convert NaN to zero
                            } else if (value.isInfinite() || value < 0.01) {
                                return new JsonPrimitive(value); // Leave small numbers and infinite alone
                            } else {
                                //上传数据中，double的经纬度数据保留9位精度
                                return new JsonPrimitive((new BigDecimal(value)).setScale(9, BigDecimal.ROUND_HALF_UP));
                            }
                        })
                .create();
    }

}
