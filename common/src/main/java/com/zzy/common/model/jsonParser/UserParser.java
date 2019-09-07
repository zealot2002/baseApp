package com.zzy.common.model.jsonParser;

import com.zzy.common.constants.HttpConstants;
import com.zzy.common.model.bean.Archives;
import com.zzy.common.model.bean.User;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.log.MyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class UserParser implements HInterface.JsonParser {
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
            JSONObject dataObj = obj.getJSONObject("data");
            User bean = new User();
            if(dataObj.has("TOKEN")) bean.setToken(dataObj.getString("TOKEN"));
            if(dataObj.has("USERINFO_ID")) bean.setId(dataObj.getString("USERINFO_ID"));
            if(dataObj.has("USERNAME")) bean.setName(dataObj.getString("USERNAME"));
            if(dataObj.has("USER_JOB")) bean.setTitle(dataObj.getString("USER_JOB"));
            if(dataObj.has("HEAD_PIC_ADDR")) bean.setHeadUrl(dataObj.getString("HEAD_PIC_ADDR"));
            if(dataObj.has("USER_GARDE")) bean.setScore(dataObj.getString("USER_GARDE"));

            return new Object[]{HConstant.SUCCESS,bean};
        } else {
            String msg = obj.getString(HttpConstants.ERROR_MESSAGE);
            return new Object[]{HConstant.ERROR, msg};
        }
    }
}
