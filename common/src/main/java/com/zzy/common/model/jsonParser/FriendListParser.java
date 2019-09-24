package com.zzy.common.model.jsonParser;

import android.net.Uri;

import com.zzy.common.constants.HttpConstants;
import com.zzy.common.model.bean.FriendsCircle;
import com.zzy.common.model.bean.Industry;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.log.MyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;


public class FriendListParser implements HInterface.JsonParser {
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
            JSONArray array = obj.getJSONArray("data");
            List<FriendsCircle> dataList = new ArrayList<>();

            for(int i=0;i<array.length();i++) {
                JSONObject infoObj = array.getJSONObject(i);
                FriendsCircle bean = new FriendsCircle();
                bean.setCreateTime(infoObj.getString("RELEASE_DATE"));
                bean.setLookNum(infoObj.getString("TOTAL_BROWSE"));
                bean.setContent(infoObj.getString("FORUM_NAME"));
                bean.setAddress(infoObj.getString("RELEASE_LOCATION"));
                bean.setAvatar(HttpConstants.SERVER_ADDRESS+"/"+infoObj.getString("HEAD_PIC_ADDR"));
                bean.setOwner(infoObj.getString("RELEASE_PERSON"));
                bean.setLikeNum(infoObj.getString("LIKE_TOTAL"));
                bean.setId(infoObj.getString("FORUM_ID"));

                JSONArray itemArray = infoObj.getJSONArray("IMAGES");
                for(int m=0;m<itemArray.length();m++) {
                    JSONObject itemObj = itemArray.getJSONObject(m);
                    Uri uri = Uri.parse(HttpConstants.SERVER_ADDRESS+"/"+itemObj.getString("PIC_ADDR"));
                    bean.getPictureList().add(uri);
                    bean.getPictureThumbList().add(uri);
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
