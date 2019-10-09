package com.zzy.common.model.jsonParser;

import com.zzy.common.constants.HttpConstants;
import com.zzy.common.model.bean.UpdateBean;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * zzy
 */
public class CheckUpdateParser implements HInterface.JsonParser {
    @Override
    public Object[] parse(String s) throws JSONException {
        if (s == null) {
            throw new JSONException("server return null");
        }
        JSONTokener jsonParser = new JSONTokener(s);
        JSONObject obj = (JSONObject) jsonParser.nextValue();
        String errorCode = obj.getString(HttpConstants.ERROR_CODE);
        if (errorCode.equals(HttpConstants.NO_ERROR)) {
            JSONObject object = obj.getJSONObject("data");
            UpdateBean bean = new UpdateBean();

            if (object.has("APP_PATH")) bean.setDownloadUrl(HttpConstants.SERVER_ADDRESS+"/"+object.getString("APP_PATH"));
            if (object.has("APP_VERSION_NAME")) bean.setVersionName(object.getString("APP_VERSION_NAME"));
            if (object.has("APP_UPDATE_LOG")) bean.setChangeList(object.getString("APP_UPDATE_LOG"));
//            if (object.has("force")) bean.setForce(object.getBoolean("force"));
            return new Object[]{HConstant.SUCCESS, bean};
        } else {
            String msg = obj.getString(HttpConstants.ERROR_MESSAGE);
            return new Object[]{HConstant.FAIL, msg};
        }
    }
}
