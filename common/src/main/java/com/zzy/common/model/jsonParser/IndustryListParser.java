package com.zzy.common.model.jsonParser;

import com.zzy.common.constants.HttpConstants;
import com.zzy.common.model.bean.Comment;
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


public class IndustryListParser implements HInterface.JsonParser {
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
            List<Industry> dataList = new ArrayList<>();

            for(int i=0;i<array.length();i++) {
                JSONObject infoObj = array.getJSONObject(i);
                Industry bean = new Industry();
                bean.setName(infoObj.getString("DESCRIPTION"));
                JSONArray itemArray = infoObj.getJSONArray("children");
                for(int m=0;m<itemArray.length();m++) {
                    JSONObject itemObj = itemArray.getJSONObject(m);
                    Industry.Item item = new Industry.Item();
                    item.setId(itemObj.getInt("ID"));
                    item.setName(itemObj.getString("DESCRIPTION"));
                    bean.getItemList().add(item);
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
