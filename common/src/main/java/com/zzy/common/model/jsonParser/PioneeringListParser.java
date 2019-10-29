package com.zzy.common.model.jsonParser;

import com.zzy.common.model.bean.Pioneering;
import com.zzy.common.constants.HttpConstants;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.log.MyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;


public class PioneeringListParser implements HInterface.JsonParser {
    @Override
    public Object[] parse(String s) throws JSONException {
        MyLog.e("服务返回:"+s);
        if(s==null){
            throw new JSONException("server return null");
        }
        JSONTokener jsonParser = new JSONTokener(s);
        JSONObject obj = (JSONObject) jsonParser.nextValue();
        int errorCode = obj.getInt(HttpConstants.ERROR_CODE);
        if (errorCode == HttpConstants.NO_ERROR) {
            JSONArray infoArray = obj.getJSONArray("data");
            List<Pioneering> dataList = new ArrayList<>();

            for(int i=0;i<infoArray.length();i++) {
                JSONObject infoObj = infoArray.getJSONObject(i);
                Pioneering bean = new Pioneering();
                bean.setId(infoObj.getInt("RECRUITMENT_ID"));
                bean.setTitle(infoObj.getString("RELEASE_PERSON")+"的求职信息");
                bean.setDate(infoObj.getString("RELEASE_TIME"));
                dataList.add(bean);
            }
            return new Object[]{HConstant.SUCCESS,dataList};
        } else {
            String msg = obj.getString(HttpConstants.ERROR_MESSAGE);
            return new Object[]{HConstant.ERROR, msg};
        }
    }
}
