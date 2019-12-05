package com.esimtek.gemaltolocation

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kaopiz.kprogresshud.KProgressHUD

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    lateinit var hudDialog: KProgressHUD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hudDialog = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true).setAnimationSpeed(2).setDimAmount(0.5f)
    }
}
