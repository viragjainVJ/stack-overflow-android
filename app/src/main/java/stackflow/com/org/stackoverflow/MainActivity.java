package stackflow.com.org.stackoverflow;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    private String url = "http://www.yudiz.com/blog/";
    private ArrayList<String> mQuestionTagsList = new ArrayList<>();
    private ArrayList<String> mQuestionTimeList = new ArrayList<>();
    private ArrayList<String> mQuestionList = new ArrayList<>();
    private Button getBtn;
    private TextView result;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
         mRecyclerView = (RecyclerView)findViewById(R.id.act_recyclerViex);
        new Description().execute();

        /*result = (TextView) findViewById(R.id.result);
        getBtn = (Button) findViewById(R.id.getBtn);
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWebsite();
            }
        });
        getWebsite();*/
    }
    private void getWebsite() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {
                    Document doc = Jsoup.connect("http://www.stackoverflow.com/").get();
                    String title = doc.title();
                    Elements links = doc.select("div[class=question-summary narrow]");
                    int mElementSize = links.size();

                    builder.append(title).append("\n");

                    for (int i = 0; i < mElementSize; i++) {
                        /*Elements link_data = doc.select("div[class=summary]").select("a[href]").eq(i);*/
                        Elements link_data = doc.select("div[class=summary]").select("h3").select("a[href]").eq(i);
                        builder.append("\n").append("Link : ").append(i)
                                .append("\n").append("Text : ").append(link_data.text());
                    }
                } catch (IOException e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(builder.toString());
                    }
                });
            }
        }).start();
    }
    private class Description extends AsyncTask<Void, Void, Void> {
        String desc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Android Basic JSoup Tutorial");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document mStackOverflow = Jsoup.connect("http://www.stackoverflow.com/").get();
                // Using Elements to get the Meta data
                Elements mElementDataSize = mStackOverflow.select("div[class=question-summary narrow]");
                // Locate the content attribute
                int mElementSize = mElementDataSize.size();

                for (int i = 0; i < mElementSize; i++) {
                    Elements mQuestion = mStackOverflow.select("div[class=summary]").select("h3").select("a[href]").eq(i);
                    String mQuestionLink = mQuestion.text();



                    Elements mQuestionTags = mStackOverflow.select("div[class=summary]").eq(i).select("div > a[class=post-tag]");
                    String mQuestionTagsLink = mQuestionTags.text();

                    Elements mQuestionTime = mStackOverflow.select("div[class=summary]").select("div[class=started]").select("a[class=started-link]").eq(i);
                    String mQuestionTimeLink = mQuestionTime.text();

                    mQuestionTagsList.add(mQuestionTagsLink);
                    mQuestionTimeList.add(mQuestionTimeLink);
                    mQuestionList.add(mQuestionLink);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView

            /*RecyclerView mRecyclerView = (RecyclerView)findViewById(R.id.act_recyclerview);*/

            DataAdapter mDataAdapter = new DataAdapter(MainActivity.this, mQuestionList, mQuestionTagsList, mQuestionTimeList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mDataAdapter);

            mProgressDialog.dismiss();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
