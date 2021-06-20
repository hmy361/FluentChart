package com.tony.stock.lib.calculate

enum class RetCode {
    Success,
    BadParam,
    OutOfRangeStartIndex,
    OutOfRangeEndIndex,
    AllocErr,
    InternalError
}