package android.example.headlinearticleapp;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

public class HeadlineFragment extends ListFragment {

    OnHeadlineSelectedListener callback;

    public interface OnHeadlineSelectedListener {
        public void onArticleSelected (int itemPosition);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            // make sure the Container Activity has implemented the interface, otherwise throw error
            callback = (OnHeadlineSelectedListener) context;
        }catch(ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OnHeadlineSelectedListener....");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
// use this built in method to replace the normal inflate method in Fragment class in oncreateView method
        setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1,Ipsum.HeadlinesArray));
    }

    // define some action if it is two_pane screen on the screen in the onStart() method
    @Override
    public void onStart() {
        super.onStart();

        // when in a two-pane layout, set the listview to highlight the list item instead of just simply blinking

        // first define the article detail holding fragment
        Fragment article_fragment = getFragmentManager().findFragmentById(R.id.showArticle_tv);
        // second define Listview in title fragment with the fixed id of "@android/id/list"
        ListView listView = getListView();
        // if both the article fragment and listView exist, then set list view with highlighted single list  item
        if (article_fragment != null && listView != null) { // this make sure it is a two pane screen
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // pass the position variable to the interface - hold onto the position variable
        callback.onArticleSelected(position);

        // set the item clicked to be highlighted, this is only visible when it is two-pane screen
        l.setItemChecked(position, true);
    }
}
