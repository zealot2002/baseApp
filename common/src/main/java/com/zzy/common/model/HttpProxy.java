package com.zzy.common.model;


import com.zzy.common.model.bean.Archives;
import com.zzy.common.model.bean.Content;
import com.zzy.common.model.bean.Image;
import com.zzy.common.model.bean.Job;
import com.zzy.common.model.bean.Log;
import com.zzy.common.model.bean.Pioneering;
import com.zzy.common.model.jsonParser.ArchivesParser;
import com.zzy.common.model.jsonParser.CommentListParser;
import com.zzy.common.model.jsonParser.ContentListParser;
import com.zzy.common.model.jsonParser.ContentParser;
import com.zzy.common.model.jsonParser.ExpertParser;
import com.zzy.common.model.jsonParser.GetRichInfoListParser;
import com.zzy.common.model.jsonParser.GetRichInfoParser;
import com.zzy.common.model.jsonParser.GoodsListParser;
import com.zzy.common.model.jsonParser.GoodsParser;
import com.zzy.common.model.jsonParser.JobListParser;
import com.zzy.common.model.jsonParser.JobParser;
import com.zzy.common.model.jsonParser.LogListParser;
import com.zzy.common.model.jsonParser.MenuListParser;
import com.zzy.common.model.jsonParser.PbListParser;
import com.zzy.common.model.jsonParser.PioneerHelpParser;
import com.zzy.common.model.jsonParser.PioneerListParser;
import com.zzy.common.model.jsonParser.PioneerParser;
import com.zzy.common.model.jsonParser.PioneerServiceListParser;
import com.zzy.common.model.jsonParser.PioneeringListParser;
import com.zzy.common.model.jsonParser.PioneeringParser;
import com.zzy.common.constants.CommonConstants;
import com.zzy.common.constants.HttpConstants;
import com.zzy.common.model.jsonParser.CommonParser;
import com.zzy.common.model.jsonParser.Register1Parser;
import com.zzy.common.model.jsonParser.UserParser;
import com.zzy.common.network.HttpUtils;
import com.zzy.common.utils.CommonUtils;
import com.zzy.commonlib.http.HInterface;

import org.json.JSONArray;
import org.json.JSONObject;

public class HttpProxy {
    /**************************    登录相关 *******************************/
    public static void login(String un, String pw, HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("MOBILE_NO", un);
        reqBody.put("PASSWORD", CommonUtils.getPw(pw));
        HttpUtils.getInstance().req(
                HttpConstants.LOGIN,
                reqBody,
                callback,
                new UserParser());
    }
    public static void register1(Archives bean, HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("USERNAME", bean.getName());
        reqBody.put("MOBILE_NO", bean.getPhone());
//        reqBody.put("CHECK_CODE_NUM", bean.getSms());
        reqBody.put("CHECK_CODE_NUM", "1234");
        reqBody.put("MOBILE_NO_Y", bean.getInviter());
        reqBody.put("IDCARD", bean.getIdNo());
        reqBody.put("PASSWORD", CommonUtils.getPw(bean.getPw()));
        reqBody.put("COUNTY", bean.getArea1());
        reqBody.put("TOWN", bean.getArea2());
        reqBody.put("VILLAGE", bean.getArea3());
        reqBody.put("ADDR_DETAIL", bean.getAddress());
        reqBody.put("BIRTHDAY", bean.getBirthday());
        reqBody.put("SEX", bean.getSex());

        HttpUtils.getInstance().req(
                HttpConstants.REGISTER1,
                reqBody,
                callback,
                new Register1Parser());
    }
    public static void register2(Archives bean, HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("USERINFO_ID", bean.getUserId());
        reqBody.put("USER_TYPE", bean.getUserType());
        reqBody.put("IS_COMPANY", bean.getIsCompany());
        reqBody.put("COMPANY_NAME", bean.getCompanyName());
        reqBody.put("COMPANY_EMP_NUM", bean.getCompanyScope());
        reqBody.put("BUSINESS_LICENSE_PIC_ADDR", bean.getCompanyImgUrl());
        reqBody.put("BUSINESS_LICENSE_PIC_NAME", bean.getCompanyImgName());

        JSONArray arr = new JSONArray();
        for(String s:bean.getSkills()){
            JSONObject obj = new JSONObject();
            obj.put("USER_SKILL",s);
            arr.put(obj);
        }
        reqBody.put("USER_SKILL",arr);

        HttpUtils.getInstance().req(
                HttpConstants.REGISTER2,
                reqBody,
                callback,
                new CommonParser());
    }
    public static void logout(HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        HttpUtils.getInstance().req(
                HttpConstants.LOGOUT,
                reqBody,
                callback,
                new CommonParser());
    }
    public static void resetPw(String opw, String npw, HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("OLDPASS", CommonUtils.getPw(opw));
        reqBody.put("NEWPASS", CommonUtils.getPw(npw));
        HttpUtils.getInstance().req(
                HttpConstants.RESET_PW,
                reqBody,
                callback,
                new CommonParser());
    }
    public static void forgetPw(String phone, String pw, String sms,HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("MOBILE_NO", phone);
        reqBody.put("PASSWORD",CommonUtils.getPw(pw));
        reqBody.put("CHECK_CODE_NUM", sms);
        HttpUtils.getInstance().req(
                HttpConstants.FORGET_PW,
                reqBody,
                callback,
                new CommonParser());
    }
    public static void getSms(String phone, HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("MOBILE_NO", phone);
        HttpUtils.getInstance().req(
                HttpConstants.GET_SMS,
                reqBody,
                callback,
                new CommonParser());
    }

    /**************************    电话本 *******************************/
    public static void getPbList(int pageNum,final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("rows", CommonConstants.PAGE_SIZE);
        reqBody.put("page", pageNum);
        HttpUtils.getInstance().req(
                HttpConstants.PB_LIST,
                reqBody,
                callback,
                new PbListParser());
    }

    /**********************        招聘            ***************************/
    public static void getJobList(int pageNum,final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
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
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("RECRUITMENT_ID", id);
        HttpUtils.getInstance().req(
                HttpConstants.JOB_DETAIL,
                reqBody,
                callback,
                new JobParser());
    }
    public static void reportJob(int id, String content, final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
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
        reqBody.put("TOKEN", CommonUtils.getToken());
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
    public static void updateJob(Job job, final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("RECRUITMENT_ID",job.getId());
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
                HttpConstants.JOB_UPDATE,
                reqBody,
                callback,
                new CommonParser());
    }
    public static void stopJob(int id,final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("RECRUITMENT_ID", id);
        HttpUtils.getInstance().req(
                HttpConstants.JOB_STOP,
                reqBody,
                callback,
                new CommonParser());
    }
    public static void getMyJobList(int pageNum,final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("rows", CommonConstants.PAGE_SIZE);
        reqBody.put("page", pageNum);
        HttpUtils.getInstance().req(
                HttpConstants.MY_JOB_LIST,
                reqBody,
                callback,
                new JobListParser());
    }

    /**********************        创业            ***************************/
    public static void getPioneeringList(int pageNum, final HInterface.DataCallback callback) throws Exception{
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
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
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("RECRUITMENT_ID", id);
        HttpUtils.getInstance().req(
                HttpConstants.PIONEERING_DETAIL,
                reqBody,
                callback,
                new PioneeringParser());
    }
    public static void newPioneering(Pioneering bean, final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("CONNECT_PERSON",bean.getContact());
        reqBody.put("MOBILE_NO", bean.getPhone());
        reqBody.put("RELEASE_TEXT", bean.getContent());
        HttpUtils.getInstance().req(
                HttpConstants.PIONEERING_NEW,
                reqBody,
                callback,
                new CommonParser());
    }
    public static void updatePioneering(Pioneering bean, final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("RECRUITMENT_ID",bean.getId());
        reqBody.put("CONNECT_PERSON",bean.getContact());
        reqBody.put("MOBILE_NO", bean.getPhone());
        reqBody.put("RELEASE_TEXT", bean.getContent());
        HttpUtils.getInstance().req(
                HttpConstants.PIONEERING_UPDATE,
                reqBody,
                callback,
                new CommonParser());
    }
    public static void stopPioneering(int id,final HInterface.DataCallback callback) throws Exception{
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("RECRUITMENT_ID", id);
        HttpUtils.getInstance().req(
                HttpConstants.PIONEERING_STOP,
                reqBody,
                callback,
                new CommonParser());
    }
    public static void getMyPioneeringList(int pageNum,final HInterface.DataCallback callback) throws Exception{
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("rows", CommonConstants.PAGE_SIZE);
        reqBody.put("page", pageNum);
        HttpUtils.getInstance().req(
                HttpConstants.MY_PIONEERING_LIST,
                reqBody,
                callback,
                new PioneeringListParser());
    }
    /**********************        创业服务            ***************************/
    public static void getPioneerServiceTypeList(final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        HttpUtils.getInstance().req(
                HttpConstants.PIONEER_SERVICE_TYPE_LIST,
                reqBody,
                callback,
                new MenuListParser());
    }
    public static void getHelpList(int pageNum,final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("rows", CommonConstants.PAGE_SIZE);
        reqBody.put("page", pageNum);
        HttpUtils.getInstance().req(
                HttpConstants.PIONEER_HELP_LIST,
                reqBody,
                callback,
                new PioneerServiceListParser());
    }
    public static void getZCHBList(int pageNum,final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("rows", CommonConstants.PAGE_SIZE);
        reqBody.put("page", pageNum);
        reqBody.put("NEWS_TYPE", "政策汇编");
        HttpUtils.getInstance().req(
                HttpConstants.PIONEER_ZCHB_LIST,
                reqBody,
                callback,
                new PioneerServiceListParser());
    }
    public static void getContentJobList(int pageNum,final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("rows", CommonConstants.PAGE_SIZE);
        reqBody.put("page", pageNum);

        HttpUtils.getInstance().req(
                HttpConstants.HELP_LIST,
                reqBody,
                callback,
                new PioneerServiceListParser());
    }

    public static void getExpertList(int pageNum,final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("rows", CommonConstants.PAGE_SIZE);
        reqBody.put("page", pageNum);
        reqBody.put("IS_EXPERT", "是");
        HttpUtils.getInstance().req(
                HttpConstants.PIONEER_EXPERT_LIST,
                reqBody,
                callback,
                new PioneerServiceListParser());
    }
    public static void getUserList(int pageNum,final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("rows", CommonConstants.PAGE_SIZE);
        reqBody.put("page", pageNum);
        reqBody.put("TYPE", "技能人才");
        HttpUtils.getInstance().req(
                HttpConstants.JOB_LIST,
                reqBody,
                callback,
                new PioneerServiceListParser());
    }
    public static void getExpertDetail(int id,final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("USERINFO_ID", id);
        reqBody.put("IS_EXPERT", "是");
        HttpUtils.getInstance().req(
                HttpConstants.PIONEER_EXPERT_DETAIL,
                reqBody,
                callback,
                new ExpertParser());
    }
    public static void getHelpDetailList(int id,final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("HELP_INFO_ID", id);
        HttpUtils.getInstance().req(
                HttpConstants.PIONEER_HELP_DETAIL,
                reqBody,
                callback,
                new PioneerHelpParser());
    }
    public static void joinClass(int id,final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("HELP_INFO_ID", id);
        HttpUtils.getInstance().req(
                HttpConstants.PIONEER_HELP_JOIN,
                reqBody,
                callback,
                new CommonParser());
    }

    /**********************        创业先锋            ***************************/
    public static void getPioneerTypeList(final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        HttpUtils.getInstance().req(
                HttpConstants.PIONEER_TYPE_LIST,
                reqBody,
                callback,
                new MenuListParser());
    }
    public static void getPioneerList(String type,int pageNum,final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
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
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("NEWS_INFORMATION_ID", id);
        HttpUtils.getInstance().req(
                HttpConstants.PIONEER_DETAIL,
                reqBody,
                callback,
                new PioneerParser());
    }

    /**********************        致富信息            ***************************/
    public static void getRichInfoList(int pageNum,final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
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
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("NEWS_INFORMATION_ID", id);
        HttpUtils.getInstance().req(
                HttpConstants.RICH_INFO_DETAIL,
                reqBody,
                callback,
                new GetRichInfoParser());
    }
    public static void likeRichInfo(int id, final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("NEWS_INFORMATION_ID", id);
        HttpUtils.getInstance().req(
                HttpConstants.RICH_INFO_LIKE,
                reqBody,
                callback,
                new CommonParser());
    }


    /**********************        内容（求助|点子|经验|朋友圈）  ***************************/
    public static void getContentList(int type,int pageNum,final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("rows", CommonConstants.PAGE_SIZE);
        reqBody.put("page", pageNum);
        String action = HttpConstants.HELP_LIST;
        if(type == CommonConstants.CONTENT_HELP){
            action = HttpConstants.HELP_LIST;
        }else if(type == CommonConstants.CONTENT_IDEA){
            action = HttpConstants.IDEA_LIST;
        }else if(type == CommonConstants.CONTENT_EXPERIENCE){
            action = HttpConstants.EXPERIENCE_LIST;
        }else if(type == CommonConstants.CONTENT_FRIEND){
            action = HttpConstants.FRIEND_LIST;
        }
        HttpUtils.getInstance().req(
                action,
                reqBody,
                callback,
                new ContentListParser());
    }
    public static void getContentDetail(int id,final HInterface.DataCallback callback) throws Exception{
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("FORUM_ID", id);
        HttpUtils.getInstance().req(
                HttpConstants.CONTENT_DETAIL,
                reqBody,
                callback,
                new ContentParser());
    }
    public static void newContent(int type, Content bean, final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
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
        }else if(type == CommonConstants.CONTENT_FRIEND){
            action = HttpConstants.FRIEND_NEW;
        }
        HttpUtils.getInstance().req(
                action,
                reqBody,
                callback,
                new CommonParser());
    }
    public static void reportContent(int id, String content, final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
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
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("FORUM_ID", id);
        HttpUtils.getInstance().req(
                HttpConstants.CONTENT_LIKE,
                reqBody,
                callback,
                new CommonParser());
    }

    /**********************        买卖            ***************************/
    public static void getGoodsBuyList(int pageNum, final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
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
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("SALE_ID", id);
        HttpUtils.getInstance().req(
                HttpConstants.GOODS_BUY_DETAIL,
                reqBody,
                callback,
                new GoodsParser());
    }
    public static void getGoodsSellList(int pageNum, final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
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
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("SALE_ID", id);
        HttpUtils.getInstance().req(
                HttpConstants.GOODS_SELL_DETAIL,
                reqBody,
                callback,
                new GoodsParser());
    }


    /**********************        我的档案            ***************************/
    public static void getMyArchives(final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        HttpUtils.getInstance().req(
                HttpConstants.ARCHIVES_INFO,
                reqBody,
                callback,
                new ArchivesParser());
    }
    public static void updateArchives(Archives bean, final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("USER_TYPE",bean.getUserType());
        reqBody.put("IS_COMPANY", bean.getIsCompany());
        reqBody.put("COMPANY_NAME", bean.getCompanyName());
        reqBody.put("COMPANY_EMP_NUM", bean.getCompanyScope());
        reqBody.put("BUSINESS_LICENSE_PIC_ADDR", bean.getCompanyImgUrl());
        reqBody.put("BUSINESS_LICENSE_PIC_NAME", bean.getCompanyImgName());

        JSONArray arr = new JSONArray();
        for(String s:bean.getSkills()){
            JSONObject obj = new JSONObject();
            obj.put("USER_SKILL",s);
            arr.put(obj);
        }
        reqBody.put("USER_SKILL",arr);

        HttpUtils.getInstance().req(
                HttpConstants.ARCHIVES_UPDATE,
                reqBody,
                callback,
                new CommonParser());
    }

    /**********************        评论            ***************************/
    public static void createComment(int contentId, String content, HInterface.DataCallback callback) throws Exception{
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("FORUM_ID",contentId);
        reqBody.put("COMMENT_TEXT", content);

        HttpUtils.getInstance().req(
                HttpConstants.COMMENT_NEW,
                reqBody,
                callback,
                new CommonParser());
    }
    public static void updateComment(int id, final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("COMMENT_ID",id);

        HttpUtils.getInstance().req(
                HttpConstants.COMMENT_UPDATE,
                reqBody,
                callback,
                new CommonParser());
    }
    public static void getMyCommentList(final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        HttpUtils.getInstance().req(
                HttpConstants.MY_COMMENT_LIST,
                reqBody,
                callback,
                new CommentListParser());
    }

    /**********************        回复            ***************************/
    public static void createReply(int contentId, int commentId, String content, HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("FORUM_ID",contentId);
        reqBody.put("COMMENT_ID",commentId);
        reqBody.put("REVIEW_TEXT", content);

        HttpUtils.getInstance().req(
                HttpConstants.REPLY_NEW,
                reqBody,
                callback,
                new CommonParser());
    }
    public static void updateReply(int id, final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("COMMENT_ID",id);

        HttpUtils.getInstance().req(
                HttpConstants.REPLY_UPDATE,
                reqBody,
                callback,
                new CommonParser());
    }
    public static void getMyReplyList(final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        HttpUtils.getInstance().req(
                HttpConstants.MY_REPLY_LIST,
                reqBody,
                callback,
                new CommentListParser());
    }

    /**********************        用户信息            ***************************/
    public static void getUserInfo(final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        HttpUtils.getInstance().req(
                HttpConstants.USER_INFO,
                reqBody,
                callback,
                new UserParser());
    }

    /**********************        创业日志            ***************************/
    public static void getLogList(int pageNum,final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("rows", CommonConstants.PAGE_SIZE);
        reqBody.put("page", pageNum);
        HttpUtils.getInstance().req(
                HttpConstants.MY_LOG_LIST,
                reqBody,
                callback,
                new LogListParser());
    }
    public static void createLog(Log bean, HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", CommonUtils.getToken());
        reqBody.put("USERINFO_ID", CommonUtils.getUserId());
        reqBody.put("LOG_CONTENT",bean.getContent());

        HttpUtils.getInstance().req(
                HttpConstants.LOG_ADD,
                reqBody,
                callback,
                new CommonParser());
    }

}