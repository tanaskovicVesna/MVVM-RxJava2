package com.helloworld.rxjava2.mvvm.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import com.helloworld.rxjava2.mvvm.App;
import com.helloworld.rxjava2.mvvm.R;
import com.helloworld.rxjava2.mvvm.adapter.LanguageSpinnerAdapter;
import com.helloworld.rxjava2.mvvm.datamodel.Language;
import com.helloworld.rxjava2.mvvm.viewmodel.MainViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;



public class MainActivity extends AppCompatActivity {
    @NonNull
    private CompositeDisposable mCompositeDisposable;

    @NonNull
    private MainViewModel mViewModel;

    @Nullable
    private TextView mGreetingView;

    @Nullable
    private Spinner mLanguagesSpinner;
    @Nullable
    private ImageView mImageView;

    @Nullable
    private LanguageSpinnerAdapter mLanguageSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewModel = getViewModel();
        setupViews();
    }

    private void setupViews() {
        mGreetingView = (TextView) findViewById(R.id.greeting);
        mImageView = (ImageView) findViewById(R.id.image);

        mLanguagesSpinner = (Spinner) findViewById(R.id.languages);
        assert mLanguagesSpinner != null;
        mLanguagesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, final View view,
                                       final int position, final long id) {
                itemSelected(position);
            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {
                //nothing to do here
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        bind();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unBind();
    }

    private void bind() {
        mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(mViewModel.getGreeting()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setGreeting));

        mCompositeDisposable.add(mViewModel.getSupportedLanguages()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setLanguages));

        mCompositeDisposable.add(mViewModel.getImage()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setImage));

    }

    private void unBind() {
        mCompositeDisposable.clear();
    }

    private void setGreeting(@NonNull final String greeting) {
        assert mGreetingView != null;

        mGreetingView.setText(greeting);
    }

    private void setLanguages(@NonNull final List<Language> languages) {
        assert mLanguagesSpinner != null;

        mLanguageSpinnerAdapter = new LanguageSpinnerAdapter(this,
                R.layout.language_item,
                languages);
        mLanguagesSpinner.setAdapter(mLanguageSpinnerAdapter);
    }
    private void setImage(@DrawableRes final int resId) {
        assert mImageView != null;

        mImageView.setImageResource(resId);
    }

    @NonNull
    private MainViewModel getViewModel() {
        return ((App) getApplication()).getViewModel();
    }

    private void itemSelected(final int position) {
        assert mLanguageSpinnerAdapter != null;

        Language languageSelected = mLanguageSpinnerAdapter.getItem(position);
        mViewModel.languageSelected(languageSelected);
    }
}
