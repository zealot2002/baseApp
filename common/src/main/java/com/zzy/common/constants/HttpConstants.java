package com.zzy.common.constants;

public class HttpConstants {

    // 测试环境
    public static String SERVER_ADDRESS = "http://grecode.gnway.cc:82";

    public static String FILE_SERVER_URL = SERVER_ADDRESS+"/forum_pic/upload.do";

    // 生产环境
//    public static String SERVER_ADDRESS = "https://aapi.hengyirong.com";


    /*********************************************  constants  **************************************************/
    // 状态码
    public static final int NO_ERROR = 200;
    public static final String ERROR_CODE = "code";
    public static final String ERROR_MESSAGE = "msg";

    /*************************************************************************************************************/
    public static final String UPLOAD_FILE = "/forum_pic/upload.do";

    /**************************    特色东坑 *******************************/
    public static final String DK_URL_1 = SERVER_ADDRESS+"/page/characteristic/introduction.html";
    public static final String DK_URL_2 = SERVER_ADDRESS+"/page/characteristic/Industry.html";
    public static final String DK_URL_3 = SERVER_ADDRESS+"/page/characteristic/programme.html";

    /**************************    主页 *******************************/
    public static final String HOME_DATA = "/userinfo/index.do";


    /**************************    产业分布 *******************************/
    public static final String INDUSTRY_LIST = "/dict_type/selAll.do";
    public static final String INDUSTRY_DETAIL = "/dict_type/selById.do";



    /**************************    电话本 *******************************/
    public static final String PB_LIST = "/userinfo/selAllbook.do";

    /**************************    登录相关 *******************************/
    public static final String LOGIN = "/Mobile_login.do";
    public static final String LOGOUT = "/Mobile_logout.do";
    public static final String REGISTER1 = "/account/insert.do";
    public static final String REGISTER2 = "/account/insertNext.do";
    public static final String FORGET_PW = "/account/forget.do";
    public static final String RESET_PW = "/userinfo/update.do";

    public static final String GET_SMS = "/account/verification.do";

    /**********************        招聘            ***************************/
    public static final String JOB_LIST = "/recruitment/selAll.do";
    public static final String JOB_DETAIL = "/recruitment/selById.do";
    public static final String JOB_NEW = "/recruitment/insert.do";
    public static final String JOB_REPORT = "/recruitment/insertReport.do";
    //my
    public static final String JOB_UPDATE = "/recruitment/update.do";
    public static final String JOB_STOP = "/recruitment/stop.do";
    public static final String MY_JOB_LIST = "/recruitment/selAllMy.do";


    /**********************        创业            ***************************/
    public static final String PIONEERING_LIST = "/recruitment/selAllpioneer.do";
    public static final String PIONEERING_DETAIL = "/recruitment/selByIdpioneer.do";
    public static final String PIONEERING_NEW = "/recruitment/insertpioneer.do";
    //my
    public static final String PIONEERING_UPDATE = "/recruitment/update.do";
    public static final String PIONEERING_STOP = "/recruitment/stop.do";
    public static final String MY_PIONEERING_LIST = "/recruitment/selAllpioneerMy.do";


    /**********************        创业先锋            ***************************/
    public static final String PIONEER_TYPE_LIST = "/news_information/selAllpioneerTYPE.do";
    public static final String PIONEER_LIST = "/news_information/selAllpioneer.do";
    public static final String PIONEER_DETAIL = "/news_information/selById.do";

    /**********************        创业服务            ***************************/
    public static final String PIONEER_SERVICE_TYPE_LIST = "/news_information/selAllBusinessTYPE.do";
    public static final String PIONEER_HELP_LIST = "/help_info/selAll.do";
    public static final String PIONEER_ZCHB_LIST = "/news_information/selAll.do";
    public static final String PIONEER_EXPERT_LIST = "/userinfo/selAll.do";

    public static final String PIONEER_HELP_DETAIL = "/help_info/selHelp_InfoById.do";
    public static final String PIONEER_HELP_JOIN = "/help_join/insert.do";
    public static final String PIONEER_EXPERT_DETAIL = "/userinfo/selById.do";

    /**********************        致富信息            ***************************/
    public static final String RICH_INFO_LIST = "/news_information/selAllRich_information.do";
    public static final String RICH_INFO_DETAIL = "/news_information/selById.do";
    public static final String RICH_INFO_LIKE = "/news_information/update.do";


    /**********************        内容（求助|点子|经验|朋友圈）  ***************************/
    public static final String CONTENT_DETAIL = "/forum/selById.do";
    public static final String CONTENT_REPORT = "/forum/insertReport.do";
    public static final String CONTENT_LIKE = "/forum/update.do";

    public static final String HELP_LIST = "/forum/selAllHelp.do";
    public static final String HELP_NEW = "/forum/insert.do";

    public static final String IDEA_LIST = "/forum/selAllopinion.do";
    public static final String IDEA_NEW = "/forum/insertexperience.do";

    public static final String EXPERIENCE_LIST = "/forum/selAllexperience.do";
    public static final String EXPERIENCE_NEW = "/forum/insertopinion.do";

    public static final String FRIEND_LIST = "/forum/selAllfriend.do";
    public static final String FRIEND_NEW = "/forum/insertfriends.do";

    /**********************        买卖            ***************************/
    public static final String GOODS_BUY_LIST = "/sale/selAllBuy.do";
    public static final String GOODS_BUY_DETAIL = "/sale/selById.do";
    public static final String GOODS_BUY_NEW = "/sale/insert.do";


    public static final String GOODS_SELL_LIST = "/sale/selAllSale.do";
    public static final String GOODS_SELL_DETAIL = "/sale/selByIdSale.do";
    public static final String GOODS_SELL_NEW = "/sale/insertSale.do";


    /**********************        我的档案            ***************************/
    public static final String ARCHIVES_INFO = "/userinfo/selByToken.do";
    public static final String ARCHIVES_UPDATE = "/userinfo/updateByToken.do";


    /**********************        用户信息            ***************************/
    public static final String USER_INFO = "/userinfo/selUserInfo.do";



    /**********************        评论            ***************************/
    //我收到的评论
    public static final String MY_COMMENT_LIST = "/comment/selAllMyComment.do";
    //修改评论
    public static final String COMMENT_UPDATE = "/comment/updateComment.do";
    public static final String COMMENT_NEW = "/forum/insertLeave.do";


    /**********************        回复            ***************************/
    //我收到的回复
    public static final String MY_REPLY_LIST = "/comment/selAllMyReply.do";
    //修改回复
    public static final String REPLY_UPDATE = "/comment/updateReply.do";
    public static final String REPLY_NEW = "/forum/insertReply.do";

    /**********************        创业日志            ***************************/
    public static final String MY_LOG_LIST = "/entrepreneurs_log/selAll.do";
    public static final String LOG_ADD = "/entrepreneurs_log/insert.do";




}
