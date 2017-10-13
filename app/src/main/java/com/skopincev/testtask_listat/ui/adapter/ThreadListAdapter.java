package com.skopincev.testtask_listat.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skopincev.testtask_listat.R;
import com.skopincev.testtask_listat.model.Data;


import java.util.List;
import java.util.Locale;

/**
 * Created by skopi on 12.10.2017.
 */

public class ThreadListAdapter extends RecyclerView.Adapter<ThreadListAdapter.ThreadItemHolder> {

    private static final String TAG = ThreadListAdapter.class.getSimpleName();

    /**
     * Used for choose the right view type(odd or even)
     */
    private interface VIEW_TYPE{
        int EVEN_TYPE = 1;
        int ODD_TYPE = 2;
    }

    class ThreadItemHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle;
        private TextView tvMessage;

        public ThreadItemHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvMessage = (TextView) itemView.findViewById(R.id.tv_message);
        }

        public void bind(Data data){
            int threadId = data.getCalculationThreadId();
            int primeNumber = data.getPrimeNumber();

            tvTitle.setText(String.format(Locale.getDefault(),
                    "%s %d",
                    itemView.getContext().getString(R.string.item_title), threadId));
            tvMessage.setText(String.format(Locale.getDefault(),
                    "%s %d",
                    itemView.getContext().getString(R.string.item_massage), primeNumber));
        }
    }

    private List<Data> mItems;
    private LayoutInflater mInflater;

    public ThreadListAdapter(Context context, List<Data> items){
        Log.d(TAG, "ThreadListAdapter: Created");

        mInflater = LayoutInflater.from(context);
        mItems = items;
    }

    public void addItem(Data data){
        mItems.add(data);
        Log.d(TAG, "addItem: Item " + data.toString() + "\n added to list");
        notifyItemInserted(mItems.size());
    }

    public void clear() {
        mItems.clear();
        Log.d(TAG, "clear: List was been cleared");
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mItems.get(position) != null){
            if (mItems.get(position).getCalculationThreadId() % 2 != 0)
                return VIEW_TYPE.EVEN_TYPE;
            else
                return VIEW_TYPE.ODD_TYPE;
        } else
            return super.getItemViewType(position);
    }

    @Override
    public ThreadItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType){
            case VIEW_TYPE.EVEN_TYPE:{
                view = mInflater.inflate(R.layout.item_even_thread, parent, false);
                break;
            }
            case VIEW_TYPE.ODD_TYPE:{
                view = mInflater.inflate(R.layout.item_odd_thread, parent, false);
                break;
            }
        }
        ThreadItemHolder holder = new ThreadItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ThreadItemHolder holder, int position) {
        Data data;
        if (mItems != null){
            data = mItems.get(position);
            if (data != null){
                holder.bind(data);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mItems != null)
            return mItems.size();
        else
            return 0;
    }

}
