package com.zzy.common.model.jsonParser;

import com.zzy.common.model.bean.Job;
import com.zzy.common.constants.HttpConstants;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.log.MyLog;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class JobParser implements HInterface.JsonParser {
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
            Job bean = new Job();
            if(dataObj.has("RECRUITMENT_ID")) bean.setId(dataObj.getInt("RECRUITMENT_ID"));
            if(dataObj.has("COMPANY_NAME")) bean.setCompanyName(dataObj.getString("COMPANY_NAME"));
            bean.setJobName(dataObj.getString("JOB_NAME"));
            bean.setAddress(dataObj.getString("WORK_LOCATION"));
            bean.setHeadcount(dataObj.getString("RECRUITMENT_PERSON_NUM"));
            bean.setSalaryMin(dataObj.getString("SALARY_MIN"));
            bean.setSalaryMax(dataObj.getString("SALARY_MAX"));
            bean.setPhone(dataObj.getString("MOBILE_NO"));

            bean.setContact(dataObj.getString("CONNECT_PERSON"));
            bean.setEducation(dataObj.getString("EDUCATION"));

            bean.setJobContent(dataObj.getString("JOB_REQUIREMENT"));
            bean.setJobRequirements(dataObj.getString("PERSON_REQUIREMENT"));
            bean.setState(dataObj.getString("RECRUITMENT_STATE"));


            return new Object[]{HConstant.SUCCESS,bean};
        } else {
            String msg = obj.getString(HttpConstants.ERROR_MESSAGE);
            return new Object[]{HConstant.ERROR, msg};
        }
    }
}
