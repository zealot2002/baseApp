package com.zzy.business.model.jsonParser;

import com.zzy.business.model.bean.Comment;
import com.zzy.business.model.bean.Goods;
import com.zzy.business.model.bean.Image;
import com.zzy.common.constants.HttpConstants;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.log.MyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class GoodsParser implements HInterface.JsonParser {
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
            Goods bean = new Goods();
            bean.setName(dataObj.getString("SALE_TITLE"));
            bean.setContact(dataObj.getString("RELEASE_PEOPLE"));
            bean.setPhone(dataObj.getString("SALE_TEL"));
            bean.setPrice(dataObj.getString("SALE_PRICE"));
            bean.setDesc(dataObj.getString("SALE_CONTENT"));

            JSONArray imgArray = dataObj.getJSONArray("IMAGES");
            for(int i=0;i<imgArray.length();i++) {
                JSONObject infoObj = imgArray.getJSONObject(i);
                bean.getImgList().add(new Image(infoObj.getString("PIC_ADDR")));
            }

            JSONArray commentArray = dataObj.getJSONArray("COMMENT");
            for(int i=0;i<commentArray.length();i++) {
                JSONObject infoObj = commentArray.getJSONObject(i);
                Comment comment = new Comment();
                comment.setContent(infoObj.getString("COMMENT_TEXT"));
                comment.setUser(infoObj.getString("COMMENT_PERSON"));
                comment.setHeadUrl(infoObj.getString("HEAD_PIC_ADDR"));
                comment.setReviewContent(infoObj.getString("REVIEW_TEXT"));

                bean.getCommentList().add(comment);
            }
            return new Object[]{HConstant.SUCCESS,bean};
        } else {
            String msg = obj.getString(HttpConstants.ERROR_MESSAGE);
            return new Object[]{HConstant.ERROR, msg};
        }
    }
}
