package com.zzy.common.model.jsonParser;

import com.zzy.common.model.bean.GetRichInfo;
import com.zzy.common.constants.HttpConstants;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.log.MyLog;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class GetRichInfoParser implements HInterface.JsonParser {
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
            GetRichInfo bean = new GetRichInfo();
            bean.setTitle(dataObj.getString("NEWS_TITLE"));
            bean.setContent(dataObj.getString("NEWS_CONTENT"));
            bean.setDate(dataObj.getString("RELEASE_DATE"));
            bean.setLike("是".equals(dataObj.getString("IS_LIKE")));
            bean.setLikeNum(dataObj.getString("TOTAL_LIKE"));
            bean.setLookNum(dataObj.getString("TOTAL_BROWSE"));
            bean.setPlaceTop("置顶".equals(dataObj.getString("IS_TOP")));

            return new Object[]{HConstant.SUCCESS,bean};
        } else {
            String msg = obj.getString(HttpConstants.ERROR_MESSAGE);
            return new Object[]{HConstant.ERROR, msg};
        }
    }
}