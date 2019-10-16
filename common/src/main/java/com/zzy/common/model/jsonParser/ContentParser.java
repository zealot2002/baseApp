package com.zzy.common.model.jsonParser;

import com.zzy.common.model.bean.Comment;
import com.zzy.common.model.bean.Content;
import com.zzy.common.model.bean.Image;
import com.zzy.common.constants.HttpConstants;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.log.MyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class ContentParser implements HInterface.JsonParser {
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
            Content bean = new Content();
            bean.setTitle(dataObj.getString("FORUM_NAME"));
            bean.setDate(dataObj.getString("RELEASE_DATE"));
            bean.setLookNum(dataObj.getString("TOTAL_BROWSE"));
            bean.setContent(dataObj.getString("RELEASE_TEXT"));

            bean.setLike(dataObj.getString("IS_LIKE").equals("是"));
            bean.setLikeNum(dataObj.getString("LIKE_TOTAL"));
            bean.setUserId(dataObj.getInt("RELEASE_PERSON_ID"));

            if(dataObj.has("RELEASE_PERSON"))  bean.setUserName(dataObj.getString("RELEASE_PERSON"));
            if(dataObj.has("HEAD_PIC_ADDR"))  bean.setUserHeadUrl(HttpConstants.SERVER_ADDRESS+"/"+dataObj.getString("HEAD_PIC_ADDR"));
            if(dataObj.has("RELEASE_LOCATION"))  bean.setLocation(dataObj.getString("RELEASE_LOCATION"));

            JSONArray array = dataObj.getJSONArray("IMAGES");
            for(int i=0;i<array.length();i++) {
                JSONObject imgObj = array.getJSONObject(i);
                Image image = new Image();
                image.setName("");
                image.setPath(HttpConstants.SERVER_ADDRESS+"/"+imgObj.getString("PIC_ADDR"));
                bean.getImgList().add(image);
            }
            JSONArray comArr = dataObj.getJSONArray("COMMENT");
            for(int i=0;i<comArr.length();i++) {
                JSONObject comObj = comArr.getJSONObject(i);
                Comment comment = new Comment();
                comment.setId(comObj.getString("COMMENT_ID"));
                comment.setUserName(comObj.getString("COMMENT_PERSON"));
                comment.setUserId(comObj.getInt("COMMENT_PERSON_ID"));
                comment.setDate(comObj.getString("COMMENT_RELEASE_TIME"));
                comment.setContent(comObj.getString("COMMENT_TEXT"));
                comment.setUserHeadUrl(HttpConstants.SERVER_ADDRESS+"/"+comObj.getString("HEAD_PIC_ADDR"));
                bean.getCommentList().add(comment);
            }
            return new Object[]{HConstant.SUCCESS,bean};
        } else {
            String msg = obj.getString(HttpConstants.ERROR_MESSAGE);
            return new Object[]{HConstant.ERROR, msg};
        }
    }
}
