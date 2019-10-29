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
            if(dataObj.has("CONNECT_PERSON")) bean.setContact(dataObj.getString("CONNECT_PERSON"));
            if(dataObj.has("USERNAME")) bean.setContact(dataObj.getString("USERNAME"));
            if(dataObj.has("MOBILE_NO")) bean.setPhone(dataObj.getString("MOBILE_NO"));
            if(dataObj.has("RELEASE_TEXT")) bean.setContent(dataObj.getString("RELEASE_TEXT"));
            if(dataObj.has("HEAD_PIC_ADDR")) bean.setHeadUrl(HttpConstants.SERVER_ADDRESS+"/"+dataObj.getString("HEAD_PIC_ADDR"));
            if(dataObj.has("RECRUITMENT_STATE")) bean.setState(dataObj.getString("RECRUITMENT_STATE"));


            if(dataObj.has("SKILL_ARR")){
                JSONArray array = dataObj.getJSONArray("SKILL_ARR");
                for(int i=0;i<array.length();i++) {
                    JSONObject infoObj = array.getJSONObject(i);
                    bean.getSkills().add(infoObj.getString("USER_SKILL"));
                }
            }else if(dataObj.has("USER_SKILL")){
                JSONArray array = dataObj.getJSONArray("USER_SKILL");
                for(int i=0;i<array.length();i++) {
                    JSONObject infoObj = array.getJSONObject(i);
                    bean.getSkills().add(infoObj.getString("USER_SKILL"));
                }
            }else if(dataObj.has("USER_SKILLARR")){
                JSONArray array = dataObj.getJSONArray("USER_SKILLARR");
                for(int i=0;i<array.length();i++) {
                    JSONObject infoObj = array.getJSONObject(i);
                    bean.getSkills().add(infoObj.getString("USER_SKILL"));
                }
            }
            return new Object[]{HConstant.SUCCESS,bean};
        } else {
            String msg = obj.getString(HttpConstants.ERROR_MESSAGE);
            return new Object[]{HConstant.ERROR, msg};
        }
    }
}
