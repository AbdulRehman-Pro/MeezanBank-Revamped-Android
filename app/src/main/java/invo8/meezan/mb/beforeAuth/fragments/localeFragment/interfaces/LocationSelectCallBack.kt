package invo8.meezan.mb.beforeAuth.fragments.localeFragment.interfaces

import invo8.meezan.mb.beforeAuth.fragments.localeFragment.modelClasses.CityLocations

interface LocationSelectCallBack {
    fun onItemClick(cityLocations: CityLocations)
}