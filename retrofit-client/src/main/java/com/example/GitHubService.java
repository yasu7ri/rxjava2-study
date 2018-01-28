package com.example;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

/**
 * Created by yasu7ri on 2018/01/28.
 */
public interface GitHubService {
    @GET("users/{user}/repos")
    Observable<List<Repo>> ListRepos(@Path("user") String user);

    @GET("users/{user}")
    Observable<User> User(@Path("user") String user);
}
