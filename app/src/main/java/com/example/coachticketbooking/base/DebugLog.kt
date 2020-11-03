package com.example.coachticketbooking.base

import android.util.Log


// This class is based on DebugLog on Fsoft GST Unit
object DebugLog {
    private var mClassName: String = ""
    private var mMethodName: String = ""
    private var mLineNumber: Int = 0

    private fun createLog(log: String): String {
        val buffer = StringBuffer()
        buffer.append("[")
            .append(mMethodName)
            .append(":")
            .append(mLineNumber)
            .append("]")
            .append(log)
        return buffer.toString()
    }

    private fun getMethodName(elements: Array<StackTraceElement>) {
        mClassName = elements[1].fileName
        mMethodName = elements[1].methodName
        mLineNumber = elements[1].lineNumber
    }

    fun e(message: String) {
        getMethodName(Throwable().stackTrace)
        Log.e(mClassName, createLog(message))
    }

    fun i(message: String) {
        getMethodName(Throwable().stackTrace)
        Log.i(mClassName, createLog(message))
    }

    fun w(message: String) {
        getMethodName(Throwable().stackTrace)
        Log.w(mClassName, createLog(message))
    }

    fun d(obj: Any) {
        getMethodName(Throwable().stackTrace)
        Log.d(mClassName, createLog(obj.toString()))
    }

}