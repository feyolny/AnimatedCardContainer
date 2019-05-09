# AnimatedCardContainer
上下滑动的卡片容器，支持view的引用

![image](https://raw.githubusercontent.com/feyolny/RelatedPictureCollection/master/AnimatedCardContainer/Video_2019-05-09_193219.gif)

# How do you use it in your project?
## Step 1. Add the JitPack repository to your build file

      allprojects{
          repositories {
		       maven { url 'https://jitpack.io' }
		        }
	      }
 
## Step 2. Add the dependency
 
    dependencies {
	          implementation 'com.github.feyolny:AnimatedCardContainer:v1.0.0'
	     }
 
 ## Step 3. Use it in your project
 
### 第一步

   设置frontView,backView的引用
   
    <com.feyolny.view.AnimatedCardContainer
        android:id="@+id/animatedCard"
        android:layout_width="125dp"
        android:layout_height="152dp"
        android:layout_centerInParent="true"
        app:backView="@layout/bottom_view"
        app:duration="500"
        app:frontView="@layout/front_view" />
 
 ### 第二步

    创建一个类(源码中为AnimationCardAdapter)继承ContainerAdapter
    复写bindFrontView、bindBackView，并在其内对数据进行绑定
    最后将容器和适配器进行绑定
 
    例如：
        // 找到卡片容器控件
        animatedCard = findViewById(R.id.animatedCard);
        //初始化适配器，该适配器已经继承ContainerAdapter并重写了相关方法
        cardAdapter = new AnimationCardAdapter();
        //将卡片容器和适配器进行绑定
        animatedCard.setContainerAdapter(cardAdapter);
 

 
 # Related API
 
  参数 | 描述 
  -|-
  frontView|前台引用(图片和按钮)
  backView|后台引用(详细介绍)
  duration|动画的时长
  
  
  方法 | 描述 
   -|-
  open()|展示后台的view
  close()|展示前台view
  setContainerAdapter()|设置适配器
 
  
