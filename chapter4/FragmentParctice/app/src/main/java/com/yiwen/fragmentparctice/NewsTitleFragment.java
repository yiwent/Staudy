package com.yiwen.fragmentparctice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/3/23.
 */

public class NewsTitleFragment extends Fragment {
    private boolean isTwoPane;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_title_frag, container, false);
        RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.new_title_RecyleerView);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        NewsAdapter newsAdapter=new NewsAdapter(getnews());
        recyclerView.setAdapter(newsAdapter);
        return view;

    }
    private  List<News>getnews(){
        List<News> newsList=new ArrayList<>();
        for (int i=0;i<50;i++){
            News news=new News();
            news.setTitle("This is title"+i);
            news.setContent(getRandomLengthContent("This is content"+i));
            newsList.add(news);
        }
        return newsList;
    }

    private String getRandomLengthContent(String content) {
        Random randon=new Random();
        int length=randon.nextInt(20)+1;
        StringBuilder stringbuilder=new StringBuilder();
        for (int i=0;i<length;i ++){
            stringbuilder.append(content);
        }
//        Log.d("content ", "getRandomLengthContent: "+content);
        return stringbuilder.toString();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.news_content_layout) != null) {
            isTwoPane = true;
        } else {
            isTwoPane = false;
        }
    }

    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
        private List<News> mlist;

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            News news=mlist.get(position);
            holder.newsText.setText(news.getTitle());
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
            final ViewHolder holder=new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    News news=mlist.get(holder.getAdapterPosition());
                    if(isTwoPane){
                       NewContentFragment newContentFragment=(NewContentFragment)
                               getFragmentManager().findFragmentById(R.id.news_content_fragment);
                        newContentFragment.refresh(news.getTitle(),news.getContent());
                    }else {
                        NewsContentActivity.actionStar(getActivity(),news.getTitle(),news.getContent());
                    }
                }
            });
            return holder;
        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }

        public NewsAdapter(List<News> mlist) {
            this.mlist = mlist;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView newsText;

            public ViewHolder(View itemView) {
                super(itemView);
                newsText = (TextView) itemView.findViewById(R.id.new_title);
            }
        }
    }
}
