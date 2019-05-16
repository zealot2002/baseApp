package com.zzy.update.model.jsonParser;

import com.zzy.common.constants.HttpConstants;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.update.model.UpdateBean;

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

            if (object.has("version_url")) bean.setDownloadUrl(object.getString("version_url"));
            if (object.has("content")) bean.setChangeList(object.getString("content"));
            if (object.has("versioncode")) bean.setVersionName(object.getString("versioncode"));
            // 0强更
            if (object.has("is_update")) bean.setForce(object.getString("is_update").equals("0"));
            return new Object[]{HConstant.SUCCESS, bean};
        } else {
            String msg = obj.getString(HttpConstants.ERROR_MESSAGE);
            return new Object[]{HConstant.FAIL, msg};
        }
    }
}
