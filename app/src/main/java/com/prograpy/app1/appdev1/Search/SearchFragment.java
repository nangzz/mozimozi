package com.prograpy.app1.appdev1.Search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.prograpy.app1.appdev1.R;

public class SearchFragment extends Fragment implements View.OnClickListener {
    String searchName;
    EditText search;
    ImageView searchButton;
    RecyclerView recyclerView;
    RecommandAdapter recommandAdapter;


    public static SearchFragment createFragment(){

        Bundle bundle = new Bundle();

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(bundle);

        return fragment;
    }


    private View.OnClickListener recommandListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(getContext(), SearchResultActivity.class);
            intent.putExtra("searchname", (String)v.getTag());
            startActivity(intent);

        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        search = (EditText) view.findViewById(R.id.search);
        searchButton = (ImageView) view.findViewById(R.id.searchButton);

        searchButton.setOnClickListener(this);

        recyclerView = (RecyclerView)view.findViewById(R.id.recommand_list);
        recommandAdapter = new RecommandAdapter(getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        recommandAdapter.setOnClickListener(recommandListener);
        recyclerView.setAdapter(recommandAdapter);


        return view;


    }

    @Override
    public void onResume() {
        super.onResume();

        search.setText("");
    }

    @Override
    public void onClick(View v) {
        searchName = search.getText().toString();

        if (search.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(getContext(), SearchResultActivity.class);
        intent.putExtra("searchname", searchName);
        startActivity(intent);

    }


}
