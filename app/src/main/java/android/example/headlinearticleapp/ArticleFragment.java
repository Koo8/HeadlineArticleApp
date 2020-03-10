package android.example.headlinearticleapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ArticleFragment extends Fragment {

    TextView article;
    String[] data;
    final static String ARG_POSITION = "position"; // this constant is used in MainActivity too for pass position in the bundle
    private int currentPosition = -1; // set it to -1 before any click on item happened

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            //if we recreated this Fragment (for instance from a screen rotate)
            //restore the previous article selection by getting it here
            currentPosition = savedInstanceState.getInt(ARG_POSITION);
        }
        //inflate the view for this fragment
        View myFragmentView = inflater.inflate(R.layout.article_fragment_layout, container, false);

        return myFragmentView;
    }

    public void updateArticleView(int position) {
        View v = getView();
        article = (TextView) v.findViewById(R.id.showArticle_tv);
        data = Ipsum.ArticlesArray;
        article.setText(data[position]);
        currentPosition = position;

    }

    // check bundle argument only after onStart stage
    @Override
    public void onStart() {
        super.onStart();
        //During startup, we should check if there are arguments (data)
        //passed to this Fragment. We know the layout has already been
        //applied to the Fragment so we can safely call the method that
        //sets the article text
        Bundle args = getArguments();
        if (args != null) {
            //set the article based on the argument passed in
            updateArticleView(args.getInt(ARG_POSITION));
            Log.d("ArticleFragment***", "CurrentPosition with ARGS " + currentPosition);
        } else if (currentPosition != -1) {
            //set the article based on the saved instance state defined during onCreateView
            updateArticleView(currentPosition);
        }
    }

    // to save the position argument
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // save the current selection for later recreation of the fragment
        outState.putInt(ARG_POSITION, currentPosition);
    }
}
