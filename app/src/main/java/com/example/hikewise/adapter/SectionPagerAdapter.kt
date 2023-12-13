package com.example.hikewise.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.hikewise.ui.bookingalat.listbooking.HistoryBookingAlatFragment
import com.example.hikewise.ui.fragment.HistoryPendakianFragment
import com.example.hikewise.ui.bookingopentrip.listbooking.HistoryPendakianOpenTripFragment

class SectionPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = HistoryPendakianFragment()
            1 -> fragment = HistoryPendakianOpenTripFragment()
            2 -> fragment = HistoryBookingAlatFragment()
        }
        return fragment as Fragment
    }
}