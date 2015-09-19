package mx.shellcore.android.navigationdrawer.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mx.shellcore.android.navigationdrawer.R;

public class ArticleFragment extends Fragment {

    public static final String ARG_ARTICLES_NUMBER = "articles_number";


    public ArticleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_article, container, false);
        int i = getArguments().getInt(ARG_ARTICLES_NUMBER);
        String article = getResources().getStringArray(R.array.tags)[i];
        Log.e("TAG", article);

        getActivity().setTitle(article);
        TextView headline = (TextView) rootView.findViewById(R.id.headline);
        headline.append(" " + article);

        return rootView;
    }
}
