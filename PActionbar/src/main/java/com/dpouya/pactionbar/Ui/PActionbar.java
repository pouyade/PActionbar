package com.dpouya.pactionbar.Ui;

/**
 * Created by pouyadark on 2/20/19.
 */

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.dpouya.pactionbar.Model.PActionbarButton;
import com.dpouya.pactionbar.R;
import com.dpouya.pactionbar.Ui.Cell.PActionbarButtonListCell;
import com.dpouya.pactionbar.helper.AndroidUtilities;
import com.dpouya.pactionbar.helper.ColorUtilies;
import com.dpouya.pactionbar.helper.LayoutUtilities;
import com.dpouya.pactionbar.helper.LocaleUtilities;
import com.google.android.material.tabs.TabLayout;

import java.util.List;




/**
 * Created by pouyadark on 11/26/18.
 */

public class PActionbar extends FrameLayout {
    private static PActionbar instance;
    private Typeface typeFace;
    private int ForeGroundColor=0xffffffff;
    private int alphaForeGroundColor=0xaaffffff;
    private int SearchHintColor=0xbbffffff;
    private int SearchBackGround=0xbb6F1E51;

    private boolean searchmode=false;
    private String title=getContext().getResources().getString(R.string.app_name);
    private String subtitle;
    private TextView txtTitle;
    private TextView txtSubTitle;
    private EditText txtSearch;
    private ImageView imgIcon;
    private ImageView imgotherIcon;
    private Activity attachedActivity;
    private String searchhint = "Search ...";
    private ImageView imgGoSearchIcon;
    private View Extraview;

    public boolean showBackButton=false;
    public boolean showDrawerMenuicon=false;
    private OnClickListener onIconClick;

    private Drawable menu_drawable = getContext().getResources().getDrawable(R.drawable.ic_menu);
    private Drawable back_drawable = getContext().getResources().getDrawable(R.drawable.ic_ac_backright);
    private Drawable back_left_drawable = getContext().getResources().getDrawable(R.drawable.ic_ac_backleft);
    private Drawable search_drawable = getContext().getResources().getDrawable(R.drawable.ic_search);
    private Drawable close_drawble = getContext().getResources().getDrawable(R.drawable.ic_close);
    private int backGroundColor=0;
    public int mActionBarSize = 0;
    private int riplebackgroundeffectResourse;
    private int primaryColor;
    private TabLayout tabLayout;
    private PActionbarButtonListCell pActionbarButtonListCell;
    private boolean eshowicon;
    private boolean eshowothericon;
    private Drawable icondrawble;
    private Drawable iconotherdrawble;
    private FrameLayout customviewframe;
    public boolean drawline=false;
    private View line;
    private TextView txtCenterTitle;
    private OnClickListener onOtherIconClick;
    private int titleColor;
    private int subTitlecolor;
    private boolean isRTL = false;
    private View customview;

    public PActionbar( Context context) {
        super(context);
        this.attachedActivity= (Activity) context;
        this.typeFace = Typeface.DEFAULT;
        init();
    }
    public PActionbar( Context context, AttributeSet attrs) {
        super(context, attrs);
        this.typeFace = Typeface.DEFAULT;
        init();
    }
    public PActionbar( Context context, View Extraview) {
        super(context);
        this.Extraview=Extraview;
        this.typeFace = Typeface.DEFAULT;
        this.attachedActivity= (Activity) context;
        init();
    }

    public static Context getContextInstance() {
        return instance.getContext();
    }

    public void setForeGroundColor(int foreGroundColor) {
        ForeGroundColor = foreGroundColor;
        alphaForeGroundColor = AndroidUtilities.adjustAlpha(foreGroundColor,0.6f);

        tabLayout.setTabTextColors(alphaForeGroundColor,ForeGroundColor);
        tabLayout.setSelectedTabIndicatorColor(ForeGroundColor);
        imgGoSearchIcon.setImageDrawable(ColorUtilies.changeColor(search_drawable,ForeGroundColor));
        txtSearch.setTextColor(ForeGroundColor);
        if(eshowicon) {
            imgIcon.setImageDrawable(ColorUtilies.changeColor(icondrawble, ForeGroundColor));
        }
        if(eshowothericon){
            imgotherIcon.setImageDrawable(ColorUtilies.changeColor(iconotherdrawble, ForeGroundColor));
        }
    }

    public PActionbar( Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setSearchhint(String searchhint) {
        this.searchhint = searchhint;
        this.txtSearch.setHint(searchhint);
    }

    public void setTitle(String title) {
        this.title = title;
        txtTitle.setText(title);
        recreateLayout();
    }
    public void setSubTitle(String subtitle) {
        this.subtitle = subtitle;
        txtSubTitle.setText(subtitle);
        recreateLayout();
    }
    public void clearTabs(){
        tabLayout.removeAllTabs();
        tabLayout.setVisibility(GONE);

    }
    public void setupTabs(List<String> tabs, TabLayout.BaseOnTabSelectedListener tablistener){
        tabLayout.setVisibility(VISIBLE);
        tabLayout.removeAllTabs();
        for (int i = 0; i < tabs.size(); i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(tabs.get(i));
            tabLayout.addTab(tab);
        }
        changeTabsFont(tabLayout);
        recreateLayout();
        tabLayout.setOnTabSelectedListener(tablistener);
    }
    public void setupTabs(List<String> tabs){
        tabLayout.setVisibility(VISIBLE);
        tabLayout.removeAllTabs();
        for (int i = 0; i < tabs.size(); i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(tabs.get(i));
            tabLayout.addTab(tab);
        }
        changeTabsFont(tabLayout);
        recreateLayout();
    }
    public void setupTabs(ViewPager pager){
        tabLayout.setVisibility(VISIBLE);
        tabLayout.removeAllTabs();
        tabLayout.setupWithViewPager(pager);
        changeTabsFont(tabLayout);
        recreateLayout();
    }
    /** @deprecated Use {@link #setupTabs(List)} or {@link #setupTabs(ViewPager)} instead. */
    @Deprecated
    public void setupTabs(List<String> tabs, ViewPager pager){
        tabLayout.setVisibility(VISIBLE);
        tabLayout.removeAllTabs();
        if(pager==null) {
            for (int i = 0; i < tabs.size(); i++) {
                TabLayout.Tab tab = tabLayout.newTab();
                tab.setText(tabs.get(i));
                tabLayout.addTab(tab);
            }
        }else {
            tabLayout.setupWithViewPager(pager);
        }
        changeTabsFont(tabLayout);
        recreateLayout();
    }
    public void setCenteredText(String text){
        txtCenterTitle.setVisibility(VISIBLE);
        txtCenterTitle.setText(text);
        txtTitle.setVisibility(GONE);
        txtSubTitle.setVisibility(GONE);
    }
    public void setCenteredText(String text,Typeface font,int sizeSp){
        txtCenterTitle.setVisibility(VISIBLE);
        txtCenterTitle.setText(text);
        txtCenterTitle.setTypeface(font);
        txtCenterTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,sizeSp);
        txtTitle.setVisibility(GONE);
        txtSubTitle.setVisibility(GONE);
    }
    public void hideCenteredText(){
        txtCenterTitle.setVisibility(GONE);
        txtTitle.setVisibility(VISIBLE);
        txtSubTitle.setVisibility(VISIBLE);
    }
    public void showCustomView(View view){
        this.customview = view;
        customviewframe.removeAllViews();
        customviewframe.addView(view, LayoutUtilities.createFrame(LayoutUtilities.WRAP_CONTENT,LayoutUtilities.MATCH_PARENT,isRTL?Gravity.LEFT:Gravity.RIGHT));
        recreateLayout();

    }
    private void init() {
        instance = this;
        getDefaults();
        isRTL = LocaleUtilities.isRTL(getContext());

        imgIcon=new ImageView(getContext());
        imgIcon.setBackgroundResource(riplebackgroundeffectResourse);
        imgIcon.setScaleType(ImageView.ScaleType.FIT_XY);
        imgIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onIconClick!=null){
                    onIconClick.onClick(view);
                }
            }
        });

        imgotherIcon=new ImageView(getContext());
        imgotherIcon.setBackgroundResource(riplebackgroundeffectResourse);
        imgotherIcon.setScaleType(ImageView.ScaleType.FIT_XY);
        imgotherIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onOtherIconClick!=null){
                    onOtherIconClick.onClick(view);
                }
            }
        });

        txtTitle=new TextView(getContext());
        txtTitle.setTextColor(ForeGroundColor);
        txtTitle.setTypeface(this.typeFace);
        txtTitle.setGravity(Gravity.CENTER_VERTICAL|(isRTL?Gravity.RIGHT:Gravity.LEFT));
        txtTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        txtTitle.setPadding(AndroidUtilities.dp10,0,AndroidUtilities.dp10,0);
        txtTitle.setText(title);
        addView(txtTitle, LayoutUtilities.createFrame(LayoutUtilities.WRAP_CONTENT,LayoutUtilities.MATCH_PARENT,
                Gravity.CENTER_VERTICAL|(isRTL?Gravity.RIGHT:Gravity.LEFT),0,0,0,0));

        txtCenterTitle=new TextView(getContext());
        txtCenterTitle.setTextColor(ForeGroundColor);
        txtCenterTitle.setVisibility(GONE);
        txtCenterTitle.setPadding(0,AndroidUtilities.dp5,0,0);
        txtCenterTitle.setTypeface(this.typeFace);
        txtCenterTitle.setGravity(Gravity.CENTER_VERTICAL);
        txtCenterTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        addView(txtCenterTitle, LayoutUtilities.createFrame(LayoutUtilities.WRAP_CONTENT,LayoutUtilities.WRAP_CONTENT,
                Gravity.CENTER_VERTICAL|Gravity.CENTER,0,0,0,0));


        txtSubTitle=new TextView(getContext());
        txtSubTitle.setTextColor(alphaForeGroundColor);
        txtSubTitle.setTypeface(this.typeFace);
        txtSubTitle.setGravity(Gravity.CENTER_VERTICAL|(isRTL?Gravity.RIGHT:Gravity.LEFT));
        txtSubTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        txtSubTitle.setVisibility(GONE);
        txtSubTitle.setText(subtitle);
        txtSubTitle.setPadding(AndroidUtilities.dp10,0,AndroidUtilities.dp10,0);
        addView(txtSubTitle, LayoutUtilities.createFrame(LayoutUtilities.WRAP_CONTENT,LayoutUtilities.MATCH_PARENT,
                Gravity.CENTER_VERTICAL|(isRTL?Gravity.RIGHT:Gravity.LEFT),0,0,0,0));

        txtSearch=new EditText(getContext());

        txtSearch.setTextColor(ForeGroundColor);
        txtSearch.setTypeface(this.typeFace);
        txtSearch.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        txtSearch.setGravity(Gravity.CENTER_VERTICAL|(isRTL?Gravity.RIGHT:Gravity.LEFT));
        txtSearch.setHint(searchhint);
        txtSearch.setBackground(null);
        txtSearch.setHintTextColor(SearchHintColor);
        txtSearch.setVisibility(GONE);
        addView(txtSearch,LayoutUtilities.createFrame(LayoutUtilities.MATCH_PARENT,mActionBarSize,
                Gravity.CENTER_VERTICAL|(isRTL?Gravity.RIGHT:Gravity.LEFT),50,0,0,0));


        addView(imgIcon, LayoutUtilities.createFrame(mActionBarSize, mActionBarSize, Gravity.CENTER_VERTICAL | Gravity.RIGHT, 0, 0, 0, 0));
        addView(imgotherIcon, LayoutUtilities.createFrame(mActionBarSize, mActionBarSize, Gravity.CENTER_VERTICAL | Gravity.RIGHT, mActionBarSize+5, 0, mActionBarSize+5, 0));

        imgIcon.setPadding(AndroidUtilities.dp10,AndroidUtilities.dp10,AndroidUtilities.dp10,AndroidUtilities.dp10);
        imgotherIcon.setPadding(AndroidUtilities.dp10,AndroidUtilities.dp10,AndroidUtilities.dp10,AndroidUtilities.dp10);


        imgGoSearchIcon=new ImageView(getContext());
        imgGoSearchIcon.setVisibility(GONE);
        imgGoSearchIcon.setImageDrawable(ColorUtilies.changeColor(search_drawable,ForeGroundColor));
        addView(imgGoSearchIcon,LayoutUtilities.createFrame(30,mActionBarSize, Gravity.CENTER_VERTICAL|Gravity.LEFT,5,0,5,0));

        pActionbarButtonListCell=new PActionbarButtonListCell(getContext(),this.typeFace);
        addView(pActionbarButtonListCell,LayoutUtilities.createFrame(LayoutUtilities.WRAP_CONTENT,mActionBarSize, Gravity.TOP|Gravity.LEFT));

        tabLayout=new TabLayout(getContext());
        tabLayout.setVisibility(GONE);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setSelectedTabIndicatorColor(ForeGroundColor);
        tabLayout.setSelectedTabIndicatorHeight(AndroidUtilities.dp2);
        tabLayout.setTabTextColors(alphaForeGroundColor,ForeGroundColor);
        addView(tabLayout,LayoutUtilities.createFrame(LayoutUtilities.MATCH_PARENT,mActionBarSize,Gravity.TOP|Gravity.RIGHT));

        customviewframe=new FrameLayout(getContext());
        customviewframe.setVisibility(GONE);
        addView(customviewframe,LayoutUtilities.createFrame(LayoutUtilities.WRAP_CONTENT,mActionBarSize,Gravity.TOP|Gravity.LEFT));
        if(drawline) {
            line = new View(getContext());
            line.setBackgroundColor(0xbbcccccc);
            addView(line, LayoutUtilities.createFrame(LayoutUtilities.MATCH_PARENT, 1, Gravity.BOTTOM));
        }
        this.updateColors();

        setLayoutParams(LayoutUtilities.createFrame(LayoutUtilities.MATCH_PARENT,mActionBarSize));
    }
    public void reset(){
        if(searchmode)setSearchmode(false);
        pActionbarButtonListCell.ClearButtons();
        txtCenterTitle.setVisibility(GONE);
        customviewframe.setVisibility(GONE);
        customviewframe.removeAllViews();
        showotherIcon(false,null);
        clearTabs();
    }
    public void setSearchmode(boolean searchmode) {
        this.searchmode = searchmode;
        recreateLayout();
    }

    public void addButton(PActionbarButton actionbarButton){
        AndroidUtilities.ChangeGravitiy(pActionbarButtonListCell,isRTL?Gravity.LEFT:Gravity.RIGHT);
        pActionbarButtonListCell.addItem(actionbarButton);
    }

    public void setExtraView(View extraView) {
        this.Extraview = extraView;
        if(Extraview!=null){
//            Extraview.setBackgroundColor(BackGround);
            addView(Extraview,LayoutUtilities.createFrame(LayoutUtilities.MATCH_PARENT,LayoutUtilities.WRAP_CONTENT,Gravity.TOP,
                    0,mActionBarSize,0,0));
        }
    }

    public void showDrawerMenuicon(boolean show) {
        showDrawerMenuicon=show;
        showIcon(show,menu_drawable);
        recreateLayout();
    }

    private void showIcon(boolean show,Drawable drawable){
        eshowicon=show;
        icondrawble=drawable;
        if(eshowicon) {
            imgIcon.setImageDrawable(ColorUtilies.changeColor(icondrawble, titleColor));
        }
        recreateLayout();
    }
    private void recreateLayout(){
        boolean hasSubtitle= subtitle!=null&&subtitle.length()>0;
        AndroidUtilities.ChangeGravitiy(imgIcon,isRTL?Gravity.RIGHT:Gravity.LEFT);
        AndroidUtilities.ChangeGravitiy(txtTitle,(isRTL?Gravity.RIGHT:Gravity.LEFT)|Gravity.CENTER_VERTICAL);
        txtTitle.setVisibility(title.length()>0?VISIBLE:GONE);
        if(eshowothericon) {
            imgotherIcon.setImageDrawable(ColorUtilies.changeColor(iconotherdrawble, ForeGroundColor));
        }

        txtTitle.measure(0,0);
        int widthpx = txtTitle.getMeasuredWidth();
        int titlewidth=AndroidUtilities.pxtodp(getContext(),widthpx);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        int rightMargin = isRTL ? (showBackButton ? mActionBarSize + 5 : 0) : 0;
        int leftMargin = !isRTL ? (showBackButton ? mActionBarSize + 5 : 0) : 0;
        tabLayout.setLayoutParams(LayoutUtilities.createFrame(LayoutUtilities.MATCH_PARENT, mActionBarSize,
                Gravity.TOP | (isRTL ? Gravity.LEFT : Gravity.RIGHT), leftMargin, 0, rightMargin, 0));

//        if(isRTL) {
//            AndroidUtilities.ChangeMarginRight(txtSearch, eshowicon ? 50 : 0);
//            AndroidUtilities.ChangeMarginLeft(txtSearch, 40);
//            AndroidUtilities.ChangeMarginRight(txtSearch, 0);
//            AndroidUtilities.ChangeMarginRight(tabLayout, titlewidth + (eshowicon ? 50 : 0));
//        }else{
//            AndroidUtilities.ChangeMarginLeft(txtSearch, eshowicon ? 50 : 0);
//            AndroidUtilities.ChangeMarginLeft(txtSearch, 0);
//            AndroidUtilities.ChangeMarginRight(txtSearch, 40);
//            AndroidUtilities.ChangeMarginLeft(tabLayout, titlewidth + (eshowicon ? 50 : 0));
//        }
        AndroidUtilities.ChangeGravitiy(txtSubTitle,isRTL?Gravity.RIGHT:Gravity.LEFT);
        AndroidUtilities.ChangeGravitiy(txtSearch,isRTL?Gravity.RIGHT:Gravity.LEFT);
        txtSubTitle.setVisibility(hasSubtitle?VISIBLE:GONE);
        if(eshowicon) {
            txtTitle.setPadding(AndroidUtilities.dp10+(isRTL?0:AndroidUtilities.dp(40)), 0, AndroidUtilities.dp10+(isRTL?AndroidUtilities.dp(40):0), hasSubtitle?AndroidUtilities.dp15:0);
            txtSubTitle.setPadding(AndroidUtilities.dp10+(isRTL?0:AndroidUtilities.dp(40)), AndroidUtilities.dp25, AndroidUtilities.dp10+(isRTL?AndroidUtilities.dp(40):0), 0);
        }else{
            txtTitle.setPadding(AndroidUtilities.dp10, 0, AndroidUtilities.dp10, hasSubtitle?AndroidUtilities.dp15:0);
            txtSubTitle.setPadding(AndroidUtilities.dp10, AndroidUtilities.dp25, AndroidUtilities.dp10, 0);
        }
        imgotherIcon.setVisibility(eshowothericon?VISIBLE:GONE);
        customviewframe.setVisibility(customview!=null?VISIBLE:GONE);

        AndroidUtilities.ChangeGravitiy(customviewframe,isRTL?Gravity.LEFT:Gravity.RIGHT);
        AndroidUtilities.ChangeGravitiy(imgotherIcon,isRTL?Gravity.RIGHT:Gravity.LEFT);
        AndroidUtilities.ChangeGravitiy(txtSearch,isRTL?Gravity.RIGHT:Gravity.LEFT);
        AndroidUtilities.ChangeGravitiy(imgGoSearchIcon,isRTL?Gravity.LEFT:Gravity.RIGHT);

        txtSearch.setVisibility(searchmode?VISIBLE:GONE);
        txtTitle.setVisibility(searchmode?GONE:VISIBLE);
        txtSubTitle.setVisibility(searchmode?GONE:VISIBLE);
        imgGoSearchIcon.setVisibility(searchmode?VISIBLE:GONE);
    }
    public ImageView getImgIcon() {
        return imgIcon;
    }

    public void showotherIcon(boolean show, Drawable drawable){
        eshowothericon=show;
        iconotherdrawble=drawable;

        recreateLayout();

    }

    public void setOnIconClick(OnClickListener onIconClick) {
        this.onIconClick = onIconClick;
    }
    public void setOnOtherIconClick(OnClickListener onotherIconClick) {
        this.onOtherIconClick = onotherIconClick;
    }

    public void getDefaults() {
        //ripple effect background resource
        int[] attrs = new int[]{R.attr.selectableItemBackground};
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs);
        riplebackgroundeffectResourse = typedArray.getResourceId(0, 0);

        //get primary color
        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        primaryColor = typedValue.data;

        //action bar size

        mActionBarSize = 50;

    }

    public void showBackButton(boolean show) {
        showBackButton=show;
        if(isRTL){
            showIcon(show,back_drawable);
        }else{
            showIcon(show,back_left_drawable);

        }
    }

    public EditText getTxtSearch() {
        return txtSearch;
    }
    public ImageView getSearchIcon() {
        return imgGoSearchIcon;
    }

    public void setIsSearching(boolean isSearching) {
        imgGoSearchIcon.setImageDrawable(isSearching? ColorUtilies.changeColor(close_drawble,ForeGroundColor):
        ColorUtilies.changeColor(search_drawable,ForeGroundColor));
    }

    public void ClearButtons() {
        pActionbarButtonListCell.ClearButtons();
    }
    private void changeTabsFont(TabLayout tabLayout) {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(this.typeFace);
                }
            }
        }
    }

    public void setDrawerMenuRtl(boolean drawerMenuRtl) {
        this.isRTL = drawerMenuRtl;
        recreateLayout();
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        txtTitle.setTextColor(titleColor);
        txtCenterTitle.setTextColor(titleColor);

    }

    public void setTypeFace(Typeface typeFace) {
        this.typeFace = typeFace;
        invalidate();
    }

    public void setSubTitleColor(int subTitlecolor) {
        this.subTitlecolor = subTitlecolor;
        txtSubTitle.setTextColor(subTitlecolor);
    }

    public void updateColors() {
        if (backGroundColor != 0) {
            setBackgroundColor(backGroundColor);
        } else {
            setBackgroundColor(primaryColor);
        }
        TypedValue typedValue = new TypedValue();
        if (getContext().getTheme().resolveAttribute(android.R.attr.textColorPrimary, typedValue, true)) {
            int textColor = typedValue.data;
            setTitleColor(textColor);
            setForeGroundColor(textColor);
        }
    }

    public void hideTitle() {
        setTitle("");
        setSubTitle("");
    }
}

