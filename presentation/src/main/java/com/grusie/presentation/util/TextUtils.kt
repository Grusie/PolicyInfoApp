package com.grusie.presentation.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.http.HttpException
import android.net.http.NetworkException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.firebase.ui.auth.util.FirebaseAuthError
import com.google.firebase.auth.FirebaseAuthException
import com.grusie.presentation.R
import java.io.IOException
import java.util.concurrent.TimeoutException

class TextUtils {
    companion object {
        fun isEmpty(msg: String?): Boolean {
            if (msg.isNullOrEmpty()) return true
            else {
                val target = msg.trim()
                if (target.isEmpty() || target == "-" || target.lowercase() == "null")
                    return true
            }
            return false
        }

        fun replaceEmptyText(initMsg: String?, targetMsg: String): String {
            return if (isEmpty(initMsg)) targetMsg else initMsg!!
        }

        fun replacePolicyKindCode(context: Context, code: String?): String {
            return if (!isEmpty(code)) {
                when (code) {
                    Constant.POLICY_KIND_JOB -> context.getString(R.string.str_policy_kind_job)
                    Constant.POLICY_KIND_LIFE -> context.getString(R.string.str_policy_kind_life)
                    Constant.POLICY_KIND_EDU -> context.getString(R.string.str_policy_kind_edu)
                    Constant.POLICY_KIND_CULTURE -> context.getString(R.string.str_policy_kind_culture)
                    Constant.POLICY_KIND_PARTICIPATION -> context.getString(R.string.str_policy_kind_participation)
                    else -> context.getString(R.string.str_none)
                }
            } else context.getString(R.string.str_none)
        }

        @SuppressLint("RestrictedApi")
        @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
        fun getErrorMsg(context: Context, error: Exception): String {
            val errorMsg = context.getString(
                when (error) {
                    is FirebaseAuthException -> {
                        Log.e("confirm error: ", error.errorCode)
                        when (error.errorCode) {
                            FirebaseAuthError.ERROR_EMAIL_ALREADY_IN_USE.toString() -> {
                                R.string.str_error_already_use
                            }

                            FirebaseAuthError.ERROR_INVALID_EMAIL.toString() -> {
                                R.string.str_error_msg_format
                            }

                            FirebaseAuthError.ERROR_WRONG_PASSWORD.toString() -> {
                                R.string.str_error_msg_password
                            }

                            FirebaseAuthError.ERROR_INVALID_EMAIL.toString() -> {
                                R.string.str_error_msg_email
                            }

                            else -> R.string.str_error_unknown
                        }
                    }

                    is NullPointerException -> R.string.str_error_data_not_found
                    is NetworkException -> R.string.str_error_network
                    is HttpException -> R.string.str_error_server
                    is IOException -> R.string.str_error_io
                    is TimeoutException -> R.string.str_error_timeout
                    else -> R.string.str_error_unknown
                }
            )
            Log.e("confirm error : ", "${error.message}, $errorMsg")
            return errorMsg
        }

        fun getAlertMsg(context: Context, alertCode: Int): String {
            val alertMsg = context.getString(
                when (alertCode) {
                    Constant.ERROR_CODE_EMPTY -> {
                        R.string.str_error_msg_empty
                    }

                    Constant.ERROR_CODE_FORMAT -> {
                        R.string.str_error_msg_format
                    }

                    Constant.ERROR_EMAIL_UNVERIFIED -> {
                        R.string.str_error_msg_email_verified
                    }

                    else -> {
                        R.string.str_error_unknown
                    }
                }
            )
            Log.e("confirm alert : ", "$alertCode, $alertMsg")
            return alertMsg
        }
    }
}