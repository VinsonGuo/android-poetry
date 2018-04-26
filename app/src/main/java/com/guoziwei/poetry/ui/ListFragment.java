package com.guoziwei.poetry.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guoziwei.poetry.R;

import java.util.List;

/**
 * Created by guoziwei on 2017/12/16.
 */

public abstract class ListFragment<T> extends Fragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    public static final int PAGE_COUNT = 10;

    protected int mPage = 1;
    protected RecyclerView mRvList;
    protected SwipeRefreshLayout mRefreshLayout;
    protected View mLoadView;
    protected BaseQuickAdapter<T, ? extends BaseViewHolder> mAdapter;
    private View mErrorPanel;
    private View mProgressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        mRefreshLayout = (SwipeRefreshLayout) v;
        int color = ContextCompat.getColor(getContext(), R.color.colorAccent);
        mRefreshLayout.setColorSchemeColors(color);
        mRvList = (RecyclerView) v.findViewById(R.id.rv_list);
        setManager();
        mLoadView = v.findViewById(R.id.load_view);
        mErrorPanel = mLoadView.findViewById(R.id.error_text);
        mProgressBar = mLoadView.findViewById(R.id.progress_bar);
        mErrorPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoadView.setVisibility(View.VISIBLE);
                mErrorPanel.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
                loadData();
            }
        });
        mAdapter = getAdapter();
        if (mAdapter.isLoadMoreEnable()) {
            mAdapter.setOnLoadMoreListener(this, mRvList);
            mRvList.setAdapter(mAdapter);
        } else {
            mAdapter.bindToRecyclerView(mRvList);
        }
        View noDataView = inflater.inflate(R.layout.view_empty, mRvList, false);
        mAdapter.setEmptyView(noDataView);
        mRefreshLayout.setOnRefreshListener(this);
        loadData();
        return v;
    }

    public abstract BaseQuickAdapter<T, ? extends BaseViewHolder> getAdapter();

    protected abstract void loadData();

    /**
     * 子类加载成功调用
     */
    protected void loadDataSuccess(List<T> list) {
        mLoadView.setVisibility(View.GONE);
        mRefreshLayout.setRefreshing(false);
        if (mPage == 1) {
            mAdapter.setNewData(list);
        } else {
            mAdapter.addData(list);
        }
        mAdapter.loadMoreComplete();
        if (mAdapter.isLoadMoreEnable()
                && (list == null || list.size() < getPageCount())) {
            mAdapter.loadMoreEnd(true);
        }
    }

    /**
     * 子类加载失败调用
     */
    protected void loadFailed() {
        loadFailed(mAdapter.getData().size() == 0);
    }

    protected void loadFailed(boolean showErrorView) {
        if (showErrorView) {
            mLoadView.setVisibility(View.VISIBLE);
            mErrorPanel.setVisibility(View.VISIBLE);
        }
        mProgressBar.setVisibility(View.GONE);

        mRefreshLayout.setRefreshing(false);
        if (mAdapter.isLoadMoreEnable()) {
            if (mPage != 1) {
                mAdapter.loadMoreFail();
                mPage--; // 还原mPage
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    protected void setManager() {
        mRvList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        loadData();
    }

    @Override
    public void onLoadMoreRequested() {
        mPage++;
        loadData();
    }


    protected int getPageCount() {
        return PAGE_COUNT;
    }
}