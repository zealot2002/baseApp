package com.zzy.home.model.jsonParser.main;

import com.zzy.common.constants.HttpConstants;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.home.model.bean.main.NoticeBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

/**
 * zzy
 */
public class NoticeListParser implements HInterface.JsonParser {
    @Override
    public Object[] parse(String s) throws JSONException {
        if(s==null){
            throw new JSONException("server return null");
        }
        JSONTokener jsonParser = new JSONTokener(s);
        JSONObject obj = (JSONObject) jsonParser.nextValue();
        String errorCode = obj.getString(HttpConstants.ERROR_CODE);
        if (errorCode.equals(HttpConstants.NO_ERROR)) {
            JSONObject data = obj.getJSONObject("data");
            List<NoticeBean> list = new ArrayList<>();
            JSONArray array = data.getJSONArray("rows");
            for(int i=0;i<array.length();i++) {
                JSONObject object = array.getJSONObject(i);
                NoticeBean bean = new NoticeBean();
                if (object.has("title"))  bean.setTitle(object.getString("title"));
                if (object.has("content")) bean.setContent(object.getString("content"));

                if (object.has("time"))  bean.setTime(object.getString("time"));
                if (object.has("title_style")){
                    if (object.has("fontsize"))  bean.setTitleTextSize(object.getInt("fontsize"));
                    if (object.has("color"))  bean.setTitleTextColor(object.getString("color"));
                }
                list.add(bean);
            }
            return new Object[]{HConstant.SUCCESS,list};
        } else {
            String msg = obj.getString(HttpConstants.ERROR_MESSAGE);
            return new Object[]{HConstant.ERROR, msg};
        }
    }
}
