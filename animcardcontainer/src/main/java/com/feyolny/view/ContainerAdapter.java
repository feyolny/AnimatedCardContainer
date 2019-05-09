package com.feyolny.view;

import android.view.View;

/**
 * Created by feyolny on 2019/5/8.
 * Describtion:抽象适配器
 * 开发者可继承此类
 * 复写方法，做一些前台、后台卡片数据的绑定工作
 */

public abstract class ContainerAdapter {

    public abstract void bindFrontView(View frontView, AnimatedCardContainer animatedCardContainer);

    public abstract void bindBackView(View backView, AnimatedCardContainer animatedCardContainer);

}
