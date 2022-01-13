package com.ajinkya.androidlauncherapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Ajinkya on 12/01/22.
 */

public class AppsDrawerFragment extends Fragment {

    RecyclerView recyclerView;
    SearchView searchView;
    RecyclerView.Adapter adapter;
    AppsDrawerAdapter appsDrawerAdapter;
    RecyclerView.LayoutManager layoutManager;

    private ArrayList<AppInfo> appInfoArrayList;


    public AppsDrawerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_apps_drawer, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.appDrawer_recylerView);
        appsDrawerAdapter = new AppsDrawerAdapter(getContext());
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(appsDrawerAdapter);
        searchView = view.findViewById(R.id.search_bar);
        setUpSearchView();
    }

    public void setUpSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //appsDrawerAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                appsDrawerAdapter.getFilter().filter(s);
                appsDrawerAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }
}