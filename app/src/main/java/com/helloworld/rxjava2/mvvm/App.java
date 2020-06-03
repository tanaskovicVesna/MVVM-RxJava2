package com.helloworld.rxjava2.mvvm;

import android.app.Application;

import androidx.annotation.NonNull;

import com.helloworld.rxjava2.mvvm.datamodel.DataModel;
import com.helloworld.rxjava2.mvvm.datamodel.IDataModel;
import com.helloworld.rxjava2.mvvm.schedulers.ISchedulerProvider;
import com.helloworld.rxjava2.mvvm.schedulers.SchedulerProvider;
import com.helloworld.rxjava2.mvvm.viewmodel.MainViewModel;

public class App extends Application {


    @NonNull
    private final IDataModel mDataModel;

    public App() {
        mDataModel = new DataModel();
    }

    @NonNull
    public IDataModel getDataModel() {
        return mDataModel;
    }

    @NonNull
    public ISchedulerProvider getSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }

    @NonNull
    public MainViewModel getViewModel() {
        return new MainViewModel(getDataModel(), getSchedulerProvider());
    }
}
