package com.prograpy.app1.appdev1.mypage;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.prograpy.app1.appdev1.IntroActivity;
import com.prograpy.app1.appdev1.R;
import com.prograpy.app1.appdev1.utils.PreferenceData;


public class MyPageFragment extends Fragment implements View.OnClickListener {

    private TextView txtEmail;
    private TextView txtNickName;

    private TextView txtLogout;

    private RelativeLayout txtNotice;
    private RelativeLayout txtSend;
    private RelativeLayout txtFaq;
    private RelativeLayout txtDev;


    public static MyPageFragment createFragment(){

        Bundle bundle = new Bundle();

        MyPageFragment fragment = new MyPageFragment();
        fragment.setArguments(bundle);

        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        txtEmail = (TextView) view.findViewById(R.id.text_email_info);
        txtEmail.setText(PreferenceData.getKeyUserEmail());

        txtNickName = (TextView) view.findViewById(R.id.text_nick_info);
        txtNickName.setText(PreferenceData.getKeyUserName());

        txtLogout = (TextView) view.findViewById(R.id.text_logout);
        txtLogout.setOnClickListener(this);

        txtNotice = (RelativeLayout) view.findViewById(R.id.text_notice);
        txtNotice.setOnClickListener(this);

        txtSend = (RelativeLayout) view.findViewById(R.id.text_send);
        txtSend.setOnClickListener(this);

        txtFaq = (RelativeLayout) view.findViewById(R.id.text_help);
        txtFaq.setOnClickListener(this);

        txtDev = (RelativeLayout) view.findViewById(R.id.text_dev);
        txtDev.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.text_logout:

                PreferenceData.setKeyAutoLogin(false);
                PreferenceData.setKeyLoginSuccess(false);
                PreferenceData.setKeyUserId("");
                PreferenceData.setKeyUserPw("");
                PreferenceData.setKeyUserEmail("");
                PreferenceData.setKeyUserName("");

                Intent intent = new Intent(getContext(), IntroActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();

                break;


            case R.id.text_notice:
                Toast.makeText(getContext(), "추후 오픈 예정입니다.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.text_help:
                Toast.makeText(getContext(), "추후 오픈 예정입니다.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.text_dev:
                CustomPopUp dialog = new CustomPopUp(getContext());
                dialog.show();
                break;

            case R.id.text_send:

                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/text");
                String[] address = {"progyb3@daum.net"};
                email.putExtra(Intent.EXTRA_EMAIL, address);
                startActivity(email);

        }


    }

}
