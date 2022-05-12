/*
 * Copyright 2015 The AppAuth for Android Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * From: https://github.com/openid/AppAuth-Android
 * Notable changes:
 *  * Rewritten from Java to Kotlin.
 *  * Updated code comments.
 */

package com.schibsted.account.webflows.activities

import android.app.Activity
import android.os.Bundle

/**
 * Activity that receives the redirect Uri sent by the OpenID endpoint. It forwards the data
 * received as part of this redirect to {@link AuthorizationManagementActivity}, which
 * destroys the browser tab before returning the result to the completion
 * {@link android.app.PendingIntent}
 * provided to {@link AuthorizationManagementActivity}.
 *
 * App developers using this library must specify an intent filter in the
 * application manifest. For example, to handle
 * `https://www.example.com/oauth2redirect}`:
 *
 * ```xml
 * <intent-filter>
 *   <action android:name="android.intent.action.VIEW"/>
 *   <category android:name="android.intent.category.DEFAULT"/>
 *   <category android:name="android.intent.category.BROWSABLE"/>
 *   <data android:scheme="https"
 *         android:host="www.example.com"
 *         android:path="/oauth2redirect" />
 * </intent-filter>
 * ```
 */
class RedirectUriReceiverActivity : Activity() {
    override fun onCreate(savedInstanceBundle: Bundle?) {
        super.onCreate(savedInstanceBundle)

        // while this does not appear to be achieving much, handling the redirect in this way
        // ensures that we can remove the browser tab from the back stack. See the documentation
        // on AuthorizationManagementActivity for more details.
        startActivity(
            AuthorizationManagementActivity.createResponseHandlingIntent(this, intent.data)
        )
        finish()
    }
}
