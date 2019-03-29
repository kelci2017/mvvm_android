package com.kelci.familynote.view.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.R
import com.kelci.familynote.view.initial.LoginActivity
import com.kelci.familynote.view.initial.MainActivity

class BlankActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (FamilyNoteApplication.familyNoteApplication?.getKeyValue(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.sessionid)) != null) {
            val startAppIntent = Intent(applicationContext, MainActivity::class.java)
            startAppIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(startAppIntent)
            finish()
            return
        }

        val startAppIntent = Intent(applicationContext, LoginActivity::class.java)
        startAppIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(startAppIntent)
    }
}