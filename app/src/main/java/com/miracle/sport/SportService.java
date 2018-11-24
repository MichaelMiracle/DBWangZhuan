package com.miracle.sport;

import com.miracle.base.network.ZResponse;
import com.miracle.michael.common.bean.ArticleCommentBean;
import com.miracle.michael.common.bean.ArticleDetailBean;
import com.miracle.michael.common.bean.NewsDetailBean;
import com.miracle.sport.community.bean.CircleBean;
import com.miracle.sport.community.bean.MyCircleBean;
import com.miracle.sport.community.bean.PostBean;
import com.miracle.sport.community.bean.PostDetailBean;
import com.miracle.sport.home.bean.ChannerlKey;
import com.miracle.sport.home.bean.FishListItem;
import com.miracle.sport.home.bean.Football;
import com.miracle.sport.home.bean.HomeCommentBean;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Michael on 2018/10/27 19:50 (星期六)
 */
public interface SportService {


    /**
     * 帖子列表
     *
     * @param type     rm 热门，传空 最新
     * @param class_id 圈子的帖子 ，传空 所有帖子，
     * @param page     当前页
     * @param pageSize 每页数量
     */
    @Headers({"BaseUrl:zh"})
    @POST("home/shenghuo/tieList")
    Call<ZResponse<List<PostBean>>> getPostList(@Query("type") String type, @Query("class_id") Integer class_id, @Query("page") int page, @Query("pageSize") int pageSize);

    /**
     * 帖子详情
     */
    @Headers({"BaseUrl:zh"})
    @POST("home/shenghuo/circleDetail")
    Call<ZResponse<PostDetailBean>> getPostDetail(@Query("id") int id);


    /**
     * 发帖
     */
    @Headers({"BaseUrl:zh"})
    @Multipart
    @POST("home/shenghuo/sendTiezi")
    Call<ZResponse> publishPost(@Query("class_id") int class_id, @Query("title") String title, @Query("content") String content, @Part() List<MultipartBody.Part> imgs);

    /**
     * 发帖
     */
    @Headers({"BaseUrl:zh"})
    @POST("home/shenghuo/sendTiezi")
    Call<ZResponse> publishPost(@Query("class_id") int class_id, @Query("title") String title, @Query("content") String content);
    /**
     * 帖子评论列表
     */
    @Headers({"BaseUrl:zh"})
    @POST("home/shenghuo/tieziCommentList")
    Call<ZResponse<List<PostDetailBean.CommentBean>>> getPostCommentList(@Query("create_id") int id, @Query("page") int page, @Query("pageSize") int pageSize);

    /**
     * 帖子发评论
     * type 评论帖子 = 1 /评论评论 = 0
     */
    @Headers({"BaseUrl:zh"})
    @POST("home/shenghuo/sendTieziComment")
    Call<ZResponse> sendPostComment(@Query("create_id") int create_id, @Query("to_user_id") int to_user_id, @Query("type") int type, @Query("content") String content);


    /**
     * 帖子点赞
     *
     * @param type 1帖子，0评论
     */
    @Headers({"BaseUrl:zh"})
    @POST("home/shenghuo/clickTiezi")
    Call<ZResponse> likePost(@Query("create_id") int create_id, @Query("click") int click, @Query("type") int type);


//    /**
//     * 首页title 类型
//     */
//    @Headers({"BaseUrl:zh"})
//    @POST("home/Goodcaipiao/type")
//    Call<ZResponse<List<ChannerlKey>>> getSearchKeys();

    /**
     * 首页title 类型
     */
    @Headers({"BaseUrl:mi"})
    @POST("life")
    Call<ZResponse<List<ChannerlKey>>> getSearchKeys();

    /**
     * 首页列表
     */
    @Headers({"BaseUrl:zh"})
    @POST("home/fish/flashList")
    Call<ZResponse<List<Football>>> getNewsList(@Query("class_id") int class_id, @Query("page") int page, @Query("pageSize") int pageSize);
    //    Call<ZResponse<List<Football>>> getNewsList(@Query("class_id") int class_id, @Query("page") int page, @Query("pageSize") int pageSize);

    /**
     * 首页列表
     */
    @Headers({"BaseUrl:mi"})
    @POST("life_content")
    Call<ZResponse<List<Football>>> getNewsSpotrList(@Query("class_id") int class_id, @Query("page") int page, @Query("pageSize") int pageSize);
    //    Call<ZResponse<List<Football>>> getNewsList(@Query("class_id") int class_id, @Query("page") int page, @Query("pageSize") int pageSize);


    /**
     * 列表详情
     */
    @Headers({"BaseUrl:zh"})
    @POST("home/Goodcaipiao/detail")
    Call<ZResponse<NewsDetailBean>> getNewsDetail(@Query("id") int id);

    /**
     * 列表详情
     */
    @Headers({"BaseUrl:zh"})
    @POST("home/Goodcaipiao/flashDetail")
    Call<ZResponse<ArticleDetailBean>> getCommentDetail(@Query("id") int id);

    /**
     * 列表详情
     */
    @Headers({"BaseUrl:mi"})
    @POST("life_row")
    Call<ZResponse<ArticleDetailBean>> getCommentDetailSport(@Query("class_id") int id);

    /**
     * 评论点赞
     *
     * @POST("home/sport/click")
     */
//    @Headers({"BaseUrl:zh"})
//    @POST("home/fish/click")
    @Headers({"BaseUrl:mi"})
    @POST("life_comment_zan")
    Call<ZResponse<String>> setClickClass(@Query("create_id") int create_id, @Query("click") int click, @Query("type") String type);

    /**
     * 删除评论
     * @POST("home/sport/click")
     */
//    @Headers({"BaseUrl:zh"})
//    @POST("home/fish/deleteComment")
    @Headers({"BaseUrl:mi"})
    @POST("life_comment_delete")
    Call<ZResponse<String>> setClickDelete( @Query("id") int id,@Query("create_id") int create_id, @Query("type") String type);

    /**
     * 收藏接口
     */
//    @Headers({"BaseUrl:zh"})
//    @POST("home/fish/collect")
    @Headers({"BaseUrl:mi"})
    @POST("life_comment_chang")
    Call<ZResponse<String>> likeOrDislike(@Query("create_id") int create_id, @Query("type") String type);

    /**
     * 我的收藏接口
     */
//    @Headers({"BaseUrl:zh"})
//    @POST("home/fish/myCollect")
    @Headers({"BaseUrl:mi"})
    @POST("life_ction")
    Call<ZResponse<List<Football>>> getMycollections(@Query("page") int page, @Query("pageSize") int pageSize);

    /**
     * 发评论
     */
    @Headers({"BaseUrl:zh"})
    @POST("home/fish/sendComment")
    Call<ZResponse<String>> sendHomeCommet(@Query("create_id") int create_id, @Query("content") String content);

    /**
     * 评论列表list
     */
//    @Headers({"BaseUrl:zh"})
//    @POST("home/fish/commentList")
    @Headers({"BaseUrl:mi"})
    @POST("life_comment_list")
    Call<ZResponse<List<ArticleCommentBean>>> getCommetList(@Query("id") int id, @Query("page") int page, @Query("pageSize") int pageSize);

//    /**
//     * 发评论
//     */
//    @Headers({"BaseUrl:zh"})
//    @POST("home/comment/sendComment")
//    Call<ZResponse<ArticleCommentBean>> sendCommentCommet(@Query("create_id") int id, @Query("content") String content, @Query("to_user_id") String to_user_id, @Query("type") String type);

    /**
     * 发评论
     */
    @Headers({"BaseUrl:mi"})
    @POST("life_comment")
    Call<ZResponse<ArticleCommentBean>> sendCommentCommet(@Query("create_id") int id, @Query("content") String content,@Query("to_user_id") String to_user_id,@Query("type") String type);


    /**
     * 获取评论列表
     */
    @Headers({"BaseUrl:zh"})
    @POST("home/sport/sportComment")
    Call<ZResponse<List<HomeCommentBean>>> getCommentList(@Query("create_id") int create_id);


    /**
     * 我的发帖
     */
    @Headers({"BaseUrl:zh"})
    @POST("home/shenghuo/myPost")
    Call<ZResponse<List<PostBean>>> getMyPostList(@Query("page") int page, @Query("pageSize") int pageSize);

    /**
     * 我的回帖
     */
    @Headers({"BaseUrl:zh"})
    @POST("home/Goodcaipiao/myReply")
    Call<ZResponse<List<HomeCommentBean>>> getReplyList();

    /**
     * 我的圈子
     */
    @Headers({"BaseUrl:zh"})
    @POST("home/shenghuo/myCircle")
    Call<ZResponse<List<MyCircleBean>>> getMyCircleList();

    /**
     * 圈子列表
     */
    @Headers({"BaseUrl:zh"})
    @POST("home/shenghuo/circleType")
    Call<ZResponse<List<CircleBean>>> getCircleList();

    /**
     * 收藏圈子
     * type传"qx"取消收藏圈子
     */
    @Headers({"BaseUrl:zh"})
    @POST("home/shenghuo/addSq")
    Call<ZResponse> addCircle(@Query("class_id") int class_id, @Query("type") int type);


}
