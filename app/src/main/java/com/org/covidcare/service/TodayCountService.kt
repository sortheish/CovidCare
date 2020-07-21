package com.org.covidcare.service

/**
 * Created by ishwari s on 7/17/2020.
 */
interface TodayCountService {
    interface TodayCountModel {
        fun getTodayConfirmedCount(): Int
        fun getTodayRecoveredCount(): Int
        fun getTodayDeceasedCount(): Int
    }

    interface TodayCountPresenter {
        fun getTodayConfirmedCount(): Int
        fun getTodayRecoveredCount(): Int
        fun getTodayDeceasedCount(): Int

    }
}