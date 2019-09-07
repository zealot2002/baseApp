package com.zzy.common.model.jsonParser;

import com.zzy.common.constants.HttpConstants;
import com.zzy.common.model.bean.Comment;
import com.zzy.common.model.bean.Content;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.log.MyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;


public class CommentListParser implements HInterface.JsonParser {
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
            JSONArray infoArray = obj.getJSONArray("data");
            List<Comment> dataList = new ArrayList<>();

            for(int i=0;i<infoArray.length();i++) {
                JSONObject infoObj = infoArray.getJSONObject(i);
                Comment bean = new Comment();
                bean.setId(infoObj.getString("COMMENT_ID"));
                bean.setDate(infoObj.getString("COMMENT_RELEASE_TIME"));
                bean.setType(infoObj.getString("COMMENT_TYPE"));
                bean.setUserName(infoObj.getString("COMMENT_PERSON"));
                bean.setContentId(infoObj.getString("ORIGINAL_ARTICLE_ID"));
                bean.setGoodsId(infoObj.getString("SALE_ID"));
                dataList.add(bean);
            }
            return new Object[]{HConstant.SUCCESS,dataList};
        } else {
            String msg = obj.getString(HttpConstants.ERROR_MESSAGE);
            return new Object[]{HConstant.ERROR, msg};
        }
    }
}
