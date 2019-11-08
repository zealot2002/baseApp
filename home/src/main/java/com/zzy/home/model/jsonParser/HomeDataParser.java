package com.zzy.home.model.jsonParser;

import com.zzy.common.constants.HttpConstants;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.log.MyLog;
import com.zzy.home.model.bean.Banner;
import com.zzy.home.model.bean.News;
import com.zzy.home.model.bean.SaleInfo;
import com.zzy.home.model.wrapper.HomeCtx;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class HomeDataParser implements HInterface.JsonParser {
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
            HomeCtx ctx = new HomeCtx();
            if(dataObj.has("USERINFO")){
                if(dataObj.getJSONObject("USERINFO").has("USERINFO_ID"))
                    ctx.getUser().setId(dataObj.getJSONObject("USERINFO").getString("USERINFO_ID"));
                if(dataObj.getJSONObject("USERINFO").has("USERNAME"))
                    ctx.getUser().setName(dataObj.getJSONObject("USERINFO").getString("USERNAME"));
                if(dataObj.getJSONObject("USERINFO").has("USER_GARDE"))
                    ctx.getUser().setScore(dataObj.getJSONObject("USERINFO").getString("USER_GARDE"));
            }
            for(int i=0;i<dataObj.getJSONArray("SALE").length();i++){
                JSONObject saleObj = dataObj.getJSONArray("SALE").getJSONObject(i);
                SaleInfo saleInfo = new SaleInfo();
                if(saleObj.has("RELEASE_TIME")) saleInfo.setPublishTime(saleObj.getString("RELEASE_TIME"));
                if(saleObj.has("RELEASE_TITLE")) saleInfo.setTitle(saleObj.getString("RELEASE_TITLE"));
                ctx.getSaleInfoList().add(saleInfo);
            }
            for(int i=0;i<dataObj.getJSONArray("NEWS").length();i++){
                JSONObject newsObj = dataObj.getJSONArray("NEWS").getJSONObject(i);
                News news = new News();
                if(newsObj.has("RELEASE_DEPT")) news.setFrom(newsObj.getString("RELEASE_DEPT"));
                if(newsObj.has("NEWS_INFORMATION_ID")) news.setId(newsObj.getInt("NEWS_INFORMATION_ID"));
                if(newsObj.has("RELEASE_DATE")) news.setPublishTime(newsObj.getString("RELEASE_DATE"));
                if(newsObj.has("NEWS_TITLE")) news.setTitle(newsObj.getString("NEWS_TITLE"));
                if(newsObj.has("NEWS_TYPE")) news.setType(newsObj.getString("NEWS_TYPE"));
                ctx.getNewsList().add(news);
            }
            if(dataObj.has("SCROLLIMAGE")){
                for(int i=0;i<dataObj.getJSONArray("SCROLLIMAGE").length();i++){
                    JSONObject bannerObj = dataObj.getJSONArray("SCROLLIMAGE").getJSONObject(i);
                    Banner banner = new Banner();
                    if(bannerObj.has("HOME_PAGE_PIC_ADDR"))
                        banner.setImgUrl(HttpConstants.SERVER_ADDRESS+"/"+bannerObj.getString("HOME_PAGE_PIC_ADDR"));
                    ctx.getBannerList().add(banner);
                }
            }
            return new Object[]{HConstant.SUCCESS,ctx};
        } else {
            String msg = obj.getString(HttpConstants.ERROR_MESSAGE);
            return new Object[]{HConstant.ERROR, msg};
        }
    }
}
