package com.zzy.home.model.jsonParser.main;

import com.zzy.common.constants.HttpConstants;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.log.MyLog;
import com.zzy.home.model.bean.main.BannerBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

/**
 * zzy
 */
public class BannerListParser implements HInterface.JsonParser {
    @Override
    public Object[] parse(String s) throws JSONException {
        MyLog.e("服务返回:"+s);
        if(s==null){
            throw new JSONException("server return null");
        }
        JSONTokener jsonParser = new JSONTokener(s);
        JSONObject obj = (JSONObject) jsonParser.nextValue();
        String errorCode = obj.getString(HttpConstants.ERROR_CODE);
        if (errorCode.equals(HttpConstants.NO_ERROR)) {
            List<BannerBean> list = new ArrayList<>();
            JSONArray array = obj.getJSONArray("data");
            for(int i=0;i<array.length();i++) {
                JSONObject object = array.getJSONObject(i);
                BannerBean bean = new BannerBean();
                if (object.has("adv_img_link"))  bean.setImgUrl(object.getString("adv_img_link"));
                if (object.has("app_adv_link"))  bean.setLinkUrl(object.getString("app_adv_link"));
                list.add(bean);
            }
            return new Object[]{HConstant.SUCCESS,list};
        } else {
            String msg = obj.getString(HttpConstants.ERROR_MESSAGE);
            return new Object[]{HConstant.FAIL, msg};
        }
    }
}
