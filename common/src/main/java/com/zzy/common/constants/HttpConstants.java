package com.zzy.common.constants;

public class HttpConstants {


    public static String TOKEN = "95ffa88f010f1b577ef5f3cf5a1585e7";

    // 测试环境
    public static String SERVER_ADDRESS = "http://grecode.vicp.cc:82";

    // 生产环境
//    public static String SERVER_ADDRESS = "https://aapi.hengyirong.com";


    /*********************************************  constants  **************************************************/
    // 状态码
    public static final int NO_ERROR = 200;
    public static final String ERROR_CODE = "code";
    public static final String ERROR_MESSAGE = "msg";

    /*************************************************************************************************************/


    /*****************************     common         ***************************/

    // 短信验证
    public static final String SMS_SEND = "/v1/user/sms-send";


    /**************************    home *******************************/
    public static final String HOME_DATA = "/userinfo/index.do";

    /**********************        rich info              ***************************/

    public static final String RICH_INFO_LIST = "/news_information/selAllRich_information.do";
    public static final String RICH_INFO_DETAIL = "/news_information/selById.do";
    public static final String RICH_INFO_LIKE = "/news_information/update.do";

    public static final String JOB_LIST = "/recruitment/selAll.do";
    public static final String JOB_DETAIL = "/recruitment/selById.do";
    public static final String JOB_NEW = "/recruitment/insert.do";
    public static final String JOB_REPORT = "/recruitment/insertReport.do";

    /*****************************    login-register   ***************************/

    // 登录
    public static final String LOGIN = "/v1/user/login";

    // 注册
    public static final String REGISTER = "/v1/user/register";

    // 退出登录
    public static final String LOGOUT = "/v1/user/logout";

    // 校验密码
    public static final String CHECK_PWD = "/v1/user/check-pwd";

    // 修改密码
    public static final String UPDATE_PWD = "/v1/user/change-pwd";

    // 手机验证码验证
    public static final String VERIFY_PHONE = "/v1/user/cphone-code";



    /******************************* 创业帮扶******************************************/
    public static final String help_info = "/help_info/selAll.do";




    /*********************************   user  **************************************/

    // 获取用户信息
    public static final String USER_INFO = "/v1/user/get-info";

    //用户补充协议
    public static final String GET_SUPPLY_AGREE = "/v1/user/supply-agree";

    // 查询用户是否需要签署补充协议
    public static final String GET_IS_SUPPLY_AGREE = "/v1/user/is-supply-agree";

    // 获取问卷调查结果
    public static final String QUESTIONNAIRE_INFO = "/v1/user/questionnaire-info";

    // 开启自动投标
    public static final String OPEN_AUTO_DIB = "/v1/user/auto-tender-plan";

    // 资产信息
    public static final String FUNDS_INFO = "/v1/capital/funds-info";

    // 用户反馈
    public static final String FEED_BACK = "/v1/content/feedback";

    // 查询用户不同类型银行卡信息
    public static final String GET_BANK_CARD_INFO = "/v1/user/card-info";

    //我的订单统计
    public static final String MY_ORDER_COUNT = "/v1/lend/order-count";

    // 帮助中心、关于我们等地址
//    public static final String GET_MINE_WEB_URLS = "/v1/content/other-info";

    // 获取资金流水类型
    public static final String GET_ASSET_TYPE = "/v1/capital/flow-type";

    // 获取资金流水
    public static final String GET_ASSET_RECORD = "/v1/capital/fundrecord";

    // 我的优惠券类型
    public static final String GET_USER_COUPON_TYPE = "/v1/user/coupon-types";

    // 优惠券可用数目
    public static final String GET_USER_COUPON_COUNT = "/v1/user/coupon-count";

    // 我的优惠券列表
    public static final String GET_USER_COUPON_LIST = "/v1/user/coupon-list";

    // 我的优惠券详情
    public static final String GET_COUPON_DETAILS = "/v1/user/coupon-detail";

    // 获取银行登录地址
    public static final String GET_BANK_LOGIN_URL = "/v1/user/get-bank-login";

    // 快捷支付签约
    public static final String GET_QUICK_CARD_SIGN = "/v1/user/quick-card-sign";

    // 修改银行卡交易密码
    public static final String UPDATE_BANK_CARD_TRADE_PWD = "/v1/user/set-pwd";


    // 免密授权
    public static final String WARRANT_INFO = "/v1/user/free-sign-info";

    public static final String WARRANT = "/v1/user/free-sign";

    /********************************************************************************/


    /*********************************** HOME  --- product  *************************/

    // 自动投标列表页文案
    public static final String AUTO_BID_INTRODUCE = "/v1/lend/service-details";

    // 锁定期
    public static final String TERM_LIST = "/v1/lend/getterm-list";

    // 债权信息轮播表
    public static final String BORROW_INFO_LIST = "/v1/lend/borrowinfo-list";

    // 自动投标列表
    public static final String AUTO_BID_LIST = "/v1/lend/autobid-list";

    // N to one 列表（一对一）
    public static final String N_TO_ONE_LIST = "/v1/loan/sanb-list";

    // 体验金出借
    public static final String TRY_LEND_SUBMIT = "/v1/lend/try-lend";

    // 订单出借记录
    public static final String ORDER_LEND_RECORD_LIST = "/v1/lend/order-list";

    // 订单（体验金）详情
    public static final String GET_TASTE_MONEY_DETAILS = "/v1/lend/order-detail";

    // 自动投标详情banner
    public static final String GET_NEWS = "/v1/content/get-news";

    /********************************************************************************/


    /*********************************** PRODUCT   ***********************************/

    // 自动投标详情内容
    public static final String AUTO_BID_DETAIL = "/v1/lend/autobid-details";

    // 自动投标出借记录
    public static final String LEND_RECORD_LIST = "/v1/lend/lendrecord-pagination";

    // 获取支持银行及限额
    public static final String BANK_SUPPORT_INFO = "/v1/capital/quota-new";

    // 风险提示内容
    public static final String RISK_TIP_LIST = "/v1/content/get-docs";

    // 一对一详情信息
    public static final String ONE_TO_ONE_DETAIL_INFO = "/v1/loan/sanb-details";

    // 一对一历史记录
    public static final String ONE_TO_ONE_HISTORY = "/v1/lend/sanb-list";


    /********************************************************************************/

    /***********************************  homeFragment  **********************************/

    // banner list
    public static final String BANNER_LIST = "/v1/content/get-banners";

    // notice list
    public static final String NOTICE_LIST = "/v1/content/get-notices";

    // 查询站内信 /content/message
    public static final String MESSAGE_LIST = "/v1/content/message";

    // shortcut list
    public static final String SHORTCUT_LIST = "/v1/content/get-news";

    // project info
    public static final String RECOMMEND_PROJECT_INFO = "/v1/lend/product-recommend";

    /********************************************************************************/

    public static final String APP_UPDATE = "/v1/content/check-version";

}
