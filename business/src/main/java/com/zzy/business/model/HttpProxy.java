package com.zzy.business.model;


import com.zzy.common.model.bean.Content;
import com.zzy.common.model.bean.Image;
import com.zzy.common.model.bean.Job;
import com.zzy.common.model.bean.Pioneering;
import com.zzy.business.model.jsonParser.ContentListParser;
import com.zzy.business.model.jsonParser.ContentParser;
import com.zzy.business.model.jsonParser.GetRichInfoListParser;
import com.zzy.business.model.jsonParser.GetRichInfoParser;
import com.zzy.business.model.jsonParser.GoodsListParser;
import com.zzy.business.model.jsonParser.GoodsParser;
import com.zzy.business.model.jsonParser.JobListParser;
import com.zzy.business.model.jsonParser.JobParser;
import com.zzy.business.model.jsonParser.MenuListParser;
import com.zzy.business.model.jsonParser.PbListParser;
import com.zzy.business.model.jsonParser.PioneerListParser;
import com.zzy.business.model.jsonParser.PioneerParser;
import com.zzy.business.model.jsonParser.PioneeringListParser;
import com.zzy.business.model.jsonParser.PioneeringParser;
import com.zzy.common.constants.CommonConstants;
import com.zzy.common.constants.HttpConstants;
import com.zzy.common.model.jsonParser.CommonParser;
import com.zzy.common.network.HttpUtils;
import com.zzy.commonlib.http.HInterface;

import org.json.JSONArray;
import org.json.JSONObject;

public class HttpProxy {
    public static void getPbList(int pageNum,final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", HttpConstants.TOKEN);
        reqBody.put("rows", CommonConstants.PAGE_SIZE);
        reqBody.put("page", pageNum);
        HttpUtils.getInstance().req(
                HttpConstants.PB_LIST,
                reqBody,
                callback,
                new PbListParser());
    }
    public static void getRichInfoList(int pageNum,final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", HttpConstants.TOKEN);
        reqBody.put("rows", CommonConstants.PAGE_SIZE);
        reqBody.put("page", pageNum);
        HttpUtils.getInstance().req(
                HttpConstants.RICH_INFO_LIST,
                reqBody,
                callback,
                new GetRichInfoListParser());
    }
    public static void getRichInfoDetail(int id,final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", HttpConstants.TOKEN);
        reqBody.put("NEWS_INFORMATION_ID", id);
        HttpUtils.getInstance().req(
                HttpConstants.RICH_INFO_DETAIL,
                reqBody,
                callback,
                new GetRichInfoParser());
    }
    public static void likeRichInfo(int id, final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", HttpConstants.TOKEN);
        reqBody.put("NEWS_INFORMATION_ID", id);
        HttpUtils.getInstance().req(
                HttpConstants.RICH_INFO_LIKE,
                reqBody,
                callback,
                new CommonParser());
    }

    public static void getJobList(int pageNum,final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", HttpConstants.TOKEN);
        reqBody.put("rows", CommonConstants.PAGE_SIZE);
        reqBody.put("page", pageNum);
        HttpUtils.getInstance().req(
                HttpConstants.JOB_LIST,
                reqBody,
                callback,
                new JobListParser());
    }
    public static void getJobDetail(int id,final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", HttpConstants.TOKEN);
        reqBody.put("RECRUITMENT_ID", id);
        HttpUtils.getInstance().req(
                HttpConstants.JOB_DETAIL,
                reqBody,
                callback,
                new JobParser());
    }
    public static void reportJob(int id, String content, final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", HttpConstants.TOKEN);
        reqBody.put("RECRUITMENT_ID", id);
        reqBody.put("COMPLAINT_REASON", content);
        HttpUtils.getInstance().req(
                HttpConstants.JOB_REPORT,
                reqBody,
                callback,
                new CommonParser());
    }
    public static void newJob(Job job, final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", HttpConstants.TOKEN);
        reqBody.put("JOB_NAME",job.getJobName());
        reqBody.put("WORK_LOCATION", job.getAddress());
        reqBody.put("RECRUITMENT_PERSON_NUM", job.getHeadcount());
        reqBody.put("SALARY_MIN", job.getSalaryMin());
        reqBody.put("SALARY_MAX", job.getSalaryMax());
        reqBody.put("MOBILE_NO", job.getPhone());
        reqBody.put("CONNECT_PERSON", job.getContact());
        reqBody.put("EDUCATION", job.getEducation());
        reqBody.put("JOB_REQUIREMENT", job.getJobRequirements());
        reqBody.put("PERSON_REQUIREMENT", job.getJobContent());
        HttpUtils.getInstance().req(
                HttpConstants.JOB_NEW,
                reqBody,
                callback,
                new CommonParser());
    }

    public static void getPioneerTypeList(final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", HttpConstants.TOKEN);
        HttpUtils.getInstance().req(
                HttpConstants.PIONEER_TYPE_LIST,
                reqBody,
                callback,
                new MenuListParser());
    }

    public static void getPioneerList(String type,int pageNum,final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", HttpConstants.TOKEN);
        reqBody.put("CREATER_TYPE", type);
        reqBody.put("rows", CommonConstants.PAGE_SIZE);
        reqBody.put("page", pageNum);
        HttpUtils.getInstance().req(
                HttpConstants.PIONEER_LIST,
                reqBody,
                callback,
                new PioneerListParser());
    }

    public static void getPioneerDetail(int id,final HInterface.DataCallback callback) throws Exception{
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", HttpConstants.TOKEN);
        reqBody.put("NEWS_INFORMATION_ID", id);
        HttpUtils.getInstance().req(
                HttpConstants.PIONEER_DETAIL,
                reqBody,
                callback,
                new PioneerParser());
    }

    public static void getPioneeringList(int pageNum, final HInterface.DataCallback callback) throws Exception{
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", HttpConstants.TOKEN);
        reqBody.put("rows", CommonConstants.PAGE_SIZE);
        reqBody.put("page", pageNum);
        HttpUtils.getInstance().req(
                HttpConstants.PIONEERING_LIST,
                reqBody,
                callback,
                new PioneeringListParser());
    }

    public static void getPioneeringDetail(int id,final HInterface.DataCallback callback) throws Exception{
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", HttpConstants.TOKEN);
        reqBody.put("RECRUITMENT_ID", id);
        HttpUtils.getInstance().req(
                HttpConstants.PIONEERING_DETAIL,
                reqBody,
                callback,
                new PioneeringParser());
    }

    public static void newPioneering(Pioneering bean, final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", HttpConstants.TOKEN);
        reqBody.put("CONNECT_PERSON",bean.getContact());
        reqBody.put("MOBILE_NO", bean.getPhone());
        reqBody.put("RELEASE_TEXT", bean.getContent());
        HttpUtils.getInstance().req(
                HttpConstants.PIONEERING_NEW,
                reqBody,
                callback,
                new CommonParser());
    }

    public static void getGoodsBuyList(int pageNum, final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", HttpConstants.TOKEN);
        reqBody.put("rows", CommonConstants.PAGE_SIZE);
        reqBody.put("page", pageNum);
        HttpUtils.getInstance().req(
                HttpConstants.GOODS_BUY_LIST,
                reqBody,
                callback,
                new GoodsListParser());
    }

    public static void getGoodsBuyDetail(int id, final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", HttpConstants.TOKEN);
        reqBody.put("SALE_ID", id);
        HttpUtils.getInstance().req(
                HttpConstants.GOODS_BUY_DETAIL,
                reqBody,
                callback,
                new GoodsParser());
    }
    public static void getGoodsSellList(int pageNum, final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", HttpConstants.TOKEN);
        reqBody.put("rows", CommonConstants.PAGE_SIZE);
        reqBody.put("page", pageNum);
        HttpUtils.getInstance().req(
                HttpConstants.GOODS_SELL_LIST,
                reqBody,
                callback,
                new GoodsListParser());
    }

    public static void getGoodsSellDetail(int id, final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", HttpConstants.TOKEN);
        reqBody.put("SALE_ID", id);
        HttpUtils.getInstance().req(
                HttpConstants.GOODS_SELL_DETAIL,
                reqBody,
                callback,
                new GoodsParser());
    }
    public static void getContentList(int type,int pageNum,final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", HttpConstants.TOKEN);
        reqBody.put("rows", CommonConstants.PAGE_SIZE);
        reqBody.put("page", pageNum);
        String action = HttpConstants.HELP_LIST;
        if(type == CommonConstants.CONTENT_HELP){
            action = HttpConstants.HELP_LIST;
        }else if(type == CommonConstants.CONTENT_IDEA){
            action = HttpConstants.IDEA_LIST;
        }else if(type == CommonConstants.CONTENT_EXPERIENCE){
            action = HttpConstants.EXPERIENCE_LIST;
        }
        HttpUtils.getInstance().req(
                action,
                reqBody,
                callback,
                new ContentListParser());
    }
    public static void getContentDetail(int id,final HInterface.DataCallback callback) throws Exception{
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", HttpConstants.TOKEN);
        reqBody.put("FORUM_ID", id);
        HttpUtils.getInstance().req(
                HttpConstants.CONTENT_DETAIL,
                reqBody,
                callback,
                new ContentParser());
    }

    public static void newContent(int type, Content bean, final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", HttpConstants.TOKEN);
        reqBody.put("FORUM_NAME",bean.getTitle());
        reqBody.put("RELEASE_TEXT", bean.getContent());

        JSONArray imgArr = new JSONArray();
        for(Image image:bean.getImgList()){
            JSONObject obj = new JSONObject();
            obj.put("PIC_NAME",image.getName());
            obj.put("PIC_ADDR",image.getPath());
            imgArr.put(obj);
        }
        reqBody.put("IMAGES",imgArr);

        String action = HttpConstants.HELP_NEW;
        if(type == CommonConstants.CONTENT_HELP){
            action = HttpConstants.HELP_NEW;
        }else if(type == CommonConstants.CONTENT_IDEA){
            action = HttpConstants.IDEA_NEW;
        }else if(type == CommonConstants.CONTENT_EXPERIENCE){
            action = HttpConstants.EXPERIENCE_NEW;
        }
        HttpUtils.getInstance().req(
                action,
                reqBody,
                callback,
                new CommonParser());
    }
    public static void reportContent(int id, String content, final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", HttpConstants.TOKEN);
        reqBody.put("FORUM_ID", id);
        reqBody.put("COMPLAINT_REASON", content);
        HttpUtils.getInstance().req(
                HttpConstants.CONTENT_REPORT,
                reqBody,
                callback,
                new CommonParser());
    }
    public static void likeContent(int id,final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", HttpConstants.TOKEN);
        reqBody.put("FORUM_ID", id);
        HttpUtils.getInstance().req(
                HttpConstants.CONTENT_LIKE,
                reqBody,
                callback,
                new CommonParser());
    }

    public static void createComment(int contentId, String content, HInterface.DataCallback callback) throws Exception{
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", HttpConstants.TOKEN);
        reqBody.put("FORUM_ID",contentId);
        reqBody.put("COMMENT_TEXT", content);

        HttpUtils.getInstance().req(
                HttpConstants.CONTENT_COMMENT,
                reqBody,
                callback,
                new CommonParser());
    }

    public static void createReply(int contentId, int commentId, String content, HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", HttpConstants.TOKEN);
        reqBody.put("FORUM_ID",contentId);
        reqBody.put("COMMENT_ID",commentId);
        reqBody.put("REVIEW_TEXT", content);

        HttpUtils.getInstance().req(
                HttpConstants.CONTENT_REPLY,
                reqBody,
                callback,
                new CommonParser());
    }
}