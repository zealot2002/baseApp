package com.zzy.common.model.jsonParser;

import com.zzy.common.constants.CommonConstants;
import com.zzy.common.constants.HttpConstants;
import com.zzy.common.model.bean.Pioneer;
import com.zzy.common.model.bean.PioneerService;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.log.MyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;


public class PioneerServiceListParser implements HInterface.JsonParser {
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
            JSONArray dataArray = obj.getJSONArray("data");
            List<PioneerService> dataList = new ArrayList<>();
            for(int i=0;i<dataArray.length();i++){
                JSONObject pO = dataArray.getJSONObject(i);
                PioneerService bean = new PioneerService();

                if(pO.has("HELP_INFO_ID")){
                    bean.setId(pO.getInt("HELP_INFO_ID")+"");
                    bean.setType(CommonConstants.PIONEER_SERVICE_HELP);
                    if(pO.has("PROJECT_TITLE")) bean.setTv1(pO.getString("PROJECT_TITLE"));
                    if(pO.has("PORJECT_SPONSOR")) bean.setTv3(pO.getString("PORJECT_SPONSOR"));
                    if(pO.has("RELEASE_DATE")) bean.setTv4(pO.getString("RELEASE_DATE"));
                }
                if(pO.has("NEWS_INFORMATION_ID")){
                    bean.setId(pO.getInt("NEWS_INFORMATION_ID")+"");
                    bean.setType(CommonConstants.PIONEER_SERVICE_ZCHB);
                    if(pO.has("NEWS_TITLE")) bean.setTv1(pO.getString("NEWS_TITLE"));
                    if(pO.has("RELEASE_DEPT")) bean.setTv3(pO.getString("RELEASE_DEPT"));
                    if(pO.has("RELEASE_DATE")) bean.setTv4(pO.getString("RELEASE_DATE"));
                }
                if(pO.has("USERINFO_ID")){
                    bean.setId(pO.getInt("USERINFO_ID")+"");
                    bean.setType(CommonConstants.PIONEER_SERVICE_EXPERT);
                    if(pO.has("USERNAME")) bean.setTv1(pO.getString("USERNAME"));
                    if(pO.has("USER_DATE")) bean.setTv2(pO.getString("USER_DATE"));
                }
                if(pO.has("RECRUITMENT_ID")){
                    bean.setId(pO.getString("RECRUITMENT_ID"));
                    bean.setType(CommonConstants.PIONEER_SERVICE_PERSON);
                    if(pO.has("RELEASE_PERSON")) bean.setTv1(pO.getString("RELEASE_PERSON"));
                    if(pO.has("RELEASE_TIME")) bean.setTv2(pO.getString("RELEASE_TIME"));
                }
                if(pO.has("FORUM_ID")){
                    bean.setId(pO.getInt("FORUM_ID")+"");
                    bean.setType(CommonConstants.PIONEER_SERVICE_PIONEER_HELP);
                    if(pO.has("FORUM_NAME")) bean.setTv1(pO.getString("FORUM_NAME"));
                    if(pO.has("RELEASE_PERSON")) bean.setTv3(pO.getString("RELEASE_PERSON"));
                    if(pO.has("RELEASE_DATE")) bean.setTv4(pO.getString("RELEASE_DATE"));
                }

                dataList.add(bean);
            }
            return new Object[]{HConstant.SUCCESS,dataList};
        } else {
            String msg = obj.getString(HttpConstants.ERROR_MESSAGE);
            return new Object[]{HConstant.ERROR, msg};
        }
    }
}
