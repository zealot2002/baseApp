package com.zzy.common.model.jsonParser;

import com.zzy.common.model.bean.Content;
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


public class ContentListParser implements HInterface.JsonParser {
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
            List<Content> dataList = new ArrayList<>();

            for(int i=0;i<infoArray.length();i++) {
                JSONObject infoObj = infoArray.getJSONObject(i);
                Content bean = new Content();
                bean.setId(infoObj.getInt("FORUM_ID"));
                bean.setFrom(infoObj.getString("RELEASE_PERSON"));
                bean.setTitle(infoObj.getString("FORUM_NAME"));
                bean.setDate(infoObj.getString("RELEASE_DATE"));
                bean.setLookNum(infoObj.getString("TOTAL_BROWSE"));
                bean.setReplyNum(infoObj.getInt("TOTAL_REPLY")+"");
                dataList.add(bean);
            }
            return new Object[]{HConstant.SUCCESS,dataList};
        } else {
            String msg = obj.getString(HttpConstants.ERROR_MESSAGE);
            return new Object[]{HConstant.ERROR, msg};
        }
    }
}
