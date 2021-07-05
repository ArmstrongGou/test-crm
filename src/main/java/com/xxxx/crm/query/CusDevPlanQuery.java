package com.xxxx.crm.query;

import com.xxxx.crm.base.BaseQuery;

public class CusDevPlanQuery extends BaseQuery {
    //根据营销机会 id  查询
    private Integer sid;

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public CusDevPlanQuery() {

    }
}
