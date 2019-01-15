package jx.com.shoppingtrolley_zihenguncle.url;

import java.security.PublicKey;

/**
 * @author 郭淄恒
 * url
 */
public class Apis {

    public static final String LOGIN_URL = "user/v1/login";
    public static final String REGISTER_URL="user/v1/register";
    public static final String BANNER_URL = "commodity/v1/bannerShow";
    public static final String GOODS_URL = "commodity/v1/commodityList";
    public static final String ERJI_URL = "commodity/v1/findFirstCategory";
    public static final String ERJI_ITEM_URL = "commodity/v1/findSecondCategory?firstCategoryId=%s";
    public static  final String HOT_LABLE_URL="commodity/v1/findCommodityListByLabel?labelId=%s&page=%d&count=%d";
    public static final String MORE_URL = "commodity/v1/findCommodityByKeyword?keyword=%s&page=%d&count=%d";
    public static final String ERJI_SHOW_URL = "commodity/v1/findCommodityByCategory?categoryId=%s&page=%d&count=%d";
    //详情接口
    public static final String DETAILS_URL = "commodity/v1/findCommodityDetailsById?commodityId=%d";
    //圈子列表
    public static final String CIRCLE_LIST_URL = "circle/v1/findCircleList?page=%d&count=%d";

    //浏览记录
    public static final String FOOT_JILU_URL = "commodity/verify/v1/browseList?page=%d&count=%d";

    //点赞
    public static final String ADD_CIRCLR_URL = "circle/verify/v1/addCircleGreat";
    //取消点赞
    public static final String CANCLE_CIRCLE_URL = "circle/verify/v1/cancelCircleGreat?circleId=%s";

    //评论列表
    public static final String DISCUSS_Url ="commodity/v1/CommodityCommentList?commodityId=%d&page=%d&count=%d";

    //查询购物车
    public static final String SELECTCAR_URL = "order/verify/v1/findShoppingCart";

    //加入购物车
    public static final String ADDCAR_URL = "order/verify/v1/syncShoppingCart";


    //创建订单
    public static final String  CREAT_ORDER_URL = "order/verify/v1/createOrder";

    //地址列表
    public static final String SITE_URL = "user/verify/v1/receiveAddressList";

    //新增用户地址
    public static final String ADD_SITE = "user/verify/v1/addReceiveAddress";

    //设置默认地址------post
    public static final String DEFAULT_ADDRESS_URL = "user/verify/v1/setDefaultReceiveAddress";

    //根据订单状态查询订单信息
    public static final String SELECT_ORDER_URL = "order/verify/v1/findOrderListByStatus?status=%d&page=%d&count=%d";


    //修改收货地址-------put
    public static final String UPDATE_ADDRESS_URL = "user/verify/v1/changeReceiveAddress";

    //我的圈子--------get
    public static final String MYCIRCLE_URL = "circle/verify/v1/findMyCircleById?page=%d&count=%d";

    //修改昵称---put
    public static final String UPDATE_NAME = "user/verify/v1/modifyUserNick";

    //修改密码----put
    public static final String UPDATE_PWD = "user/verify/v1/modifyUserPwd";

    //支付----post
    public static final String PAY_URL = "order/verify/v1/pay";

    //确认收货-------put
    public static final String DELIVERY_URL = "order/verify/v1/confirmReceipt";

    //评论----post
    public static final String TAKE_URL = "commodity/verify/v1/addCommodityComment";

    //发布圈子---post
    public static final String ADD_CIRLE = "circle/verify/v1/releaseCircle";

    //查询用户钱包----get
    public static final String SELECT_PAY = "user/verify/v1/findUserWallet?page=%d&count=%d";

    //查询个人资料--put
    public static final String SELECT_MY = "user/verify/v1/getUserById";

    //删除订单----delete
    public static final String DELETE_ORDER_URL = "order/verify/v1/deleteOrder?orderId=%s";
}
