package com.prograpy.app1.appdev1.Search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.prograpy.app1.appdev1.R;
import com.prograpy.app1.appdev1.popup.NetworkProgressDialog;
import com.prograpy.app1.appdev1.view.TopbarView;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    String searchName;
    EditText search;
    ImageButton searchButton;
    private TopbarView topbarView;
    private NetworkProgressDialog networkProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search = (EditText)findViewById(R.id.search);
        searchButton = (ImageButton)findViewById(R.id.searchButton);


        topbarView = (TopbarView) findViewById(R.id.title);
        topbarView.setType(TopbarView.TOPBAR_TYPE.BACK_TITLE);
        topbarView.setTopBarTitle("검색");
        topbarView.setTopMenuBackClick(new TopbarView.ItemClick() {
            @Override
            public void onItemClick() {
                finish();
            }
        });
        networkProgressDialog = new NetworkProgressDialog(this);

       searchButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        searchName = search.getText().toString();

        if (search.getText().toString().isEmpty()) {
            Toast.makeText(SearchActivity.this, "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra("searchname", searchName);
        startActivity(intent);

    }
}
