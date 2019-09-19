package com.zzy.common.model.jsonParser;

import com.zzy.common.constants.HttpConstants;
import com.zzy.common.model.bean.HelpClass;
import com.zzy.common.model.bean.Pioneer;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.log.MyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class PioneerHelpParser implements HInterface.JsonParser {
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
            HelpClass bean = new HelpClass();
            bean.setTitle(dataObj.getString("PROJECT_TITLE"));
            bean.setContent(dataObj.getString("PROJECT_CLASS"));
            bean.setTime(dataObj.getString("START_TIME"));
            bean.setParterNum(dataObj.getInt("JOIN_PEOPLE_NUM"));
            bean.setIsJoined(dataObj.getString("IS_SIGNUP"));
            bean.setAddress(dataObj.getString("PROJECT_ADDR"));
            bean.setTeacher(dataObj.getString("USER_INTRODUCTION"));

            JSONArray array = dataObj.getJSONArray("HELP_JOIN");
            for(int i=0;i<array.length();i++){
                JSONObject po = array.getJSONObject(i);
                HelpClass.Parter parter = new HelpClass.Parter(
                        HttpConstants.SERVER_ADDRESS+"/"+po.getString("HEAD_PIC_ADDR"),
                        po.getString("USERINFO_ID"));
                bean.getParterList().add(parter);
            }
            return new Object[]{HConstant.SUCCESS,bean};
        } else {
            String msg = obj.getString(HttpConstants.ERROR_MESSAGE);
            return new Object[]{HConstant.ERROR, msg};
        }
    }
}
