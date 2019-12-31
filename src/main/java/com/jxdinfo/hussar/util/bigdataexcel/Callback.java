package com.jxdinfo.hussar.util.bigdataexcel;

import java.util.Map;

/**
 * @author wenqingkuan
 * @date 2019-10-21 11:25
 */
public interface Callback {
    void callback(Map<String,String> result, int currentRowNumber, int availabledRows);
}
