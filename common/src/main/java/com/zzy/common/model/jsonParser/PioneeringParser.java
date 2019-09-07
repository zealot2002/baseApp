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


public class PioneeringParser implements HInterface.JsonParser {
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
            Pioneering bean = new Pioneering();
            bean.setContact(dataObj.getString("CONNECT_PERSON"));
            bean.setPhone(dataObj.getString("MOBILE_NO"));
            bean.setContent(dataObj.getString("RELEASE_TEXT"));
            bean.setHeadUrl(dataObj.getString("HEAD_PIC_ADDR"));

            JSONArray array = dataObj.getJSONArray("USER_SKILL");
            for(int i=0;i<array.length();i++) {
                JSONObject infoObj = array.getJSONObject(i);
                bean.getSkills().add(infoObj.getString("USER_SKILL"));
            }
            return new Object[]{HConstant.SUCCESS,bean};
        } else {
            String msg = obj.getString(HttpConstants.ERROR_MESSAGE);
            return new Object[]{HConstant.ERROR, msg};
        }
    }
}
