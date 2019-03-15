package com.kelci.familynote.view.Initial

import android.os.Bundle
import com.kelci.familynote.R
import com.kelci.familynote.view.Base.RootActivity


class LoginActivity : RootActivity {

    constructor() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}