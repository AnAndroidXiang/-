package com.axiang.smallyellowduck.homepage.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.axiang.smallyellowduck.R;
import com.axiang.smallyellowduck.about.AboutPreferenceActivity;
import com.axiang.smallyellowduck.adapters.MainAdapter;
import com.axiang.smallyellowduck.app.GlobalContants;
import com.axiang.smallyellowduck.favorite.FavoriteActivity;
import com.axiang.smallyellowduck.homepage.douban.DoubanFragment;
import com.axiang.smallyellowduck.homepage.douban.DoubanPresenter;
import com.axiang.smallyellowduck.homepage.guokr.GuokrFramgent;
import com.axiang.smallyellowduck.homepage.guokr.GuokrPresenter;
import com.axiang.smallyellowduck.homepage.zhihu.ZhihuFragment;
import com.axiang.smallyellowduck.homepage.zhihu.ZhihuPresenter;
import com.axiang.smallyellowduck.set.SetPreferenceActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by a2389 on 2017/7/19.
 */

public class MainFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private Context context;

    private AppCompatActivity appCompatActivity;

    private MainAdapter adapter;

    private DrawerLayout drawerLayout;

    private NavigationView navigationView;

    private Toolbar toolbar;

    private FloatingActionButton fab;

    private DatePickerDialog pickerDialog;

    private ViewPager viewPager;

    private TabLayout tabLayout;

    private List<String> pagerList;

    private List<Fragment> viewList;

    private ZhihuFragment zhihuFragment;
    private ZhihuPresenter zhihuPresenter;

    private DoubanFragment doubanFragment;
    private DoubanPresenter doubanPresenter;

    private GuokrFramgent guokrFramgent;
    private GuokrPresenter guokrPresenter;

    public MainFragment() {

    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getActivity();
        this.appCompatActivity = (AppCompatActivity) getActivity();

        // 实例化Fragment
        if (savedInstanceState == null) {
            initFragments();
        } else {
            zhihuFragment = (ZhihuFragment) appCompatActivity.getSupportFragmentManager().getFragment(savedInstanceState, GlobalContants.ZHIHU_FRAGMENT);
            doubanFragment = (DoubanFragment) appCompatActivity.getSupportFragmentManager().getFragment(savedInstanceState, GlobalContants.DOUBAN_FRAGMENT);
            guokrFramgent = (GuokrFramgent) appCompatActivity.getSupportFragmentManager().getFragment(savedInstanceState, GlobalContants.GUOKR_FRAGMENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {

        // 初始化Drawable
        drawerLayout = (DrawerLayout) view.findViewById(R.id.layout_drawer);
        navigationView = (NavigationView) view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(false);
                drawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.nav_about:
                        context.startActivity(new Intent(context, AboutPreferenceActivity.class));
                        break;
                    case R.id.nav_collection:
                        context.startActivity(new Intent(context, FavoriteActivity.class));
                        break;
                    case R.id.nav_set:
                        context.startActivity(new Intent(context, SetPreferenceActivity.class));
                        break;
                }
                return true;
            }
        });

        // 初始化Toolbar
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        appCompatActivity.setSupportActionBar(toolbar);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        setHasOptionsMenu(true);

        // 初始化FloatingActionButton
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        Calendar now = Calendar.getInstance();
        pickerDialog = DatePickerDialog.newInstance(
                MainFragment.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerDialog.show(appCompatActivity.getFragmentManager(), "Datepickerdialog");
            }
        });

        // 初始化主体标题
        pagerList = new ArrayList<>();
        pagerList.add(getResources().getString(R.string.tab_zhihu));
        pagerList.add(getResources().getString(R.string.tab_douban));
        pagerList.add(getResources().getString(R.string.tab_guokr));
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        viewList = new ArrayList<>();

        // 初始化ZhihuPresenter并绑定到ZhihuFragment
        zhihuPresenter = new ZhihuPresenter(context, zhihuFragment);
        zhihuFragment.setPresenter(zhihuPresenter);
        viewList.add(zhihuFragment);

        // 初始化DoubanPresenter并绑定到DoubanFragment
        doubanPresenter = new DoubanPresenter(context, doubanFragment);
        doubanFragment.setPresenter(doubanPresenter);
        viewList.add(doubanFragment);

        // 初始化GuokrPresenter并绑定到GuokrFragment
        guokrPresenter = new GuokrPresenter(context, guokrFramgent);
        guokrFramgent.setPresenter(guokrPresenter);
        viewList.add(guokrFramgent);

        // 初始化主体布局内容
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        adapter = new MainAdapter(appCompatActivity.getSupportFragmentManager(), pagerList, viewList);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    fab.setVisibility(View.GONE);
                } else {
                    fab.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initFragments() {

        // 初始化ZhihuFragment
        zhihuFragment = ZhihuFragment.newInstance();

        // 初始化DoubanFragment
        doubanFragment = DoubanFragment.newInstance();

        // 初始化GuokrFragment
        guokrFramgent = GuokrFramgent.newInstance();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.framgent_main_options, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String dateStr = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (viewPager.getCurrentItem() == 0) {
            zhihuFragment.loadDataFromAssignDate(date.getTime());
        } else if (viewPager.getCurrentItem() == 1) {
            doubanFragment.loadDataFromAssignDate(date.getTime());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()) {
             case android.R.id.home:
                 drawerLayout.openDrawer(GravityCompat.START);
                 break;
            case R.id.collection:
                context.startActivity(new Intent(context, FavoriteActivity.class));
                break;
            case R.id.set:
                context.startActivity(new Intent(context, SetPreferenceActivity.class));
                break;
            case R.id.about:
                context.startActivity(new Intent(context, AboutPreferenceActivity.class));
                break;
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(zhihuFragment.isAdded()) {
            appCompatActivity.getSupportFragmentManager().putFragment(outState, GlobalContants.ZHIHU_FRAGMENT, zhihuFragment);
        }

        if(doubanFragment.isAdded()) {
            appCompatActivity.getSupportFragmentManager().putFragment(outState, GlobalContants.DOUBAN_FRAGMENT, doubanFragment);
        }

        if(guokrFramgent.isAdded()) {
            appCompatActivity.getSupportFragmentManager().putFragment(outState, GlobalContants.GUOKR_FRAGMENT, guokrFramgent);
        }
    }
}
