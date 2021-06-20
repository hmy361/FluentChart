package com.tony.stock.lib.calculate

class CandleSetting  {

    var settingType : CandleSettingType

    var rangeType : RangeType

    var avgPeriod : Int

    var factor : Double

    constructor(pCandleSetting: CandleSetting) {
        settingType = pCandleSetting.settingType
        rangeType = pCandleSetting.rangeType
        avgPeriod = pCandleSetting.avgPeriod
        factor = pCandleSetting.factor
    }

    constructor(pSettingType: CandleSettingType, pRangeType: RangeType,
                pAvgPeriod: Int, pFactor: Double) {
        this.settingType = pSettingType
        this.rangeType = pRangeType
        this.avgPeriod = pAvgPeriod
        this.factor = pFactor
    }


    fun copyFrom(src: CandleSetting) {
        settingType = src.settingType
        rangeType = src.rangeType
        avgPeriod = src.avgPeriod
        factor = src.factor
    }
}