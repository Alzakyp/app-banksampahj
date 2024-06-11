package alzaky.putra.banksampah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ViewPager mSlideViewPager;
    LinearLayout mDotLayout;
    Button letsStartBtn;

    TextView[] dots;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        letsStartBtn = findViewById(R.id.btnLetStart);

        mSlideViewPager = findViewById(R.id.slideViewPager);
        mDotLayout = findViewById(R.id.indicator_layout);

        viewPagerAdapter = new ViewPagerAdapter(this);
        mSlideViewPager.setAdapter(viewPagerAdapter);

        setUpIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);

        letsStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });



        // Menyembunyikan tombol Let's Start dan Sign Up secara default
        letsStartBtn.setVisibility(View.GONE);
    }

    public void setUpIndicator(int position) {
        dots = new TextView[4];
        mDotLayout.removeAllViews();
        // looping gambar heading description
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.inactive, getApplicationContext().getTheme()));
            mDotLayout.addView(dots[i]);
        }
        dots[position].setTextColor(getResources().getColor(R.color.active, getApplicationContext().getTheme()));
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            setUpIndicator(position);
            if (position == viewPagerAdapter.getCount() - 1) {
                letsStartBtn.setVisibility(View.VISIBLE);
            } else {
                letsStartBtn.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    };
}
