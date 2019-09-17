package com.zzy.common.model.jsonParser;

import com.zzy.common.constants.HttpConstants;
import com.zzy.common.model.bean.Expert;
import com.zzy.common.model.bean.User;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.log.MyLog;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class ExpertParser implements HInterface.JsonParser {
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
            Expert bean = new Expert();
            if(dataObj.has("USER_INTRODUCTION")) bean.setIntroduction(dataObj.getString("USER_INTRODUCTION"));
            if(dataObj.has("MOBILE_NO")) bean.setPhone(dataObj.getString("MOBILE_NO"));
            if(dataObj.has("USERNAME")) bean.setName(dataObj.getString("USERNAME"));

            return new Object[]{HConstant.SUCCESS,bean};
        } else {
            String msg = obj.getString(HttpConstants.ERROR_MESSAGE);
            return new Object[]{HConstant.ERROR, msg};
        }
    }
}
