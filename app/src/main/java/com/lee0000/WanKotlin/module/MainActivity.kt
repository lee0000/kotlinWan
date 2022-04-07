package com.lee0000.WanKotlin.module

import android.view.Gravity
import androidx.navigation.*
import com.blankj.utilcode.util.ToastUtils
import com.lee0000.WanKotlin.module.base.BaseActivity
import com.umeng.analytics.MobclickAgent
import kotlinx.android.synthetic.main.wan_activity_main.*
import kotlin.system.exitProcess
import com.lee0000.WanKotlin.R
import com.lee0000.WanKotlin.module.home.MainFragment
import com.lee0000.WanKotlin.module.public.PublicFragment
import com.lee0000.WanKotlin.widget.navigation.FragmentNavigatorHideShow


/**
author: Lee
date:   2022/3/27
 */
class MainActivity: BaseActivity() {

    override fun getLayoutResId(): Int {
        return R.layout.wan_activity_main
    }

    override fun initView() {

        iv_menu.setOnClickListener {
            drawer_layout.openDrawer(Gravity.LEFT)
        }

        val navController: NavController =
            Navigation.findNavController(this, R.id.nav_host_fragment)
        val provider = navController.navigatorProvider
        val fragmentNavigatorHideShow = FragmentNavigatorHideShow(this, supportFragmentManager, R.id.nav_host_fragment)
        provider.addNavigator(fragmentNavigatorHideShow)
        val navDestination = initNavGraph(provider, fragmentNavigatorHideShow)
        navController.graph = navDestination
        bottom_navigation?.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.home_fragment -> {
                    tv_title.text = "首页"
                    navController.navigate(item.itemId)
                }
                R.id.public_fragment -> {
                    tv_title.text = "公众号"
                    navController.navigate(item.itemId)
                }
                else -> {}
            }

            true
        }
    }

    override fun initData() {

    }

    private fun initNavGraph(provider: NavigatorProvider, fragmentNavigator: FragmentNavigatorHideShow): NavGraph {

        val navGraph = NavGraph(NavGraphNavigator(provider));

        //用自定义的导航器来创建目的地
        val destination1 = fragmentNavigator.createDestination()
        destination1.id = R.id.home_fragment
        destination1.className = MainFragment::class.java.canonicalName
        navGraph.addDestination(destination1)

        val destination2 = fragmentNavigator.createDestination()
        destination2.id = R.id.public_fragment
        destination2.className = PublicFragment::class.java.canonicalName
        navGraph.addDestination(destination2)

        navGraph.startDestination = destination1.id

        return navGraph
    }

    // 双击退出的时间标识
    private var lastClickOutTime = 0L
    override fun onBackPressed() {

        val nowClickOutTime = System.currentTimeMillis()
        if (nowClickOutTime - lastClickOutTime < 2000){

            MobclickAgent.onKillProcess(this)
            finish()
            exitProcess(0)
        }else{
            lastClickOutTime = nowClickOutTime
            ToastUtils.showShort("再按一次退出程序")
        }

    }

}