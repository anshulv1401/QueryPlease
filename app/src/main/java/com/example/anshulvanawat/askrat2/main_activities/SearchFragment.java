package com.example.anshulvanawat.askrat2.main_activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anshulvanawat.askrat2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search, container, false);

        MainActivity.CURRENT_FRAGMENT=MainActivity.SEARCH_FRAGMENT;//used to change the action bar option
        MainActivity.refreshActionBarMenu(getActivity());//user defined method

        return view;
    }

}
