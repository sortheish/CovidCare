package com.org.covidcare.service

/**
 * Created by ishwari s on 6/22/2020.
 */
interface CountService {
    interface CountModel {
        fun getConfirmedCount(): Int
        fun getRecoveredCount(): Int
        fun getDeceasedCount(): Int
        fun getActiveCount(): Int
    }

    interface CountPresenter {
        fun getConfirmedCount(): Int
        fun getRecoveredCount(): Int
        fun getDeceasedCount(): Int
        fun getActiveCount(): Int
    }
}
