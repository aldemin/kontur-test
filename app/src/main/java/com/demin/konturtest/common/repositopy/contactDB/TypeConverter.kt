package com.demin.konturtest.common.repositopy.contactDB

import androidx.room.TypeConverter
import com.demin.konturtest.common.entity.EducationPeriod

class TypeConverter {

    @TypeConverter
    fun toEducationPeriod(period: String): EducationPeriod {
        val periodList = period.split(EDUCATION_PERIOD_DELIMITER)
        return EducationPeriod(periodList[0], periodList[1])
    }

    @TypeConverter
    fun fromEducationPeriod(period: EducationPeriod) =
        "${period.start}$EDUCATION_PERIOD_DELIMITER${period.end}"

    companion object {

        private const val EDUCATION_PERIOD_DELIMITER = "%@#"

    }

}