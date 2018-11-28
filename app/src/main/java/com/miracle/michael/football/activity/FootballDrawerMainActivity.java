package com.miracle.michael.football.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.BaseActivity;
import com.miracle.base.GOTO;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.base.bean.UserInfoBean;
import com.miracle.base.network.GlideApp;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.base.network.ZService;
import com.miracle.base.util.CommonUtils;
import com.miracle.databinding.ActivityFootballDrawerBinding;
import com.miracle.michael.common.bean.DrawerItemBean;
import com.miracle.michael.football.fragment.FootballF1;
import com.miracle.michael.football.fragment.FootballF3;
import com.miracle.michael.football.fragment.FootballF5;
import com.miracle.sport.SportService;
import com.miracle.sport.community.bean.MyCircleBean;
import com.miracle.sport.community.bean.PostBean;
import com.miracle.sport.community.fragment.CommunityFragment;
import com.miracle.sport.home.bean.Football;
import com.miracle.sport.home.fragment.HomeFragment;
import com.miracle.sport.me.activity.DDZMyCircleActivity;
import com.miracle.sport.me.activity.DDZMyPostActivity;
import com.miracle.sport.me.activity.MyCollectionsActivity;
import com.miracle.sport.onetwo.frag.FragmentLotteryMain;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Michael on 2018/10/18 19:52 (星期四)
 */
public class FootballDrawerMainActivity extends BaseActivity<ActivityFootballDrawerBinding> {
    private UserInfoBean userInfo;
    private DrawerItemAdapter mAdapter;
    private FragmentManager fragmentManager;
    private Fragment fragment1, fragment2, fragment3, fragment4;

    private DrawerItemBean[] drawerItems = {new DrawerItemBean(CommonUtils.getString(R.string.icon_tab_home), "首页"),
            new DrawerItemBean(CommonUtils.getString(R.string.icon_tab_auction), "资讯"),
            new DrawerItemBean(CommonUtils.getString(R.string.icon_chatroom), "发现"),
            new DrawerItemBean(CommonUtils.getString(R.string.icon_order_manage), "聊天室"),
            new DrawerItemBean(CommonUtils.getString(R.string.icon_settings), "设置")
    };

    @Override
    public int getLayout() {
        return R.layout.activity_football_drawer;
    }

    @Override
    public void initView() {
        hideTitle();
        showContent();
        binding.drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        });

        binding.recyclerView.setAdapter(mAdapter = new DrawerItemAdapter());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mAdapter.setNewData(Arrays.asList(drawerItems));
        fragmentManager = getSupportFragmentManager();
        fragment1 = new FragmentLotteryMain().setDrawer(binding.drawerLayout);
        fragment2 = new HomeFragment().setDrawer(binding.drawerLayout);
        fragment3 = new CommunityFragment().setDrawer(binding.drawerLayout);
//        fragment4 = new FootballF4().setDrawer(binding.drawerLayout);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, fragment1);
        fragmentTransaction.add(R.id.container, fragment2);
        fragmentTransaction.add(R.id.container, fragment3);
        fragmentTransaction.hide(fragment2);
        fragmentTransaction.hide(fragment3);
        lastFragment = fragment1;
        fragmentTransaction.commitNow();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (CommonUtils.getUser() != null)
            reqData();
    }

    private void reqData() {
        ZClient.getService(ZService.class).getUserInfo().enqueue(new ZCallback<ZResponse<UserInfoBean>>() {
            @Override
            public void onSuccess(ZResponse<UserInfoBean> data) {
                userInfo = data.getData();
                binding.tvName.setText(userInfo.getNickname());
                GlideApp.with(mContext).load(userInfo.getImg()).placeholder(R.mipmap.default_head).into(binding.ivHeadImg);
            }
        });

        ZClient.getService(SportService.class).getMyPostList(1, 10).enqueue(new ZCallback<ZResponse<List<PostBean>>>() {
            @Override
            protected void onSuccess(ZResponse<List<PostBean>> zResponse) {
                if (zResponse != null) {
                    binding.ibmyPost.setText(MessageFormat.format("我的发帖{0}", zResponse.getTotal()));
                }
            }
        });
        ZClient.getService(SportService.class).getMyCircleList().enqueue(new ZCallback<ZResponse<List<MyCircleBean>>>() {
            @Override
            public void onSuccess(ZResponse<List<MyCircleBean>> zResponse) {
                if (zResponse != null) {
                    List<MyCircleBean> data = zResponse.getData();
                    if (data != null && !data.isEmpty()) {
                        binding.ibmyCircle.setText(MessageFormat.format("我的圈子{0}", data.size()));
                    }
                }
            }
        });

        ZClient.getService(SportService.class).getMycollections(1, 10).enqueue(new ZCallback<ZResponse<List<Football>>>() {
            @Override
            protected void onSuccess(ZResponse<List<Football>> zResponse) {
                if (zResponse != null && zResponse.getTotal() > 0) {
                    binding.ibBailManage.setText(MessageFormat.format("我的收藏{0}", zResponse.getTotal()));
                }
            }
        });

    }

    private final class DrawerItemAdapter extends RecyclerViewAdapter<DrawerItemBean> {

        public DrawerItemAdapter() {
            super(R.layout.item_drawer);
        }

        @Override
        protected void convert(BaseViewHolder helper, DrawerItemBean item) {
            helper.setText(R.id.tvIconLeft, item.getIcon());
            helper.setText(R.id.tvText, item.getName());
        }

    }

    @Override
    public void initListener() {
        binding.llMe.setOnClickListener(this);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                        showFragment(fragment1);
                        break;
                    case 1:
                        showFragment(fragment2);
                        break;
                    case 2:
                        showFragment(fragment3);
                        break;
                    case 3:
                        GOTO.ChatActivity(mContext);
                        break;
                    case 4:
                        GOTO.SettingActivity(mContext);
                        break;

                }
                binding.drawerLayout.closeDrawers();
            }
        });

        binding.ibmyCircle.setOnClickListener(this);
        binding.ibmyPost.setOnClickListener(this);
        binding.ibBailManage.setOnClickListener(this);
    }

    private Fragment lastFragment;

    private void showFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.show(fragment);
        fragmentTransaction.hide(lastFragment);
        lastFragment = fragment;
        fragmentTransaction.commitNow();
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llMe:
                if (userInfo == null) {
                    GOTO.LoginActivity(mContext);
                } else {
                    GOTO.MeInfoActivity(mContext, userInfo);
                }
                break;
            case R.id.ibBailManage:
                if (CommonUtils.getUser() == null) {
                    GOTO.LoginActivity(mContext);
                } else {
//                    GOTO.LotteryMyCollectionsActivity();
                    startActivity(new Intent(mContext, MyCollectionsActivity.class));
                }
                break;
            case R.id.ibmyCircle:
                if (CommonUtils.getUser() == null) {
                    GOTO.LoginActivity(mContext);
                } else {
                    startActivity(new Intent(mContext, DDZMyCircleActivity.class));
                }
                break;
            case R.id.ibmyPost:
                if (CommonUtils.getUser() == null) {
                    GOTO.LoginActivity(mContext);
                } else {
                    startActivity(new Intent(mContext, DDZMyPostActivity.class));
                }
                break;
        }
    }

}
