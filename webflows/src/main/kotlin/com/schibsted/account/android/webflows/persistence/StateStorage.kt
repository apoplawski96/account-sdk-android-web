package com.schibsted.account.android.webflows.persistence

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.schibsted.account.android.webflows.client.MfaType

internal class StateStorage(context: Context) {
    private val gson = Gson()

    private val prefs: SharedPreferences by lazy {
        context.applicationContext.getSharedPreferences(PREFERENCE_FILENAME, Context.MODE_PRIVATE)
    }

    fun <T> setValue(key: String, value: T) {
        val editor = prefs.edit()
        val json = gson.toJson(value)
        editor.putString(key, json)
        editor.apply()
    }

    inline fun <reified T> getValue(key: String): T? {
        val json = prefs.getString(key, null) ?: return null
        return gson.fromJson(json, T::class.java);
    }

    companion object {
        const val PREFERENCE_FILENAME = "SCHACC"
    }
}