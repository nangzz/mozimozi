package com.prograpy.app1.appdev1.popup.info;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.prograpy.app1.appdev1.R;


/**
 * 내가 직접 만든 팝업을 정의하고자 할때는
 * Dialog 클래스를 상속 받아서 만든다
 * Created by SeungJun on 2018-04-02.
 */

public class CustomPopup extends Dialog implements View.OnClickListener { //클래스 상속

    private Context context;

    private Button popupHeartEmp;
    private ImageView popupUrlBtn;
    private ImageView popupRelItem;

    /**
     * 커스텀 팝업 생성자 필수 view 초기화
     *
     * @param context
     */
    public CustomPopup(@NonNull Context context) {
        super(context);
        this.context = context;

        initView();
    }

    public CustomPopup(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;

        initView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * 팝업 뷰를 초기화 해주는 함수
     */
    private void initView() {

        // 다이얼로그 외부 화면 흐리게 표현 하기 위해서 옵션을 적용
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        // 뒷 배경을 흐리게 한다는 flag 이것 이외에도 여러가지가 존재한다
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        // 얼마나 흐리게 할 건지 값
        lpWindow.dimAmount = 0.8f;
        // 해당 옵션을 현재 window 즉, 팝업에 적용 시킨다
        getWindow().setAttributes(lpWindow);

        // 시스템 뒤로가기 키 방지
        setCancelable(false);
        setCanceledOnTouchOutside(true);

        setContentView(R.layout.custom_popup_info); //뷰 연결

        popupHeartEmp = (Button) findViewById(R.id.popup_heart_btn);
        popupUrlBtn = (ImageView) findViewById(R.id.popup_url_btn);
        popupRelItem = (ImageView) findViewById(R.id.popup_rel_item);

        popupHeartEmp.setOnClickListener(this);
        popupRelItem.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.popup_heart_btn:

                break;

            case R.id.popup_url_btn:

                break;

            case R.id.popup_rel_item:

                break;
        }
    }
}
