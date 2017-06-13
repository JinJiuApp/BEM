package com.junwu.user.business.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jaryjun.common_base.widget.pullrefresh.PullToRefreshBase;
import com.jaryjun.common_base.widget.pullrefresh.PullToRefreshListView;
import com.junwu.common_net.base.LFBaseServiceFragment;
import com.junwu.user.R;
import com.junwu.user.business.model.MainItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页那个比较复杂的页面
 * Created by Glen on 2016/6/1.
 */
public class LFUserMainFragment extends LFBaseServiceFragment  {


    private RadioGroup rg_title_cut;
    private RadioButton rg_recommend,rg_news,rg_attention;
    private ImageView iv_camera;
    private PullToRefreshListView lv_content;
    private MainAdapter mMainAdapter;
    List<MainItemModel> mDatas = new ArrayList<MainItemModel>();//接收服务器返回数据的集合

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_main, null);
        initViews(view);
      /*  initListeners();
        changeViewsPositions();
        init();
        onStartReplaceLogo();*/
        return view;
    }

    private void initViews(View view) {
        rg_title_cut = (RadioGroup) view.findViewById(R.id.rg_title_cut);
        rg_recommend = (RadioButton)view.findViewById(R.id.rg_recommend);
        rg_news = (RadioButton)view.findViewById(R.id.rg_news);
        rg_attention = (RadioButton)view.findViewById(R.id.rg_attention);
        iv_camera = (ImageView)view.findViewById(R.id.iv_camera);
        lv_content = (PullToRefreshListView)view.findViewById(R.id.prlv_content);

        lv_content.setMode(PullToRefreshBase.Mode.BOTH);
        lv_content.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // TODO Auto-generated method stub
                //刷新时重新加载数据的第一页
               // pageNo = 1;
               // loadSupply(false);
            }
            //上拉刷新
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // TODO Auto-generated method stub
                //加载的页面开始累加
             //   pageNo++;
              //  loadSupply(false);
            }

        });
        mMainAdapter = new MainAdapter(getContext(), mDatas);
        lv_content.setAdapter(mMainAdapter);
    }


}
