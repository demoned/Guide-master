package com.demons.guide_master;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.demons.guide.NewbieGuide;
import com.demons.guide.core.Controller;
import com.demons.guide.listener.OnGuideChangedListener;
import com.demons.guide.listener.OnLayoutInflatedListener;
import com.demons.guide.listener.OnPageChangedListener;
import com.demons.guide.model.GuidePage;
import com.demons.guide.model.HighLight;
import com.demons.guide.model.RelativeGuide;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.tv);
        TextView tvBottom = (TextView) findViewById(R.id.tv_bottom);
        final Button button = (Button) findViewById(R.id.btn);
        View anchor = findViewById(R.id.ll_anchor);

        Animation enterAnimation = new AlphaAnimation(0f, 1f);
        enterAnimation.setDuration(600);
        enterAnimation.setFillAfter(true);

        Animation exitAnimation = new AlphaAnimation(1f, 0f);
        exitAnimation.setDuration(600);
        exitAnimation.setFillAfter(true);

        //新增多页模式，即一个引导层显示多页引导内容
        NewbieGuide.with(this)
                .setShowCounts(1)
                .setLabel("page")//设置引导层标示区分不同引导层，必传！否则报错
//                .anchor(anchor)
                .setOnGuideChangedListener(new OnGuideChangedListener() {
                    @Override
                    public void onShowed(Controller controller) {
                        Log.e(TAG, "NewbieGuide onShowed: ");
                        //引导层显示
                    }

                    @Override
                    public void onRemoved(Controller controller) {
                        Log.e(TAG, "NewbieGuide  onRemoved: ");
                        //引导层消失（多页切换不会触发）
                    }
                })
                .setOnPageChangedListener(new OnPageChangedListener() {

                    @Override
                    public void onPageChanged(int page) {
                        //引导页切换，page为当前页位置，从0开始
                        Toast.makeText(MainActivity.this, "引导页切换：" + page, Toast.LENGTH_SHORT).show();

                    }
                })
                .alwaysShow(true)//是否每次都显示引导层，默认false，只显示一次
                .addGuidePage(//添加一页引导页
                        GuidePage.newInstance()//创建一个实例
                                .addHighLight(button)//添加高亮的view
                                .addHighLight(tvBottom,
                                        new RelativeGuide(R.layout.view_relative_guide, Gravity.TOP, 100) {
                                            @Override
                                            protected void offsetMargin(MarginInfo marginInfo, ViewGroup viewGroup, View view) {
                                                marginInfo.leftMargin += 100;
                                            }
                                        })
                                .setLayoutRes(R.layout.view_guide)//设置引导页布局
                                .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {

                                    @Override
                                    public void onLayoutInflated(View view, Controller controller) {
                                        //引导页布局填充后回调，用于初始化
                                        TextView tv = view.findViewById(R.id.textView2);
                                        tv.setText("我是动态设置的文本");
                                    }
                                })
                                .setEnterAnimation(enterAnimation)//进入动画
                                .setExitAnimation(exitAnimation)//退出动画
                )
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(tvBottom, HighLight.Shape.RECTANGLE, 20)
                        .setLayoutRes(R.layout.view_guide_custom, R.id.iv)//引导页布局，点击跳转下一页或者消失引导层的控件id
                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view, final Controller controller) {
                                view.findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        controller.showPreviewPage();
                                    }
                                });
                            }
                        })
                        .setEverywhereCancelable(false)//是否点击任意地方跳转下一页或者消失引导层，默认true
                        .setBackgroundColor(getResources().getColor(R.color.colorAccent))//设置背景色，建议使用有透明度的颜色
                        .setEnterAnimation(enterAnimation)//进入动画
                        .setExitAnimation(exitAnimation)//退出动画
                )
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(tvBottom)
                        .setLayoutRes(R.layout.view_guide_dialog)
                )
                .show();//显示引导层(至少需要一页引导页才能显示)
    }
}