package com.zzy.login.model.jsonParser;

import com.zzy.commonlib.log.MyLog;
import com.zzy.login.model.AreaTreeNode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class AreasParser {
    public static AreaTreeNode parse(String s) throws JSONException {
        MyLog.e("服务返回:"+s);
        if(s==null){
            throw new JSONException("server return null");
        }
        AreaTreeNode areaRoot = new AreaTreeNode();

        JSONTokener jsonParser = new JSONTokener(s);
        JSONObject obj = (JSONObject) jsonParser.nextValue();
        JSONArray area1List = obj.getJSONArray("areaList");
        for(int i=0;i<area1List.length();i++) {
            JSONObject area1Obj = area1List.getJSONObject(i);
            AreaTreeNode area1Node = new AreaTreeNode(area1Obj.getString("name"),
                    area1Obj.getInt("code"), areaRoot);
            JSONArray children1 = area1Obj.getJSONArray("children");

            for(int m=0;m<children1.length();m++) {
                JSONObject area2Obj = children1.getJSONObject(m);
                AreaTreeNode area2Node = new AreaTreeNode(area2Obj.getString("name"),
                        area2Obj.getInt("code"), area1Node);

                JSONArray children2 = area2Obj.getJSONArray("children");

                for(int n=0;n<children2.length();n++) {
                    JSONObject area3Obj = children2.getJSONObject(n);
                    new AreaTreeNode(area3Obj.getString("name"),
                            area3Obj.getInt("code"), area2Node);
                }
            }
        }
        return areaRoot;
    }
}
