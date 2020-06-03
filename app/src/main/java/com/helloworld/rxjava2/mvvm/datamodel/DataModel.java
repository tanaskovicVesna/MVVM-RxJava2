package com.helloworld.rxjava2.mvvm.datamodel;



import androidx.annotation.NonNull;

import com.helloworld.rxjava2.mvvm.R;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;


public class DataModel implements IDataModel {
    @NonNull
    @Override
    public Observable<List<Language>> getSupportedLanguages() {
        return Observable.fromCallable(this::getLanguages);
    }

    @NonNull
    private List<Language> getLanguages() {
        return Arrays
                .asList(new Language("English", Language.LanguageCode.EN),
                        new Language("German", Language.LanguageCode.DE),
                        new Language("Serbian", Language.LanguageCode.SR));
    }

    @NonNull
    @Override
    public Observable<String> getGreetingByLanguageCode(@NonNull final Language.LanguageCode code) {
        switch (code) {
            case DE:
                return Observable.just("Hallo!");
            case EN:
                return Observable.just("Hello!");
            case SR:
                return Observable.just("Zdravo!");
            default:
                return Observable.empty();
        }
    }

    @NonNull
    @Override
    public Observable<Integer> getFlagImageByLanguageCode(@NonNull final Language.LanguageCode code) {
        switch (code) {
            case DE:
                return Observable.just(R.drawable.germanflag);
            case EN:
                return Observable.just(R.drawable.englandflag);
            case SR:
                return Observable.just(R.drawable.serbianflag);
            default:
                return Observable.empty();
        }
    }
}
