package com.animatedcardcontainer;

import android.view.View;
import android.widget.TextView;

import com.feyolny.view.AnimatedCardContainer;
import com.feyolny.view.ContainerAdapter;

/**
 * Created by feyolny on 2019/5/9.
 * Describtion:动画卡片容器的适配器，做一些数据绑定操作
 */

public class AnimationCardAdapter extends ContainerAdapter {

    @Override
    public void bindFrontView(View frontView, final AnimatedCardContainer animatedCardContainer) {
        if (frontView != null) {
            TextView checkDetail = frontView.findViewById(R.id.tv_check_detail);
            checkDetail.setText("查看详情");
            checkDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    animatedCardContainer.open();
                }
            });
        }
    }

    @Override
    public void bindBackView(View backView, final AnimatedCardContainer animatedCardContainer) {
        if (backView != null) {
            TextView tvDetail = backView.findViewById(R.id.tv_detail);
            tvDetail.setText("掌握食品安全法律法规及知识，提升个人素质增加竞争力。拥有优质食品安全管理员，对食品的安全保证更权威顾客更满意，大大提高食品的安全性。");
        }
    }
}
