package stackflow.com.org.stackoverflow;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Virag on 4/25/2018.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {

    private ArrayList<String> mQuestionList = new ArrayList<>();
    private ArrayList<String> mQuestionTagsList = new ArrayList<>();
    private ArrayList<String> mQuestionTimeList = new ArrayList<>();
    private Activity mActivity;
    private int lastPosition = -1;

    public DataAdapter(MainActivity activity, ArrayList<String> mQuestionList, ArrayList<String> mQuestionTagsList, ArrayList<String> mQuestionTimeList) {
        this.mActivity = activity;
        this.mQuestionList = mQuestionList;
        this.mQuestionTagsList = mQuestionTagsList;
        this.mQuestionTimeList = mQuestionTimeList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_blog_title, tv_blog_author, tv_blog_upload_date;

        public MyViewHolder(View view) {
            super(view);
            tv_blog_title = (TextView) view.findViewById(R.id.row_tv_blog_title);
            tv_blog_author = (TextView) view.findViewById(R.id.row_tv_blog_author);
            tv_blog_upload_date = (TextView) view.findViewById(R.id.row_tv_blog_upload_date);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_data, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_blog_title.setText(mQuestionList.get(position));
        holder.tv_blog_author.setText(mQuestionTagsList.get(position));
        holder.tv_blog_upload_date.setText(mQuestionTimeList.get(position));
    }

    @Override
    public int getItemCount() {
        return mQuestionList.size();
    }
}
