package com.tony.stock.lib.calculate

import com.tictactec.ta.lib.MInteger

class Core {

    var unstablePeriod: IntArray

    var candleSettings: Array<CandleSetting?>

    var compatibility: Compatibility

    var TA_CandleDefaultSettings = arrayOf(
            CandleSetting(CandleSettingType.BodyLong,
                    RangeType.RealBody, 10, 1.0),
            CandleSetting(CandleSettingType.BodyVeryLong,
                    RangeType.RealBody, 10, 3.0),
            CandleSetting(CandleSettingType.BodyShort,
                    RangeType.RealBody, 10, 1.0),
            CandleSetting(CandleSettingType.BodyDoji,
                    RangeType.HighLow, 10, 0.1),
            CandleSetting(CandleSettingType.ShadowLong,
                    RangeType.RealBody, 0, 1.0),
            CandleSetting(CandleSettingType.ShadowVeryLong,
                    RangeType.RealBody, 0, 2.0),
            CandleSetting(CandleSettingType.ShadowShort,
                    RangeType.Shadows, 10, 1.0),
            CandleSetting(CandleSettingType.ShadowVeryShort,
                    RangeType.HighLow, 10, 0.1),
            CandleSetting(CandleSettingType.Near,
                    RangeType.HighLow, 5, 0.2),
            CandleSetting(CandleSettingType.Far,
                    RangeType.HighLow, 5, 0.6),
            CandleSetting(CandleSettingType.Equal,
                    RangeType.HighLow, 5, 0.05)
    )


    constructor() {
        unstablePeriod = intArrayOf(FuncUnstld.All.ordinal)
        compatibility = Compatibility.Default
        candleSettings = arrayOfNulls(CandleSettingType.AllCandleSettings.ordinal)

        for (i in candleSettings.indices) {
            candleSettings[i] = CandleSetting(TA_CandleDefaultSettings[i]);
        }
    }

    /**
     * 简单移动平均线
     * @param inReal 收盘价数组
     * @param optInTimePeriod 计算周期
     * @param outReal 计算结果
     * */
    fun sma(inReal: DoubleArray, optInTimePeriod: Int, outReal: DoubleArray): RetCode {
        if (optInTimePeriod == Int.MIN_VALUE) {
            return RetCode.BadParam
        }
        if (optInTimePeriod < 2 || optInTimePeriod > 100000) {
            return RetCode.BadParam
        }

        return TA_INT_SMA(inReal, optInTimePeriod, outReal)
    }

    /**
     * 平滑移动平均线
     * @param inReal 收盘价数组
     * @param optInTimePeriod 计算周期
     * @param outReal 计算结果
     * */
    fun ema(inReal: DoubleArray, optInTimePeriod: Int, outReal: DoubleArray): RetCode {
        if (optInTimePeriod < 2 || optInTimePeriod > 100000) {
            return RetCode.BadParam
        }
        return TA_INT_EMA(inReal, optInTimePeriod, 2.0 / (optInTimePeriod + 1.0), outReal)
    }


    fun emaLookback(optInTimePeriod: Int): Int {
        var optInTimePeriod = optInTimePeriod
        if (optInTimePeriod == Int.MIN_VALUE) {
            optInTimePeriod = 30
        } else if (optInTimePeriod < 2 || optInTimePeriod > 100000) {
            return -1
        }
        return optInTimePeriod - 1;
    }

    fun TA_INT_EMA(inReal: DoubleArray, optInTimePeriod: Int, optInK_1: Double, outReal: DoubleArray): RetCode {
        var tempReal: Double
        var prevMA: Double
        var i: Int
        var today: Int
        var outIdx: Int

        var endIdx = inReal.size - 1
        var lookbackTotal: Int = emaLookback(optInTimePeriod) //lookbacktotal 4
        var startIdx = lookbackTotal // startIdx = 4

        if (startIdx > endIdx) {
            return RetCode.Success
        }
        today = startIdx - lookbackTotal// today = 0
        i = optInTimePeriod// i = 5
        outIdx = 0
        tempReal = 0.0
        while (i-- > 0) {//将前五个值加起来
            tempReal += inReal[today++]
            outIdx++
        }
        prevMA = tempReal / optInTimePeriod
        while (today <= startIdx) {
            prevMA = (inReal[today++] - prevMA) * optInK_1 + prevMA
        }
        outReal[--outIdx] = prevMA

        while (today <= endIdx) {
            prevMA = (inReal[today++] - prevMA) * optInK_1 + prevMA
            outReal[++outIdx] = prevMA
        }
        return RetCode.Success
    }

    fun TA_INT_SMA(inReal: DoubleArray, optInTimePeriod: Int, outReal: DoubleArray): RetCode {
        var periodTotal: Double = 0.0
        var tempReal: Double
        var i: Int
        var outIdx: Int = 0
        i = 0
        if (optInTimePeriod > 1) {
            while (i < optInTimePeriod - 1) {
                periodTotal += inReal[i++]
                outIdx++
            }
        }
        do {
            periodTotal += inReal[i++]
            tempReal = periodTotal
            periodTotal -= inReal[i - optInTimePeriod]
            outReal[outIdx++] = tempReal / optInTimePeriod
        } while (i <= inReal.size - 1)
        return RetCode.Success
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            var core = Core()
            var taCore = com.tictactec.ta.lib.Core()
            var source = doubleArrayOf(41.7, 41.55, 45.8, 45.4, 47.0, 46.95, 45.9, 45.3, 8.0, 9.0)
            var result = DoubleArray(source.size)
            var resultEles = DoubleArray(source.size)
            taCore.ema(0, source.size - 1, source, 5, MInteger(), MInteger(), result)
            for (i in result) {
                println(i.toString())
            }

//            core.ema(source, 5, resultEles)
            core.sma(source, 9, resultEles)
            println("calculate finish")
            for (i in resultEles) {
                println(i.toString())
            }

            val arr = Array(3) { it }
        }

    }

}

