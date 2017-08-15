/**
 *文件名：ListUtil.java
 *版权：南京安元科技有限公司
 *描述：
 *修改人：lijie
 *修改时间：2013-1-21
 *修改内容：
 */
package com.ay.framework.util.collenction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;

/**
 * 一句话功能简述
 * 功能详细描述	
 * @author lijie
 * @version 2013-1-21
 * @see 
 * @since
 * @version V1.0
 */
public class ListUtil
{
    
    /**
    * 对象数组转List集合
    * lijie  2013-1-21
    * @param @param array
    * @param @return    
    * @return List<Object>   
    * @throws
     */
    public static List<Object> fromArray(Object[] array) {
        if ((array == null) || (array.length == 0)) {
            return new ArrayList<Object>();
        }

        List<Object> list = new ArrayList<Object>(array.length);
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }
        return list;
    }
    
}
