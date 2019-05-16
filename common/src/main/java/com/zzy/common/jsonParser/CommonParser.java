package com.zzy.common.jsonParser;

import com.zzy.common.constants.HttpConstants;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * @author zzy
 * @date 2019/3/19
 */

public class CommonParser implements HInterface.JsonParser {
    @Override
    public Object[] parse(String s) throws JSONException {
        if (s == null) {
            throw new JSONException("server return null");
        }
        JSONTokener jsonParser = new JSONTokener(s);
        JSONObject obj = (JSONObject) jsonParser.nextValue();
        String errorCode = obj.getString(HttpConstants.ERROR_CODE);
        if (errorCode.equals(HttpConstants.NO_ERROR)) {
            JSONObject data = obj.getJSONObject("data");
            return new Object[]{HConstant.SUCCESS, data};
        } else {
            String msg = obj.getString(HttpConstants.ERROR_MESSAGE);
            return new Object[]{HConstant.FAIL, msg};
        }
    }
}
