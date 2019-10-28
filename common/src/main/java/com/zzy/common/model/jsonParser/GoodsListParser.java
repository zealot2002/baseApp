package com.zzy.common.model.jsonParser;

import com.zzy.common.model.bean.Goods;
import com.zzy.common.model.bean.Image;
import com.zzy.common.constants.HttpConstants;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.log.MyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;


public class GoodsListParser implements HInterface.JsonParser {
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
            List<Goods> dataList = new ArrayList<>();

            for(int i=0;i<infoArray.length();i++) {
                JSONObject infoObj = infoArray.getJSONObject(i);
                Goods bean = new Goods();
                bean.setId(infoObj.getInt("SALE_ID"));
                bean.setName(infoObj.getString("SALE_TITLE"));
                bean.setContact(infoObj.getString("RELEASE_PEOPLE"));
                bean.setPhone(infoObj.getString("SALE_TEL"));

                String type = "买";
                if(infoObj.has("SALE_TYPE")) type = infoObj.getString("SALE_TYPE");
                if(type.equals("买")){
                    if(infoObj.has("SALE_PRICE")) bean.setStartPrice(infoObj.getString("SALE_PRICE"));
                    if(infoObj.has("SALE_PRICE_UP")) bean.setEndPrice(infoObj.getString("SALE_PRICE_UP"));
                }else {
                    if(infoObj.has("SALE_PRICE")) bean.setPrice(infoObj.getString("SALE_PRICE"));
                }

                if(infoObj.has("SALE_GRADE")){
                    try{
                        bean.setScore(Float.valueOf(infoObj.getString("SALE_GRADE")));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                bean.getImgList().add(new Image(HttpConstants.SERVER_ADDRESS+"/"+infoObj.getString("PIC_ADDR")));
                dataList.add(bean);
            }
            return new Object[]{HConstant.SUCCESS,dataList};
        } else {
            String msg = obj.getString(HttpConstants.ERROR_MESSAGE);
            return new Object[]{HConstant.ERROR, msg};
        }
    }
}
