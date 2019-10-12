package com.zzy.common.model.jsonParser;

import com.zzy.common.constants.HttpConstants;
import com.zzy.common.model.bean.Archives;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.log.MyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class ArchivesParser implements HInterface.JsonParser {
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
            Archives bean = new Archives();
            bean.setName(dataObj.getString("USERNAME"));
            bean.setPhone(dataObj.getString("MOBILE_NO"));
            bean.setArea1(dataObj.getString("COUNTY"));
            bean.setArea2(dataObj.getString("TOWN"));
            bean.setArea3(dataObj.getString("VILLAGE"));
            bean.setAddress(dataObj.getString("ADDR_DETAIL"));
            bean.setBirthday(dataObj.getString("BIRTHDAY"));
            bean.setSex(dataObj.getString("SEX"));
            bean.setIdNo(dataObj.getString("IDCARD"));
            bean.setUserType(dataObj.getString("USER_TYPE"));
            bean.setIsCompany(dataObj.getString("IS_COMPANY"));
            bean.setCompanyName(dataObj.getString("COMPANY_NAME"));
            bean.setCompanyScope(dataObj.getString("COMPANY_EMP_NUM"));

            if(dataObj.has("USER_SKILL")){
                JSONArray imgArray = dataObj.getJSONArray("USER_SKILL");
                for(int i=0;i<imgArray.length();i++) {
                    JSONObject infoObj = imgArray.getJSONObject(i);
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
