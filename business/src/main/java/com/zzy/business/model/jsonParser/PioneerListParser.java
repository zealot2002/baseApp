package com.zzy.business.model.jsonParser;

import com.zzy.business.model.bean.Pioneer;
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


public class PioneerListParser implements HInterface.JsonParser {
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
            List<Pioneer> dataList = new ArrayList<>();

            for(int i=0;i<infoArray.length();i++) {
                JSONObject infoObj = infoArray.getJSONObject(i);
                Pioneer bean = new Pioneer();
                bean.setId(infoObj.getInt("NEWS_INFORMATION_ID"));
                bean.setTitle(infoObj.getString("NEWS_TITLE"));
                bean.setDate(infoObj.getString("RELEASE_DATE"));
                bean.setLookNum(infoObj.getString("TOTAL_BROWSE"));
                dataList.add(bean);
            }
            return new Object[]{HConstant.SUCCESS,dataList};
        } else {
            String msg = obj.getString(HttpConstants.ERROR_MESSAGE);
            return new Object[]{HConstant.ERROR, msg};
        }
    }
}
