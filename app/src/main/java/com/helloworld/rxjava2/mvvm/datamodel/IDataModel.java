package com.helloworld.rxjava2.mvvm.datamodel;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;


public interface IDataModel {
    @NonNull
    Observable<List<Language>> getSupportedLanguages();

    @NonNull
    Observable<String> getGreetingByLanguageCode(@NonNull final Language.LanguageCode code);

    @NonNull
    Observable<Integer> getFlagImageByLanguageCode(@NonNull final Language.LanguageCode code);
}
