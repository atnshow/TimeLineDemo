package com.ec.android.test.timelinedemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ec.module.timelinemodule.TimelineView;

import java.util.List;

/**
 * Created by HP-HP on 05-12-2015.
 */
public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.VerticalViewHolder> {

    private List<TimeLineModel> mFeedList;
    private Context mContext;

    public VerticalAdapter(List<TimeLineModel> feedList) {
        mFeedList = feedList;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public VerticalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        View view = View.inflate(parent.getContext(), R.layout.item_timeline, null);

        return new VerticalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VerticalViewHolder holder, int position) {
        TimeLineModel timeLineModel = mFeedList.get(position);

        holder.name.setText("name：" + timeLineModel.getName() + "    age：" + timeLineModel.getAge());
        ////
        final int viewType = TimelineView.getTimeLineViewType(position, getItemCount());

        holder.mTimelineView.drawLine(viewType);
    }

    @Override
    public int getItemCount() {
        return (mFeedList != null ? mFeedList.size() : 0);
    }

    class VerticalViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TimelineView mTimelineView;

        public VerticalViewHolder(View itemView) {
            super(itemView);
            //
            name = (TextView) itemView.findViewById(R.id.tx_name);
            mTimelineView = (TimelineView) itemView.findViewById(R.id.time_marker);
        }
    }
}
