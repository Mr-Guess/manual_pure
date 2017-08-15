package com.ay.framework.core.dao.pagination;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * 
 * 
 * @author lushigai
 * @version 1.0
 * @Date:2012-9-10
 */
@SuppressWarnings("all")
public class ListPage extends Page
{
	@Override
    public void setCollection(Collection collection)
    {
        if (collection != null)
        {
            this.setCount(collection.size());
        }
        super.setCollection(collection);
    }

    @Override
    public Collection getOnePage()
    {
        // 从新创建一个容器盛放分页
        Collection c = new ArrayList();
        int i = 1;
        for (Iterator iter = getCollection().iterator(); iter.hasNext();)
        {
            Object o = iter.next();
            if (i > this.getFrom() && i <= this.getTo())
            {
                c.add(o);
            }
            i++;
        }
        return c;
    }
}
