package com.zzy.common.constants;

public class HttpConstants {


    public static String TOKEN = "95ffa88f010f1b577ef5f3cf5a1585e7";

    // 测试环境
    public static String SERVER_ADDRESS = "http://grecode.vicp.cc:82";


    public static String FILE_SERVER_URL = SERVER_ADDRESS+"/forum_pic/upload.do";

    // 生产环境
//    public static String SERVER_ADDRESS = "https://aapi.hengyirong.com";


    /*********************************************  constants  **************************************************/
    // 状态码
    public static final int NO_ERROR = 200;
    public static final String ERROR_CODE = "code";
    public static final String ERROR_MESSAGE = "msg";

    /*************************************************************************************************************/


    /**************************    home *******************************/
    public static final String HOME_DATA = "/userinfo/index.do";


    public static final String PB_LIST = "/userinfo/selAllbook.do";

    /**************************    login *******************************/
    public static final String LOGIN = "/Mobile_login.do";
    public static final String LOGOUT = "/Mobile_logout.do";
    public static final String REGISTER1 = "/account/insertNext.do";
    public static final String REGISTER2 = "/account/insert.do";
    public static final String FORGET_PW = "/account/forget.do";
    public static final String RESET_PW = "/userinfo/update.do";

    /**********************        business             ***************************/
    //招聘
    public static final String JOB_LIST = "/recruitment/selAll.do";
    public static final String JOB_DETAIL = "/recruitment/selById.do";
    public static final String JOB_NEW = "/recruitment/insert.do";
    public static final String JOB_REPORT = "/recruitment/insertReport.do";
    //招聘 my
    public static final String JOB_UPDATE = "/recruitment/update.do";
    public static final String JOB_STOP = "/recruitment/stop.do";
    public static final String MY_JOB_LIST = "/recruitment/selAllMy.do";

    //创业
    public static final String PIONEERING_LIST = "/recruitment/selAllpioneer.do";
    public static final String PIONEERING_DETAIL = "/recruitment/selByIdpioneer.do";
    public static final String PIONEERING_NEW = "/recruitment/insertpioneer.do";
    //创业 my
    public static final String PIONEERING_UPDATE = "/recruitment/update.do";
    public static final String PIONEERING_STOP = "/recruitment/stop.do";
    public static final String MY_PIONEER_LIST = "/recruitment/selAllpioneerMy.do";


    public static final String RICH_INFO_LIST = "/news_information/selAllRich_information.do";
    public static final String RICH_INFO_DETAIL = "/news_information/selById.do";
    public static final String RICH_INFO_LIKE = "/news_information/update.do";

    //先锋
    public static final String PIONEER_TYPE_LIST = "/news_information/selAllpioneerTYPE.do";
    public static final String PIONEER_LIST = "/news_information/selAllpioneer.do";
    public static final String PIONEER_DETAIL = "/news_information/selById.do";

    public static final String GOODS_BUY_LIST = "/sale/selAllBuy.do";
    public static final String GOODS_BUY_DETAIL = "/sale/selById.do";

    public static final String GOODS_SELL_LIST = "/sale/selAllSale.do";
    public static final String GOODS_SELL_DETAIL = "/sale/selByIdSale.do";


    //content
    public static final String CONTENT_DETAIL = "/forum/selById.do";
    public static final String CONTENT_REPORT = "/forum/insertReport.do";
    public static final String CONTENT_LIKE = "/forum/update.do";
    public static final String CONTENT_COMMENT = "/forum/insertLeave.do";
    public static final String CONTENT_REPLY = "/forum/insertReply.do";

    public static final String HELP_LIST = "/forum/selAllHelp.do";
    public static final String HELP_NEW = "/forum/insert.do";

    public static final String IDEA_LIST = "/forum/selAllopinion.do";
    public static final String IDEA_NEW = "/forum/insertopinion.do";

    public static final String EXPERIENCE_LIST = "/forum/selAllexperience.do";
    public static final String EXPERIENCE_NEW = "/forum/insertexperience.do";


    /**********************        user             ***************************/
    //my archives
    public static final String ARCHIVES_INFO = "/userinfo/selByToken.do";
    public static final String ARCHIVES_UPDATE = "/userinfo/updateByToken.do";

    //user info
    public static final String USER_INFO = "/userinfo/selById.do";

    //comment
    //我收到的评论
    public static final String MY_COMMENT_LIST = "/comment/selAllMyComment.do";
    //我收到的回复
    public static final String MY_REPLY_LIST = "/comment/selAllMyReply.do";
    //修改评论
    public static final String COMMENT_UPDATE = "/comment/updateComment.do";
    //修改回复
    public static final String REPLY_UPDATE = "/comment/updateReply.do";

    //log
    public static final String MY_LOG_LIST = "/entrepreneurs_log/selAll.do";
    public static final String LOG_ADD = "/entrepreneurs_log/insert.do";


}
