package com.example.calendar

import android.app.Application
import com.kakao.sdk.auth.AuthApiClient.Companion.instance
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {



    override fun onCreate() {


        KakaoSdk.init(this, "7d93b360b9e054deeba80bd62b95ec25")

        super.onCreate()
    }


}